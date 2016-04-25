package com.van.halley.bpm.listener;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.service.BasisSubstanceService;

public class BpmTransitionTakeListner implements ExecutionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BpmTransitionTakeListner.class);
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		String eventName = execution.getEventName();
        if ("take".equals(eventName)) {
        	logger.info("eventName {}, CurrentActivityId {}", eventName, execution.getCurrentActivityId());
            try {
            	String activityId = execution.getCurrentActivityId();
        		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
        				.getDeployedProcessDefinition(execution.getProcessDefinitionId());
        		ActivityImpl activity = processDefinition.findActivity(activityId);
        		if(activity.getProperty("type").equals("inclusiveGateway")){
        			DbSqlSession dbSqlSession = Context.getCommandContext().getDbSqlSession();
        			List<HistoricActivityInstanceEntity> cachedHistoricActivityInstances = dbSqlSession.findInCache(HistoricActivityInstanceEntity.class);
        		    for (HistoricActivityInstanceEntity cachedHistoricActivityInstance: cachedHistoricActivityInstances) {
        		    	if (execution.getProcessInstanceId().equals(cachedHistoricActivityInstance.getProcessInstanceId())
        		           && activityId != null
        		           && (activityId.equals(cachedHistoricActivityInstance.getActivityId()))
        		           && (cachedHistoricActivityInstance.getEndTime()==null)) {
        		    	  
        		    	 cachedHistoricActivityInstance.markEnded(null);
        		    	 
        		      }
        		    }
        		}
        		//网关结束之后直接连接结束节点, 此时暂将业务状态修改为“已归档”
        		//FIXME
        		if(activity.getProperty("type").equals("inclusiveGateway") 
        				|| activity.getProperty("type").equals("parallelGateway")
        				|| activity.getProperty("type").equals("exclusiveGateway")){
        			List<PvmTransition> pvmTransition = activity.getOutgoingTransitions();
        			if(pvmTransition.size() == 1){
        				if(pvmTransition.get(0).getDestination().getProperty("type").equals("endEvent")){
        					String businessKey = execution.getProcessBusinessKey();
        					BasisSubstance basisSubstance = basisSubstanceService.getById(businessKey);
        					basisSubstance.setStatus("已归档");
        					basisSubstanceService.modify(basisSubstance);
        				}
        			}
        			
        		}
        		
        		/**
        		 * 
        		 Context
				      .getCommandContext().getHistoryManager().recordActivityEnd((ExecutionEntity) execution);
        			
        			DefaultHistoryManager historyManager = (DefaultHistoryManager) Context.getCommandContext().getHistoryManager();
        		if(activity.getProperty("type").equals("inclusiveGateway")){
        			List<HistoricActivityInstance> historicActivities = historyService.
	    					createHistoricActivityInstanceQuery().activityId(activityId).
	    					processInstanceId(execution.getProcessInstanceId()).list();
	    			for(HistoricActivityInstance historicActivity : historicActivities){
	    				if(historicActivity.getEndTime() == null){
	    					jdbcTemplate.update("UPDATE ACT_HI_ACTINST SET END_TIME_=? WHERE ID_=?", new Date(), historicActivity.getId());
	    				}
	    			}
        		}else if(activity.getProperty("type").equals("userTask")){
        			List<PvmTransition> transitions = activity.getIncomingTransitions();
        			for(PvmTransition transition : transitions){
        				PvmActivity sourceRef = transition.getSource();
        				if(sourceRef.getProperty("type").equals("inclusiveGateway")){
        					List<HistoricActivityInstance> historicActivities = historyService.
        	    					createHistoricActivityInstanceQuery().activityId(sourceRef.getId()).
        	    					processInstanceId(execution.getProcessInstanceId()).list();
        	    			for(HistoricActivityInstance historicActivity : historicActivities){
        	    				if(historicActivity.getEndTime() == null){
        	    					jdbcTemplate.update("UPDATE ACT_HI_ACTINST SET END_TIME_=? WHERE ID_=?", new Date(), historicActivity.getId());
        	    				}
        	    			}
        				}
        			}
        			
        			transitions = activity.getOutgoingTransitions();
        			for(PvmTransition transition : transitions){
        				PvmActivity targetRef = transition.getDestination();
        				if(targetRef.getProperty("type").equals("inclusiveGateway")){
        					
        					Context
        				      .getCommandContext().getHistoryManager().recordActivityEnd((ExecutionEntity) execution);
        					
        					List<HistoricActivityInstance> historicActivities = historyService.
        	    					createHistoricActivityInstanceQuery().activityId(targetRef.getId()).
        	    					processInstanceId(execution.getProcessInstanceId()).list();
        	    			for(HistoricActivityInstance historicActivity : historicActivities){
        	    				if(historicActivity.getEndTime() == null){
        	    					jdbcTemplate.update("UPDATE ACT_HI_ACTINST SET END_TIME_=? WHERE ID_=?", new Date(), historicActivity.getId());
        	    				}
        	    			}
        				}
        			}
        		}**/
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
	}

}

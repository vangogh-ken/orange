package com.van.halley.bpm.listener;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.util.StringUtil;
import com.van.service.BasisSubstanceService;
import com.van.service.BasisValueService;
import com.van.service.BpmConfigNodeService;

public class BpmTaskCompleteListner implements TaskListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BpmTaskCompleteListner.class);
	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private BasisValueService basisValueService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void notify(DelegateTask delegateTask) {
		String event = delegateTask.getEventName();
		if(event.equals("complete")){
			String processInstanceId = delegateTask.getProcessInstanceId();
			String processDefinitionId = delegateTask.getProcessDefinitionId();
			String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
			//String processDefinitionKey = repositoryService.
					//createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult().getKey();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if(pi != null){
				BpmConfigNode bpmConfigNode = bpmConfigNodeService.getByPdIdAndTdKey(processDefinitionId, taskDefinitionKey);
				String businessKey = pi.getBusinessKey();
				BasisSubstance basisSubstance = basisSubstanceService.getById(businessKey);
				basisSubstance.setStatus(bpmConfigNode.getTargetStatus());
				basisSubstanceService.modify(basisSubstance);
				
				//执行完成任务时sql
				String onComplete = bpmConfigNode.getOnComplete();
				if(!StringUtil.isNullOrEmpty(onComplete)){
					for(String sql : onComplete.split(";")){
						if(sql.contains(":")){
							//1.空格截取 2.冒号截取
							String[] ts = sql.split("\\s+");
							List<String> params = new ArrayList<String>();
							for(int i=0, len = ts.length; i<len; i++){
								if(ts[i].contains(":")){
									String param = ts[i].split(":")[1];
									params.add(param);
								}
							}
							
							List<Object> values = new ArrayList<Object>();
							for(int i=0, len = params.size(); i<len; i++){
								sql = sql.replace(":" + params.get(i), "?");
								values.add(basisValueService.getSingleValue(businessKey, params.get(i), false).getValue());
							}
							logger.info("Executing sql:{} params:{}", sql, values.toArray());
							jdbcTemplate.update(sql, values.toArray());
						}else{
							logger.info("Executing sql:{}", sql);
							jdbcTemplate.update(sql);
						}
					}
				}
			}
			
			//完成之前需要将throughUserTasks,需要重新设值,避免数据重叠,导致流程走向混乱
			List<String> throughUserTasks = new ArrayList<String>();
			String[] activityIds = (String[]) delegateTask.getVariable("activityIds");
			if(activityIds != null){
				for(String activityId : activityIds){
					throughUserTasks.add(activityId);
				}
			}
			
			delegateTask.setVariable("throughUserTasks", throughUserTasks);
		}
	}
}

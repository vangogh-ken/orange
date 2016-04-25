package com.van.halley.bpm.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.bpm.support.AssigneeHelper;
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.util.StringUtil;
import com.van.service.BasisSubstanceService;
import com.van.service.BasisValueService;
import com.van.service.BpmConfigNodeService;

public class BpmTaskCreateListner implements TaskListener{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BpmTaskCreateListner.class);
	//private Map<RuleMatcher, AssigneeRule> assigneeRuleMap = new HashMap<RuleMatcher, AssigneeRule>();
	@Autowired
	private AssigneeHelper assigneeHelper;
	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private BasisSubstanceService basisSubstanceService;
	@Autowired
	private BasisValueService basisValueService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public BpmTaskCreateListner(){
		//常用语, 岗位规则
		/*ConstantAssigneeRule constantAssigneeRule = new ConstantAssigneeRule();
        PositionAssigneeRule positionAssigneeRule = new PositionAssigneeRule();
        assigneeRuleMap.put(new RuleMatcher("常用语"), constantAssigneeRule);
        assigneeRuleMap.put(new RuleMatcher("岗位"), positionAssigneeRule);*/
	}

	@Override
	public void notify(DelegateTask delegateTask) {
		String event = delegateTask.getEventName();
		if(event.equals("create")){
			logger.info("eventName: {}, taskName: {}", event, delegateTask.getName());
			assigneeHelper.assignee(delegateTask);
			
			
			//任务超时时间
			//if(!StringUtil.isNullOrEmpty(bpmConfigNode.getDueDate())){
			//}
			if(delegateTask.getDueDate() == null){
				delegateTask.setDueDate(new Date());
			}
			//根据配置修改业务数据的状态;启动流程时，执行startEnvent，流程实例为空
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().
					processInstanceId(delegateTask.getProcessInstanceId()).singleResult();
			if(pi != null){
				BpmConfigNode bpmConfigNode = bpmConfigNodeService.getByPdIdAndTdKey(
						delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey());
				String businessKey = pi.getBusinessKey();
				BasisSubstance basisSubstance = basisSubstanceService.getById(businessKey);
				basisSubstance.setStatus(bpmConfigNode.getSourceStatus());
				basisSubstanceService.modify(basisSubstance);
				//执行创建任务时sql
				String createBehaviors = bpmConfigNode.getOnCreate();
				if(!StringUtil.isNullOrEmpty(createBehaviors)){
					for(String sql : createBehaviors.split(";")){
						//FIXME：暂且只做一个:的SQL, 约定:XXX后面必须有空格
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
							
							jdbcTemplate.update(sql, values.toArray());
						}else{
							jdbcTemplate.update(sql);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 如果直接委托,则无论怎样都只取第一个用户
	 *//*
	public void setAssigneeAlias(String configAssignee, DelegateTask delegateTask){
		for (Map.Entry<RuleMatcher, AssigneeRule> entry : assigneeRuleMap.entrySet()) {
            RuleMatcher ruleMatcher = entry.getKey();

            if (!ruleMatcher.matches(configAssignee)){
                continue;
            }

            String value = ruleMatcher.getValue(configAssignee);
            AssigneeRule assigneeRule = entry.getValue();
            logger.debug("value : {}", value);
            logger.debug("assigneeRule : {}", assigneeRule);

            if (assigneeRule instanceof ConstantAssigneeRule) {
            	List<String> userIds = assigneeRule.process(value, getInitiator(delegateTask));
                delegateTask.setAssignee(userIds.get(0));
            } else if (assigneeRule instanceof PositionAssigneeRule) {
            	List<String> userIds = assigneeRule.process(value, getInitiator(delegateTask));
                delegateTask.setAssignee(userIds.get(0));
            }
		}
	}
	
	*//**
	 * 设置候选人,直接addCandidateUsers
	 *//*
	public void setCandidateAlias(String configCandidate, DelegateTask delegateTask){
		for (Map.Entry<RuleMatcher, AssigneeRule> entry : assigneeRuleMap.entrySet()) {
            RuleMatcher ruleMatcher = entry.getKey();

            if (!ruleMatcher.matches(configCandidate)){
                continue;
            }

            String value = ruleMatcher.getValue(configCandidate);
            AssigneeRule assigneeRule = entry.getValue();
            logger.debug("value : {}", value);
            logger.debug("assigneeRule : {}", assigneeRule);

            if (assigneeRule instanceof ConstantAssigneeRule) {
                List<String> userIds = assigneeRule.process(value, getInitiator(delegateTask));
                delegateTask.addCandidateUsers(userIds);
            } else if (assigneeRule instanceof PositionAssigneeRule) {
            	List<String> userIds = assigneeRule.process(value, getInitiator(delegateTask));
            	delegateTask.addCandidateUsers(userIds);
            }
		}
	}
	
	//获取流程发起人
	public String getInitiator(DelegateTask delegateTask){
		//首先直接获取参数
		Object initiator = delegateTask.getVariables().get("initiator");
		if(initiator != null){
			logger.debug("initiator 已找到 : {}", initiator);
			return initiator.toString();
		}else{
			logger.debug("initiator 没有找到 , 重新从系统中获取");
			List<IdentityLink> identityLinks = runtimeService.
					getIdentityLinksForProcessInstance(delegateTask.getProcessInstanceId());
			for(IdentityLink idLink: identityLinks){
				if(idLink.getType().equals("starter")){
					delegateTask.setVariable("initiator", idLink.getUserId());
					return idLink.getUserId();
				}
			}
		}
		return null;
	}*/
}

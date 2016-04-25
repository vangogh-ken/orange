package com.van.halley.bpm.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.bpm.service.TaskOperateService;
import com.van.halley.bpm.support.BpmGraphDetailsUtils;
import com.van.halley.db.persistence.entity.User;
import com.van.service.UserService;

@Transactional
@Service("taskOperateService")
public class TaskOperateServiceImpl implements TaskOperateService {
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private UserService userService;

	@Override
	public String getBusinessKey(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String businessKey = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult()
				.getBusinessKey();
		return businessKey;
	}

	public Map<String, Object> getVariables(HttpServletRequest request) {
		Enumeration<String> keys = request.getParameterNames();
		Map<String, Object> variables = new HashMap<String, Object>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.endsWith("_variable")) {
				variables.put(key, request.getParameter(key));
			}
		}
		
		String[] activityIds = request.getParameterValues("activityId");
		variables.put("activityIds", activityIds);
		
		return variables;
	}

	@Override
	public void claimTask(String taskId, String userId) {
		taskService.claim(taskId, userId);
	}

	@Override
	public boolean completeTask(String taskId, HttpServletRequest request) {
		Map<String, Object> variables = getVariables(request);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		DelegationState delegationState = task.getDelegationState();
		//说明是协办的工作(协办人员处理完成之后还会把任务交还给原来的assignee)
		if(delegationState != null && delegationState.equals(DelegationState.PENDING)){
			taskService.resolveTask(taskId, variables);
		}else{
			taskService.complete(taskId, variables);
		}
		
		return true;
	}

	@Override
	public Map<String, String> getNextActivity(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String taskDefinitionKey =  task.getTaskDefinitionKey();
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		Map<String, String> nextActivityDetails = new HashMap<String, String>();
		ActivityImpl activity = processDefinition.findActivity(taskDefinitionKey);
		//用户任务出去的迁移线只可能一条,若有多条必然是使用网关。
		List<PvmTransition> transtions = activity.getOutgoingTransitions();
		for(PvmTransition transtion : transtions){
			ActivityImpl nextActivity = processDefinition.findActivity(transtion.getDestination().getId());
			String type = (String) nextActivity.getProperty("type");
			nextActivityDetails.put("type", type);
			
			String transtionName = (String) transtion.getProperty("name");//获取迁移线标签名称
			//默认为网关之后连接的必然是任务,任务之后连接的必然是任务或者是网关
			if(type.equals("userTask")){
				nextActivityDetails.put(nextActivity.getId(), BpmGraphDetailsUtils.getUserTaskInfo(nextActivity, processDefinition, transtionName));
			}else if(type.equals("endEvent")){
				nextActivityDetails.put(nextActivity.getId(), BpmGraphDetailsUtils.getEndEventInfo(transtionName));
			}else if(type.contains("Gateway")){
				//网关的信息也需要保存
				nextActivityDetails.put(nextActivity.getId(), BpmGraphDetailsUtils.getGetWayInfo(nextActivity));
				
				List<PvmTransition> transtionsOfGateway = nextActivity.getOutgoingTransitions();
				if(transtionsOfGateway.size() == 1){
					nextActivityDetails.put("type", "userTask");
				}
				for(PvmTransition tstion : transtionsOfGateway){
					String transitionName = (String) tstion.getProperty("name");//获取迁移线标签名称
					ActivityImpl activityAfterGateway = processDefinition.findActivity(tstion.getDestination().getId());
					if(activityAfterGateway.getProperty("type").equals("userTask")){
						if(canTransfer(activityAfterGateway, transitionName, taskId)){
							nextActivityDetails.put(activityAfterGateway.getId(), BpmGraphDetailsUtils.getUserTaskInfo(activityAfterGateway, processDefinition, transitionName));
						}
					}else if(activityAfterGateway.getProperty("type").equals("endEvent")){
						nextActivityDetails.put(activityAfterGateway.getId(), BpmGraphDetailsUtils.getEndEventInfo(transitionName));
					}else{
						throw new NullPointerException("Wrong Process Definition, Please check the xml of BPM");
					}
				}
			}
		}
		
		return nextActivityDetails;
	}

	@Override
	public boolean canTransfer(ActivityImpl activityAfterGateway, String transitionName,  String taskId) {
		String getwayType = activityAfterGateway.getIncomingTransitions().get(0).getSource().getProperty("type").toString();
		if(getwayType.equals("parallelGateway") || getwayType.equals("inclusiveGateway")){
			return true;
		}else{
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			String userId = (String)runtimeService.getVariables(task.getExecutionId()).get("initiator");//暂时获取的是姓名
			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(task.getProcessDefinitionId());
			User user = userService.getById(userId);
			if("merit".equals(processDefinition.getKey()) || "merit-season".equals(processDefinition.getKey())){//
				if("一级考核".equals(task.getName())) {//确定会分支的节点
					if(user.getPosition() != null && user.getPosition().getGrade() == 5){//副总级
						if("副总级".equals(transitionName)){
							return true;
						}else{
							return false;
						}
					}else{
						if("副总级".equals(transitionName)){
							return false;
						}else{
							return true;
						}
					}
				}else{
					return true;
				}
			}if("vacation".equals(processDefinition.getKey())){//
				if("上级审批".equals(task.getName())) {//确定会分支的节点
					if(user.getPosition() != null && user.getPosition().getGrade() == 5){//副总级
						if("副总级".equals(transitionName)){
							return true;
						}else{
							return false;
						}
					}else{
						if("副总级".equals(transitionName)){
							return false;
						}else{
							return true;
						}
					}
				}else{
					return true;
				}
			}else{//其他流程，在else中添加
				return true;
			}
		}
		
	}

}

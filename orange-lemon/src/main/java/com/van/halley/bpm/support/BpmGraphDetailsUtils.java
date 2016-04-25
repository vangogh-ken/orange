package com.van.halley.bpm.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.BpmConfigNodeService;
import com.van.service.UserService;

public class BpmGraphDetailsUtils {
	private static UserService userService;
	private static HistoryService historyService;
	private static BpmConfigNodeService bpmConfigNodeService;
	
	static{
		userService = ApplicationContextHelper.getBean(UserService.class);
		historyService = ApplicationContextHelper.getBean(HistoryService.class);
		bpmConfigNodeService = ApplicationContextHelper.getBean(BpmConfigNodeService.class);
	}
	
	/** 
	 * 获取流程定义的所有信息,返回的Map key为AcitivtyId, 后续可用活动信息更多的时候可以直接添加
	 * @param processDefinition
	 * @return
	 */
	public static Map<String, String> getProcessDefinitionInfo(ProcessDefinitionEntity processDefinition){
		Map<String, String> map = new HashMap<String, String>();
		List<ActivityImpl> pvmAcitivities = processDefinition.getActivities();
		for(ActivityImpl acitivity : pvmAcitivities){
			String type = (String) acitivity.getProperty("type");
			if(type.equals("userTask")){
				map.put(acitivity.getId(), getUserTaskInfo(acitivity, processDefinition, null));
			}else if(type.equals("startEvent")){
				map.put(acitivity.getId(), getStartEventInfo());
			}else if(type.equals("endEvent")){
				map.put(acitivity.getId(), getEndEventInfo(null));
			}else if(type.contains("Gateway")){
				map.put(acitivity.getId(), getGetWayInfo(acitivity));
			}
		}
		
		return map;
	}
	
	//获取用户任务信息
	public static String getUserTaskInfo(ActivityImpl acitivity, ProcessDefinitionEntity processDefinition, String transitionName){
		Map<String, TaskDefinition> taskDefinitions = processDefinition.getTaskDefinitions();
		
		TaskDefinition taskDefinition = taskDefinitions.get(acitivity.getId());
		Expression nameExpression = taskDefinition.getNameExpression();
		String name = nameExpression.getExpressionText();
		
		Expression assigneeExpression = taskDefinition.getAssigneeExpression();
		String assignee = assigneeExpression == null ? "" : assigneeExpression.getExpressionText();
		
		Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
		String candidateGroupIds = "";
		if(candidateGroupIdExpressions == null || candidateGroupIdExpressions.isEmpty()){
			candidateGroupIds = "";
		}else{
			for(Expression exp : candidateGroupIdExpressions){
				candidateGroupIds += exp.getExpressionText() + ",";
			}
			candidateGroupIds.substring(0, candidateGroupIds.lastIndexOf(","));
		}
		
		if(StringUtil.isNullOrEmpty(assignee) && StringUtil.isNullOrEmpty(candidateGroupIds)){
			BpmConfigNode bpmConfigNode = bpmConfigNodeService.
					getByPdIdAndTdKey(processDefinition.getId(), taskDefinition.getKey());
			List<String> candidates = bpmConfigNodeService.getAuthNameByBpmConfigNodeId(bpmConfigNode.getId());
			if(candidates != null && !candidates.isEmpty()){
				if(candidates.size() == 1){
					assignee = candidates.get(0);
				}else{
					for(String candidate : candidates){
						candidateGroupIds += candidate + ",";
					}
					
					candidateGroupIds = candidateGroupIds.substring(0, candidateGroupIds.length() - 1);
				}
			}
		}
		
		StringBuilder str = new StringBuilder();
		str.append("任务名称:");
		str.append("<!!>");
		
		if(!StringUtil.isNullOrEmpty(transitionName)){
			str.append(name + "_" + transitionName);
		}else{
			str.append(name);
		}
		
		str.append("<!!>");
		if(StringUtil.isNullOrEmpty(assignee) && StringUtil.isNullOrEmpty(candidateGroupIds)){
			str.append("候选人:");
			str.append("<!!>");
			str.append("无");
		}else if(!StringUtil.isNullOrEmpty(assignee)){
			str.append("委托人:");
			str.append("<!!>");
			str.append(assignee);
		}else if(!StringUtil.isNullOrEmpty(candidateGroupIds)){
			str.append("候选人:");
			str.append("<!!>");
			str.append(candidateGroupIds);
		}
		return str.toString();
	}
	
	//获取启动事件信息
	public static String getStartEventInfo(){
		StringBuilder str = new StringBuilder();
		str.append("节点名称:");
		str.append("<!!>");
		str.append("启动流程");

		return str.toString();
	}
	
	//获取结束事件信息
	public static String getEndEventInfo(String transitionName){
		StringBuilder str = new StringBuilder();
		str.append("节点名称:");
		str.append("<!!>");
		
		if(!StringUtil.isNullOrEmpty(transitionName)){
			str.append("结束流程" + "_" + transitionName);
		}else{
			str.append("结束流程");
		}
		return str.toString();
	}
	
	//获取网关信息
	public static String getGetWayInfo(ActivityImpl acitivity){
		String type = (String) acitivity.getProperty("type");
		
		if(type.equals("parallelGateway")){
			return "并行网关<!!> ";
		}else if(type.equals("inclusiveGateway")){
			return "包含网关<!!> ";
		}else if(type.equals("exclusiveGateway")){
			return "排他网关<!!> ";
		}else if(type.equals("eventGateway")){
			return "网关<!!> ";
		}else{
			throw new NullPointerException("Invalide Gateway, Please check the process.");
		}
	}
	//获取坐标信息
	public static Map<String, ActivityPosition> getActivityPositions(ProcessDefinitionEntity processDefinition, int minX, int minY){
		Map<String, ActivityPosition> map = new HashMap<String, ActivityPosition>();
		List<ActivityImpl> pvmAcitivities = processDefinition.getActivities();
		for(ActivityImpl activity : pvmAcitivities){
			map.put(activity.getId(), new ActivityPosition(activity.getHeight(), activity.getWidth(),(activity.getX() - minX),(activity.getY() - minY)));
		}
		
		return map;
	}
	
	
	public static Map<String, String> getProcessInstanceInfo(List<HistoricActivityInstance> historicActivities, ProcessDefinitionEntity processDefinition){
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> definitionInfo = getProcessDefinitionInfo(processDefinition);
		
		for(String activityId : definitionInfo.keySet()){
			StringBuilder str = new StringBuilder();
			for(HistoricActivityInstance hi : historicActivities){
				if(hi.getActivityId().equals(activityId)){
					if(str.length() != 0){
						str.append("<~~>");
					}
					str.append(getHistoricActivityInfo(hi));
				}
			}
			
			if(str.length() == 0){
				str.append(definitionInfo.get(activityId));
				if(activityId.contains("usertask") || activityId.contains("userTask")){
				str.append("<!!>");
				str.append("任务状态:");
				str.append("<!!>");
				str.append("未流转至");
				}
			}
			map.put(activityId, str.toString());
		}
		
		return map;
	}
	
	
	public static String getHistoricActivityInfo(HistoricActivityInstance hi){
		String type = hi.getActivityType();
		if(type.equals("startEvent")){
			return getInstanceStartEventInfo(hi);
		}else if(type.equals("endEvent")){
			return getInstanceEndEventInfo(hi);
		}else if(type.contains("Gateway")){
			return getInstanceGetWayInfo(hi);
		}else if(type.equals("userTask")){
			return getInstanceUserTask(hi);
		}else{
			return "暂无信息<!!>";
		}
	}
	
	public static String getInstanceStartEventInfo(HistoricActivityInstance hi){
		StringBuilder str = new StringBuilder();
		str.append("节点名称:");
		str.append("<!!>");
		str.append("启动流程");
		str.append("<!!>");
		str.append("开始时间:");
		str.append("<!!>");
		str.append(StringUtil.getTimeString(hi.getStartTime()));
		str.append("<!!>");
		str.append("流程发起人:");
		str.append("<!!>");
		str.append(getStarter(hi.getProcessInstanceId()));
		
		return str.toString();
	}
	
	public static String getInstanceEndEventInfo(HistoricActivityInstance hi){
		StringBuilder str = new StringBuilder();
		str.append("节点名称:");
		str.append("<!!>");
		str.append("结束流程");
		str.append("<!!>");
		str.append("结束时间:");
		str.append("<!!>");
		str.append(StringUtil.getTimeString(hi.getEndTime()));
		return str.toString();
	}
	
	public static String getInstanceGetWayInfo(HistoricActivityInstance hi){
		String type = hi.getActivityType();
		if(type.equals("parallelGateway")){
			return "并行网关:<!!>" + StringUtil.getTimeString(hi.getStartTime());
		}else if(type.equals("inclusiveGateway")){
			return "包含网关:<!!>" + StringUtil.getTimeString(hi.getStartTime());
		}else if(type.equals("exclusiveGateway")){
			return "排他网关:<!!>" + StringUtil.getTimeString(hi.getStartTime());
		}else if(type.equals("eventGateway")){
			return "事件网关:<!!>" + StringUtil.getTimeString(hi.getStartTime());
		}else{
			throw new NullPointerException("Invalide Gateway, Please check the process.");
		}
	}
	
	public static String getInstanceUserTask(HistoricActivityInstance hi){
		HistoryService historyService = ApplicationContextHelper.getBean(HistoryService.class);
		HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery().taskId(hi.getTaskId()).singleResult();
		StringBuilder str = new StringBuilder();
		str.append("任务名称:");
		str.append("<!!>");
		str.append(hti.getName());
		str.append("<!!>");
		str.append("签收时间:");
		str.append("<!!>");
		if(hti.getClaimTime() != null){
			str.append(StringUtil.getTimeString(hti.getClaimTime()));
		}else{
			str.append(StringUtil.getTimeString(hti.getStartTime()));
		}
		
		str.append("<!!>");
		str.append("完成时间:");
		str.append("<!!>");
		str.append(hi.getEndTime() == null ? "处理中" : StringUtil.getTimeString(hti.getEndTime()));
		str.append("<!!>");
		str.append("委托人:");
		str.append("<!!>");
		if(hti.getAssignee() == null){
			str.append("未签收");
			str.append("<!!>");
			str.append("候选人:");
			str.append("<!!>");
			str.append(getCandidateGroupIdsByHistoricTaskInstance(hti));
		}else{
			str.append(userService.getById(hti.getAssignee()).getDisplayName());
		}
		
		str.append("<!!>");
		str.append("任务状态:");
		str.append("<!!>");
		str.append(hti.getEndTime() == null ? "正常待处理" : hti.getDeleteReason().equals("completed")? "正常完成" :
			hti.getDeleteReason().equals("跳过")? "手动纠正":hti.getDeleteReason().equals("回退")?"回退完成":hti.getDeleteReason());
		return str.toString();
	}
	
	public static String getStarter(String processInstanceId){
		String userId = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult().getStartUserId();
		return userService.getById(userId).getDisplayName();
	}
	
	//获取候选组,前提为任务候选人为空
	public static String getCandidateGroupIdsByHistoricTaskInstance(HistoricTaskInstance hti){
		String startUserId = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(hti.getProcessInstanceId()).singleResult().getStartUserId();
		BpmConfigNode bpmConfigNode = bpmConfigNodeService
				.getByPdIdAndTdKey(hti.getProcessDefinitionId(), hti.getTaskDefinitionKey());
		List<User> users = bpmConfigNodeService.getAuthByBpmConfigNodeId(bpmConfigNode.getId(), startUserId);
		StringBuilder text = new StringBuilder();
		if(users != null && !users.isEmpty()){
			for(int i=0, size=users.size(); i<size; i++){
				if(i == 0){
					text.append(users.get(i).getDisplayName());
				}else{
					text.append("," + users.get(i).getDisplayName());
				}
			}
		}
		return text.toString();
	}
}

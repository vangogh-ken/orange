package com.van.halley.bpm.support;

import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.BpmConfigOperation;
import com.van.halley.db.persistence.entity.User;
import com.van.service.BasisSubstanceService;
import com.van.service.BpmConfigNodeService;
import com.van.service.BpmConfigOperationService;
import com.van.service.UserService;

public class TaskQueryCache {
	private static HistoryService historyService;
	private static BpmConfigNodeService bpmConfigNodeService;
	private static BpmConfigOperationService bpmConfigOperationService;
	private static UserService userService;
	private static BasisSubstanceService basisSubstanceService;
	
	static{
		historyService = ApplicationContextHelper.getBean("historyService");
		bpmConfigNodeService = ApplicationContextHelper.getBean("bpmConfigNodeService");
		bpmConfigOperationService = ApplicationContextHelper.getBean("bpmConfigOperationService");
		userService = ApplicationContextHelper.getBean("userService");
		basisSubstanceService = ApplicationContextHelper.getBean("basisSubstanceService");
	}
	
	/*public static void setHistoryService(HistoryService historyService) {
		TaskQueryCache.historyService = historyService;
	}
	public static void setBpmConfigNodeService(
			BpmConfigNodeService bpmConfigNodeService) {
		TaskQueryCache.bpmConfigNodeService = bpmConfigNodeService;
	}
	public static void setBpmConfigOperationService(
			BpmConfigOperationService bpmConfigOperationService) {
		TaskQueryCache.bpmConfigOperationService = bpmConfigOperationService;
	}
	public static void setUserService(UserService userService) {
		TaskQueryCache.userService = userService;
	}
	
	public static void setBasisSubstanceService(
			BasisSubstanceService basisSubstanceService) {
		TaskQueryCache.basisSubstanceService = basisSubstanceService;
	}*/
	/**
	 * 获取上个任务委托人
	 * @param taskId
	 * @param processInstanceId
	 * @return
	 */
	public static String getLastTaskAssignee(String taskId, String processInstanceId){
		/*if(historyService == null){
			historyService = ApplicationContextHelper.getBean("historyService");
			setHistoryService(historyService);
		}
		
		if(bpmConfigNodeService == null){
			bpmConfigNodeService = ApplicationContextHelper.getBean("bpmConfigNodeService");
			setBpmConfigNodeService(bpmConfigNodeService);
		}
		
		if(bpmConfigOperationService == null){
			bpmConfigOperationService = ApplicationContextHelper.getBean("bpmConfigOperationService");
			setBpmConfigOperationService(bpmConfigOperationService);
		}
		
		if(userService == null){
			userService = ApplicationContextHelper.getBean("userService");
			setUserService(userService);
		}
		
		if(basisSubstanceService == null){
			basisSubstanceService = ApplicationContextHelper.getBean("basisSubstanceService");
			setBasisSubstanceService(basisSubstanceService);
		}*/
		
		HistoricTaskInstance historicTaskInstance = 
				historyService.createHistoricTaskInstanceQuery().
				processInstanceId(processInstanceId).taskId(taskId).singleResult();
		
		Date startTime = historicTaskInstance.getStartTime();
		
		List<HistoricTaskInstance> historicTaskInstances = 
				historyService.createHistoricTaskInstanceQuery().
				processInstanceId(processInstanceId).list();
		
		for(HistoricTaskInstance hti : historicTaskInstances){
			if(startTime.equals(hti.getEndTime())){
				User user = userService.getById(hti.getAssignee());
				return user == null ? "开始节点" : user.getDisplayName();
			}
		}
		
		return "开始节点";
	}
	
	/**
	 * 获取该任务可用操作
	 * @param taskId
	 * @return
	 */
	public static List<BpmConfigOperation> getAvailableOperationByTaskId(String taskId){
		HistoricTaskInstance historicTaskInstance = 
				historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		String taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
		String processDefinitionId = historicTaskInstance.getProcessDefinitionId();
		BpmConfigNode bpmConfigNode = bpmConfigNodeService.getByPdIdAndTdKey(processDefinitionId, taskDefinitionKey);
		
		BpmConfigOperation filter = new BpmConfigOperation();
		filter.setBpmConfigNode(bpmConfigNode);
		filter.setEnable("T");
		
		return bpmConfigOperationService.queryForList(filter);
	}
	
	/**
	 * 判断是否可删除：业务数据状态为草稿
	 * @return
	 */
	public static boolean canDelete(String taskId){
		HistoricTaskInstance hti = 
				historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceId(hti.getProcessInstanceId()).singleResult();
		if(hpi.getBusinessKey() != null){
			BasisSubstance basisSubstance = basisSubstanceService.getById(hpi.getBusinessKey());
			if(basisSubstance != null){
				if("草稿".equals(basisSubstance.getStatus())){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 获取流程发起人用户姓名
	 * @param processInstanceId
	 * @return
	 */
	public static String getStarterDisplayName(String processInstanceId){
		String startUserId = historyService.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult().getStartUserId();
		User user = userService.getById(startUserId);
		return user == null ? "" : user.getDisplayName();
	}
	
	/**
	 * 获取委托人姓名
	 * @param taskId
	 * @return
	 */
	public static String getAssigneeDisplayName(String taskId){
		String assigneeId = historyService.createHistoricTaskInstanceQuery()
				.taskId(taskId).singleResult().getAssignee();
		User user = userService.getById(assigneeId);
		return user == null ? "" : user.getDisplayName();
	}
	
	/**
	 * 获取任务所属人姓名
	 * @param taskId
	 * @return
	 */
	public static String getOwnerDisplayName(String taskId){
		String ownerId = historyService.createHistoricTaskInstanceQuery()
				.taskId(taskId).singleResult().getOwner();
		User user = userService.getById(ownerId);
		return user == null ? "" : user.getDisplayName();
	}
	
	/**
	 * 获取任何人
	 * @param userId
	 * @return
	 */
	public static String getAnyDisplayName(String userId){
		User user = userService.getById(userId);
		return user == null ? "" : user.getDisplayName();
	}
}

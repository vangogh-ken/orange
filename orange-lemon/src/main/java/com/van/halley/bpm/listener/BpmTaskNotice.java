package com.van.halley.bpm.listener;

import java.util.Date;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.core.message.MessageAdapter;

public abstract class BpmTaskNotice extends MessageAdapter{
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 根据流程获取流程发起人ID
	 * @param delegateTask
	 * @return
	 */
	public String getInitiator(DelegateTask delegateTask) {
		return (String) delegateTask.getVariables().get("initiator");
	}
	
	/**
	 * 开始时间与结束时间一致和有一条线到delegateTask，则认为是
	 * @param delegateTask
	 * @return
	 */
	public String getLastAssignee(DelegateTask delegateTask){
		/*ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(delegateTask.getProcessDefinitionId());
		List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).list();
		for(HistoricActivityInstance activityInstance :  activityInstances){
			if(activityInstance.getEndTime().getTime() == delegateTask.getCreateTime().getTime()){
				ActivityImpl activity = processDefinition.findActivity(activityInstance.getActivityId());
				List<PvmTransition> pvmTransitions = activity.getOutgoingTransitions();
				for(PvmTransition pvmTransition : pvmTransitions){
					if(pvmTransition.getDestination().equals(delegateTask.getTaskDefinitionKey())){
						
					}
				}
			}
		}*/
		
		//HistoricTaskInstance historicTaskInstance = 
				//historyService.createHistoricTaskInstanceQuery().
				//processInstanceId(delegateTask.getProcessInstanceId()).taskId(delegateTask.getId()).singleResult();
		
		//Date startTime = historicTaskInstance.getStartTime();
		Date startTime = delegateTask.getCreateTime();
		
		List<HistoricTaskInstance> historicTaskInstances = 
				historyService.createHistoricTaskInstanceQuery().
				processInstanceId(delegateTask.getProcessInstanceId()).list();
		
		for(HistoricTaskInstance hti : historicTaskInstances){
			if(startTime.equals(hti.getEndTime())){
				return hti.getAssignee();
			}
		}
		
		return null;
	}
}

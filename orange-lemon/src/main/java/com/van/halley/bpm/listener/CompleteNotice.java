package com.van.halley.bpm.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.BpmConfigNotice;
import com.van.halley.db.persistence.entity.BpmMailTemplate;
import com.van.service.BpmConfigNodeService;
import com.van.service.BpmConfigNoticeService;
import com.van.service.BpmMailTemplateService;
import com.van.service.UserBaseService;
import com.van.service.UserService;

@Component
public class CompleteNotice extends BpmTaskNotice{
	public static final String TYPE_ARRIVAL = "TYPE_ARRIVAL";
	public static final String TYPE_COMPLETE = "TYPE_COMPLETE";
	public static final String TYPE_TIMEOUT = "TYPE_TIMEOUT";

	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	@Autowired
	private BpmConfigNoticeService bpmConfigNoticeService;
	@Autowired
	private UserService userService;
	@Autowired
    private UserBaseService userBaseService;
	@Autowired
	private BpmMailTemplateService bpmMailTemplateService;

	public void process(DelegateTask delegateTask) {
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
		String processDefinitionId = delegateTask.getProcessDefinitionId();

		BpmConfigNode bpmConfigNode = bpmConfigNodeService
				.getByPdIdAndTdKey(processDefinitionId, taskDefinitionKey);
		String nodeId = bpmConfigNode.getId();
        
        List<BpmConfigNotice> notices = bpmConfigNoticeService.getByNodeId(nodeId);
        
        //如果流程节点没有配置，则直接返回
        if(notices == null || notices.isEmpty()){
        	return;
        }
        for(BpmConfigNotice bpmConfigNotice : notices){
        	if(bpmConfigNotice.getType().equals(TYPE_COMPLETE)){
        		//processComplete(delegateTask, bpmConfigNotice);//暂不使用
            }
        }
	}

	public void processComplete(DelegateTask delegateTask,
			BpmConfigNotice bpmConfigNotice) {
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setId(delegateTask.getId());
		taskEntity.setName(delegateTask.getName());
		taskEntity.setAssigneeWithoutCascade(delegateTask.getAssignee());
		taskEntity.setVariableLocal("initiator", getInitiator(delegateTask));
		taskEntity.setVariableLocal("processDefinition", delegateTask.getVariable("processDefinition"));

		String receiver = bpmConfigNotice.getReceiver();
		BpmMailTemplate bpmMailTemplate = bpmConfigNotice.getBpmMailTemplate();
		ExpressionManager expressionManager = Context
				.getProcessEngineConfiguration().getExpressionManager();
		String to = null;
		String subject = expressionManager
				.createExpression(bpmMailTemplate.getSubject())
				.getValue(taskEntity).toString();

		String content = expressionManager
				.createExpression(bpmMailTemplate.getContent())
				.getValue(taskEntity).toString();
		
		if ("任务委托人".equals(receiver)) {
        	super.processMessage(getLastAssignee(delegateTask), delegateTask.getAssignee(), "流程任务", content);
        } else if ("流程发起人".equals(receiver)) {
        	super.processMessage(getLastAssignee(delegateTask), getInitiator(delegateTask), "流程任务", content);
        } else {
        	super.processMessage(getLastAssignee(delegateTask), getInitiator(delegateTask), "流程任务", content);
        }
        
        
        //通过邮件发送消息
		/*if ("任务委托人".equals(receiver)) {
			UserBase userBase = userBaseService.getByUserId(delegateTask.getAssignee());
			to = userBase == null ? "" : userBase.getMailAddress();
		} else if ("流程发起人".equals(receiver)) {
			UserBase userBase = userBaseService.getByUserId((String) delegateTask.getVariables().get("initiator"));
			to = userBase == null ? "" : userBase.getMailAddress();
		} else {
			HistoricProcessInstanceEntity historicProcessInstanceEntity = Context
					.getCommandContext()
					.getHistoricProcessInstanceEntityManager()
					.findHistoricProcessInstance(
							delegateTask.getProcessInstanceId());
			UserBase userBase = userBaseService.getByUserId(historicProcessInstanceEntity.getStartUserId());
			to = userBase == null ? "" : userBase.getMailAddress();
		}

		mailFacade.sendMail(to, subject, content);*/
	}
}

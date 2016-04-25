package com.van.halley.bpm.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.User;
import com.van.service.BpmConfigNodeService;

/**
 * 设置委托人工具
 * @author ken
 *
 */
@Component
public class AssigneeHelper {
	private static Logger logger = LoggerFactory.getLogger(AssigneeHelper.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	/**
	 * 获取发起人ID
	 * @param delegateTask
	 * @return
	 */
	public String getInitiatorId(DelegateTask delegateTask){
		Object initiator = delegateTask.getVariables().get("initiator");
		if(initiator != null){
			logger.debug("initiator 已找到 : {}", initiator);
			return initiator.toString();
		}else{
			logger.debug("initiator 没有找到 , 重新从系统中获取");
			List<IdentityLink> identityLinks = runtimeService.getIdentityLinksForProcessInstance(delegateTask.getProcessInstanceId());
			for(IdentityLink idLink: identityLinks){
				if(idLink.getType().equals("starter")){
					delegateTask.setVariable("initiator", idLink.getUserId());
					return idLink.getUserId();
				}
			}
		}
		return null;
	}
	
	public void assignee(DelegateTask delegateTask){
		Set<IdentityLink> candidates = delegateTask.getCandidates();//候选人
		String assignee = delegateTask.getAssignee();//委派
		
		BpmConfigNode bpmConfigNode = bpmConfigNodeService.getByPdIdAndTdKey(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey());
		if(candidates.isEmpty() && assignee == null){
			List<User> users = bpmConfigNodeService.getAuthByBpmConfigNodeId(bpmConfigNode.getId(), getInitiatorId(delegateTask));
			if(users != null && !users.isEmpty()){
				if(users.size() == 1){
					delegateTask.setAssignee(users.get(0).getId());
				}else{
					List<String> candidateUsers = new ArrayList<String>();
					for(User user : users){
						candidateUsers.add(user.getId());
					}
					delegateTask.addCandidateUsers(candidateUsers);
				}
			}else{
				throw new NullPointerException("could not find any candidates to assignee");
			}
		}
	}
}

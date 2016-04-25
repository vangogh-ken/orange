package com.van.halley.bpm.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.bpm.support.ProcessDefinitionCache;
import com.van.halley.db.persistence.entity.BpmConfigBasis;
import com.van.halley.db.persistence.entity.BpmConfigDelegate;
import com.van.service.BpmConfigBasisService;
import com.van.service.BpmConfigDelegateService;
import com.van.service.UserService;

/**
 * @author Think
 * 此监听器将对自动委托代理相关事宜进行处理
 *
 */
public class BpmTaskAssigneeListner implements TaskListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BpmTaskAssigneeListner.class);
	@Autowired
	private BpmConfigDelegateService bpmConfigDelegateService;
	@Autowired
	private BpmConfigBasisService bpmConfigBasisService;
	@Autowired
	private UserService userService;

	@Override
	public void notify(DelegateTask delegateTask) {
		String event = delegateTask.getEventName();
		if(event.equals("assignment")){
			String assignee = delegateTask.getAssignee();
			String taskName = delegateTask.getName();
			String processDefinitionId = delegateTask.getProcessDefinitionId();
			String processDefinitionKey = ProcessDefinitionCache.get(processDefinitionId).getKey();
			String processName = ProcessDefinitionCache.getProcessName(processDefinitionId);
			logger.info("流程: {}, 任务: {}, 签收人: {}",  processName, taskName, userService.getById(assignee).getDisplayName());
			
			BpmConfigBasis bpmConfigBasis = bpmConfigBasisService.getByBpmKey(processDefinitionKey);
			BpmConfigDelegate filter = new BpmConfigDelegate();
			filter.setBpmConfigBasis(bpmConfigBasis);
			filter.setTaskAssignee(userService.getById(assignee));
			BpmConfigDelegate bpmConfigDelegate = bpmConfigDelegateService.queryForOne(filter);
			if(bpmConfigDelegate != null){
				String delegateType = bpmConfigDelegate.getDelegateType();
				String status = bpmConfigDelegate.getStatus();
				if(delegateType.equals("once") && status.equals("start")){
					if (delegateTask.getOwner() == null) {
						delegateTask.setOwner(assignee);
			        }
					delegateTask.setAssignee(bpmConfigDelegate.getTaskAgent().getId());
					logger.info("流程: {}, 任务: {}, 代理人: {}",  processName, taskName, bpmConfigDelegate.getTaskAgent().getDisplayName());
					//一次性的代理则在使用完成之后,需要改变其状态
					bpmConfigDelegate.setStatus("used");
					bpmConfigDelegateService.modify(bpmConfigDelegate);
				}else if(delegateType.equals("period")){
					//如果时间符合
					long currentTime = new Date().getTime();
					if(bpmConfigDelegate.getStartTime().getTime() < currentTime
							&& currentTime < bpmConfigDelegate.getEndTime().getTime()){
						if (delegateTask.getOwner() == null) {
							delegateTask.setOwner(assignee);
				        }
						delegateTask.setAssignee(bpmConfigDelegate.getTaskAgent().getId());
						logger.info("流程: {}, 任务: {}, 代理人: {}",  processName, taskName, bpmConfigDelegate.getTaskAgent().getDisplayName());
					}
				}
			}
		}
	}

}

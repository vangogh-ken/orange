package com.van.halley.bpm.listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.context.Context;

import com.van.halley.bpm.cmd.DelegateTaskCmd;
import com.van.halley.bpm.delegate.DelegateInfo;
import com.van.halley.bpm.delegate.DelegateService;
import com.van.halley.bpm.support.DefaultTaskListener;

public class DelegateTaskListener extends DefaultTaskListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DelegateService delegateService;

    @Override
    public void onAssignment(DelegateTask delegateTask) throws Exception {
        String assignee = delegateTask.getAssignee();
        String processDefinitionId = delegateTask.getProcessDefinitionId();
        DelegateInfo delegateInfo = delegateService.getDelegateInfo(assignee,
                processDefinitionId);

        if (delegateInfo == null) {
            return;
        }

        String attorney = delegateInfo.getAttorney();
        new DelegateTaskCmd(delegateTask.getId(), attorney).execute(Context
                .getCommandContext());
    }

    @Resource
    public void setDelegateService(DelegateService delegateService) {
        this.delegateService = delegateService;
    }
}

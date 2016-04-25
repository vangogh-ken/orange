package com.van.halley.bpm.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import com.van.halley.bpm.support.DefaultTaskListener;

@Component("autoCompleteTaskListener")
public class AutoCompleteTaskListener extends DefaultTaskListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void onCreate(DelegateTask delegateTask) throws Exception {
        String username = Authentication.getAuthenticatedUserId();
        String assignee = delegateTask.getAssignee();

        if ((username != null) && username.equals(assignee)) {
        	//修改 Version 5.16
            ((TaskEntity) delegateTask).complete(null, true);
        }
    }
}

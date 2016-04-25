package com.van.halley.bpm.listener;

import java.util.Collections;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProxyExecutionListener implements ExecutionListener {
	private static final long serialVersionUID = 1L;
	private List<ExecutionListener> executionListeners = Collections.EMPTY_LIST;
    
    @Override
	public void notify(DelegateExecution execution) throws Exception {
		for(ExecutionListener executionListener : executionListeners){
			executionListener.notify(execution);
		}
	}
    
    public void setExecutionListeners(List<ExecutionListener> executionListeners) {
		this.executionListeners = executionListeners;
	}
}

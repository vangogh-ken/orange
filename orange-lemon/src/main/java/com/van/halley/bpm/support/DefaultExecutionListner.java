package com.van.halley.bpm.support;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExecutionListner implements ExecutionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory
            .getLogger(DefaultTaskListener.class);

	@Override
	public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        logger.debug("{}", this);
        logger.debug("{} : {}", eventName, execution);

        if ("start".equals(eventName)) {
            try {
                this.onStart(execution);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        if ("take".equals(eventName)) {
            try {
                this.onTake(execution);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        if ("end".equals(eventName)) {
            try {
                this.onEnd(execution);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

    }

    public void onStart(DelegateExecution execution) throws Exception {
    	execution.getCurrentActivityId();
    }

    public void onTake(DelegateExecution execution) throws Exception {
    }

    public void onEnd(DelegateExecution execution) throws Exception {
    }



}

package com.van.halley.bpm.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.bpm.support.DefaultTaskListener;

/**
 * <p>
 * 任务到达提醒：xx您好，您有新任务需要处理。
 * </p>
 * <p>
 * 任务超时提醒：xx您好，您的任务还有xx时间即将过期，请尽快处理。
 * </p>
 * <p>
 * 提醒起草人：xx您好，您的流程已经到达xx环节，预计处理需要xx时间。
 * </p>
 * <p>
 * 提醒关键岗位：xx您好，xx任务已经交由xx处理，请知晓。
 * </p>
 * 
 * <p>
 * 超时提醒不是这个Listener里能判断的。
 * </p>
 */
public class NoticeTaskListener extends DefaultTaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
    private ArrivalNotice arrivalNotice;
	@Autowired
    private CompleteNotice completeNotice;

    @Override
    public void onCreate(DelegateTask delegateTask) throws Exception {
        //arrivalNotice.process(delegateTask);
    }

    @Override
    public void onAssignment(DelegateTask delegateTask) throws Exception {
    	arrivalNotice.process(delegateTask);
    }
    
    @Override
    public void onComplete(DelegateTask delegateTask) throws Exception {
        completeNotice.process(delegateTask);
    }
}

package com.van.halley.quartz.support;

import com.van.halley.db.persistence.entity.QuartzJob;

/**
 * 所有的调度任务都需要实现此抽象方法
 * @author Administrator
 * 
 */
public abstract class AbstractQuartzTarget{
	private QuartzJob quartzJob;

	public QuartzJob getQuartzJob(){
		return this.quartzJob;
	}

	public void setQuartzJob(QuartzJob quartzJob){
		this.quartzJob = quartzJob;
	}
}

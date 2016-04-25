package com.van.halley.quartz.support;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.van.halley.db.persistence.entity.QuartzJob;

@DisallowConcurrentExecution
public class SerialExecution implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get("quartzJob");
		ExecutionUtils.invokMethod(quartzJob);
	}
}
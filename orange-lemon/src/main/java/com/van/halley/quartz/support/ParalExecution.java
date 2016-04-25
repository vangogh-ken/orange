package com.van.halley.quartz.support;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.van.halley.db.persistence.entity.QuartzJob;

public class ParalExecution implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get("quartzJob");
		ExecutionUtils.invokMethod(quartzJob);
	}
}
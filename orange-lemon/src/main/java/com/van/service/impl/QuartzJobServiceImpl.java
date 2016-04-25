package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.QuartzCronDao;
import com.van.halley.db.persistence.QuartzJobDao;
import com.van.halley.db.persistence.QuartzTaskDao;
import com.van.halley.db.persistence.entity.QuartzCron;
import com.van.halley.db.persistence.entity.QuartzJob;
import com.van.halley.db.persistence.entity.QuartzTask;
import com.van.halley.quartz.support.ParalExecution;
import com.van.halley.quartz.support.SerialExecution;
import com.van.service.QuartzJobService;

@Transactional
@Service("quartzJobService")
public class QuartzJobServiceImpl implements QuartzJobService {
	private static Logger LOG = LoggerFactory.getLogger(QuartzJobServiceImpl.class);
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private ScheduledTaskRegistrar scheduledTaskRegistrar;
	@Autowired
	private QuartzJobDao quartzJobDao;
	@Autowired
	private QuartzTaskDao quartzTaskDao;
	@Autowired
	private QuartzCronDao quartzCronDao;

	public List<QuartzJob> getAll() {
		return quartzJobDao.getAll();
	}

	public List<QuartzJob> queryForList(QuartzJob quartzJob) {
		return quartzJobDao.queryForList(quartzJob);
	}

	public QuartzJob queryForOne(QuartzJob quartzJob) {
		return quartzJobDao.queryForOne(quartzJob);
	}

	public PageView<QuartzJob> query(PageView<QuartzJob> pageView, QuartzJob quartzJob) {
		List<QuartzJob> list = quartzJobDao.query(pageView, quartzJob);
		pageView.setResults(list);
		return pageView;
	}

	public void add(QuartzJob quartzJob) {
		quartzJobDao.add(quartzJob);
	}

	public void delete(String id) {
		quartzJobDao.delete(id);
	}

	public void modify(QuartzJob quartzJob) {
		quartzJobDao.modify(quartzJob);
		//trigger(quartzJob);
	}

	public QuartzJob getById(String id) {
		return quartzJobDao.getById(id);
	}

	@Override
	public void trigger(QuartzJob quartzJob) {
		if (quartzJob == null || !QuartzJob.STATUS_RUNNING.equals(quartzJob.getJobStatus())) {
			return;
		}
		try{
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			LOG.debug(scheduler + ".......................................................................................add");
			//需要添加一个盐值来确定其唯一性
			TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 不存在，创建一个
			if (null == trigger) {
				Class<? extends Job> clazz = QuartzJob.CONCURRENT_YES.equals(quartzJob.getConcurrent()) ? ParalExecution.class : SerialExecution.class;
				JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(quartzJob.getIdentityName(), quartzJob.getIdentityGroup()).build();
				
				jobDetail.getJobDataMap().put("quartzJob", quartzJob);
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getQuartzCron().getCronExpression());
				//trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getIdentityName(), quartzJob.getIdentityGroup()).withSchedule(scheduleBuilder).build();
				
				TriggerBuilder<CronTrigger> builder = TriggerBuilder.newTrigger().withIdentity(quartzJob.getIdentityName(), quartzJob.getIdentityGroup()).withSchedule(scheduleBuilder);
				if(quartzJob.getStartTime() != null && quartzJob.getEndTime() != null && quartzJob.getStartTime().before(quartzJob.getEndTime())){
					trigger = builder.startAt(quartzJob.getStartTime()).endAt(quartzJob.getEndTime()).build();
				}else if(quartzJob.getStartTime() != null && quartzJob.getEndTime() == null){
					trigger = builder.startAt(quartzJob.getStartTime()).build();
				}else if(quartzJob.getStartTime() == null && quartzJob.getEndTime() != null){
					trigger = builder.endAt(quartzJob.getEndTime()).build();
				}else{
					trigger = builder.startNow().build();
				}
				
				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getQuartzCron().getCronExpression());// Trigger已存在，那么更新相应的定时设置
				// 按新的cronExpression表达式重新构建trigger
				//trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				TriggerBuilder<CronTrigger> builder = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder);
				if(quartzJob.getStartTime() != null && quartzJob.getEndTime() != null && quartzJob.getStartTime().before(quartzJob.getEndTime())){
					trigger = builder.startAt(quartzJob.getStartTime()).endAt(quartzJob.getEndTime()).build();
				}else if(quartzJob.getStartTime() != null && quartzJob.getEndTime() == null){
					trigger = builder.startAt(quartzJob.getStartTime()).build();
				}else if(quartzJob.getStartTime() == null && quartzJob.getEndTime() != null){
					trigger = builder.endAt(quartzJob.getEndTime()).build();
				}else{
					trigger = builder.startNow().build();
				}
				
				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
			}
			quartzJob.setJobStatus(QuartzJob.STATUS_RUNNING);
			quartzJobDao.modify(quartzJob);
		}catch(SchedulerException e){
			LOG.error("触发任务失败, 错误 : {}", e);
		}
	}

	@Override
	public void triggerCurrent() {
		List<QuartzJob> quartzJobs = getAll();
		for(QuartzJob quartzJob : quartzJobs){
			trigger(quartzJob);
		}
		
		if(scheduledTaskRegistrar.hasTasks()){
			List<CronTask> list = scheduledTaskRegistrar.getCronTaskList();
			if(list != null && !list.isEmpty()){
				for(CronTask item : list){
					LOG.info("one cron task : expression " + item.getExpression());
				}
			}
		}
	}
	

	@Override
	public void fire(QuartzJob quartzJob) {
		quartzJob.setJobStatus(QuartzJob.STATUS_RUNNING);
		trigger(quartzJob);
		quartzJobDao.modify(quartzJob);
	}
	
	@Override
	public void start(QuartzJob quartzJob) {
		// TODO Auto-generated method stu
		quartzJob.setJobStatus(QuartzJob.STATUS_RUNNING);
		trigger(quartzJob);
		quartzJobDao.modify(quartzJob);
	}

	@Override
	public void stop(QuartzJob quartzJob) {
		quartzJob.setJobStatus(QuartzJob.STATUS_SHUTDOWN);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		quartzJobDao.modify(quartzJob);
	}

	@Override
	public void pause(QuartzJob quartzJob) {
		quartzJob.setJobStatus(QuartzJob.STATUS_PAUSED);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		quartzJobDao.modify(quartzJob);
	}

	@Override
	public void resume(QuartzJob quartzJob) {
		quartzJob.setJobStatus(QuartzJob.STATUS_RUNNING);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		quartzJobDao.modify(quartzJob);
	}

	/*@Override
	public void delete(QuartzJob quartzJob) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}*/

	@Override
	public void runNow(QuartzJob quartzJob) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setCron(QuartzJob quartzJob) {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getIdentityName(), quartzJob.getIdentityGroup());
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getQuartzCron().getCronExpression());
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<QuartzJob> getRunning() {
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			List<QuartzJob> list = new ArrayList<QuartzJob>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				
				JobDetail jobDetail = executingJob.getJobDetail();
				Trigger trigger = executingJob.getTrigger();
				
				JobKey jobKey = jobDetail.getKey();
				//QuartzJob job = new QuartzJob();
				QuartzJob job = quartzJobDao.getById(jobKey.getName());
				
				/**
				QuartzTask quartzTask = quartzTaskDao.getByTaskKey(jobKey.getName().split(":")[0]);
				job.setQuartzTask(quartzTask);
				
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					QuartzCron quartzCron = quartzCronDao.getByCronExpression(cronExpression);
					job.setQuartzCron(quartzCron);
				}
				**/
				list.add(job);
				return list;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public List<QuartzJob> getpaused() {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		try {
			Set<String> ss = scheduler.getPausedTriggerGroups();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//初始化的时候将自动任务加载
	@PostConstruct
	public void init(){
		triggerCurrent();
	}
}

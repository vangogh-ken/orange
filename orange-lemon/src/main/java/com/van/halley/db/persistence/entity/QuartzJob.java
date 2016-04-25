package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class QuartzJob implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 运行
	 */
	public static final String STATUS_RUNNING = "T";
	/**
	 * 停止
	 */
	public static final String STATUS_SHUTDOWN = "F";
	/**
	 * 暂停
	 */
	public static final String STATUS_PAUSED = "P";
	public static final String CONCURRENT_YES = "T";
	public static final String CONCURRENT_NOT = "F";
	
	private String id;
	private String jobStatus;
	private String concurrent;
	private Date startTime;
	private Date endTime;
	
	private QuartzTask quartzTask;
	private QuartzCron quartzCron;
	private User owner;
	
	//交给quartz执行的判断
	private String identityName;
	private String identityGroup;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}
	public QuartzTask getQuartzTask() {
		return quartzTask;
	}
	public void setQuartzTask(QuartzTask quartzTask) {
		this.quartzTask = quartzTask;
	}
	public QuartzCron getQuartzCron() {
		return quartzCron;
	}
	public void setQuartzCron(QuartzCron quartzCron) {
		this.quartzCron = quartzCron;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getIdentityName() {
		//return this.getQuartzTask().getTaskKey() + ":" + this.getId();
		return this.getId();
	}
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}
	public String getIdentityGroup() {
		return this.getQuartzTask().getTaskKey() + ":" +this.getQuartzTask().getQuartzGroup().getGroupKey();
	}
	public void setIdentityGroup(String identityGroup) {
		this.identityGroup = identityGroup;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}

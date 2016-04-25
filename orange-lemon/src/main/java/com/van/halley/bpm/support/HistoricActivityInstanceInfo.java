package com.van.halley.bpm.support;

import java.util.Date;

public class HistoricActivityInstanceInfo{
	private String id;
	private String activityId;
	private String activityName;
	private String activityType;
	private String assignee;
	private Date startTime;
	private Date endTime;
	private long durationTime;
	
	public HistoricActivityInstanceInfo(String id, String activityId,
			String activityName, String activityType, String assignee,
			Date startTime, Date endTime, long durationTime) {
		super();
		this.id = id;
		this.activityId = activityId;
		this.activityName = activityName;
		this.activityType = activityType;
		this.assignee = assignee;
		this.startTime = startTime;
		this.endTime = endTime;
		this.durationTime = durationTime;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
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
	public long getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(long durationTime) {
		this.durationTime = durationTime;
	}
}

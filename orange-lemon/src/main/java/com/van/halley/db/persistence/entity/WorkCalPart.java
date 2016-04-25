package com.van.halley.db.persistence.entity;

import java.io.Serializable;


public class WorkCalPart implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** null. */
    private String id;

    /** 规则. */
    private WorkCalRule workCalRule;

    /** 时间段. */
    private  int shift;

    /** 开始时间. */
    private String startTime;

    /** 结束时间. */
    private String endTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public WorkCalRule getWorkCalRule() {
		return workCalRule;
	}

	public void setWorkCalRule(WorkCalRule workCalRule) {
		this.workCalRule = workCalRule;
	}
}

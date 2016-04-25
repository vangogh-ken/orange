package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 * 工作日历规则
 */
public class WorkCalRule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** null. */
    private String id;
    /** 类别. */
    private WorkCalType workCalType;
    /** 年. */
    private int year;

    /** 周. */
    private int week;

    /** null. */
    private String name;

    /** null. */
    private Date workDate;

    /** 状态. */
    private int status;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}


	public Set<WorkCalPart> getWorkcalParts() {
		return workcalParts;
	}

	public void setWorkcalParts(Set<WorkCalPart> workcalParts) {
		this.workcalParts = workcalParts;
	}

	/** 工作段 */
    private Set<WorkCalPart> workcalParts = new HashSet<WorkCalPart>(0);

	public WorkCalType getWorkCalType() {
		return workCalType;
	}

	public void setWorkCalType(WorkCalType workCalType) {
		this.workCalType = workCalType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}

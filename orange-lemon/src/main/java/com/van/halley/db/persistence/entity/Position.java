package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位, 在某个组织机构之下
 * @author ken
 */
public class Position implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 名称
	 */
	private String positionName;
	
	/**
	 * 优先级
	 */
	private int priority;
	/**
	 * 职级
	 */
	private int grade;
	/**
	 * 所在组织机构
	 */
	private OrgEntity orgEntity;
	
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
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
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
	
	
	
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 货运业务各种类型的操作结果。
 * @author Think
 *
 */
public class FreightAuditRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 记录的实体对象ID
	 */
	private String targetId;
	/**
	 * 实体对象原状态
	 */
	private String sourceStatus;
	/**
	 * 审批之后的状态
	 */
	private String targetStatus;
	/**
	 * 执行者用户ID
	 */
	private User executor;
	
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
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getSourceStatus() {
		return sourceStatus;
	}
	public void setSourceStatus(String sourceStatus) {
		this.sourceStatus = sourceStatus;
	}
	public String getTargetStatus() {
		return targetStatus;
	}
	public void setTargetStatus(String targetStatus) {
		this.targetStatus = targetStatus;
	}
	public User getExecutor() {
		return executor;
	}
	public void setExecutor(User executor) {
		this.executor = executor;
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

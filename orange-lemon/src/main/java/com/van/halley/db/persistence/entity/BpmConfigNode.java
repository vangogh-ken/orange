package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class BpmConfigNode implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 流程Id
	 */
	private String pdId;
	/**
	 * 流程key
	 */
	private String pdKey;
	/**
	 * 流程名称
	 */
	private String pdName;
	/**
	 * 任务key
	 */
	private String tdKey;
	/**
	 * 任务名称
	 */
	private String tdName;
	/**
	 * 暂时未用
	 */
	private String tdListners;
	/**
	 * 任务持续时间(超过该时间将有超时提示,最低单位:分钟)
	 */
	private String dueDate;
	
	/**
	 * 任务创建时,赋予业务对象的状态
	 */
	private String sourceStatus;
	/**
	 * 任务完成时,赋予业务对象的状态
	 */
	private String targetStatus;
	/**
	 * 任务创建时要执行的sqls
	 */
	private String onCreate;
	/**
	 * 任务完成时要执行的sqls
	 */
	private String onComplete;
	/**
	 * 配置状态
	 */
	private BasisStatus basisStatus;
	
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
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public BasisStatus getBasisStatus() {
		return basisStatus;
	}
	public void setBasisStatus(BasisStatus basisStatus) {
		this.basisStatus = basisStatus;
	}
	public String getPdId() {
		return pdId;
	}
	public void setPdId(String pdId) {
		this.pdId = pdId;
	}
	public String getPdKey() {
		return pdKey;
	}
	public void setPdKey(String pdKey) {
		this.pdKey = pdKey;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public String getTdKey() {
		return tdKey;
	}
	public void setTdKey(String tdKey) {
		this.tdKey = tdKey;
	}
	public String getTdName() {
		return tdName;
	}
	public void setTdName(String tdName) {
		this.tdName = tdName;
	}
	public String getTdListners() {
		return tdListners;
	}
	public void setTdListners(String tdListners) {
		this.tdListners = tdListners;
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
	public String getOnCreate() {
		return onCreate;
	}
	public void setOnCreate(String onCreate) {
		this.onCreate = onCreate;
	}
	public String getOnComplete() {
		return onComplete;
	}
	public void setOnComplete(String onComplete) {
		this.onComplete = onComplete;
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

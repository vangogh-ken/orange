package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务代理设置
 * @author Think
 */
public class BpmConfigDelegate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 代理类型（只一次 once,一段时间 period）
	 * once 以及 status 为start时才启用
	 * period 只要当前时间在开始时间与结束时间之间
	 */
	private String delegateType;
	/**
	 * 流程信息
	 */
	private BpmConfigBasis bpmConfigBasis;
	/**
	 * 任务委托人
	 */
	private User taskAssignee;
	/**
	 * 任务代理人
	 */
	private User taskAgent;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 状态(start used)
	 */
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
	public String getDelegateType() {
		return delegateType;
	}
	public void setDelegateType(String delegateType) {
		this.delegateType = delegateType;
	}
	public BpmConfigBasis getBpmConfigBasis() {
		return bpmConfigBasis;
	}
	public void setBpmConfigBasis(BpmConfigBasis bpmConfigBasis) {
		this.bpmConfigBasis = bpmConfigBasis;
	}
	public User getTaskAssignee() {
		return taskAssignee;
	}
	public void setTaskAssignee(User taskAssignee) {
		this.taskAssignee = taskAssignee;
	}
	public User getTaskAgent() {
		return taskAgent;
	}
	public void setTaskAgent(User taskAgent) {
		this.taskAgent = taskAgent;
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

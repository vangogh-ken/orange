package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightActionTypeIdentity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 动作类型
	 */
	private String freightActionTypeId;
	/**
	 * 委托用户ID
	 */
	private String assigneeUserId;
	/**
	 * 候选组ID
	 */
	private String candidateGroupId;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FreightActionTypeIdentity(){
		
	}
	
	/**
	 * 委托用户ID, 候选组ID 只能保留其中之一
	 * @param freightActionTypeId
	 * @param assigneeUserId
	 * @param candidateGroupId
	 */
	public FreightActionTypeIdentity(String freightActionTypeId, String assigneeUserId, String candidateGroupId){
		this.freightActionTypeId = freightActionTypeId;
		if(assigneeUserId != null){
			this.assigneeUserId = assigneeUserId;
		}else{
			this.candidateGroupId = candidateGroupId;
		}
	}
	
	public FreightActionTypeIdentity(String freightActionTypeId, String assigneeUserId){
		this.freightActionTypeId = freightActionTypeId;
		this.assigneeUserId = assigneeUserId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFreightActionTypeId() {
		return freightActionTypeId;
	}
	public void setFreightActionTypeId(String freightActionTypeId) {
		this.freightActionTypeId = freightActionTypeId;
	}
	public String getAssigneeUserId() {
		return assigneeUserId;
	}
	public void setAssigneeUserId(String assigneeUserId) {
		this.assigneeUserId = assigneeUserId;
	}
	public String getCandidateGroupId() {
		return candidateGroupId;
	}
	public void setCandidateGroupId(String candidateGroupId) {
		this.candidateGroupId = candidateGroupId;
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

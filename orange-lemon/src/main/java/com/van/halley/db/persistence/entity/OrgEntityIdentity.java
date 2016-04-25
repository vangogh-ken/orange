package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织机构与负责人之间的关联关系
 * @author Think
 *
 */
public class OrgEntityIdentity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 优先级 1 > 2 > 3  1为主要负责人
	 */
	private int priority;
	/**
	 * 组织机构
	 */
	private String orgEntityId;
	/**
	 * 负责人
	 */
	private String userId;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public OrgEntityIdentity(){
		
	}
	
	public OrgEntityIdentity(String orgEntityId, String userId){
		this.priority = 1;
		this.orgEntityId = orgEntityId;
		this.userId = userId;
	}
	
	public OrgEntityIdentity(int priority, String orgEntityId, String userId){
		this.priority = priority;
		this.orgEntityId = orgEntityId;
		this.userId = userId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public String getOrgEntityId() {
		return orgEntityId;
	}
	public void setOrgEntityId(String orgEntityId) {
		this.orgEntityId = orgEntityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class UserRole implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 角色ID
	 */
	private String roleId;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

	public UserRole() {

	}
	
	public UserRole(String userId, String roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
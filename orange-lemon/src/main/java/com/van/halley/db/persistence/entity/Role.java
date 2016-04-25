package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String roleName;
	private String roleKey;
	private Set<Resource> resources = new HashSet<Resource>(0);
	
	private String descn;
	private String status; // 是否禁用角色　1　表示禁用　2　表示不禁用
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getDescn() {
		return descn;
	}


	public void setDescn(String descn) {
		this.descn = descn;
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


	public Role() {
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

}
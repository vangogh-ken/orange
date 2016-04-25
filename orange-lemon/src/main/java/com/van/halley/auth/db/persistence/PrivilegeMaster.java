package com.van.halley.auth.db.persistence;

import java.util.Date;

/**
 * 权限关系数据
 *
 */
public class PrivilegeMaster {
	
	private String id;
	private String privilegeType;
	private String privilegeMasterId;
	private String privilegeMasterValue;
	private String privilegeAccessId;
	private String privilegeAccessValue;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	static class Type{
		private static final String USER_ROLE = "USER_ROLE";
		private static final String ROLE_RESOURCE = "ROLE_RESOURCE";
		private static final String ROLE_BPMPDF = "ROLE_BPMPDF";
		private static final String ROLE_REPORT = "ROLE_REPORT";
		private static final String ROLE_QUARTZ = "ROLE_QUARTZ";
	}
	
	public static void main(String[] args) {
		//String s = PrivilegeMaster.Type.ROLE_BPMPDF;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String getPrivilegeMasterId() {
		return privilegeMasterId;
	}

	public void setPrivilegeMasterId(String privilegeMasterId) {
		this.privilegeMasterId = privilegeMasterId;
	}

	public String getPrivilegeMasterValue() {
		return privilegeMasterValue;
	}

	public void setPrivilegeMasterValue(String privilegeMasterValue) {
		this.privilegeMasterValue = privilegeMasterValue;
	}

	public String getPrivilegeAccessId() {
		return privilegeAccessId;
	}

	public void setPrivilegeAccessId(String privilegeAccessId) {
		this.privilegeAccessId = privilegeAccessId;
	}

	public String getPrivilegeAccessValue() {
		return privilegeAccessValue;
	}

	public void setPrivilegeAccessValue(String privilegeAccessValue) {
		this.privilegeAccessValue = privilegeAccessValue;
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

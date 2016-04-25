package com.van.halley.db.persistence.entity;

import java.util.Date;

public class RoleReport{
	private String id;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 报表模板ID
	 */
	private String reportTemplateId;

	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public RoleReport(){
		
	}
	
	public RoleReport(String roleId, String reportTemplateId){
		this.roleId = roleId;
		this.reportTemplateId = reportTemplateId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getReportTemplateId() {
		return reportTemplateId;
	}
	public void setReportTemplateId(String reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
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

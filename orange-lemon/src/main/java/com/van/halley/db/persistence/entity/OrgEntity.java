package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * @author Think
 * 组织机构实体
 */
public class OrgEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 名称
	 */
	private String orgEntityName;
	/**
	 * 所属组织类型
	 */
	private OrgType orgType;
	/**
	 * 上级组织
	 */
	private OrgEntity parentOrg;
	
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
	public OrgType getOrgType() {
		return orgType;
	}
	public void setOrgType(OrgType orgType) {
		this.orgType = orgType;
	}
	
	@JsonIgnore//json序列化是动态忽略字段使用此注释，用于get方法之上，还有其他注释 JsonIgnoreProperties(value = {"xxx", "yyy"})，此注释用于类名或者接口之上
	public OrgEntity getParentOrg() {
		return parentOrg;
	}
	public void setParentOrg(OrgEntity parentOrg) {
		this.parentOrg = parentOrg;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public String getOrgEntityName() {
		return orgEntityName;
	}
	public void setOrgEntityName(String orgEntityName) {
		this.orgEntityName = orgEntityName;
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
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class BasisStatusAttribute implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 状态ID
	 */
	private String basisStatusId;
	/**
	 * 属性ID
	 */
	private String basisAttributeId;
	/**
	 * 是否只读
	 */
	private String readonly;
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
	public String getBasisStatusId() {
		return basisStatusId;
	}
	public void setBasisStatusId(String basisStatusId) {
		this.basisStatusId = basisStatusId;
	}
	public String getBasisAttributeId() {
		return basisAttributeId;
	}
	public void setBasisAttributeId(String basisAttributeId) {
		this.basisAttributeId = basisAttributeId;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
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

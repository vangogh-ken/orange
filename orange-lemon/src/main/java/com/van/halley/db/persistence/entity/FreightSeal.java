package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ken
 * 封号
 */
public class FreightSeal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 封号编号
	 */
	private String sealNumber;
	/**
	 * 封号类别(其他封,铁路封)
	 */
	private String sealType;
	/**
	 * 发封单位
	 */
	private String sealBelong;
	private String descn;
	private String status;//（0：未使用；1：已使用；2：已遗失；3：已作废）
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSealNumber() {
		return sealNumber;
	}
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	public String getSealType() {
		return sealType;
	}
	public void setSealType(String sealType) {
		this.sealType = sealType;
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
	public String getSealBelong() {
		return sealBelong;
	}
	public void setSealBelong(String sealBelong) {
		this.sealBelong = sealBelong;
	}

}

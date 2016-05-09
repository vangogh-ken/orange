package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ItemCategory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 
	 */
	private String categoryName;
	/**
	 * 
	 */
	private int categoryIndex;
	/**
	 * 
	 */
	private String aboveCategoryId;
	
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryIndex() {
		return categoryIndex;
	}
	public void setCategoryIndex(int categoryIndex) {
		this.categoryIndex = categoryIndex;
	}
	public String getAboveCategoryId() {
		return aboveCategoryId;
	}
	public void setAboveCategoryId(String aboveCategoryId) {
		this.aboveCategoryId = aboveCategoryId;
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

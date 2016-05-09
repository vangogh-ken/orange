package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class ItemAttribute implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 字段名
	 */
	private String attributeName;
	/**
	 * 字段
	 */
	private String attributeColumn;
	/**
	 * 字段类型
	 */
	private String attributeType;
	/**
	 * 是否必须
	 */
	private String isRequired;
	/**
	 * 是否为可选值
	 */
	private String isSelected;
	/**
	 * 
	 */
	private String vAttrId;
	/**
	 * 类型
	 */
	private String itemCategoryId;
	
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
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeColumn() {
		return attributeColumn;
	}
	public void setAttributeColumn(String attributeColumn) {
		this.attributeColumn = attributeColumn;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
	public String getvAttrId() {
		return vAttrId;
	}
	public void setvAttrId(String vAttrId) {
		this.vAttrId = vAttrId;
	}
	public String getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
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

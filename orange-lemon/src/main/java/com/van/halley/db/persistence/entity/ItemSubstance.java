package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ItemSubstance implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 
	 */
	private String itemNumber;
	/**
	 * 
	 */
	private String itemModel;
	/**
	 * 
	 */
	private String itemName;
	/**
	 * 
	 */
	private String itemCategoryId;
	/**
	 * 
	 */
	private String itemSupplierId;
	/**
	 * 
	 */
	private String itemManufacturerId;
	
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
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemModel() {
		return itemModel;
	}
	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	public String getItemSupplierId() {
		return itemSupplierId;
	}
	public void setItemSupplierId(String itemSupplierId) {
		this.itemSupplierId = itemSupplierId;
	}
	public String getItemManufacturerId() {
		return itemManufacturerId;
	}
	public void setItemManufacturerId(String itemManufacturerId) {
		this.itemManufacturerId = itemManufacturerId;
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

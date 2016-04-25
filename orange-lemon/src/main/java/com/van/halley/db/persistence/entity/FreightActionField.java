package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightActionField implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 字段名
	 */
	private String fieldName;
	/**
	 * 字段
	 */
	private String fieldColumn;
	/**
	 * 字段类型
	 */
	private String fieldType;
	/**
	 * 是否必须
	 */
	private String required;
	/**
	 * 可共享数据
	 */
	private String participate;
	/**
	 * 是否为可选值
	 */
	private String canSelect;
	private String vAttrId;
	private String vClsId;
	private String vColumn;
	private String vFilterId;
	/**
	 * 是否按箱(即不同集装箱有不同的值)
	 */
	private String forBox;
	/**
	 * 类型
	 */
	private FreightActionType freightActionType;
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
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldColumn() {
		return fieldColumn;
	}
	public void setFieldColumn(String fieldColumn) {
		this.fieldColumn = fieldColumn;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public FreightActionType getFreightActionType() {
		return freightActionType;
	}
	public void setFreightActionType(FreightActionType freightActionType) {
		this.freightActionType = freightActionType;
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
	public String getParticipate() {
		return participate;
	}
	public void setParticipate(String participate) {
		this.participate = participate;
	}
	public String getvAttrId() {
		return vAttrId;
	}
	public void setvAttrId(String vAttrId) {
		this.vAttrId = vAttrId;
	}
	public String getvClsId() {
		return vClsId;
	}
	public void setvClsId(String vClsId) {
		this.vClsId = vClsId;
	}
	public String getvColumn() {
		return vColumn;
	}
	public void setvColumn(String vColumn) {
		this.vColumn = vColumn;
	}
	public String getCanSelect() {
		return canSelect;
	}
	public void setCanSelect(String canSelect) {
		this.canSelect = canSelect;
	}
	public String getvFilterId() {
		return vFilterId;
	}
	public void setvFilterId(String vFilterId) {
		this.vFilterId = vFilterId;
	}
	public String getForBox() {
		return forBox;
	}
	public void setForBox(String forBox) {
		this.forBox = forBox;
	}
	
}

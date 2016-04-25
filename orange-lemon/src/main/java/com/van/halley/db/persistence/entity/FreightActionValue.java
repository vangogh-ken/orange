package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightActionValue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 字符串值(少)
	 */
	private String stringValue;
	/**
	 * 字符串(多)
	 */
	private String textValue;
	/**
	 * 浮点
	 */
	private double doubleValue;
	/**
	 * 整数
	 */
	private int intValue;
	/**
	 * 时间类型值
	 */
	private Date dateValue;
	/**
	 * 实际值
	 */
	private Object actualValue;
	/**
	 * 字段
	 */
	private FreightActionField freightActionField;
	/**
	 * 动作
	 */
	private FreightAction freightAction;
	/**
	 * 相关箱封
	 */
	private FreightOrderBox freightOrderBox;
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
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	public FreightActionField getFreightActionField() {
		return freightActionField;
	}
	public void setFreightActionField(FreightActionField freightActionField) {
		this.freightActionField = freightActionField;
	}
	public FreightAction getFreightAction() {
		return freightAction;
	}
	public void setFreightAction(FreightAction freightAction) {
		this.freightAction = freightAction;
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
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	public double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public FreightOrderBox getFreightOrderBox() {
		return freightOrderBox;
	}
	public void setFreightOrderBox(FreightOrderBox freightOrderBox) {
		this.freightOrderBox = freightOrderBox;
	}
	public Object getActualValue() {
		String fieldType = getFreightActionField().getFieldType();
		if("VARCHAR".equals(fieldType)){
			return getStringValue();
		}else if("TEXT".equals(fieldType)){
			return getTextValue();
		}else if("INT".equals(fieldType)){
			return getIntValue();
		}else if("DOUBLE".equals(fieldType)){
			return getDoubleValue();
		}else if("DATETIME".equals(fieldType) || "TIMESTAMP".equals(fieldType)){
			return getDateValue();
		}else{
			return null;
		}
	}
	
	public void setActualValue(Object actualValue) {
		this.actualValue = actualValue;
	}
	
}

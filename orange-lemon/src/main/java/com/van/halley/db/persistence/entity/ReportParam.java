package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表参数；在数据源中使用:paramColumn的形式取用
 * @author Think
 *
 */
public class ReportParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 参数名
	 */
	private String paramName;
	/**
	 * 参数字段
	 */
	private String paramColumn;
	/**
	 * 参数类型
	 */
	private String paramType;
	/**
	 * 字符串值
	 */
	private String stringValue;
	/**
	 * 整数值
	 */
	private int intValue;
	/**
	 * 小数值
	 */
	private double doubleValue;
	/**
	 * 时间值
	 */
	private Date dateValue;
	/**
	 * 默认值，修改默认值时，其自动就为正式值
	 */
	private String defaultValue;
	/**
	 * 是否必须
	 */
	private String required;
	/**
	 * 是否为可选值
	 */
	private String canSelect;
	private String vAttrId;
	private String vClsId;
	private String vColumn;
	private String vFilterId;
	private String reportTemplateId;
	
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
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamColumn() {
		return paramColumn;
	}
	public void setParamColumn(String paramColumn) {
		this.paramColumn = paramColumn;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getRequired() {
		return required;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public String getCanSelect() {
		return canSelect;
	}
	public void setCanSelect(String canSelect) {
		this.canSelect = canSelect;
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
	public String getvFilterId() {
		return vFilterId;
	}
	public void setvFilterId(String vFilterId) {
		this.vFilterId = vFilterId;
	}
	public String getReportTemplateId() {
		return reportTemplateId;
	}
	public void setReportTemplateId(String reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public int getIntValue() {
		return intValue;
	}
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	public double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 属性
 */
public class ValueAttribute implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 属性名
	 */
	private String attributeName;
	/**
	 * 属性字段
	 */
	private String columnName;
	/**
	 * 描述
	 */
	private String descn;
	/**
	 * 类型
	 */
	private ValueClass valueClass; 
	/**
	 * 状态
	 */
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getId() {
		return id;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public String getColumnName() {
		return columnName;
	}
	public String getDescn() {
		return descn;
	}
	public ValueClass getValueClass() {
		return valueClass;
	}
	public String getStatus() {
		return status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public void setValueClass(ValueClass valueClass) {
		this.valueClass = valueClass;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
}

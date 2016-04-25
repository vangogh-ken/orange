package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 各个类型的的值选项
 */
public class ValueDictionary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 值
	 */
	private String valueContent;
	/**
	 * 描述
	 */
	private String descn;
	/**
	 * 属性
	 */
	private ValueAttribute valueAttribute;
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
	public String getValueContent() {
		return valueContent;
	}
	public String getDescn() {
		return descn;
	}
	public ValueAttribute getValueAttribute() {
		return valueAttribute;
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
	public void setValueContent(String valueContent) {
		this.valueContent = valueContent;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public void setValueAttribute(ValueAttribute valueAttribute) {
		this.valueAttribute = valueAttribute;
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

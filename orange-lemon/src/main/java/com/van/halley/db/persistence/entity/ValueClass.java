package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 给定表名类型
 */
public class ValueClass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 类型名
	 */
	private String className;
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 描述
	 */
	private String descn;
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
	public String getClassName() {
		return className;
	}
	public String getTableName() {
		return tableName;
	}
	public String getDescn() {
		return descn;
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
	public void setId(String id) {
		this.id = id;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setDescn(String descn) {
		this.descn = descn;
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
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
}

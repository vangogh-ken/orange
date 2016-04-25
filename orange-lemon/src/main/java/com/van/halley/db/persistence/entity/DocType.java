package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 文档类型
 */
public class DocType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 如果为临时文件多少天之后自动删除
	 */
	private int limitDayCount;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 显示顺序
	 */
	private int displayIndex;
	
	
	public String getId() {
		return id;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getDescription() {
		return description;
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
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public int getLimitDayCount() {
		return limitDayCount;
	}
	public void setLimitDayCount(int limitDayCount) {
		this.limitDayCount = limitDayCount;
	}
}

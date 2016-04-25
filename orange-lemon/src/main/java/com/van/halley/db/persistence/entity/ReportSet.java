package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板可配置值
 * @author Think
 *
 */
public class ReportSet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 设置类型：空值替换；隐藏零值；隐藏重复值(横向)；隐藏重复值(纵向)；
	 * 行和列都是动态取值数据
	 */
	private String setType;
	/**
	 * T/F
	 */
	private String enable;
	/**
	 * 起始行
	 */
	private String firstRow;
	/**
	 * 截止行
	 */
	private String lastRow;
	/**
	 * 起始列
	 */
	private String firstColumn;
	/**
	 * 截止列
	 */
	private String lastColumn;
	/**
	 * 模板
	 */
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
	public String getSetType() {
		return setType;
	}
	public void setSetType(String setType) {
		this.setType = setType;
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
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getFirstRow() {
		return firstRow;
	}
	public void setFirstRow(String firstRow) {
		this.firstRow = firstRow;
	}
	public String getLastRow() {
		return lastRow;
	}
	public void setLastRow(String lastRow) {
		this.lastRow = lastRow;
	}
	public String getFirstColumn() {
		return firstColumn;
	}
	public void setFirstColumn(String firstColumn) {
		this.firstColumn = firstColumn;
	}
	public String getLastColumn() {
		return lastColumn;
	}
	public void setLastColumn(String lastColumn) {
		this.lastColumn = lastColumn;
	}
	public String getReportTemplateId() {
		return reportTemplateId;
	}
	public void setReportTemplateId(String reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	
}

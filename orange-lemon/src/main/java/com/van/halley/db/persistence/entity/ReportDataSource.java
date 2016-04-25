package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 报表数据源
 * @author Think
 *
 */
public class ReportDataSource implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 数据源名称
	 */
	private String dataSourceName;
	/**
	 * 数据源KEY
	 */
	private String dataSourceKey;
	/**
	 * 数据源SQL语句
	 */
	private String sqlText;
	/**
	 * 是否为共享KEY
	 */
	private String participate;
	/**
	 * 报表模板
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
	public String getDataSourceKey() {
		return dataSourceKey;
	}
	public void setDataSourceKey(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
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
	public String getDataSourceName() {
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	public String getSqlText() {
		return sqlText;
	}
	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}
	public String getReportTemplateId() {
		return reportTemplateId;
	}
	public void setReportTemplateId(String reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}
	public String getParticipate() {
		return participate;
	}
	public void setParticipate(String participate) {
		this.participate = participate;
	}
	
	
}

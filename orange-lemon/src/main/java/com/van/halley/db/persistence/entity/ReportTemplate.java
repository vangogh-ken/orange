package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportTemplate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 报表类别
	 */
	private ReportCategory reportCategory;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 模板类型，报表，普通
	 */
	private String templateType;
	/**
	 * 模板文件
	 */
	private String templateFile;
	/**
	 * 类型
	 */
	private String beanClass;
	/**
	 * 方法
	 */
	private String methodName;
	/**
	 * 图形路径
	 */
	private String graphUrl;
	
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
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
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
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public ReportCategory getReportCategory() {
		return reportCategory;
	}
	public void setReportCategory(ReportCategory reportCategory) {
		this.reportCategory = reportCategory;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getGraphUrl() {
		return graphUrl;
	}
	public void setGraphUrl(String graphUrl) {
		this.graphUrl = graphUrl;
	}
	
}

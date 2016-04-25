package com.van.halley.db.persistence.entity;

import java.util.Date;

/**
 * 模板可配置值
 * @author Think
 *
 */
public class ReportRegion {
	private String id;
	/**
	 * 是否为固定合并；
	 * 如为固定合并则只需要取4个INT类型的数据进行；
	 * 如不为固定合并，则动态取值进行合并
	 */
	private String regular;
	/**
	 * T/F
	 */
	private String enable;
	/**
	 * 起始行
	 */
	private int firstRowRegular;
	/**
	 * 截止行
	 */
	private int lastRowRegular;
	/**
	 * 起始列
	 */
	private int firstColumnRegular;
	/**
	 * 截止列
	 */
	private int lastColumnRegular;
	/**
	 * 起始行
	 */
	private String firstRowDynamic;
	/**
	 * 截止行
	 */
	private String lastRowDynamic;
	/**
	 * 起始列
	 */
	private String firstColumnDynamic;
	/**
	 * 截止列
	 */
	private String lastColumnDynamic;
	
	private ReportTemplate reportTemplate;
	
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
	public String getRegular() {
		return regular;
	}
	public void setRegular(String regular) {
		this.regular = regular;
	}
	public int getFirstRowRegular() {
		return firstRowRegular;
	}
	public void setFirstRowRegular(int firstRowRegular) {
		this.firstRowRegular = firstRowRegular;
	}
	public int getLastRowRegular() {
		return lastRowRegular;
	}
	public void setLastRowRegular(int lastRowRegular) {
		this.lastRowRegular = lastRowRegular;
	}
	public int getFirstColumnRegular() {
		return firstColumnRegular;
	}
	public void setFirstColumnRegular(int firstColumnRegular) {
		this.firstColumnRegular = firstColumnRegular;
	}
	public int getLastColumnRegular() {
		return lastColumnRegular;
	}
	public void setLastColumnRegular(int lastColumnRegular) {
		this.lastColumnRegular = lastColumnRegular;
	}
	public String getFirstRowDynamic() {
		return firstRowDynamic;
	}
	public void setFirstRowDynamic(String firstRowDynamic) {
		this.firstRowDynamic = firstRowDynamic;
	}
	public String getLastRowDynamic() {
		return lastRowDynamic;
	}
	public void setLastRowDynamic(String lastRowDynamic) {
		this.lastRowDynamic = lastRowDynamic;
	}
	public String getFirstColumnDynamic() {
		return firstColumnDynamic;
	}
	public void setFirstColumnDynamic(String firstColumnDynamic) {
		this.firstColumnDynamic = firstColumnDynamic;
	}
	public String getLastColumnDynamic() {
		return lastColumnDynamic;
	}
	public void setLastColumnDynamic(String lastColumnDynamic) {
		this.lastColumnDynamic = lastColumnDynamic;
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
	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}
	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	
	
}

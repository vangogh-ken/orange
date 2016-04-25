package com.van.halley.db.persistence.entity;

import java.io.Serializable;


/**
 * 报表配置中心
 * @author Think
 *
 */
public class ReportConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String reportKey;
	private String dataSql;
	private String type;
	private String title;
	private int width;
	private int height;
	private int start;
	private int end;
	private int step;
	private String unit;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataSql() {
		return dataSql;
	}
	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getReportKey() {
		return reportKey;
	}
	public void setReportKey(String reportKey) {
		this.reportKey = reportKey;
	}
}

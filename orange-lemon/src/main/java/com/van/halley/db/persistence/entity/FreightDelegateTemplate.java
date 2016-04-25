package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Think
 * 委托模板
 */
@JsonIgnoreProperties(value={"descn", "status", "createTime", "modifyTime", "displayIndex"})
public class FreightDelegateTemplate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 模板文件
	 */
	private String templateFile;
	/**
	 * 单元格参数,格式： 2,3,4,5;2,3,3,5
	 * 合并单元格需要的数据都为 int： first row, last row, first col, last col
	 * 其中箱需信息和箱封信息是动态的，因此也要把这个数据获取到；
	 * 箱封长度是BS, eg: BS+2
	 * 箱需长度是RS, eg: rs+3
	 */
	private String regionParam;
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
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public String getRegionParam() {
		return regionParam;
	}
	public void setRegionParam(String regionParam) {
		this.regionParam = regionParam;
	}
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class ReportIs implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 模板ID，一个模板对应一个流；新增时应删除原模板
	 */
	private String templateId;
	/**
	 * 存入库中的模板，templateFile获取不到文件就通过此从数据库中取出
	 * mybatis对blob文件对应类型为object
	 */
	private Object templateStream;
	
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
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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
	public Object getTemplateStream() {
		return templateStream;
	}
	public void setTemplateStream(Object templateStream) {
		this.templateStream = templateStream;
	}
	
	
}

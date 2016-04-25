package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 数据模板对Action字段关系
 */
public class FreightDataTemplateActionValue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 数据模板
	 */
	private String freightDataTemplateId;
	/**
	 * 动作值ID
	 */
	private String freightActionValueId;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FreightDataTemplateActionValue(){
		
	}
	
	public FreightDataTemplateActionValue(String freightDataTemplateId, String freightActionValueId){
		this.freightDataTemplateId = freightDataTemplateId;
		this.freightActionValueId = freightActionValueId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFreightDataTemplateId() {
		return freightDataTemplateId;
	}
	public void setFreightDataTemplateId(String freightDataTemplateId) {
		this.freightDataTemplateId = freightDataTemplateId;
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

	public String getFreightActionValueId() {
		return freightActionValueId;
	}

	public void setFreightActionValueId(String freightActionValueId) {
		this.freightActionValueId = freightActionValueId;
	}
	
}

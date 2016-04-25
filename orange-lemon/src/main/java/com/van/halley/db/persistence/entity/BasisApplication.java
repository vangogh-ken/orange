package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 实体数据的全局控制
 */
public class BasisApplication implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 应用名称
	 */
	private String applicationName;
	
	/**
	 * 只读控制
	 */
	private String readonly;
	/**
	 * 数据过滤
	 */
	private String filterText;
	/**
	 * 类型
	 */
	private BasisSubstanceType basisSubstanceType;
	/**
	 * 界面
	 */
	private BasisSchema basisSchema;
	
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
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getReadonly() {
		return readonly;
	}
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	public String getFilterText() {
		return filterText;
	}
	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
	public BasisSubstanceType getBasisSubstanceType() {
		return basisSubstanceType;
	}
	public void setBasisSubstanceType(BasisSubstanceType basisSubstanceType) {
		this.basisSubstanceType = basisSubstanceType;
	}
	public BasisSchema getBasisSchema() {
		return basisSchema;
	}
	public void setBasisSchema(BasisSchema basisSchema) {
		this.basisSchema = basisSchema;
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
	
	
}

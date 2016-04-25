package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


public class BasisMenu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 名称
	 */
	private String menuName;
	/**
	 * KEY
	 */
	private String menuKey;
	/**
	 * 类型
	 */
	private BasisSubstanceType basisSubstanceType;
	/**
	 * 应用
	 */
	private BasisApplication basisApplication;
	/**
	 * 是否可用
	 */
	private String enable;
	/**
	 * 上级资源
	 */
	private Resource parentResource;
	/**
	 * 数据填报
	 */
	private String configPrimeUrl;
	/**
	 * 数据管理
	 */
	private String configManageUrl;
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
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuKey() {
		return menuKey;
	}
	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}
	public BasisSubstanceType getBasisSubstanceType() {
		return basisSubstanceType;
	}
	public void setBasisSubstanceType(BasisSubstanceType basisSubstanceType) {
		this.basisSubstanceType = basisSubstanceType;
	}
	public BasisApplication getBasisApplication() {
		return basisApplication;
	}
	public void setBasisApplication(BasisApplication basisApplication) {
		this.basisApplication = basisApplication;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public Resource getParentResource() {
		return parentResource;
	}
	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}
	public String getConfigPrimeUrl() {
		return configPrimeUrl;
	}
	public void setConfigPrimeUrl(String configPrimeUrl) {
		this.configPrimeUrl = configPrimeUrl;
	}
	public String getConfigManageUrl() {
		return configManageUrl;
	}
	public void setConfigManageUrl(String configManageUrl) {
		this.configManageUrl = configManageUrl;
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

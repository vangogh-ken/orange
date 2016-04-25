package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class Resource implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 资源名称
	 */
	private String resourceName;
	/**
	 * 资源唯一KEY
	 */
	private String resourceKey;
	/**
	 * 资源地址
	 */
	private String resourceUrl;
	/**
	 * 资源类型：目录，菜单，按钮
	 */
	private String resourceType;
	/**
	 * 菜单图标
	 */
	private String icon;
	/**
	 * 是否启用
	 */
	private String enable;
	/**
	 * 上级资源
	 */
	private Resource parentResource;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

	public Resource() {
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getResourceType() {
		return resourceType;
	}


	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
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

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}


	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

}
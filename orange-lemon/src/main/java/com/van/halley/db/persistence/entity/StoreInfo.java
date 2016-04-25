package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class StoreInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** null. */
    private String id;

    /** null. */
    private String storeName;
    
    /** null. */
    private String storeType;

    /** null. */
    private String storeModel;

    /** null. */
    private String storePath;

    /** null. */
    private Long storeSize;

    /** null. */
    private Date createTime;
    
    /** null. */
    private Date modifyTime;

    /** null. */
    private String tenantId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getStoreModel() {
		return storeModel;
	}

	public void setStoreModel(String storeModel) {
		this.storeModel = storeModel;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public Long getStoreSize() {
		return storeSize;
	}

	public void setStoreSize(Long storeSize) {
		this.storeSize = storeSize;
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
    
    
}

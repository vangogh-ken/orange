package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 访问控制，每条共享内容则
 * @author anxinxx
 *
 */
public class DiskAcl implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 可访问人
     */
    private String accessaryId;
    /**
     * 共享
     */
    private String diskShareId;
    
    private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

    public DiskAcl(String accessaryId, String diskShareId) {
    	this.accessaryId = accessaryId;
    	this.diskShareId = diskShareId;
    }
    
    public DiskAcl(){
    	
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getAccessaryId() {
		return accessaryId;
	}

	public void setAccessaryId(String accessaryId) {
		this.accessaryId = accessaryId;
	}

	public String getDiskShareId() {
		return diskShareId;
	}

	public void setDiskShareId(String diskShareId) {
		this.diskShareId = diskShareId;
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

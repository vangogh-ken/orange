package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 分享连接
 * @author anxinxx
 *
 */
public class DiskShare implements Serializable {
    private static final long serialVersionUID = 0L;
    /** null. */
    private String id;
    /**
     * public private group
     */
    private String shareType;
    
    /** null. */
    private Date shareTime;
    
    /** null. */
    private Date expireTime;

    /** null. */
    private int countView;

    /** null. */
    private int countSave;

    /** null. */
    private int countDownload;
    
    /** null. */
    private User sharer;
    
    /** null. */
    private DiskInfo diskInfo;
    
    private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;

    public DiskShare() {
    }
    
    public DiskShare(DiskInfo diskInfo, String shareType){
    	this.diskInfo = diskInfo;
    	this.sharer = diskInfo.getCreator();
    	this.shareType = shareType;
    }
    
    public DiskShare(DiskInfo diskInfo, String shareType, Date shareTime, Date expireTime){
    	this.diskInfo = diskInfo;
    	this.sharer = diskInfo.getCreator();
    	this.shareType = shareType;
    	this.shareTime = shareTime;
    	this.expireTime = expireTime;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public User getSharer() {
		return sharer;
	}

	public void setSharer(User sharer) {
		this.sharer = sharer;
	}

	public DiskInfo getDiskInfo() {
		return diskInfo;
	}

	public void setDiskInfo(DiskInfo diskInfo) {
		this.diskInfo = diskInfo;
	}

	public int getCountView() {
		return countView;
	}

	public void setCountView(int countView) {
		this.countView = countView;
	}

	public int getCountSave() {
		return countSave;
	}

	public void setCountSave(int countSave) {
		this.countSave = countSave;
	}

	public int getCountDownload() {
		return countDownload;
	}

	public void setCountDownload(int countDownload) {
		this.countDownload = countDownload;
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
    
	public static class Type{
		public static final String PUBLIC = "public";
		public static final String GROUP = "group";
		public static final String PRIVATE = "private";
	}
}

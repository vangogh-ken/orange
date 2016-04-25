package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.van.halley.core.store.FileStoreHelper;

/**
 * 基本信息
 * @author anxinxx
 *
 */
public class DiskInfo implements Serializable {
	public static final String defaultFileVersion = "0";
	public static final String defaultDirSuffix = "default/user/%s";
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String fileName;
    /**
     * dir etc.
     */
    private String fileSuffix;
    /**
     * file path
     */
    private String fileRef;
    /**
     * default 0
     */
    private String fileVersion;
    /**
     * parent path
     */
    private String fileDir;
    /**
     * file size
     */
    private long fileSize;
    
    private User creator;
    
    private User modifier;
    
    private String model;
    
    private String key;
    
    private String descn;
	/**
	 * trash active
	 */
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public static class Status{
		public static final String ACTIVE = "active";
		public static final String TRASH = "trash";
	}

    public DiskInfo() {
    }
    
    public DiskInfo(String fileName, String fileRef, long fileSize, String fileDir){
    	this.fileName = fileName;
    	this.fileRef = fileRef;
    	this.fileSize = fileSize;
    	this.fileDir = fileDir;
    	this.fileSuffix = FileStoreHelper.getSuffix(fileName);
    	this.fileVersion = defaultFileVersion;
    	this.status = Status.ACTIVE;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileRef() {
		return fileRef;
	}

	public void setFileRef(String fileRef) {
		this.fileRef = fileRef;
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
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

	public String getModel() {
		return String.format(DiskInfo.defaultDirSuffix, this.getCreator().getId()) + this.getFileDir();
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getKey() {
		return this.getFileRef();
	}

	public void setKey(String key) {
		this.key = key;
	}
    
    

}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class DocInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 文件名
	 */
	private String docName;
	/**
	 * 使用UUID生成的文件名数据
	 */
	private String docData;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 是否临时文件
	 */
	private String eternal;
	/**
	 * 是否共享
	 */
	private String participate;
	/**
	 * 类型
	 */
	private DocType docType;
	/**
	 * 所属人
	 */
	private User owner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getParticipate() {
		return participate;
	}

	public void setParticipate(String participate) {
		this.participate = participate;
	}

	public String getDocName() {
		return docName;
	}

	public String getDocData() {
		return docData;
	}


	public DocType getDocType() {
		return docType;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public void setDocData(String docData) {
		this.docData = docData;
	}


	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getEternal() {
		return eternal;
	}

	public void setEternal(String eternal) {
		this.eternal = eternal;
	}

}

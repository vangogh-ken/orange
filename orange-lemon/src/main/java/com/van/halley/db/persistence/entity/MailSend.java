package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class MailSend implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String addressFrom;
	private String addressTo;
	private String addressCopy;
	private String subject;
	private String content;
	private String attachment;
	private String status;
	private String flag;
	private Date createTime;
	private Date sendTime;
	private String userId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddressFrom() {
		return addressFrom;
	}
	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}
	public String getAddressTo() {
		return addressTo;
	}
	public void setAddressTo(String addressTo) {
		this.addressTo = addressTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAddressCopy() {
		return addressCopy;
	}
	public void setAddressCopy(String addressCopy) {
		this.addressCopy = addressCopy;
	}
}

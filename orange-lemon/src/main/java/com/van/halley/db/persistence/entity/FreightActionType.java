package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"descn", "status", "createTime", "modifyTime", "displayIndex"})
public class FreightActionType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 名称
	 */
	private String typeName;
	/**
	 * 是否发送委托, 给具体action一个参考值
	 */
	private String delegate;
	/**
	 * 是否为内部动作, 给具体action一个参考值
	 */
	private String internal;
	/**
	 * 是否有填写内容, 给具体action一个参考值
	 */
	private String prime;
	/**
	 * 委派人
	 */
	private User assignee;
	/**
	 * 委托模板
	 */
	private FreightDelegateTemplate freightDelegateTemplate;
	private String scope;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	public List<FreightActionField> getFreightActionFields() {
		return freightActionFields;
	}
	public void setFreightActionFields(List<FreightActionField> freightActionFields) {
		this.freightActionFields = freightActionFields;
	}
	**/
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
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
	public FreightDelegateTemplate getFreightDelegateTemplate() {
		return freightDelegateTemplate;
	}
	public void setFreightDelegateTemplate(
			FreightDelegateTemplate freightDelegateTemplate) {
		this.freightDelegateTemplate = freightDelegateTemplate;
	}
	public String getDelegate() {
		return delegate;
	}
	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}
	public String getInternal() {
		return internal;
	}
	public void setInternal(String internal) {
		this.internal = internal;
	}
	public String getPrime() {
		return prime;
	}
	public void setPrime(String prime) {
		this.prime = prime;
	}
	public User getAssignee() {
		return assignee;
	}
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
}

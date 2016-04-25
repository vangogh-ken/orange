package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class CrmCustomerFollow implements Serializable{
	/**
	 * 公司全称  产品及货量	运输方式	联系人	职务	电话	上次拜访时间	本次拜访时间	下次拜访时间	拜访内容	销售机会及开发计划	开发建议
	 * 
	 * */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private String contactPerson;
	private String contactPosition;
	private String contactPhone;
	
	private Date lastFollowTime;
	private Date nextFollowTime;
	private Date currentFollowTime;
	
	private String followContent;
	private String chancePlan;
	private String chanceSuggest;
	
	private CrmCustomer crmCustomer;
	private OrgEntity orgEntity;
	private User follower;
	
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
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getContactPosition() {
		return contactPosition;
	}
	public void setContactPosition(String contactPosition) {
		this.contactPosition = contactPosition;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public Date getLastFollowTime() {
		return lastFollowTime;
	}
	public void setLastFollowTime(Date lastFollowTime) {
		this.lastFollowTime = lastFollowTime;
	}
	public Date getNextFollowTime() {
		return nextFollowTime;
	}
	public void setNextFollowTime(Date nextFollowTime) {
		this.nextFollowTime = nextFollowTime;
	}
	public Date getCurrentFollowTime() {
		return currentFollowTime;
	}
	public void setCurrentFollowTime(Date currentFollowTime) {
		this.currentFollowTime = currentFollowTime;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public String getChancePlan() {
		return chancePlan;
	}
	public void setChancePlan(String chancePlan) {
		this.chancePlan = chancePlan;
	}
	public String getChanceSuggest() {
		return chanceSuggest;
	}
	public void setChanceSuggest(String chanceSuggest) {
		this.chanceSuggest = chanceSuggest;
	}
	public CrmCustomer getCrmCustomer() {
		return crmCustomer;
	}
	public void setCrmCustomer(CrmCustomer crmCustomer) {
		this.crmCustomer = crmCustomer;
	}
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	public User getFollower() {
		return follower;
	}
	public void setFollower(User follower) {
		this.follower = follower;
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

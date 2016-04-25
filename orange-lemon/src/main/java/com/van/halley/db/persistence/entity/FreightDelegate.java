package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 委托
 */
public class FreightDelegate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 委托号
	 */
	private String delegateNumber;
	/**
	 * 执行状态
	 */
	private String executeStatus;
	/**
	 * 锁定状态
	 */
	private String lockStatus;
	/**
	 * 下委托时间
	 */
	private Date placeTime;
	/**
	 * 生成好的委托文件
	 */
	private String delegateFile;
	/**
	 * 动作
	 */
	private FreightAction freightAction;
	
	/**
	 * 委托接收单位(如果委外委托,则有此单位)
	 */
	private FreightPart freightPart;
	/**
	 * 委托接收部门(如果对内,则有此部门)
	 */
	private OrgEntity orgEntity;
	/**
	 * 委托处理人(如果对内,则有此设置)
	 */
	private User owner;
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
	public String getDelegateNumber() {
		return delegateNumber;
	}
	public void setDelegateNumber(String delegateNumber) {
		this.delegateNumber = delegateNumber;
	}
	public String getExecuteStatus() {
		return executeStatus;
	}
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	public Date getPlaceTime() {
		return placeTime;
	}
	public void setPlaceTime(Date placeTime) {
		this.placeTime = placeTime;
	}
	public FreightAction getFreightAction() {
		return freightAction;
	}
	public void setFreightAction(FreightAction freightAction) {
		this.freightAction = freightAction;
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
	public String getDelegateFile() {
		return delegateFile;
	}
	public void setDelegateFile(String delegateFile) {
		this.delegateFile = delegateFile;
	}
	public FreightPart getFreightPart() {
		return freightPart;
	}
	public void setFreightPart(FreightPart freightPart) {
		this.freightPart = freightPart;
	}
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
}

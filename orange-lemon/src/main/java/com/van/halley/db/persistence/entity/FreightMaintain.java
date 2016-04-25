package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * @author ken
 * 操作范围
 */
@JsonIgnoreProperties(value={"freightOrder", "descn", "status", "createTime", "modifyTime", "displayIndex"})
public class FreightMaintain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 操作名
	 */
	private FreightMaintainType freightMaintainType;
	/**
	 * 订单
	 */
	private FreightOrder freightOrder;
	/**
	 * 父操作
	 */
	private FreightMaintain parentMaintain;
	/**
	 * 动作
	 */
	private List<FreightAction> freightActions;
	/**
	 * 说明
	 */
	private String descn;
	/**
	 * 状态
	 */
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getId() {
		return id;
	}
	public FreightOrder getFreightOrder() {
		return freightOrder;
	}
	public FreightMaintain getParentMaintain() {
		return parentMaintain;
	}
	public List<FreightAction> getFreightActions() {
		return freightActions;
	}
	public String getDescn() {
		return descn;
	}
	public String getStatus() {
		return status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFreightOrder(FreightOrder freightOrder) {
		this.freightOrder = freightOrder;
	}
	public void setParentMaintain(FreightMaintain parentMaintain) {
		this.parentMaintain = parentMaintain;
	}
	public void setFreightActions(List<FreightAction> freightActions) {
		this.freightActions = freightActions;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public FreightMaintainType getFreightMaintainType() {
		return freightMaintainType;
	}
	public void setFreightMaintainType(FreightMaintainType freightMaintainType) {
		this.freightMaintainType = freightMaintainType;
	}
}

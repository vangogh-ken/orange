package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 数据模板,可通过配置的订单,复制订单所有的动作的数据
 */
public class FreightDataTemplate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 此模板来自于
	 */
	private FreightAction freightAction;
	/**
	 * 所有者
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
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
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
	public FreightAction getFreightAction() {
		return freightAction;
	}
	public void setFreightAction(FreightAction freightAction) {
		this.freightAction = freightAction;
	}
	
	
}

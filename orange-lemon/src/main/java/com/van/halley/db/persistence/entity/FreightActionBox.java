package com.van.halley.db.persistence.entity;

import java.util.Date;

/**
 * @author Think
 * 动作与箱封关系
 */
public class FreightActionBox {
	private String id;
	/**
	 * 动作
	 */
	private String freightActionId;
	/**
	 * 箱封
	 */
	private String freightOrderBoxId;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FreightActionBox(){
		
	}
	public FreightActionBox(String freightActionId, String freightOrderBoxId){
		this.freightActionId = freightActionId;
		this.freightOrderBoxId = freightOrderBoxId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFreightActionId() {
		return freightActionId;
	}
	public void setFreightActionId(String freightActionId) {
		this.freightActionId = freightActionId;
	}
	public String getFreightOrderBoxId() {
		return freightOrderBoxId;
	}
	public void setFreightOrderBoxId(String freightOrderBoxId) {
		this.freightOrderBoxId = freightOrderBoxId;
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

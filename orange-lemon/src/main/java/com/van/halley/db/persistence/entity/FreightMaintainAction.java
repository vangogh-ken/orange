package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ken
 * 操作类型与动作类型之间关系
 */
public class FreightMaintainAction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 操作类型
	 */
	private String freightMaintainTypeId;
	/**
	 * 动作类型
	 */
	private String freightActionTypeId;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FreightMaintainAction(){
		
	}
	
	public FreightMaintainAction(String freightMaintainTypeId, String freightActionTypeId){
		this.freightMaintainTypeId = freightMaintainTypeId;
		this.freightActionTypeId = freightActionTypeId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getFreightMaintainTypeId() {
		return freightMaintainTypeId;
	}
	public void setFreightMaintainTypeId(String freightMaintainTypeId) {
		this.freightMaintainTypeId = freightMaintainTypeId;
	}
	public String getFreightActionTypeId() {
		return freightActionTypeId;
	}
	public void setFreightActionTypeId(String freightActionTypeId) {
		this.freightActionTypeId = freightActionTypeId;
	}
	
	
}

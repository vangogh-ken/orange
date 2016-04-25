package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 按箱的费用与箱封关联
 * @author ken
 *
 */
public class FreightExpenseBox implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 费用ID
	 */
	private String freightExpenseId;
	/**
	 * 箱封ID
	 */
	private String freightOrderBoxId;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FreightExpenseBox(){
		
	}
	
	public FreightExpenseBox(String freightExpenseId, String freightOrderBoxId){
		this.freightExpenseId = freightExpenseId;
		this.freightOrderBoxId = freightOrderBoxId;
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
	public String getFreightExpenseId() {
		return freightExpenseId;
	}
	public void setFreightExpenseId(String freightExpenseId) {
		this.freightExpenseId = freightExpenseId;
	}
	public String getFreightOrderBoxId() {
		return freightOrderBoxId;
	}
	public void setFreightOrderBoxId(String freightOrderBoxId) {
		this.freightOrderBoxId = freightOrderBoxId;
	}
	
	
}

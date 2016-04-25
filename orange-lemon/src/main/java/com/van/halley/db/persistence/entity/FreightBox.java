package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 集装箱
 */
public class FreightBox implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 箱号
	 */
	private String boxNumber;
	/**
	 * 箱型
	 */
	private String boxType;
	/**
	 * 箱属
	 */
	private String boxBelong;
	/**
	 * 箱况
	 */
	private String boxCondition;
	/**
	 * 自有箱,外借箱
	 */
	private String boxSource;
	/**
	 * 标箱数
	 */
	private int teuCount;
	/**
	 * 说明
	 */
	private String descn;
	/**
	 * 空重状态
	 */
	private String emptyStatus;
	/**
	 * 进出状态
	 */
	private String inoutStatus;
	/**
	 * 放箱状态
	 */
	private String putStatus;
	/**
	 * 事件状态
	 */
	private String eventStatus;
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
	public String getBoxNumber() {
		return boxNumber;
	}
	public String getBoxType() {
		return boxType;
	}
	public String getBoxBelong() {
		return boxBelong;
	}
	public String getBoxCondition() {
		return boxCondition;
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
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public void setBoxBelong(String boxBelong) {
		this.boxBelong = boxBelong;
	}
	public void setBoxCondition(String boxCondition) {
		this.boxCondition = boxCondition;
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
	public String getEmptyStatus() {
		return emptyStatus;
	}
	public void setEmptyStatus(String emptyStatus) {
		this.emptyStatus = emptyStatus;
	}
	public String getInoutStatus() {
		return inoutStatus;
	}
	public void setInoutStatus(String inoutStatus) {
		this.inoutStatus = inoutStatus;
	}
	public String getPutStatus() {
		return putStatus;
	}
	public void setPutStatus(String putStatus) {
		this.putStatus = putStatus;
	}
	public String getBoxSource() {
		return boxSource;
	}
	public void setBoxSource(String boxSource) {
		this.boxSource = boxSource;
	}
	
	public void setTeuCount(int teuCount) {
		this.teuCount = teuCount;
	}
	public int getTeuCount() {
		int count = 0;
		if(boxType.startsWith("20")){
			count = 1;
		}else if(boxType.startsWith("40")){
			count = 2;
		}else{
			count = 1;
		}
		return count;
	}
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	
}

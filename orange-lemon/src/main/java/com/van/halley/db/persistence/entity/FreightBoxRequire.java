package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Think
 * 每个订单对集装箱的要求
 */
public class FreightBoxRequire implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 集装箱来源
	 */
	private String boxSource;
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
	 * 箱量
	 */
	private int boxCount;
	/**
	 * 提单号
	 */
	private String blNo;
	/**
	 * 起始地
	 */
	private String beginStation;
	/**
	 * 终止地
	 */
	private String arriveStation;
	/**
	 * 订单
	 */
	private FreightOrder freightOrder;
	
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
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public String getBoxBelong() {
		return boxBelong;
	}
	public void setBoxBelong(String boxBelong) {
		this.boxBelong = boxBelong;
	}
	public String getBoxCondition() {
		return boxCondition;
	}
	public void setBoxCondition(String boxCondition) {
		this.boxCondition = boxCondition;
	}
	@JsonIgnore
	public FreightOrder getFreightOrder() {
		return freightOrder;
	}
	public void setFreightOrder(FreightOrder freightOrder) {
		this.freightOrder = freightOrder;
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
	
	public String getBoxSource() {
		return boxSource;
	}
	public void setBoxSource(String boxSource) {
		this.boxSource = boxSource;
	}
	public String getBeginStation() {
		return beginStation;
	}
	public void setBeginStation(String beginStation) {
		this.beginStation = beginStation;
	}
	public String getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}
	public int getBoxCount() {
		return boxCount;
	}
	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}
	public String getBlNo() {
		return blNo;
	}
	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}
	
}

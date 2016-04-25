package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 订单与集装箱关系
 */
public class FreightOrderBox implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 重量
	 */
	//private double weight;
	/**
	 * 体积
	 */
	//private double capacity;
	/**
	 * 箱所在地，提箱地
	 */
	private String location;
	/**
	 * 集装箱
	 */
	private FreightBox freightBox;
	/**
	 * 封号
	 */
	private FreightSeal freightSeal;
	/**
	 * 集装箱要求
	 */
	private FreightBoxRequire freightBoxRequire;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	/*public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}*/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FreightBox getFreightBox() {
		return freightBox;
	}
	public void setFreightBox(FreightBox freightBox) {
		this.freightBox = freightBox;
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
	public FreightBoxRequire getFreightBoxRequire() {
		return freightBoxRequire;
	}
	public void setFreightBoxRequire(FreightBoxRequire freightBoxRequire) {
		this.freightBoxRequire = freightBoxRequire;
	}
	public FreightSeal getFreightSeal() {
		return freightSeal;
	}
	public void setFreightSeal(FreightSeal freightSeal) {
		this.freightSeal = freightSeal;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}

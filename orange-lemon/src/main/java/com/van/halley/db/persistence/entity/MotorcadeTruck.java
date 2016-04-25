package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 货车
 * @author Think
 *
 */
public class MotorcadeTruck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 种类
	 */
	private String truckCategory;
	/**
	 * 车型
	 */
	private String truckType;
	/**
	 * 车牌号
	 */
	private String headstockNumber;
	/**
	 * 挂车号
	 */
	private String trailerNumber;
	/**
	 * 购进款
	 */
	private double headstockFund;
	/**
	 * 购进款
	 */
	private double trailerFund;
	/**
	 * 月折旧
	 */
	private double headstockDepreciation;
	/**
	 * 月折旧
	 */
	private double trailerDepreciation;
	/**
	 * 生产厂家
	 */
	private String manufacturer;
	/**
	 * 购进时间
	 */
	private Date purchaseTime;
	/**
	 * 年审时间
	 */
	private Date annualTime;
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
	public String getTruckType() {
		return truckType;
	}
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getPurchaseTime() {
		return purchaseTime;
	}
	public void setPurchaseTime(Date purchaseTime) {
		this.purchaseTime = purchaseTime;
	}
	public Date getAnnualTime() {
		return annualTime;
	}
	public void setAnnualTime(Date annualTime) {
		this.annualTime = annualTime;
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
	public String getHeadstockNumber() {
		return headstockNumber;
	}
	public void setHeadstockNumber(String headstockNumber) {
		this.headstockNumber = headstockNumber;
	}
	public String getTrailerNumber() {
		return trailerNumber;
	}
	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}
	public double getHeadstockFund() {
		return headstockFund;
	}
	public void setHeadstockFund(double headstockFund) {
		this.headstockFund = headstockFund;
	}
	public double getTrailerFund() {
		return trailerFund;
	}
	public void setTrailerFund(double trailerFund) {
		this.trailerFund = trailerFund;
	}
	public double getHeadstockDepreciation() {
		return headstockDepreciation;
	}
	public void setHeadstockDepreciation(double headstockDepreciation) {
		this.headstockDepreciation = headstockDepreciation;
	}
	public double getTrailerDepreciation() {
		return trailerDepreciation;
	}
	public void setTrailerDepreciation(double trailerDepreciation) {
		this.trailerDepreciation = trailerDepreciation;
	}
	public String getTruckCategory() {
		return truckCategory;
	}
	public void setTruckCategory(String truckCategory) {
		this.truckCategory = truckCategory;
	}
	
	
}

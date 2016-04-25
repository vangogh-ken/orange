package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 各种通行费
 * @author Think
 *
 */
public class MotorcadeToll implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 车型
	 */
	private String truckType;
	/**
	 * 总重
	 */
	private double totalWeight;
	/**
	 * 进站
	 */
	private String beginStation;
	/**
	 * 进站时间
	 */
	private Date beginTime;
	/**
	 * 出站
	 */
	private String arriveStation;
	/**
	 * 出站时间
	 */
	private Date arriveTime;
	
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 汇率
	 */
	private double exchangeRate;
	/**
	 * 金额
	 */
	private double moneyCount;
	/**
	 * 票种
	 */
	private FasInvoiceType fasInvoiceType;
	/**
	 * 货车
	 */
	private MotorcadeTruck motorcadeTruck;
	/**
	 * 司机
	 */
	private MotorcadeDriver motorcadeDriver;
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
	public String getBeginStation() {
		return beginStation;
	}
	public void setBeginStation(String beginStation) {
		this.beginStation = beginStation;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public String getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public double getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(double moneyCount) {
		this.moneyCount = moneyCount;
	}
	public FasInvoiceType getFasInvoiceType() {
		return fasInvoiceType;
	}
	public void setFasInvoiceType(FasInvoiceType fasInvoiceType) {
		this.fasInvoiceType = fasInvoiceType;
	}
	public MotorcadeTruck getMotorcadeTruck() {
		return motorcadeTruck;
	}
	public void setMotorcadeTruck(MotorcadeTruck motorcadeTruck) {
		this.motorcadeTruck = motorcadeTruck;
	}
	public MotorcadeDriver getMotorcadeDriver() {
		return motorcadeDriver;
	}
	public void setMotorcadeDriver(MotorcadeDriver motorcadeDriver) {
		this.motorcadeDriver = motorcadeDriver;
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
	public String getTruckType() {
		return truckType;
	}
	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	
}

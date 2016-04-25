package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 加油
 * @author Think
 *
 */
/**
 * @author Think
 *
 */
public class MotorcadePetrol implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 加油升
	 */
	private double lubricateCapacity;
	/**
	 * 加油时间
	 */
	private Date lubricateTime;
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
	 * 当前公里数
	 */
	private double kilometerCount;
	/**
	 * 上次油耗
	 */
	private double fuelConsumptionLast;
	/**
	 * 月均
	 */
	private double fuelConsumptionMonth;
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
	public double getLubricateCapacity() {
		return lubricateCapacity;
	}
	public void setLubricateCapacity(double lubricateCapacity) {
		this.lubricateCapacity = lubricateCapacity;
	}
	public Date getLubricateTime() {
		return lubricateTime;
	}
	public void setLubricateTime(Date lubricateTime) {
		this.lubricateTime = lubricateTime;
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
	public double getKilometerCount() {
		return kilometerCount;
	}
	public void setKilometerCount(double kilometerCount) {
		this.kilometerCount = kilometerCount;
	}
	public double getFuelConsumptionLast() {
		return fuelConsumptionLast;
	}
	public void setFuelConsumptionLast(double fuelConsumptionLast) {
		this.fuelConsumptionLast = fuelConsumptionLast;
	}
	public double getFuelConsumptionMonth() {
		return fuelConsumptionMonth;
	}
	public void setFuelConsumptionMonth(double fuelConsumptionMonth) {
		this.fuelConsumptionMonth = fuelConsumptionMonth;
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
	
	

}

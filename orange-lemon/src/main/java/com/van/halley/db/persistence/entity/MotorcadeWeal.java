package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class MotorcadeWeal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 福利
	 */
	private String wealType;
	/**
	 * 时间
	 */
	private Date wealTime;
	/**
	 * 收付
	 */
	private String incomeOrExpense;
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
	 * 司机
	 */
	private MotorcadeDriver motorcadeDriver;
	/**
	 * 车辆
	*/ 
	private MotorcadeTruck motorcadeTruck;
	
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
	public String getWealType() {
		return wealType;
	}
	public void setWealType(String wealType) {
		this.wealType = wealType;
	}
	public Date getWealTime() {
		return wealTime;
	}
	public void setWealTime(Date wealTime) {
		this.wealTime = wealTime;
	}
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
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
	public MotorcadeDriver getMotorcadeDriver() {
		return motorcadeDriver;
	}
	public void setMotorcadeDriver(MotorcadeDriver motorcadeDriver) {
		this.motorcadeDriver = motorcadeDriver;
	}
	public MotorcadeTruck getMotorcadeTruck() {
		return motorcadeTruck;
	}
	public void setMotorcadeTruck(MotorcadeTruck motorcadeTruck) {
		this.motorcadeTruck = motorcadeTruck;
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

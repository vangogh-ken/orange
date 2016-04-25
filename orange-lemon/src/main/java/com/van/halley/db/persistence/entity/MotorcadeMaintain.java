package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 车队维修
 * @author Think
 *
 */
/**
 * @author Think
 *
 */
public class MotorcadeMaintain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 维修项目
	 */
	private String maintainItem;
	/**
	 * 计价单位：次、个、件、樋、支、根、台、圈
	 */
	private String maintainUnit;
	/**
	 * 数量
	 */
	private double maintainCount;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 汇率
	 */
	private double exchangeRate;
	/**
	 * 单价
	 */
	private double moneyCount;
	/**
	 * 总额
	 */
	private double moneyAmount;
	/**
	 * 票种
	 */
	private FasInvoiceType fasInvoiceType;
	/**
	 * 维修时间
	 */
	private Date maintainTime;
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
	public String getMaintainUnit() {
		return maintainUnit;
	}
	public void setMaintainUnit(String maintainUnit) {
		this.maintainUnit = maintainUnit;
	}
	public double getMaintainCount() {
		return maintainCount;
	}
	public void setMaintainCount(double maintainCount) {
		this.maintainCount = maintainCount;
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
	public double getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(double moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	public FasInvoiceType getFasInvoiceType() {
		return fasInvoiceType;
	}
	public void setFasInvoiceType(FasInvoiceType fasInvoiceType) {
		this.fasInvoiceType = fasInvoiceType;
	}
	public Date getMaintainTime() {
		return maintainTime;
	}
	public void setMaintainTime(Date maintainTime) {
		this.maintainTime = maintainTime;
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
	public MotorcadeDriver getMotorcadeDriver() {
		return motorcadeDriver;
	}
	public void setMotorcadeDriver(MotorcadeDriver motorcadeDriver) {
		this.motorcadeDriver = motorcadeDriver;
	}
	public String getMaintainItem() {
		return maintainItem;
	}
	public void setMaintainItem(String maintainItem) {
		this.maintainItem = maintainItem;
	}
	
	
}

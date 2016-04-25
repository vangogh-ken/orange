package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 派车单，业务
 * @author Think
 *
 */
public class MotorcadeDispatch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 货车
	 */
	private String motorcadeTruck;
	/**
	 * 司机
	 */
	private String motorcadeDriver;
	/**
	 * 派单号
	 */
	private String dispatchNumber;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 委托单位
	 */
	private String delegatePart;
	/**
	 * 品名
	 */
	private String cargoName;
	/**
	 * 货物毛件体
	 */
	private String cargoUnit;
	private String cargoWeight;
	private String cargoCapacity;
	/**
	 * 箱型
	 */
	private String boxType;
	/**
	 * 箱量
	 */
	private int boxCount;
	/**
	 * 箱号
	 */
	private String boxNumber;
	/**
	 * 委托时间
	 */
	private Date delegateTime;
	/**
	 * 提箱地
	 */
	private String pickAddress;
	/**
	 * 提箱时间
	 */
	private Date pickTime;
	/**
	 * 装/卸箱地
	 */
	private String loadAddress;
	/**
	 * 装/卸箱时间
	 */
	private Date loadTime;
	/**
	 * 还箱地
	 */
	private String returnAddress;
	/**
	 * 还箱时间
	 */
	private Date returnTime;
	
	/**
	 * 出发地
	 */
	private String departure;
	/**
	 * 目的地
	 */
	private String destination;
	/**
	 * 提成
	 */
	private double dispatchDeduct;
	/**
	 * 涉及费用
	 */
	private List<MotorcadeFee> motorcadeFees;
	/**
	 * 收支差
	 */
	private double balance;
	/**
	 * 收入
	 */
	private double amountIncome;
	/**
	 * 税金
	 */
	private double amountTaxation;
	
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
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getDelegatePart() {
		return delegatePart;
	}
	public void setDelegatePart(String delegatePart) {
		this.delegatePart = delegatePart;
	}
	public String getCargoName() {
		return cargoName;
	}
	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}
	public String getCargoUnit() {
		return cargoUnit;
	}
	public void setCargoUnit(String cargoUnit) {
		this.cargoUnit = cargoUnit;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public int getBoxCount() {
		return boxCount;
	}
	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}
	public String getBoxNumber() {
		return boxNumber;
	}
	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}
	public Date getDelegateTime() {
		return delegateTime;
	}
	public void setDelegateTime(Date delegateTime) {
		this.delegateTime = delegateTime;
	}
	public String getPickAddress() {
		return pickAddress;
	}
	public void setPickAddress(String pickAddress) {
		this.pickAddress = pickAddress;
	}
	public Date getPickTime() {
		return pickTime;
	}
	public void setPickTime(Date pickTime) {
		this.pickTime = pickTime;
	}
	public String getLoadAddress() {
		return loadAddress;
	}
	public void setLoadAddress(String loadAddress) {
		this.loadAddress = loadAddress;
	}
	public Date getLoadTime() {
		return loadTime;
	}
	public void setLoadTime(Date loadTime) {
		this.loadTime = loadTime;
	}
	public String getReturnAddress() {
		return returnAddress;
	}
	public void setReturnAddress(String returnAddress) {
		this.returnAddress = returnAddress;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
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
	public String getDispatchNumber() {
		return dispatchNumber;
	}
	public void setDispatchNumber(String dispatchNumber) {
		this.dispatchNumber = dispatchNumber;
	}
	public String getCargoWeight() {
		return cargoWeight;
	}
	public void setCargoWeight(String cargoWeight) {
		this.cargoWeight = cargoWeight;
	}
	public String getCargoCapacity() {
		return cargoCapacity;
	}
	public void setCargoCapacity(String cargoCapacity) {
		this.cargoCapacity = cargoCapacity;
	}
	public String getMotorcadeTruck() {
		return motorcadeTruck;
	}
	public void setMotorcadeTruck(String motorcadeTruck) {
		this.motorcadeTruck = motorcadeTruck;
	}
	public String getMotorcadeDriver() {
		return motorcadeDriver;
	}
	public void setMotorcadeDriver(String motorcadeDriver) {
		this.motorcadeDriver = motorcadeDriver;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getDispatchDeduct() {
		return dispatchDeduct;
	}
	public void setDispatchDeduct(double dispatchDeduct) {
		this.dispatchDeduct = dispatchDeduct;
	}
	@JsonIgnore
	public List<MotorcadeFee> getMotorcadeFees() {
		return motorcadeFees;
	}
	public void setMotorcadeFees(List<MotorcadeFee> motorcadeFees) {
		this.motorcadeFees = motorcadeFees;
	}
	
	public double getBalance() {
		double count = 0;
		List<MotorcadeFee> motorcadeFees = this.getMotorcadeFees();
		if(motorcadeFees != null && !motorcadeFees.isEmpty()){
			for(MotorcadeFee motorcadeFee : motorcadeFees){
				if("收".equals(motorcadeFee.getIncomeOrExpense())){
					count += (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate())/(1 + motorcadeFee.getFasInvoiceType().getTaxRate());
				}else if("付".equals(motorcadeFee.getIncomeOrExpense())){
					count += 0 - (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate())/(1 + motorcadeFee.getFasInvoiceType().getTaxRate());
				}
			}
		}
		return count;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getAmountIncome() {
		double count = 0;
		List<MotorcadeFee> motorcadeFees = this.getMotorcadeFees();
		if(motorcadeFees != null && !motorcadeFees.isEmpty()){
			for(MotorcadeFee motorcadeFee : motorcadeFees){
				if("收".equals(motorcadeFee.getIncomeOrExpense())){
					count += (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate());
				}else if("付".equals(motorcadeFee.getIncomeOrExpense())){
					count += 0 - (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate());
				}
			}
		}
		return count;
	}
	public void setAmountIncome(double amountIncome) {
		this.amountIncome = amountIncome;
	}
	public double getAmountTaxation() {
		double count = 0;
		List<MotorcadeFee> motorcadeFees = this.getMotorcadeFees();
		if(motorcadeFees != null && !motorcadeFees.isEmpty()){
			for(MotorcadeFee motorcadeFee : motorcadeFees){
				if("收".equals(motorcadeFee.getIncomeOrExpense())){
					count += 0 - (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate() - (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate())/(1 + motorcadeFee.getFasInvoiceType().getTaxRate()));
				}else if("付".equals(motorcadeFee.getIncomeOrExpense())){
					count += (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate() - (motorcadeFee.getMoneyCount()*motorcadeFee.getExchangeRate())/(1 + motorcadeFee.getFasInvoiceType().getTaxRate()));
				}
			}
		}
		return count;
	}
	public void setAmountTaxation(double amountTaxation) {
		this.amountTaxation = amountTaxation;
	}
	
	
}

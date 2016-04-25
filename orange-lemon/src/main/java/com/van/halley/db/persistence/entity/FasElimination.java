package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 发票销账的记录
 * @author Think
 *
 */
public class FasElimination implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 收/付
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
	 * 销账金额
	 */
	private double reconcileCount;
	/**
	 * 对应的开票任务
	 */
	private FreightInvoice freightInvoice;
	/**
	 * 受款银行账户
	 */
	private FasAccount fasAccount;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public FasAccount getFasAccount() {
		return fasAccount;
	}
	public void setFasAccount(FasAccount fasAccount) {
		this.fasAccount = fasAccount;
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
	public FreightInvoice getFreightInvoice() {
		return freightInvoice;
	}
	public void setFreightInvoice(FreightInvoice freightInvoice) {
		this.freightInvoice = freightInvoice;
	}
	public double getReconcileCount() {
		return reconcileCount;
	}
	public void setReconcileCount(double reconcileCount) {
		this.reconcileCount = reconcileCount;
	}
	
	
}

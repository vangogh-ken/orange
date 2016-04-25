package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 销账记录，包括收款或付款
 * @author ken
 *
 */
public class FasReconcile implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 收支
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
	 * 一般为freightInvoieId
	 */
	private String sourceId;
	/**
	 * 如果为收，则是银行收款ID；如果为付，则是付款申请单ID。
	 */
	private String targetId;
	
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
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
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
	
	
	
	/****************
	private String id;
	 * 收/付
	private String incomeOrExpense;
	 * 销账方式
	private String reconcileType;
	 * 销账单位(公司对外收付单位)
	private String reconcilePart;
	 * 销账人员(公司内部报销或者借款销账对应人员)
	private String reconcileUser;
	 * 币种
	private String currency;
	 * 汇率
	private double exchangeRate;
	 * 金额
	private double moneyCount;
	 * 发票任务
	private FreightInvoice freightInvoice;
	 受款银行账户
	private FasAccount fasAccount;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public String getReconcileUser() {
		return reconcileUser;
	}
	public void setReconcileUser(String reconcileUser) {
		this.reconcileUser = reconcileUser;
	}
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public FreightInvoice getFreightInvoice() {
		return freightInvoice;
	}
	public void setFreightInvoice(FreightInvoice freightInvoice) {
		this.freightInvoice = freightInvoice;
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
	public String getReconcileType() {
		return reconcileType;
	}
	public void setReconcileType(String reconcileType) {
		this.reconcileType = reconcileType;
	}
	public String getReconcilePart() {
		return reconcilePart;
	}
	public void setReconcilePart(String reconcilePart) {
		this.reconcilePart = reconcilePart;
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
	***********/
	
}

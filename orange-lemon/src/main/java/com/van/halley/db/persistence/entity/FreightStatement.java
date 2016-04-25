package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Think
 * 对账单
 * 对账单可能包含人民币的费用，美元的费用，也仅包含人民币和美元，其他币种费用折合成美元进行录入；
 * 人民币费用和美元费用可同时存在；
 * 开票时，人民币的金额对应开人民币的发票，美元金额开美元发票，且金额之间不会出现交叉的情况。
 */
public class FreightStatement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 账单号
	 */
	private String statementNumber;
	/**
	 * 收/付
	 */
	private String incomeOrExpense;
	/**
	 * 人民币币种,相当于给个标记，如果有此标记，则就计算金额
	 */
	private String currencyRmb;
	/**
	 * 人民币汇率
	 */
	private double exchangeRateRmb;
	/**
	 * 人民币金额
	 */
	private double moneyCountRmb;
	/**
	 * 已开票
	 */
	private double eliminateCountRmb;
	/**
	 * 未开票
	 */
	private double remainCountRmb;
	/**
	 * 美元币种,相当于给个标记，如果有此标记，则就计算金额
	 */
	private String currencyDollar;
	/**
	 * 美元汇率
	 */
	private double exchangeRateDollar;
	/**
	 * 美元金额
	 */
	private double moneyCountDollar;
	/**
	 * 已开票
	 */
	private double eliminateCountDollar;
	/**
	 * 未开票
	 */
	private double remainCountDollar;
	/**
	 * 发票票种
	 */
	private FasInvoiceType fasInvoiceType;
	/**
	 * 收付费用单位
	 */
	private FreightPart freightPart;
	/**
	 * 账单关联费用
	 */
	private List<FreightExpense> freightExpenses;
	/**
	 * 创建者
	 */
	private User creator;
	/**
	 * 创建者部门
	 */
	private OrgEntity orgEntity;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	public FasInvoiceType getFasInvoiceType() {
		return fasInvoiceType;
	}
	public void setFasInvoiceType(FasInvoiceType fasInvoiceType) {
		this.fasInvoiceType = fasInvoiceType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatementNumber() {
		return statementNumber;
	}
	public void setStatementNumber(String statementNumber) {
		this.statementNumber = statementNumber;
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
	public String getCurrencyDollar() {
		return currencyDollar;
	}
	public void setCurrencyDollar(String currencyDollar) {
		this.currencyDollar = currencyDollar;
	}
	public double getMoneyCountDollar() {
		return moneyCountDollar;
	}
	public void setMoneyCountDollar(double moneyCountDollar) {
		this.moneyCountDollar = moneyCountDollar;
	}
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public String getCurrencyRmb() {
		return currencyRmb;
	}
	public void setCurrencyRmb(String currencyRmb) {
		this.currencyRmb = currencyRmb;
	}
	public double getMoneyCountRmb() {
		return moneyCountRmb;
	}
	public void setMoneyCountRmb(double moneyCountRmb) {
		this.moneyCountRmb = moneyCountRmb;
	}
	public double getExchangeRateRmb() {
		return exchangeRateRmb;
	}
	public void setExchangeRateRmb(double exchangeRateRmb) {
		this.exchangeRateRmb = exchangeRateRmb;
	}
	public double getExchangeRateDollar() {
		return exchangeRateDollar;
	}
	public void setExchangeRateDollar(double exchangeRateDollar) {
		this.exchangeRateDollar = exchangeRateDollar;
	}
	public double getEliminateCountRmb() {
		return eliminateCountRmb;
	}
	public void setEliminateCountRmb(double eliminateCountRmb) {
		this.eliminateCountRmb = eliminateCountRmb;
	}
	public double getRemainCountRmb() {
		return remainCountRmb;
	}
	public void setRemainCountRmb(double remainCountRmb) {
		this.remainCountRmb = remainCountRmb;
	}
	public double getEliminateCountDollar() {
		return eliminateCountDollar;
	}
	public void setEliminateCountDollar(double eliminateCountDollar) {
		this.eliminateCountDollar = eliminateCountDollar;
	}
	public double getRemainCountDollar() {
		return remainCountDollar;
	}
	public void setRemainCountDollar(double remainCountDollar) {
		this.remainCountDollar = remainCountDollar;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	@JsonIgnore
	public List<FreightExpense> getFreightExpenses() {
		return freightExpenses;
	}
	public void setFreightExpenses(List<FreightExpense> freightExpenses) {
		this.freightExpenses = freightExpenses;
	}
	public FreightPart getFreightPart() {
		return freightPart;
	}
	public void setFreightPart(FreightPart freightPart) {
		this.freightPart = freightPart;
	}
	
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ken
 * 报价，账期数据应当在生成账单时，从系统其他数据中提取。
 * 
 * 对于特殊费用，通过是否按照实际成本进行计算进行判断。如果T，则最终对账单金额的该费用则是按照actualCount进行计算；
 * 且对此费用的报价财务单独对此报价的actualCount进行修改。
 */
public class FreightPrice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 收付
	 */
	private String incomeOrExpense;
	/**
	 * 是否为固定费用
	 */
	private String regular;
	/**
	 * 是否按照成本实际报价计算（特殊费用）; T/F/N： T表示标记为特殊费用，F表示已生成特殊费用，N表示已取消特殊费用。
	 */
	private String actual;
	/**
	 * 计价单位
	 */
	private String countUnit;
	/**
	 * 报价金额
	 */
	private double moneyCount;
	/**
	 * 实际金额,与公司对账金额
	 */
	private double actualCount;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 汇率
	 */
	private String exchangeRate;
	/**
	 * 计时方式(开票时间/发货时间/上船时间)
	 */
	private String meterType;
	/**
	 * 账期天数
	 */
	private int period;
	/**
	 * 账期开始计算时间
	 */
	private Date effectTime;
	/**
	 * 价格名称
	 */
	private FreightExpenseType freightExpenseType;
	/**
	 * 发票类型
	 */
	private FasInvoiceType fasInvoiceType;
	/**
	 * 单位
	 */
	private FreightPart freightPart;
	/**
	 * 相关合同
	 */
	private FreightPact freightPact;
	
	private String descn;
	/**
	 * 成本信息的状态有3个 O表示原始成本信息，供填报费用时加载； T表示已经被相关费用所关联； F表示禁用该成本信息。
	 */
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
	public String getCountUnit() {
		return countUnit;
	}
	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}
	public double getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(double moneyCount) {
		this.moneyCount = moneyCount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public String getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public FreightPart getFreightPart() {
		return freightPart;
	}
	public void setFreightPart(FreightPart freightPart) {
		this.freightPart = freightPart;
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
	public String getRegular() {
		return regular;
	}
	public void setRegular(String regular) {
		this.regular = regular;
	}
	public double getActualCount() {
		return actualCount;
	}
	public void setActualCount(double actualCount) {
		this.actualCount = actualCount;
	}
	public FreightPact getFreightPact() {
		return freightPact;
	}
	public void setFreightPact(FreightPact freightPact) {
		this.freightPact = freightPact;
	}
	public FreightExpenseType getFreightExpenseType() {
		return freightExpenseType;
	}
	public void setFreightExpenseType(FreightExpenseType freightExpenseType) {
		this.freightExpenseType = freightExpenseType;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getMeterType() {
		return meterType;
	}
	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}
	public FasInvoiceType getFasInvoiceType() {
		return fasInvoiceType;
	}
	public void setFasInvoiceType(FasInvoiceType fasInvoiceType) {
		this.fasInvoiceType = fasInvoiceType;
	}
	
}

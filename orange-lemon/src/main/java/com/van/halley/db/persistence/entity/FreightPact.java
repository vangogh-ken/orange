package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 合同
 */
public class FreightPact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 合同编号
	 */
	private String pactNumber;
	/**
	 * 合同名称
	 */
	private String pactTitle;
	/**
	 * 合同类型
	 */
	private String pactType;
	/**
	 * 支出或收入
	 */
	private String incomeOrExpense;
	/**
	 * 甲方
	 */
	private String partA;
	/**
	 * 乙方
	 */
	private String partB;
	/**
	 * 丙方
	 */
	private String partC;
	/**
	 * 经办人
	 */
	private String transactor;
	/**
	 * 签字日期
	 */
	private Date signDate;
	/**
	 * 生效日期
	 */
	private Date effectDate;
	/**
	 * 截止日期
	 */
	private Date cutOffDate;
	/**
	 * 结算方式
	 */
	private String payType;
	/**
	 * 默认汇率
	 */
	private String defaultRate;
	/**
	 * 默认账期天数
	 */
	private int defaultSettleDays;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 金额
	 */
	private double moneyCount;
	/**
	 * 内容
	 */
	private String pactContent;
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
	public String getPactNumber() {
		return pactNumber;
	}
	public void setPactNumber(String pactNumber) {
		this.pactNumber = pactNumber;
	}
	public String getPactTitle() {
		return pactTitle;
	}
	public void setPactTitle(String pactTitle) {
		this.pactTitle = pactTitle;
	}
	public String getPactType() {
		return pactType;
	}
	public void setPactType(String pactType) {
		this.pactType = pactType;
	}
	
	public String getPartA() {
		return partA;
	}
	public void setPartA(String partA) {
		this.partA = partA;
	}
	public String getPartB() {
		return partB;
	}
	public void setPartB(String partB) {
		this.partB = partB;
	}
	public String getPartC() {
		return partC;
	}
	public void setPartC(String partC) {
		this.partC = partC;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Date getEffectDate() {
		return effectDate;
	}
	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	public Date getCutOffDate() {
		return cutOffDate;
	}
	public void setCutOffDate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getDefaultRate() {
		return defaultRate;
	}
	public void setDefaultRate(String defaultRate) {
		this.defaultRate = defaultRate;
	}
	public int getDefaultSettleDays() {
		return defaultSettleDays;
	}
	public void setDefaultSettleDays(int defaultSettleDays) {
		this.defaultSettleDays = defaultSettleDays;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(double moneyCount) {
		this.moneyCount = moneyCount;
	}
	public String getPactContent() {
		return pactContent;
	}
	public void setPactContent(String pactContent) {
		this.pactContent = pactContent;
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
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public String getTransactor() {
		return transactor;
	}
	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}
	
}

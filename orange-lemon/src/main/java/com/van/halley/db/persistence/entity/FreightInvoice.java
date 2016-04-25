package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ken
 * 发票，更类似于开票任务；
 * 根据账单而来，人民币的费用对应开具人民币的发票，但是发票销账时，可能币种会转换。
 */
public class FreightInvoice implements Serializable{
	/**
	 * 
	 */
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
	 * 未销账金额
	 */
	private double remainCount;
	/**
	 * 已销账金额
	 */
	private double eliminateCount;
	/**
	 * 开具发票日期
	 */
	private Date releaseTime;
	/**
	 * 领取发票时间
	 */
	private Date claimTime;
	/**
	 * 最近销账时间
	 */
	private Date eliminateTime;
	/**
	 * 账期天数
	 */
	private int period;
	/**
	 * 到账时间
	 */
	private Date effectTime;
	/**
	 * 开票单位
	 */
	private FreightPart freightPart;
	/**
	 * 对账单
	 */
	private FreightStatement freightStatement;
	/**
	 * 税务发票
	 */
	private FasInvoice fasInvoice;
	/**
	 * 发票类型
	 */
	private FasInvoiceType fasInvoiceType;
	/**
	 * 相关的订单号
	 */
	private String involveOrderNumber;
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
	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
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
	public Date getClaimTime() {
		return claimTime;
	}
	public void setClaimTime(Date claimTime) {
		this.claimTime = claimTime;
	}
	public FreightStatement getFreightStatement() {
		return freightStatement;
	}
	public void setFreightStatement(FreightStatement freightStatement) {
		this.freightStatement = freightStatement;
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
	public FasInvoice getFasInvoice() {
		return fasInvoice;
	}
	public void setFasInvoice(FasInvoice fasInvoice) {
		this.fasInvoice = fasInvoice;
	}
	public double getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(double remainCount) {
		this.remainCount = remainCount;
	}
	public double getEliminateCount() {
		return eliminateCount;
	}
	public void setEliminateCount(double eliminateCount) {
		this.eliminateCount = eliminateCount;
	}
	public String getIncomeOrExpense() {
		return incomeOrExpense;
	}
	public void setIncomeOrExpense(String incomeOrExpense) {
		this.incomeOrExpense = incomeOrExpense;
	}
	public double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public Date getEliminateTime() {
		return eliminateTime;
	}
	public void setEliminateTime(Date eliminateTime) {
		this.eliminateTime = eliminateTime;
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
	public FreightPart getFreightPart() {
		return freightPart;
	}
	public void setFreightPart(FreightPart freightPart) {
		this.freightPart = freightPart;
	}
	public FasInvoiceType getFasInvoiceType() {
		return fasInvoiceType;
	}
	public void setFasInvoiceType(FasInvoiceType fasInvoiceType) {
		this.fasInvoiceType = fasInvoiceType;
	}
	public String getInvolveOrderNumber() {
		StringBuilder text = new StringBuilder();
		FreightStatement freightStatement = this.getFreightStatement();
		if(freightStatement != null){
			List<FreightExpense> freightExpenses = freightStatement.getFreightExpenses();
			if(freightExpenses != null && !freightExpenses.isEmpty()){
				for(FreightExpense freightExpense : freightExpenses){
					if(freightExpense.getFreightOrder() != null 
							&& text.indexOf(freightExpense.getFreightOrder().getOrderNumber()) < 0){
						text.append(freightExpense.getFreightOrder().getOrderNumber() + ",");
					}
				}
			}
		}
		if(text.length() > 0){
			text.deleteCharAt(text.lastIndexOf(","));
		}
		
		return text.toString();
	}
	public void setInvolveOrderNumber(String involveOrderNumber) {
		this.involveOrderNumber = involveOrderNumber;
	}
	
}

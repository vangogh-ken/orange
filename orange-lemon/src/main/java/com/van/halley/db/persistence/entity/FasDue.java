package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.van.halley.util.DoubleUtil;

/**
 * 银行收款
 * @author Think
 *
 */
public class FasDue implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 编号
	 */
	private String dueNumber;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 汇率
	 */
	private double exchangeRate;
	/**
	 * 收据金额
	 */
	private double dueCount;
	/**
	 * 确认金额
	 */
	private double actualCount;
	/**
	 * 销账金额
	 */
	private double eliminateCount;
	/**
	 * 未销账金额
	 */
	private double remainCount;
	/**
	 * 打款单位
	 */
	private FreightPart payPart;
	/**
	 * 打款账号
	 */
	private FasAccount payAccount;
	/**
	 * 收款单位/默认为公司自己
	 */
	private FreightPart duePart;
	/**
	 * 收款账号
	 */
	private FasAccount dueAccount;
	/**
	 * 收款时间
	 */
	private Date dueTime;
	/**
	 * 销账记录
	 */
	private List<FasReconcile> fasReconciles;
	
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
	public double getActualCount() {
		return actualCount;
	}
	public void setActualCount(double actualCount) {
		this.actualCount = actualCount;
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
	public FreightPart getPayPart() {
		return payPart;
	}
	public void setPayPart(FreightPart payPart) {
		this.payPart = payPart;
	}
	public FasAccount getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(FasAccount payAccount) {
		this.payAccount = payAccount;
	}
	public double getDueCount() {
		return dueCount;
	}
	public void setDueCount(double dueCount) {
		this.dueCount = dueCount;
	}
	public FasAccount getDueAccount() {
		return dueAccount;
	}
	public void setDueAccount(FasAccount dueAccount) {
		this.dueAccount = dueAccount;
	}
	public FreightPart getDuePart() {
		return duePart;
	}
	public void setDuePart(FreightPart duePart) {
		this.duePart = duePart;
	}
	public List<FasReconcile> getFasReconciles() {
		return fasReconciles;
	}
	public void setFasReconciles(List<FasReconcile> fasReconciles) {
		this.fasReconciles = fasReconciles;
	}
	public double getEliminateCount() {
		double count = 0;
		if(this.getFasReconciles() == null || this.getFasReconciles().isEmpty()){
			return count;
		}else{
			for(FasReconcile fasReconcile : this.getFasReconciles()){
				//count += fasReconcile.getExchangeRate() * fasReconcile.getMoneyCount();
				count += DoubleUtil.mul(fasReconcile.getExchangeRate(), fasReconcile.getMoneyCount());
			}
			//return count/this.exchangeRate;
			this.eliminateCount = DoubleUtil.div(count, this.exchangeRate);//使用特殊处理避免double进行计算的时候导致精度丢失的问题
			return this.eliminateCount;
		}
	}
	public void setEliminateCount(double eliminateCount) {
		this.eliminateCount = eliminateCount;
	}
	public double getRemainCount() {
		//return this.actualCount - this.getEliminateCount();
		return DoubleUtil.sub(this.actualCount, this.getEliminateCount());
	}
	public void setRemainCount(double remainCount) {
		this.remainCount = remainCount;
	}
	public String getDueNumber() {
		return dueNumber;
	}
	public void setDueNumber(String dueNumber) {
		this.dueNumber = dueNumber;
	}
	public Date getDueTime() {
		return dueTime;
	}
	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}
	
	
}

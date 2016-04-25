package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 账期
 * @author ken
 *
 */
public class FreightNetDay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 是否为固定账期
	 */
	private String regular;
	/**
	 * 收付
	 */
	private String incomeOrExpense;
	/**
	 * 币种
	 */
	private String currency;
	/**
	 * 收付单位
	 */
	private FreightPart freightPart;
	/**
	 * 账期天数,如果账期不固定，则开票时间加上此天数
	 */
	private int period;
	/**
	 * 延迟月数（下个月、下下个月）
	 */
	private int delayMonth;
	/**
	 * 固定日（一月之中的某天）
	 */
	private int regularDay;
	
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
	public String getRegular() {
		return regular;
	}
	public void setRegular(String regular) {
		this.regular = regular;
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
	public FreightPart getFreightPart() {
		return freightPart;
	}
	public void setFreightPart(FreightPart freightPart) {
		this.freightPart = freightPart;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public int getDelayMonth() {
		return delayMonth;
	}
	public void setDelayMonth(int delayMonth) {
		this.delayMonth = delayMonth;
	}
	public int getRegularDay() {
		return regularDay;
	}
	public void setRegularDay(int regularDay) {
		this.regularDay = regularDay;
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

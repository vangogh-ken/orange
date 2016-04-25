package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ken
 * 具体的动作
 */
public class FreightAction implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 是否发送委托
	 */
	private String delegate;
	/**
	 * 是否为内部动作
	 */
	private String internal;
	/**
	 * 是否有填写内容
	 */
	private String prime;
	/**
	 * 动作类型
	 */
	private FreightActionType freightActionType;
	/**
	 * 操作
	 */
	private FreightMaintain freightMaintain;
	/**
	 * 该动作的内部费用
	 */
	private List<FreightExpense> freightExpenses;
	/**
	 * 执行内部动作的收支差
	 */
	private double balance;
	/**
	 * 收入
	 */
	private double incomeAmount;
	/**
	 * 支出
	 */
	private double paymentAmount;
	/**
	 * 税金
	 */
	private double amountsTaxation;
	/**
	 * 说明
	 */
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
	public String getDelegate() {
		return delegate;
	}
	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}
	public String getInternal() {
		return internal;
	}
	public void setInternal(String internal) {
		this.internal = internal;
	}
	public String getPrime() {
		return prime;
	}
	public void setPrime(String prime) {
		this.prime = prime;
	}
	public FreightMaintain getFreightMaintain() {
		return freightMaintain;
	}
	public void setFreightMaintain(FreightMaintain freightMaintain) {
		this.freightMaintain = freightMaintain;
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
	public FreightActionType getFreightActionType() {
		return freightActionType;
	}
	public void setFreightActionType(FreightActionType freightActionType) {
		this.freightActionType = freightActionType;
	}
	
	@JsonIgnore
	public List<FreightExpense> getFreightExpenses() {
		return freightExpenses;
	}
	public void setFreightExpenses(List<FreightExpense> freightExpenses) {
		this.freightExpenses = freightExpenses;
	}
	public double getBalance() {
		double count = 0;
		if("F".equals(this.getInternal()) || "F".equals(this.getDelegate())){
			count = 0;
		}else{
			List<FreightExpense> list = this.getFreightExpenses();
			if(list != null && !list.isEmpty()){
				for(FreightExpense freightExpense : list){
					if("收".equals(freightExpense.getIncomeOrExpense())){
						count += (freightExpense.getPredictAmount()*freightExpense.getExchangeRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
					}else if("付".equals(freightExpense.getIncomeOrExpense())){
						count += 0 - (freightExpense.getPredictAmount()*freightExpense.getExchangeRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
					}
				}
			}
		}
		
		return count;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getIncomeAmount() {
		double count = 0;
		if("F".equals(this.getInternal()) || "F".equals(this.getDelegate())){
			count = 0;
		}else{
			List<FreightExpense> list = this.getFreightExpenses();
			if(list != null && !list.isEmpty()){
				for(FreightExpense freightExpense : list){
					if("收".equals(freightExpense.getIncomeOrExpense())){
						count += (freightExpense.getPredictAmount()*freightExpense.getExchangeRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
					}
				}
			}
		}
		
		return count;
	}
	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public double getPaymentAmount() {
		double count = 0;
		if("F".equals(this.getInternal()) || "F".equals(this.getDelegate())){
			count = 0;
		}else{
			List<FreightExpense> list = this.getFreightExpenses();
			if(list != null && !list.isEmpty()){
				for(FreightExpense freightExpense : list){
					if("付".equals(freightExpense.getIncomeOrExpense())){
						count += 0 - (freightExpense.getPredictAmount()*freightExpense.getExchangeRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
					}
				}
			}
		}
		
		return count;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public double getAmountsTaxation() {
		double count = 0;
		if("F".equals(this.getInternal()) || "F".equals(this.getDelegate())){
			count = 0;
		}else{
			List<FreightExpense> list = this.getFreightExpenses();
			if(list != null && !list.isEmpty()){
				for(FreightExpense freightExpense : list){
					if("收".equals(freightExpense.getIncomeOrExpense())){
						count += freightExpense.getPredictAmount() * freightExpense.getExchangeRate() * freightExpense.getFasInvoiceType().getTaxRate();
					}else if("付".equals(freightExpense.getIncomeOrExpense())){
						count += 0 - (freightExpense.getPredictAmount() * freightExpense.getExchangeRate() * freightExpense.getFasInvoiceType().getTaxRate());
					}
				}
			}
		}
		
		return count;
	}
	public void setAmountsTaxation(double amountsTaxation) {
		this.amountsTaxation = amountsTaxation;
	}
	
	
}

package com.van.halley.rep.data.dto;

public class FeeIncomeDTO{
	private String orderNumber;
	private String partName;
	private double taxRate;
	private double incomeAmountRmb;
	private double incomeTaxRmb;
	private double incomeAmountUsd;
	private double incomeTaxUsd;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}
	public double getIncomeAmountRmb() {
		return incomeAmountRmb;
	}
	public void setIncomeAmountRmb(double incomeAmountRmb) {
		this.incomeAmountRmb = incomeAmountRmb;
	}
	public double getIncomeTaxRmb() {
		return incomeTaxRmb;
	}
	public void setIncomeTaxRmb(double incomeTaxRmb) {
		this.incomeTaxRmb = incomeTaxRmb;
	}
	public double getIncomeTaxUsd() {
		return incomeTaxUsd;
	}
	public void setIncomeTaxUsd(double incomeTaxUsd) {
		this.incomeTaxUsd = incomeTaxUsd;
	}
	public double getIncomeAmountUsd() {
		return incomeAmountUsd;
	}
	public void setIncomeAmountUsd(double incomeAmountUsd) {
		this.incomeAmountUsd = incomeAmountUsd;
	}
	
}

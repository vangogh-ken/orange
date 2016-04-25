package com.van.halley.rep.data.dto;

public class FeePaymentDTO{
	private String orderNumber;
	private String partName;
	private double taxRate;
	private double paymentAmountRmb;
	private double paymentTaxRmb;
	private double paymentAmountUsd;
	private double paymentTaxUsd;
	
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
	public double getPaymentAmountRmb() {
		return paymentAmountRmb;
	}
	public void setPaymentAmountRmb(double paymentAmountRmb) {
		this.paymentAmountRmb = paymentAmountRmb;
	}
	public double getPaymentTaxRmb() {
		return paymentTaxRmb;
	}
	public void setPaymentTaxRmb(double paymentTaxRmb) {
		this.paymentTaxRmb = paymentTaxRmb;
	}
	public double getPaymentAmountUsd() {
		return paymentAmountUsd;
	}
	public void setPaymentAmountUsd(double paymentAmountUsd) {
		this.paymentAmountUsd = paymentAmountUsd;
	}
	public double getPaymentTaxUsd() {
		return paymentTaxUsd;
	}
	public void setPaymentTaxUsd(double paymentTaxUsd) {
		this.paymentTaxUsd = paymentTaxUsd;
	}
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 奖金
 * @author anxin
 *
 */
public class SalaryBonus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 应发
	 */
	private double predictSalary;
	/**
	 * 应付
	 */
	private double predictPayment; 
	/**
	 * 应扣
	 */
	private double predictDebit;
	/**
	 * 实付
	 */
	private double actualPayment;
	/**
	 * 年份
	 */
	private int salaryYear;
	/**
	 * 月份
	 */
	private int salaryMonth;
	/**
	 * 结算时间
	 */
	private Date computeTime;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	/**
	 * 对应等级
	 */
	private SalaryGrade salaryGrade;
	private String descn;
	/**
	 * T 已发放 F 未发放 N 未填报
	 */
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	//岗位
	private double postBonus;
	//绩效
	private double performanceBonus;
	//提成
	private double deductBonus;
	//5S
	private double executiveBonus;
	//风险奖金
	private double riskBonus;
	//特殊奖金
	private double specialBonus;
	
	//工龄
	private double senioritySubsidy;
	//住房补贴
	private double hosingSubsidy;
	//餐补
	private double lunchSubsidy;
	//加班
	private double overtimeSubsidy;
	//保护金
	private double protectSubsidy;
	//行政扣除
	private double executiveDebit;
	//风险金扣除
	private double riskDebit;
	//其他扣除
	private double otherDebit;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPredictSalary() {
		return predictSalary;
	}
	public void setPredictSalary(double predictSalary) {
		this.predictSalary = predictSalary;
	}
	public double getPredictPayment() {
		return predictPayment;
	}
	public void setPredictPayment(double predictPayment) {
		this.predictPayment = predictPayment;
	}
	public double getPredictDebit() {
		return predictDebit;
	}
	public void setPredictDebit(double predictDebit) {
		this.predictDebit = predictDebit;
	}
	public double getActualPayment() {
		return actualPayment;
	}
	public void setActualPayment(double actualPayment) {
		this.actualPayment = actualPayment;
	}
	public int getSalaryYear() {
		return salaryYear;
	}
	public void setSalaryYear(int salaryYear) {
		this.salaryYear = salaryYear;
	}
	public int getSalaryMonth() {
		return salaryMonth;
	}
	public void setSalaryMonth(int salaryMonth) {
		this.salaryMonth = salaryMonth;
	}
	public Date getComputeTime() {
		return computeTime;
	}
	public void setComputeTime(Date computeTime) {
		this.computeTime = computeTime;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	public SalaryGrade getSalaryGrade() {
		return salaryGrade;
	}
	public void setSalaryGrade(SalaryGrade salaryGrade) {
		this.salaryGrade = salaryGrade;
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
	public double getPostBonus() {
		return postBonus;
	}
	public void setPostBonus(double postBonus) {
		this.postBonus = postBonus;
	}
	public double getPerformanceBonus() {
		return performanceBonus;
	}
	public void setPerformanceBonus(double performanceBonus) {
		this.performanceBonus = performanceBonus;
	}
	public double getDeductBonus() {
		return deductBonus;
	}
	public void setDeductBonus(double deductBonus) {
		this.deductBonus = deductBonus;
	}
	public double getExecutiveBonus() {
		return executiveBonus;
	}
	public void setExecutiveBonus(double executiveBonus) {
		this.executiveBonus = executiveBonus;
	}
	public double getRiskBonus() {
		return riskBonus;
	}
	public void setRiskBonus(double riskBonus) {
		this.riskBonus = riskBonus;
	}
	public double getSpecialBonus() {
		return specialBonus;
	}
	public void setSpecialBonus(double specialBonus) {
		this.specialBonus = specialBonus;
	}
	public double getSenioritySubsidy() {
		return senioritySubsidy;
	}
	public void setSenioritySubsidy(double senioritySubsidy) {
		this.senioritySubsidy = senioritySubsidy;
	}
	public double getHosingSubsidy() {
		return hosingSubsidy;
	}
	public void setHosingSubsidy(double hosingSubsidy) {
		this.hosingSubsidy = hosingSubsidy;
	}
	public double getLunchSubsidy() {
		return lunchSubsidy;
	}
	public void setLunchSubsidy(double lunchSubsidy) {
		this.lunchSubsidy = lunchSubsidy;
	}
	public double getOvertimeSubsidy() {
		return overtimeSubsidy;
	}
	public void setOvertimeSubsidy(double overtimeSubsidy) {
		this.overtimeSubsidy = overtimeSubsidy;
	}
	public double getProtectSubsidy() {
		return protectSubsidy;
	}
	public void setProtectSubsidy(double protectSubsidy) {
		this.protectSubsidy = protectSubsidy;
	}
	public double getExecutiveDebit() {
		return executiveDebit;
	}
	public void setExecutiveDebit(double executiveDebit) {
		this.executiveDebit = executiveDebit;
	}
	public double getRiskDebit() {
		return riskDebit;
	}
	public void setRiskDebit(double riskDebit) {
		this.riskDebit = riskDebit;
	}
	public double getOtherDebit() {
		return otherDebit;
	}
	public void setOtherDebit(double otherDebit) {
		this.otherDebit = otherDebit;
	}
	
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础工资
 * @author anxin
 *
 */
public class SalaryBasic implements Serializable {
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
	
	//各种金额
	private double endowmentIndividual;//养老保险
	private double endowmentCompany;

	private double medicalIndividual; // 医疗保险
	private double medicalCompany; // 医疗保险

	private double unemploymentIndividual;//失业保险
	private double unemploymentCompany;//失业保险

	private double injuryIndividual;//工伤保险
	private double injuryCompany;//工伤保险

	private double maternityIndividual;//生育保险
	private double maternityCompany;//生育保险

	private double housingIndividual;//住房公积金
	private double housingCompany;//住房公积金
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
	public double getEndowmentIndividual() {
		return endowmentIndividual;
	}
	public void setEndowmentIndividual(double endowmentIndividual) {
		this.endowmentIndividual = endowmentIndividual;
	}
	public double getEndowmentCompany() {
		return endowmentCompany;
	}
	public void setEndowmentCompany(double endowmentCompany) {
		this.endowmentCompany = endowmentCompany;
	}
	public double getMedicalIndividual() {
		return medicalIndividual;
	}
	public void setMedicalIndividual(double medicalIndividual) {
		this.medicalIndividual = medicalIndividual;
	}
	public double getMedicalCompany() {
		return medicalCompany;
	}
	public void setMedicalCompany(double medicalCompany) {
		this.medicalCompany = medicalCompany;
	}
	public double getUnemploymentIndividual() {
		return unemploymentIndividual;
	}
	public void setUnemploymentIndividual(double unemploymentIndividual) {
		this.unemploymentIndividual = unemploymentIndividual;
	}
	public double getUnemploymentCompany() {
		return unemploymentCompany;
	}
	public void setUnemploymentCompany(double unemploymentCompany) {
		this.unemploymentCompany = unemploymentCompany;
	}
	public double getInjuryIndividual() {
		return injuryIndividual;
	}
	public void setInjuryIndividual(double injuryIndividual) {
		this.injuryIndividual = injuryIndividual;
	}
	public double getInjuryCompany() {
		return injuryCompany;
	}
	public void setInjuryCompany(double injuryCompany) {
		this.injuryCompany = injuryCompany;
	}
	public double getMaternityIndividual() {
		return maternityIndividual;
	}
	public void setMaternityIndividual(double maternityIndividual) {
		this.maternityIndividual = maternityIndividual;
	}
	public double getMaternityCompany() {
		return maternityCompany;
	}
	public void setMaternityCompany(double maternityCompany) {
		this.maternityCompany = maternityCompany;
	}
	public double getHousingIndividual() {
		return housingIndividual;
	}
	public void setHousingIndividual(double housingIndividual) {
		this.housingIndividual = housingIndividual;
	}
	public double getHousingCompany() {
		return housingCompany;
	}
	public void setHousingCompany(double housingCompany) {
		this.housingCompany = housingCompany;
	}
	
	
}

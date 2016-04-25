package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款
 * @author ken
 *
 */
public class FasDebitNote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 借款单号
	 */
	private String debiteNumber;
	/**
	 * 借款人
	 */
	private User debtor;
	/**
	 * 所属部门
	 */
	private OrgEntity belongOrg;
	/**
	 * 借款时间
	 */
	private Date debitTime;
	/**
	 * 借款缘由
	 */
	private String debiteReason;
	/**
	 * 预计还款时间
	 */
	private Date predictRepayTime;
	/**
	 * 最后一次实际还款时间
	 */
	private Date actualRepayTime;
	/**
	 * 金额
	 */
	private double moneyCount;
	/**
	 * 还款金额
	 */
	private double eliminateCount;
	/**
	 * 未还金额
	 */
	private double remainCount;
	
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
	public String getDebiteNumber() {
		return debiteNumber;
	}
	public void setDebiteNumber(String debiteNumber) {
		this.debiteNumber = debiteNumber;
	}
	public OrgEntity getBelongOrg() {
		return belongOrg;
	}
	public void setBelongOrg(OrgEntity belongOrg) {
		this.belongOrg = belongOrg;
	}
	public Date getDebitTime() {
		return debitTime;
	}
	public void setDebitTime(Date debitTime) {
		this.debitTime = debitTime;
	}
	public String getDebiteReason() {
		return debiteReason;
	}
	public void setDebiteReason(String debiteReason) {
		this.debiteReason = debiteReason;
	}
	public Date getPredictRepayTime() {
		return predictRepayTime;
	}
	public void setPredictRepayTime(Date predictRepayTime) {
		this.predictRepayTime = predictRepayTime;
	}
	public Date getActualRepayTime() {
		return actualRepayTime;
	}
	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}
	public double getMoneyCount() {
		return moneyCount;
	}
	public void setMoneyCount(double moneyCount) {
		this.moneyCount = moneyCount;
	}
	public double getEliminateCount() {
		return eliminateCount;
	}
	public void setEliminateCount(double eliminateCount) {
		this.eliminateCount = eliminateCount;
	}
	public double getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(double remainCount) {
		this.remainCount = remainCount;
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
	public User getDebtor() {
		return debtor;
	}
	public void setDebtor(User debtor) {
		this.debtor = debtor;
	}
	
	
}

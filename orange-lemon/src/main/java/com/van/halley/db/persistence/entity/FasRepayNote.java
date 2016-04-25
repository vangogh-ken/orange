package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 还款，对应借款之后还款的记录；
 * 一条借款信息可对应多条还款信息；
 * @author ken
 *
 */
public class FasRepayNote implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 还款编号
	 */
	private String repayNumber;
	/**
	 * 还款人
	 */
	private User repayer; 
	/**
	 * 借款单
	 */
	private FasDebitNote fasDebitNote;
	/**
	 * 还款金额
	 */
	private double repayCount;
	/**
	 * 还款时间
	 */
	private Date repayTime;
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
	public String getRepayNumber() {
		return repayNumber;
	}
	public void setRepayNumber(String repayNumber) {
		this.repayNumber = repayNumber;
	}
	public User getRepayer() {
		return repayer;
	}
	public void setRepayer(User repayer) {
		this.repayer = repayer;
	}
	public FasDebitNote getFasDebitNote() {
		return fasDebitNote;
	}
	public void setFasDebitNote(FasDebitNote fasDebitNote) {
		this.fasDebitNote = fasDebitNote;
	}
	public double getRepayCount() {
		return repayCount;
	}
	public void setRepayCount(double repayCount) {
		this.repayCount = repayCount;
	}
	public Date getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
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

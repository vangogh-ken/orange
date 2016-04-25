package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 账单冲抵
 * @author Think
 *
 */
public class FreightStatementOffset implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 冲抵类型: careCurrency, ignoreCurrency
	 */
	private String offsetType;
	/**
	 * 账单A
	 */
	private String freightStatementIdA;
	/**
	 * 账单B
	 */
	private String freightStatementIdB;
	/**
	 * 冲抵的A账单的人民币金额
	 */
	private double eliminateCountRmbA;
	/**
	 * 冲抵的B账单的人民币金额
	 */
	private double eliminateCountRmbB;
	/**
	 * 冲抵的A账单的美元金额
	 */
	private double eliminateCountDollarA;
	/**
	 * 冲抵的B账单的美元金额
	 */
	private double eliminateCountDollarB;
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
	public String getOffsetType() {
		return offsetType;
	}
	public void setOffsetType(String offsetType) {
		this.offsetType = offsetType;
	}
	public String getFreightStatementIdA() {
		return freightStatementIdA;
	}
	public void setFreightStatementIdA(String freightStatementIdA) {
		this.freightStatementIdA = freightStatementIdA;
	}
	public String getFreightStatementIdB() {
		return freightStatementIdB;
	}
	public void setFreightStatementIdB(String freightStatementIdB) {
		this.freightStatementIdB = freightStatementIdB;
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
	public double getEliminateCountRmbA() {
		return eliminateCountRmbA;
	}
	public void setEliminateCountRmbA(double eliminateCountRmbA) {
		this.eliminateCountRmbA = eliminateCountRmbA;
	}
	public double getEliminateCountRmbB() {
		return eliminateCountRmbB;
	}
	public void setEliminateCountRmbB(double eliminateCountRmbB) {
		this.eliminateCountRmbB = eliminateCountRmbB;
	}
	public double getEliminateCountDollarA() {
		return eliminateCountDollarA;
	}
	public void setEliminateCountDollarA(double eliminateCountDollarA) {
		this.eliminateCountDollarA = eliminateCountDollarA;
	}
	public double getEliminateCountDollarB() {
		return eliminateCountDollarB;
	}
	public void setEliminateCountDollarB(double eliminateCountDollarB) {
		this.eliminateCountDollarB = eliminateCountDollarB;
	}
}

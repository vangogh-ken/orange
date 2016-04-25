package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class FreightDeduct implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 仅操作提成计入
	 */
	public static final String DEDUCT_PREPARED = "DEDUCT_PREPARED";//
	/**
	 * 未销账
	 */
	public static final String RECONCILE_HAVNT = "RECONCILE_HAVNT";//F
	/**
	 * 已销账
	 */
	public static final String RECONCILE_HAVE = "RECONCILE_HAVE";//T
	/**
	 * 已提成，无修改
	 */
	public static final String DEDUCT_HAVE = "DEDUCT_HAVE";//D
	/**
	 * 已提成，且修改
	 */
	public static final String DEDUCT_REVISED = "DEDUCT_REVISED";//DF
	/**
	 * 已提成，订单作废或者被删除
	 */
	public static final String DEDUCT_DELETED = "DEDUCT_DELETED";//DF
	/**
	 * 不提成
	 */
	public static final String DEDUCT_NONE = "DEDUCT_NONE";
	/**
	 * 补偿提成
	 */
	public static final String DEDUCT_SATE = "DEDUCT_SATE";
	
	private String id;
	/**
	 * 提成类型：订单提成、箱量提成
	 */
	private String deductType;
	/**
	 * 订单
	 */
	private FreightOrder freightOrder;
	/**
	 * 销售部门
	 */
	private OrgEntity orgEntity;
	/**
	 * 订单收支差
	 */
	private double orderBalance;
	/**
	 * 提成收支差
	 */
	private double deductBalance;
	/**
	 * 销售提成金额
	 */
	private double deductCountSalesman;
	/**
	 * 是否已提成
	 */
	private String settleDoneSalesman;
	/**
	 * 提成时间
	 */
	private Date settleTimeSalesman;
	/**
	 * 客服提成金额
	 */
	private double deductCountService;
	/**
	 * 是否已提成
	 */
	private String settleDoneService;
	/**
	 * 提成时间
	 */
	private Date settleTimeService;
	/**
	 * 操作提成金额
	 */
	private double deductCountManipulator;
	/**
	 * 是否已提成
	 */
	private String settleDoneManipulator;
	/**
	 * 提成时间
	 */
	private Date settleTimeManipulator;
	/**
	 * 销账时间
	 */
	private Date reconcileTime;
	/**
	 * 提成时间/标记提成时间
	 */
	private Date deductTime;
	
	private String descn;
	/**
	 * 状态 
	 * F未结算  
	 * T已结算未提成
	 * D已提成
	 * DF已提成，但订单作废或不存在或修改过费用。
	 */
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
	public FreightOrder getFreightOrder() {
		return freightOrder;
	}
	public void setFreightOrder(FreightOrder freightOrder) {
		this.freightOrder = freightOrder;
	}
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	public double getDeductCountSalesman() {
		return deductCountSalesman;
	}
	public void setDeductCountSalesman(double deductCountSalesman) {
		this.deductCountSalesman = deductCountSalesman;
	}
	public String getSettleDoneSalesman() {
		return settleDoneSalesman;
	}
	public void setSettleDoneSalesman(String settleDoneSalesman) {
		this.settleDoneSalesman = settleDoneSalesman;
	}
	public Date getSettleTimeSalesman() {
		return settleTimeSalesman;
	}
	public void setSettleTimeSalesman(Date settleTimeSalesman) {
		this.settleTimeSalesman = settleTimeSalesman;
	}
	public double getDeductCountService() {
		return deductCountService;
	}
	public void setDeductCountService(double deductCountService) {
		this.deductCountService = deductCountService;
	}
	public String getSettleDoneService() {
		return settleDoneService;
	}
	public void setSettleDoneService(String settleDoneService) {
		this.settleDoneService = settleDoneService;
	}
	public Date getSettleTimeService() {
		return settleTimeService;
	}
	public void setSettleTimeService(Date settleTimeService) {
		this.settleTimeService = settleTimeService;
	}
	public double getDeductCountManipulator() {
		return deductCountManipulator;
	}
	public void setDeductCountManipulator(double deductCountManipulator) {
		this.deductCountManipulator = deductCountManipulator;
	}
	public String getSettleDoneManipulator() {
		return settleDoneManipulator;
	}
	public void setSettleDoneManipulator(String settleDoneManipulator) {
		this.settleDoneManipulator = settleDoneManipulator;
	}
	public Date getSettleTimeManipulator() {
		return settleTimeManipulator;
	}
	public void setSettleTimeManipulator(Date settleTimeManipulator) {
		this.settleTimeManipulator = settleTimeManipulator;
	}
	public Date getReconcileTime() {
		return reconcileTime;
	}
	public void setReconcileTime(Date reconcileTime) {
		this.reconcileTime = reconcileTime;
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
	public String getDeductType() {
		return deductType;
	}
	public void setDeductType(String deductType) {
		this.deductType = deductType;
	}
	public double getOrderBalance() {
		return orderBalance;
	}
	public void setOrderBalance(double orderBalance) {
		this.orderBalance = orderBalance;
	}
	public double getDeductBalance() {
		return deductBalance;
	}
	public void setDeductBalance(double deductBalance) {
		this.deductBalance = deductBalance;
	}
	public Date getDeductTime() {
		return deductTime;
	}
	public void setDeductTime(Date deductTime) {
		this.deductTime = deductTime;
	}
	
	
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 付款申请书
 * @author ken
 *
 */
public class FasPay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 编号
	 */
	private String payNumber;
	/**
	 * 付款方式
	 */
	private String payType;
	/**
	 * 人民币金额
	 */
	private double payCountRmb;
	/**
	 * 美元
	 */
	private double payCountDollar;
	/**
	 * 人民币账户
	 */
	private FasAccount fasAccountRmb;
	/**
	 * 美元账户
	 */
	private FasAccount fasAccountDollar;
	/**
	 * 收款单位
	 */
	private FreightPart beneficiary;
	/**
	 * 申请人
	 */
	private User proposer;
	/**
	 * 部门
	 */
	private OrgEntity orgEntity;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 付款内容
	 */
	private String payFor;
	/**
	 * 涉及订单
	 */
	private String involveOrderNumber;
	/**
	 * 单位相关的账号信息
	 */
	private List<FasAccount> fasAccounts;
	/**
	 * 销账记录
	 */
	private List<FasReconcile> fasReconciles;
	/**
	 * 付款时间
	 */
	private Date payTime;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;//看成付款日期
	private int displayIndex;
	
	public Date getApplyTime() {
		return applyTime;
	}
	public FreightPart getBeneficiary() {
		return beneficiary;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public String getDescn() {
		return descn;
	}
	public int getDisplayIndex() {
		return displayIndex;
	}
	public String getId() {
		return id;
	}
	public String getInvolveOrderNumber() {
		return involveOrderNumber;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public double getPayCountDollar() {
		double count = 0;
		if(this.getFasReconciles() == null || this.getFasReconciles().isEmpty()){
			return count;
		}else{
			for(FasReconcile fasReconcile : this.getFasReconciles()){
				if("美元".equals(fasReconcile.getCurrency())){
					count += fasReconcile.getMoneyCount();
				}
			}
			return count;
		}
	}
	public double getPayCountRmb() {
		double count = 0;
		if(this.getFasReconciles() == null || this.getFasReconciles().isEmpty()){
			return count;
		}else{
			for(FasReconcile fasReconcile : this.getFasReconciles()){
				if("人民币".equals(fasReconcile.getCurrency())){
					count += fasReconcile.getMoneyCount();
				}
			}
			return count;
		}
	}
	public String getPayFor() {
		return payFor;
	}
	public String getPayType() {
		return payType;
	}
	public User getProposer() {
		return proposer;
	}
	public String getStatus() {
		return status;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public void setBeneficiary(FreightPart beneficiary) {
		this.beneficiary = beneficiary;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setInvolveOrderNumber(String involveOrderNumber) {
		this.involveOrderNumber = involveOrderNumber;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	public void setPayCountDollar(double payCountDollar) {
		this.payCountDollar = payCountDollar;
	}
	public void setPayCountRmb(double payCountRmb) {
		this.payCountRmb = payCountRmb;
	}
	public void setPayFor(String payFor) {
		this.payFor = payFor;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public void setProposer(User proposer) {
		this.proposer = proposer;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<FasReconcile> getFasReconciles() {
		return fasReconciles;
	}
	public void setFasReconciles(List<FasReconcile> fasReconciles) {
		this.fasReconciles = fasReconciles;
	}
	public List<FasAccount> getFasAccounts() {
		return fasAccounts;
	}
	public void setFasAccounts(List<FasAccount> fasAccounts) {
		this.fasAccounts = fasAccounts;
	}
	public FasAccount getFasAccountRmb() {
		if(this.getFasAccounts() != null && !this.getFasAccounts().isEmpty()){
			for(FasAccount fasAccount : this.getFasAccounts()){
				if("人民币".equals(fasAccount.getCurrency())){
					return fasAccount;
				}
			}
		}
		
		return null;
	}
	public void setFasAccountRmb(FasAccount fasAccountRmb) {
		this.fasAccountRmb = fasAccountRmb;
	}
	public FasAccount getFasAccountDollar() {
		if(this.getFasAccounts() != null && !this.getFasAccounts().isEmpty()){
			for(FasAccount fasAccount : this.getFasAccounts()){
				if("美元".equals(fasAccount.getCurrency())){
					return fasAccount;
				}
			}
		}
		
		return null;
	}
	public void setFasAccountDollar(FasAccount fasAccountDollar) {
		this.fasAccountDollar = fasAccountDollar;
	}
	public String getPayNumber() {
		return payNumber;
	}
	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	
}

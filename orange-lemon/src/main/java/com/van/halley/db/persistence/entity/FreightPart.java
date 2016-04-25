package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author ken
 * 货运业务来往单位(此处注意体现每个人的权限) base_units 表
 */
@JsonIgnoreProperties(value={"partType", "internal", "partAddress", "partCharger", "partContact", "partRegAddress", "partExpAddress",
		"partFax", "partMail", "engageScope", "saleMan", "delegateStatus", "publicStatus", "bankNameRmb", "bankAccountRmb",
		"bankNameDollar", "bankAccountDollar", "descn", "createTime", "modifyTime", "displayIndex"})
public class FreightPart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 单位名称
	 */
	private String partName;
	/**
	 * 客户属性
	 */
	private String partType;
	/**
	 * 是否映射到内部部门
	 */
	private String internal;
	/**
	 * 单位地址
	 */
	private String partAddress;
	/**
	 * 负责人
	 */
	private String partCharger;
	/**
	 * 联系电话
	 */
	private String partContact;
	/**
	 * 公司注册地
	 */
	private String partRegAddress;
	/**
	 * 文件邮寄地址
	 */
	private String partExpAddress;
	/**
	 * 传真
	 */
	private String partFax;
	/**
	 * 电子邮件
	 */
	private String partMail;
	/**
	 * 业务范围
	 */
	private String engageScope;
	/**
	 * 归属（业务员）/业务负责人
	 */
	private String saleMan;
	/**
	 * 归属（接收委托/委外）
	 */
	private String delegateStatus;
	/**
	 * 是否为公共
	 */
	private String publicStatus;
	/**
	 * 人民币银行名称
	 */
	private String bankNameRmb;
	/**
	 * 人民币银行账号
	 */
	private String bankAccountRmb;
	/**
	 * 美元银行名称
	 */
	private String bankNameDollar;
	/**
	 * 美元银行账号
	 */
	private String bankAccountDollar;
	/**
	 * 映射到公司内部部门
	 */
	private OrgEntity orgEntity;
	
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
	
	
	public String getBankNameRmb() {
		return bankNameRmb;
	}
	public void setBankNameRmb(String bankNameRmb) {
		this.bankNameRmb = bankNameRmb;
	}
	public String getBankAccountRmb() {
		return bankAccountRmb;
	}
	public void setBankAccountRmb(String bankAccountRmb) {
		this.bankAccountRmb = bankAccountRmb;
	}
	public String getBankNameDollar() {
		return bankNameDollar;
	}
	public void setBankNameDollar(String bankNameDollar) {
		this.bankNameDollar = bankNameDollar;
	}
	public String getBankAccountDollar() {
		return bankAccountDollar;
	}
	public void setBankAccountDollar(String bankAccountDollar) {
		this.bankAccountDollar = bankAccountDollar;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getPartAddress() {
		return partAddress;
	}
	public void setPartAddress(String partAddress) {
		this.partAddress = partAddress;
	}
	public String getPartCharger() {
		return partCharger;
	}
	public void setPartCharger(String partCharger) {
		this.partCharger = partCharger;
	}
	public String getPartContact() {
		return partContact;
	}
	public void setPartContact(String partContact) {
		this.partContact = partContact;
	}
	public String getPartRegAddress() {
		return partRegAddress;
	}
	public void setPartRegAddress(String partRegAddress) {
		this.partRegAddress = partRegAddress;
	}
	public String getPartExpAddress() {
		return partExpAddress;
	}
	public void setPartExpAddress(String partExpAddress) {
		this.partExpAddress = partExpAddress;
	}
	public String getPartFax() {
		return partFax;
	}
	public void setPartFax(String partFax) {
		this.partFax = partFax;
	}
	public String getEngageScope() {
		return engageScope;
	}
	public void setEngageScope(String engageScope) {
		this.engageScope = engageScope;
	}
	public String getSaleMan() {
		return saleMan;
	}
	public void setSaleMan(String saleMan) {
		this.saleMan = saleMan;
	}
	public String getDelegateStatus() {
		return delegateStatus;
	}
	public void setDelegateStatus(String delegateStatus) {
		this.delegateStatus = delegateStatus;
	}
	public String getPublicStatus() {
		return publicStatus;
	}
	public void setPublicStatus(String publicStatus) {
		this.publicStatus = publicStatus;
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
	public String getPartMail() {
		return partMail;
	}
	public void setPartMail(String partMail) {
		this.partMail = partMail;
	}
	@JsonIgnore
	public OrgEntity getOrgEntity() {
		return orgEntity;
	}
	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}
	public String getInternal() {
		return internal;
	}
	public void setInternal(String internal) {
		this.internal = internal;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof FreightPart){
			FreightPart temp = (FreightPart)obj;
			if(this.getId().equals(temp.getId())){
				return true;
			}
		}
		return false;
	}
	
}

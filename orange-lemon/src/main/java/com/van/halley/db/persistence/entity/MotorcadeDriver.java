package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ken
 * 驾驶员
 */
public class MotorcadeDriver implements	Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 司机类型：A 货运司机、B 业务员工
	 */
	private String driverType;
	/**
	 * 姓名
	 */
	private String displayName;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 联系方式
	 */
	private String telephone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 驾驶证号
	 */
	private String drivingNumber;
	/**
	 * 准驾车型
	 */
	private String quasiType;
	/**
	 * 登记日期
	 */
	private Date registerTime;
	/**
	 * 有效期
	 */
	private Date validTime;
	/**
	 * 发证机关
	 */
	private String certification;
	
	
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDrivingNumber() {
		return drivingNumber;
	}
	public void setDrivingNumber(String drivingNumber) {
		this.drivingNumber = drivingNumber;
	}
	public String getQuasiType() {
		return quasiType;
	}
	public void setQuasiType(String quasiType) {
		this.quasiType = quasiType;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Date getValidTime() {
		return validTime;
	}
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	public String getCertification() {
		return certification;
	}
	public void setCertification(String certification) {
		this.certification = certification;
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
	public String getDriverType() {
		return driverType;
	}
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
	
	
}

package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Think
 * 一些用户的基本信息
 * 
 */
public class UserBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String mailAddress;
	private String mailPassword;
	private String mailAsync;
	private String mobile;
	private String telephone;
	private String icon;
	private String userId;
	private String workStatus;
	private Date birthDay;
	/**
	 * 微信登录账号
	 */
	private String weixinName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMailAsync() {
		return mailAsync;
	}
	public void setMailAsync(String mailAsync) {
		this.mailAsync = mailAsync;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getWeixinName() {
		return weixinName;
	}
	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}
	
}

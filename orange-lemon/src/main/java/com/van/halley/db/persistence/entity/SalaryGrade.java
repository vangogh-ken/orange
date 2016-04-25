package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import com.van.halley.db.persistence.entity.User;

/**
 * 
 * @author anxinxx
 *
 */
public class SalaryGrade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 等级
	 */
	private int gradeCount; 
	/**
	 * 基础工资
	 */
	private double basicSalary;
	/**
	 * 岗位工资
	 */
	private double postSalary;
	/**
	 * 开始时间 最新的标注开始时间和结束时间之后将其他类型的标注为F 不可用状态
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 用户
	 */
	private User user;
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
	public int getGradeCount() {
		return gradeCount;
	}
	public void setGradeCount(int gradeCount) {
		this.gradeCount = gradeCount;
	}
	public double getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}
	public double getPostSalary() {
		return postSalary;
	}
	public void setPostSalary(double postSalary) {
		this.postSalary = postSalary;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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

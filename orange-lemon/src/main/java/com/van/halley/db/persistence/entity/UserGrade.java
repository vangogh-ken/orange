package com.van.halley.db.persistence.entity;

import java.util.Date;

/**
 * 职级
 * @author Administrator
 *
 */
public class UserGrade {
	private String id;
	/**
	 * 等级
	 */
	private int gradeType;
	/**
	 * 对应薪资
	 */
	private int gradeSalary;
	/**
	 * 岗位工资
	 */
	private int postSalary;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	private String descn;
	private String status;
	private Date createTime;
	private Date modifyTime;
	private int displayIndex;
}

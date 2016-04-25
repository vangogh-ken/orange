package com.van.halley.db.persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户实体表
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 姓名
	 */
	private String displayName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 等级
	 */
	private int level;
	/**
	 * 等级日期
	 */
	private Date regTime;
	/**
	 * 入职日期
	 */
	private Date hireTime;
	/**
	 * 离职日期
	 */
	private Date fireTime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 基本信息
	 */
	private UserBase userBase;
	/**
	 * 职位
	 */
	private Position position;
	/**
	 * 组织机构（组织机构类型为部门）
	 */
	private OrgEntity orgEntity;
	/**
	 * 拥有的角色
	 */
	private List<Role> roles = new ArrayList<Role>(0);
	
	//private List<Resource> resources = new ArrayList<Resource>(0);
	private int displayIndex;
	
	public User() {
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public String getDisplayName() {
		return displayName;
	}


	public String getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}


	public String getPassword() {
		return password;
	}

	public Position getPosition() {
		return position;
	}

	public Date getRegTime() {
		return regTime;
	}

	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}
	/*@JsonIgnore
	public List<Resource> getResources() {
		return resources;
	}*/
	public String getStatus() {
		return status;
	}

	public String getUserName() {
		return userName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setPosition(Position position) {
		this.position = position;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	public OrgEntity getOrgEntity() {
		if(getPosition() != null){
			if("部门".equals(getPosition().getOrgEntity().getOrgType().getTypeName())){
				return getPosition().getOrgEntity();
			}else if("部门".equals(getPosition().getOrgEntity().getParentOrg().getOrgType().getTypeName())){
				return getPosition().getOrgEntity().getParentOrg();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	public void setOrgEntity(OrgEntity orgEntity) {
		this.orgEntity = orgEntity;
	}

	public UserBase getUserBase() {
		return userBase;
	}

	public void setUserBase(UserBase userBase) {
		this.userBase = userBase;
	}

	public Date getHireTime() {
		return hireTime;
	}

	public void setHireTime(Date hireTime) {
		this.hireTime = hireTime;
	}

	public Date getFireTime() {
		return fireTime;
	}

	public void setFireTime(Date fireTime) {
		this.fireTime = fireTime;
	}
}

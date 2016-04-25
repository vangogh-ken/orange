package com.van.halley.db.persistence.entity;

import java.io.Serializable;

/**
 * @author ken
 * 角色与流程的关系，用于控制发起流程的权限
 */
public class RoleBpm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roleId;
	private String bpmId;
	
	public RoleBpm(){
	}
	
	public RoleBpm(String roleId, String bpmId){
		this.roleId = roleId;
		this.bpmId = bpmId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getBpmId() {
		return bpmId;
	}
	public void setBpmId(String bpmId) {
		this.bpmId = bpmId;
	}
	
	
}

package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.UserRole;

public interface RoleService {
	public PageView query(PageView pageView, Role role);

	public void add(Role role);

	public void delete(String id);

	public void modify(Role role);

	public Role getById(String id);

	public List<Role> getAll();

	public void saveUserRole(List<UserRole> userRoles);

	public boolean isExistRoleByName(String roleName);
	
	public List<Role> getRoleByUserId(String userId);
	/**
	 * 通过职位获取角色
	 * @param positionId
	 * @return
	 */
	public List<Role> getRoleByPositionId(String positionId);
	/**
	 * 职位角色关联
	 * @param positionId
	 * @param roleIds
	 */
	public void positionRoleAllocation(String positionId, String[] roleIds);

	/**
	 * 分配角色报表权限
	 * @param roleId
	 * @return
	 */
	public Map<String, Object> toAllocationReport(String roleId);
	/**
	 * 完成分配权限
	 * @param roleId
	 * @param reportTemplateId
	 * @return
	 */
	public boolean doneAllocationReport(String roleId, String[] reportTemplateIds);

	/**
	 * 分配角色流程权限
	 * @param roleId
	 * @return
	 */
	public Map<String, Object> toAllocationBpm(String roleId);

	/**
	 * 完成分配角色流程权限
	 * @param roleId
	 * @param bpmId
	 * @return
	 */
	public boolean doneAllocationBpm(String roleId, String[] bpmIds);
	
	/**
	 * 分配角色任务权限
	 * @param roleId
	 * @return
	 */
	public Map<String, Object> toAllocationQuartz(String roleId);

	/**
	 * 完成分配角色任务权限
	 * @param roleId
	 * @param bpmId
	 * @return
	 */
	public boolean doneAllocationQuartz(String roleId, String[] quartzTaskIds);

	/**
	 * 分配角色资源权限
	 * @param roleId
	 * @return
	 */
	public Map<String, Object> toAllocationResource(String roleId);

	/**
	 * 完成分配角色资源权限
	 * @param roleId
	 * @param resourceIds
	 * @return
	 */
	public boolean doneAllocationResource(String roleId, String[] resourceIds);
}

package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.UserRole;

public interface RoleDao extends BaseDao<Role> {
	/**
	 *根据用户ID 删除用户关联角色
	 * @param userId
	 */
	public void deleteUserRole(String userId);

	/**
	 * 保存一条用户角色关系
	 * @param userRole
	 */
	public void addUserRole(UserRole userRole);
	/**
	 * 通过用户ID 获取用户关联角色
	 * @param userId
	 * @return
	 */
	public List<Role> getRoleByUserId(String userId);
	/**
	 * 通过职位获取角色
	 * @param positionId
	 * @return
	 */
	public List<Role> getRoleByPositionId(String positionId);

	/**
	 * 删除职位角色关联
	 * @param positionId
	 */
	public void deletePositionRole(String positionId);
	/**
	 * 新增职位角色关联
	 * @param positionId
	 * @param roleId
	 */
	public void addPositionRole(String positionId, String roleId);
	/**
	 * 获取流程节点角色权限
	 * @param bpmConfigNodeId
	 * @return
	 */
	public List<Role> getByBpmConfigNodeId(String bpmConfigNodeId);
}

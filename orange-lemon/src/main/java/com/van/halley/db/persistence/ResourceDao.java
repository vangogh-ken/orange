package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.Resource;

public interface ResourceDao extends BaseDao<Resource> {
	/**
	 * 根据用户Id获取该用户的权限
	 * @param userId
	 * @return
	 */
	public List<Resource> getResourceByUserId(String userId);
	/**
	 * 获取角色最终关联的资源
	 * @param roleId
	 * @return
	 */
	public List<Resource> getResourceByRoleId(String roleId);
	/**
	 * 获取职位最终关联的资源
	 * @param positionId
	 * @return
	 */
	public List<Resource> getResourceByPositionId(String positionId);
	/**
	 * 删除角色关联资源
	 * @param roleId
	 */
	public void deleteRoleResource(String roleId);
	/**
	 * 添加角色关联资源
	 * @param roleId
	 * @param resourceId
	 */
	public void addRoleResource(String roleId, String resourceId);
}

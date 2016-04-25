package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.UserRole;

public interface UserRoleDao extends BaseDao<UserRole> {
	/**
	 * 删除已经分配用户的角色
	 * @param userId
	 */
	public void deleteUserRoleByUserId(String userId);
}
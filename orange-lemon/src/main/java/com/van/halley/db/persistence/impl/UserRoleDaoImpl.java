package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.UserRoleDao;
import com.van.halley.db.persistence.entity.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements
		UserRoleDao {

	@Override
	public void deleteUserRoleByUserId(String userId) {
		getSqlSession().delete("userrole.deleteUserRoleByUserId", userId);
	}
}

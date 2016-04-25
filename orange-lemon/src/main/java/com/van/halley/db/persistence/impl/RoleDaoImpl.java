package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.RoleDao;
import com.van.halley.db.persistence.entity.PositionRole;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.UserRole;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
	/////////////////////用户角色关联
	@Override
	public void addUserRole(UserRole userRole) {
		getSqlSession().insert("role.addUserRole", userRole);
	}
	
	@Override
	public void deleteUserRole(String userId) {
		getSqlSession().delete("role.deleteUserRole", userId);
	}

	@Override
	public List<Role> getRoleByUserId(String userId) {
		return getSqlSession().selectList("role.getRoleByUserId", userId);
	}

	/////////////////////职位角色关联
	@Override
	public List<Role> getRoleByPositionId(String positionId) {
		return getSqlSession().selectList("role.getRoleByPositionId", positionId);
	}

	@Override
	public void deletePositionRole(String positionId) {
		getSqlSession().delete("role.deletePositionRole", positionId);
	}

	@Override
	public void addPositionRole(String positionId, String roleId) {
		getSqlSession().delete("role.addPositionRole", new PositionRole(positionId, roleId));
	}

	@Override
	public List<Role> getByBpmConfigNodeId(String bpmConfigNodeId) {
		return getSqlSession().selectList("role.getByBpmConfigNodeId", bpmConfigNodeId);
	}
}

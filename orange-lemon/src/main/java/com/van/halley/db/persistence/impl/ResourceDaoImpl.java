package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ResourceDao;
import com.van.halley.db.persistence.entity.Resource;
import com.van.halley.db.persistence.entity.RoleResource;

@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements
		ResourceDao {
	@Override
	public List<Resource> getResourceByUserId(String userId){
		return getSqlSession().selectList("resource.getResourceByUserId", userId);
	}

	@Override
	public List<Resource> getResourceByRoleId(String roleId){
		return getSqlSession().selectList("resource.getResourceByRoleId", roleId);
	}

	@Override
	public List<Resource> getResourceByPositionId(String positionId) {
		return getSqlSession().selectList("resource.getResourceByPositionId", positionId);
	}

	@Override
	public void deleteRoleResource(String roleId) {
		getSqlSession().delete("resource.deleteRoleResource", roleId);
		
	}

	@Override
	public void addRoleResource(String roleId, String resourceId) {
		RoleResource roleResource = new RoleResource(roleId, resourceId);
		getSqlSession().insert("resource.addRoleResource", roleResource);
	}
}

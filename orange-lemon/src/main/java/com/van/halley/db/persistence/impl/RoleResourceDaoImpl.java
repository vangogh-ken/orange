package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.RoleResourceDao;
import com.van.halley.db.persistence.entity.RoleResource;

@Repository("roleResourceDao")
public class RoleResourceDaoImpl extends BaseDaoImpl<RoleResource> implements
		RoleResourceDao {
}

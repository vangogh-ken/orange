package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightActionTypeIdentityDao;
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;

@Repository("freightActionTypeIdentityDao")
public class FreightActionTypeIdentityDaoImpl extends
		BaseDaoImpl<FreightActionTypeIdentity> implements
		FreightActionTypeIdentityDao {

	@Override
	public void deleteByFreightActionTypeId(String freightActionTypeId) {
		getSqlSession().delete("freightactiontypeidentity.deleteByFreightActionTypeId", freightActionTypeId);
	}
	
	@Override
	public FreightActionTypeIdentity getByFreightActionTypeId(String freightActionTypeId) {
		FreightActionTypeIdentity filter = new FreightActionTypeIdentity();
		filter.setFreightActionTypeId(freightActionTypeId);
		return queryForOne(filter);
	}
}

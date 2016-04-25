package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;

public interface FreightActionTypeIdentityDao extends
		BaseDao<FreightActionTypeIdentity> {

	public void deleteByFreightActionTypeId(String freightActionTypeId);

	public FreightActionTypeIdentity getByFreightActionTypeId(String freightActionTypeId);
}
package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightActionTypeDao;
import com.van.halley.db.persistence.entity.FreightActionType;

@Repository("freightActionTypeDao")
public class FreightActionTypeDaoImpl extends BaseDaoImpl<FreightActionType>
		implements FreightActionTypeDao {

	@Override
	public List<FreightActionType> getByMaintainTypeId(String freightMaintainTypeId) {
		return getSqlSession().selectList("freightactiontype.getByMaintainTypeId", freightMaintainTypeId);
	}

	@Override
	public void deleteByMaintainTypeId(String freightMaintainTypeId) {
		getSqlSession().delete("freightactiontype.deleteByMaintainTypeId", freightMaintainTypeId);
	}
}

package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightActionType;

public interface FreightActionTypeDao extends BaseDao<FreightActionType> {

	public List<FreightActionType> getByMaintainTypeId(String freightMaintainTypeId);
	
	public void deleteByMaintainTypeId(String freightMaintainTypeId);
}
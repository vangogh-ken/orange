package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightMaintainAction;

public interface FreightMaintainActionDao extends BaseDao<FreightMaintainAction> {

	public void deleteByMaintainAndAction(String freightMaintainTypeId, String freightActionTypeId);
}
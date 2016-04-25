package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightMaintainActionDao;
import com.van.halley.db.persistence.entity.FreightMaintainAction;

@Repository("freightMaintainActionDao")
public class FreightMaintainActionDaoImpl extends
		BaseDaoImpl<FreightMaintainAction> implements FreightMaintainActionDao {

	@Override
	public void deleteByMaintainAndAction(String freightMaintainTypeId, String freightActionTypeId) {
		if(freightMaintainTypeId != null && freightActionTypeId != null){
			getSqlSession().delete("freightmaintainaction.deleteByMaintainAndAction",
					new FreightMaintainAction(freightMaintainTypeId, freightActionTypeId));
		}
	}
}

package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightActionDao;
import com.van.halley.db.persistence.entity.FreightAction;

@Repository("freightActionDao")
public class FreightActionDaoImpl extends BaseDaoImpl<FreightAction> implements
		FreightActionDao {

	@Override
	public List<FreightAction> getByFreightOrderId(String freightOrderId) {
		return getSqlSession().selectList("freightaction.getByFreightOrderId", freightOrderId);
	}

	@Override
	public List<FreightAction> getActionHasPrimeByFreightOrderId(String freightOrderId) {
		return getSqlSession().selectList("freightaction.getActionHasPrimeByFreightOrderId", freightOrderId);
	}
}

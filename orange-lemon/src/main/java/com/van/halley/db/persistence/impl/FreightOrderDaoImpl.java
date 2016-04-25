package com.van.halley.db.persistence.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.entity.FreightOrder;
@Repository("freightOrderDao")
public class FreightOrderDaoImpl extends BaseDaoImpl<FreightOrder> implements FreightOrderDao {

	@Override
	public List<FreightOrder> getByFreightStatementId(String freightStatementId) {
		return getSqlSession().selectList("freightorder.getByFreightStatementId", freightStatementId);
	}}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightDeductDao;
import com.van.halley.db.persistence.entity.FreightDeduct;
@Repository("freightDeductDao")
public class FreightDeductDaoImpl extends BaseDaoImpl<FreightDeduct> implements FreightDeductDao {

	@Override
	public FreightDeduct getByFreightOrderId(String freightOrderId) {
		return getSqlSession().selectOne("freightdeduct.getByFreightOrderId", freightOrderId);
	}}

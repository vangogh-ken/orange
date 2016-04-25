package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightExpenseTypeDao;
import com.van.halley.db.persistence.entity.FreightExpenseType;
@Repository("freightExpenseTypeDao")
public class FreightExpenseTypeDaoImpl extends BaseDaoImpl<FreightExpenseType> implements FreightExpenseTypeDao {

	@Override
	public FreightExpenseType getByTypeName(String typeName) {
		FreightExpenseType filter = new FreightExpenseType();
		filter.setTypeName(typeName);
		return queryForOne(filter);
	}}

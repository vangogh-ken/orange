package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.entity.FasInvoiceType;
@Repository("fasInvoiceTypeDao")
public class FasInvoiceTypeDaoImpl extends BaseDaoImpl<FasInvoiceType> implements FasInvoiceTypeDao {

	@Override
	public FasInvoiceType getByTypeName(String typeName) {
		FasInvoiceType filter = new FasInvoiceType();
		filter.setTypeName(typeName);
		return queryForOne(filter);
	}}

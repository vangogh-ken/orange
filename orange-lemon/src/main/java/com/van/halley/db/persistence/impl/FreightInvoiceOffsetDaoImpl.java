package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightInvoiceOffsetDao;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;

@Repository("freightInvoiceOffsetDao")
public class FreightInvoiceOffsetDaoImpl extends
		BaseDaoImpl<FreightInvoiceOffset> implements FreightInvoiceOffsetDao {

	@Override
	public void deleteInvoiceOffset(FreightInvoiceOffset freightInvoiceOffset) {
		getSqlSession().delete("freightinvoiceoffset.deleteInvoiceOffset", freightInvoiceOffset);
	}
}

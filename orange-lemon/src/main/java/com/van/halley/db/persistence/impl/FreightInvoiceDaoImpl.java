package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.entity.FreightInvoice;

@Repository("freightInvoiceDao")
public class FreightInvoiceDaoImpl extends BaseDaoImpl<FreightInvoice>
		implements FreightInvoiceDao {
	@Override
	public List<FreightInvoice> getHasOffsetInvoice(String freightInvoiceId) {
		return getSqlSession().selectList("freightinvoice.getHasOffsetInvoice", freightInvoiceId);
	}

	@Override
	public List<FreightInvoice> getByFasDueId(String fasDueId) {
		return getSqlSession().selectList("freightinvoice.getByFasDueId", fasDueId);
	}

	@Override
	public List<FreightInvoice> getByFasPayId(String fasPayId) {
		return getSqlSession().selectList("freightinvoice.getByFasPayId", fasPayId);
	}

	@Override
	public List<FreightInvoice> getByFreightStatementId(String freightStatementId) {
		return getSqlSession().selectList("freightinvoice.getByFreightStatementId", freightStatementId);
	}
}

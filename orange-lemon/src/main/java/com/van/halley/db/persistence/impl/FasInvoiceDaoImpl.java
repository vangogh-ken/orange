package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasInvoiceDao;
import com.van.halley.db.persistence.entity.FasInvoice;

@Repository("fasInvoiceDao")
public class FasInvoiceDaoImpl extends BaseDaoImpl<FasInvoice> implements
		FasInvoiceDao {

	@Override
	public FasInvoice getFasInvoiceProximate(FasInvoice fasInvoice) {
		return getSqlSession().selectOne("fasinvoice.getFasInvoiceProximate", fasInvoice);
	}
}

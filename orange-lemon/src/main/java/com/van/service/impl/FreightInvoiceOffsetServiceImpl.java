package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightInvoiceOffsetDao;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;
import com.van.service.FreightInvoiceOffsetService;

@Transactional
@Service("freightInvoiceOffsetService")
public class FreightInvoiceOffsetServiceImpl implements
		FreightInvoiceOffsetService {
	@Autowired
	private FreightInvoiceOffsetDao freightInvoiceOffsetDao;

	public List<FreightInvoiceOffset> getAll() {
		return freightInvoiceOffsetDao.getAll();
	}

	public List<FreightInvoiceOffset> queryForList(
			FreightInvoiceOffset freightInvoiceOffset) {
		return freightInvoiceOffsetDao.queryForList(freightInvoiceOffset);
	}

	public FreightInvoiceOffset queryForOne(
			FreightInvoiceOffset freightInvoiceOffset) {
		return freightInvoiceOffsetDao.queryForOne(freightInvoiceOffset);
	}

	public PageView query(PageView pageView,
			FreightInvoiceOffset freightInvoiceOffset) {
		List<FreightInvoiceOffset> list = freightInvoiceOffsetDao.query(
				pageView, freightInvoiceOffset);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightInvoiceOffset freightInvoiceOffset) {
		freightInvoiceOffsetDao.add(freightInvoiceOffset);
	}

	public void delete(String id) {
		freightInvoiceOffsetDao.delete(id);
	}

	public void modify(FreightInvoiceOffset freightInvoiceOffset) {
		freightInvoiceOffsetDao.modify(freightInvoiceOffset);
	}

	public FreightInvoiceOffset getById(String id) {
		return freightInvoiceOffsetDao.getById(id);
	}
}

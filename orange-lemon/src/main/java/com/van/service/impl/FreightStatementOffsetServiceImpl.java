package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightStatementOffsetDao;
import com.van.halley.db.persistence.entity.FreightStatementOffset;
import com.van.service.FreightStatementOffsetService;

@Transactional
@Service("freightStatementOffsetService")
public class FreightStatementOffsetServiceImpl implements
		FreightStatementOffsetService {
	@Autowired
	private FreightStatementOffsetDao freightStatementOffsetDao;

	public List<FreightStatementOffset> getAll() {
		return freightStatementOffsetDao.getAll();
	}

	public List<FreightStatementOffset> queryForList(
			FreightStatementOffset freightStatementOffset) {
		return freightStatementOffsetDao.queryForList(freightStatementOffset);
	}

	public FreightStatementOffset queryForOne(
			FreightStatementOffset freightStatementOffset) {
		return freightStatementOffsetDao.queryForOne(freightStatementOffset);
	}

	public PageView query(PageView pageView,
			FreightStatementOffset freightStatementOffset) {
		List<FreightStatementOffset> list = freightStatementOffsetDao.query(
				pageView, freightStatementOffset);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightStatementOffset freightStatementOffset) {
		freightStatementOffsetDao.add(freightStatementOffset);
	}

	public void delete(String id) {
		freightStatementOffsetDao.delete(id);
	}

	public void modify(FreightStatementOffset freightStatementOffset) {
		freightStatementOffsetDao.modify(freightStatementOffset);
	}

	public FreightStatementOffset getById(String id) {
		return freightStatementOffsetDao.getById(id);
	}
}

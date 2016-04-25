package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.entity.FreightActionValue;
import com.van.service.FreightActionValueService;

@Transactional
@Service("freightActionValueService")
public class FreightActionValueServiceImpl implements FreightActionValueService {
	@Autowired
	private FreightActionValueDao freightActionValueDao;

	public List<FreightActionValue> getAll() {
		return freightActionValueDao.getAll();
	}

	public List<FreightActionValue> queryForList(
			FreightActionValue freightActionValue) {
		return freightActionValueDao.queryForList(freightActionValue);
	}

	public PageView query(PageView pageView,
			FreightActionValue freightActionValue) {
		List<FreightActionValue> list = freightActionValueDao.query(pageView,
				freightActionValue);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightActionValue freightActionValue) {
		freightActionValueDao.add(freightActionValue);
	}

	public void delete(String id) {
		freightActionValueDao.delete(id);
	}

	public void modify(FreightActionValue freightActionValue) {
		freightActionValueDao.modify(freightActionValue);
	}

	public FreightActionValue getById(String id) {
		return freightActionValueDao.getById(id);
	}
}

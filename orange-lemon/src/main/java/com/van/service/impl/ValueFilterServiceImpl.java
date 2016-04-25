package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ValueFilterDao;
import com.van.halley.db.persistence.entity.ValueFilter;
import com.van.service.ValueFilterService;

@Transactional
@Service("valueFilterService")
public class ValueFilterServiceImpl implements ValueFilterService {
	@Autowired
	private ValueFilterDao valueFilterDao;

	public List<ValueFilter> getAll() {
		return valueFilterDao.getAll();
	}

	public List<ValueFilter> queryForList(ValueFilter valueFilter) {
		return valueFilterDao.queryForList(valueFilter);
	}

	public ValueFilter queryForOne(ValueFilter valueFilter) {
		return valueFilterDao.queryForOne(valueFilter);
	}

	public PageView query(PageView pageView, ValueFilter valueFilter) {
		List<ValueFilter> list = valueFilterDao.query(pageView, valueFilter);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ValueFilter valueFilter) {
		valueFilterDao.add(valueFilter);
	}

	public void delete(String id) {
		valueFilterDao.delete(id);
	}

	public void modify(ValueFilter valueFilter) {
		valueFilterDao.modify(valueFilter);
	}

	public ValueFilter getById(String id) {
		return valueFilterDao.getById(id);
	}
}

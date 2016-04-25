package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ValueClassDao;
import com.van.halley.db.persistence.entity.ValueClass;
import com.van.service.ValueClassService;

@Transactional
@Service("valueClassService")
public class ValueClassServiceImpl implements ValueClassService {
	@Autowired
	private ValueClassDao valueClassDao;

	public List<ValueClass> getAll() {
		return valueClassDao.getAll();
	}

	public List<ValueClass> queryForList(ValueClass valueClass) {
		return valueClassDao.queryForList(valueClass);
	}

	public PageView query(PageView pageView, ValueClass valueClass) {
		List<ValueClass> list = valueClassDao.query(pageView, valueClass);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ValueClass valueClass) {
		valueClassDao.add(valueClass);
	}

	public void delete(String id) {
		valueClassDao.delete(id);
	}

	public void modify(ValueClass valueClass) {
		valueClassDao.modify(valueClass);
	}

	public ValueClass getById(String id) {
		return valueClassDao.getById(id);
	}
}

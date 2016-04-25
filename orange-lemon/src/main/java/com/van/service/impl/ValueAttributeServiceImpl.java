package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ValueAttributeDao;
import com.van.halley.db.persistence.entity.ValueAttribute;
import com.van.service.ValueAttributeService;

@Transactional
@Service("valueAttributeService")
public class ValueAttributeServiceImpl implements ValueAttributeService {
	@Autowired
	private ValueAttributeDao valueAttributeDao;

	public List<ValueAttribute> getAll() {
		return valueAttributeDao.getAll();
	}

	public List<ValueAttribute> queryForList(ValueAttribute valueAttribute) {
		return valueAttributeDao.queryForList(valueAttribute);
	}

	public PageView query(PageView pageView, ValueAttribute valueAttribute) {
		List<ValueAttribute> list = valueAttributeDao.query(pageView,
				valueAttribute);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ValueAttribute valueAttribute) {
		valueAttributeDao.add(valueAttribute);
	}

	public void delete(String id) {
		valueAttributeDao.delete(id);
	}

	public void modify(ValueAttribute valueAttribute) {
		valueAttributeDao.modify(valueAttribute);
	}

	public ValueAttribute getById(String id) {
		return valueAttributeDao.getById(id);
	}
}

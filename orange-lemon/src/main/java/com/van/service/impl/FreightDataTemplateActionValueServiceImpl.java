package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightDataTemplateActionValueDao;
import com.van.halley.db.persistence.entity.FreightDataTemplateActionValue;
import com.van.service.FreightDataTemplateActionValueService;

@Transactional
@Service("freightDataTemplateActionValueService")
public class FreightDataTemplateActionValueServiceImpl implements
		FreightDataTemplateActionValueService {
	@Autowired
	private FreightDataTemplateActionValueDao freightDataTemplateActionValueDao;

	public List<FreightDataTemplateActionValue> getAll() {
		return freightDataTemplateActionValueDao.getAll();
	}

	public List<FreightDataTemplateActionValue> queryForList(
			FreightDataTemplateActionValue freightDataTemplateActionValue) {
		return freightDataTemplateActionValueDao
				.queryForList(freightDataTemplateActionValue);
	}

	public FreightDataTemplateActionValue queryForOne(
			FreightDataTemplateActionValue freightDataTemplateActionValue) {
		return freightDataTemplateActionValueDao
				.queryForOne(freightDataTemplateActionValue);
	}

	public PageView query(PageView pageView,
			FreightDataTemplateActionValue freightDataTemplateActionValue) {
		List<FreightDataTemplateActionValue> list = freightDataTemplateActionValueDao
				.query(pageView, freightDataTemplateActionValue);
		pageView.setResults(list);
		return pageView;
	}

	public void add(
			FreightDataTemplateActionValue freightDataTemplateActionValue) {
		freightDataTemplateActionValueDao.add(freightDataTemplateActionValue);
	}

	public void delete(String id) {
		freightDataTemplateActionValueDao.delete(id);
	}

	public void modify(
			FreightDataTemplateActionValue freightDataTemplateActionValue) {
		freightDataTemplateActionValueDao
				.modify(freightDataTemplateActionValue);
	}

	public FreightDataTemplateActionValue getById(String id) {
		return freightDataTemplateActionValueDao.getById(id);
	}
}

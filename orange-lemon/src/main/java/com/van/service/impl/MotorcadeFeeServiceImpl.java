package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeFeeDao;
import com.van.halley.db.persistence.entity.MotorcadeFee;
import com.van.service.MotorcadeFeeService;

@Transactional
@Service("motorcadeFeeService")
public class MotorcadeFeeServiceImpl implements MotorcadeFeeService {
	@Autowired
	private MotorcadeFeeDao motorcadeFeeDao;

	public List<MotorcadeFee> getAll() {
		return motorcadeFeeDao.getAll();
	}

	public List<MotorcadeFee> queryForList(MotorcadeFee motorcadeFee) {
		return motorcadeFeeDao.queryForList(motorcadeFee);
	}

	public MotorcadeFee queryForOne(MotorcadeFee motorcadeFee) {
		return motorcadeFeeDao.queryForOne(motorcadeFee);
	}

	public PageView<MotorcadeFee> query(PageView<MotorcadeFee> pageView, MotorcadeFee motorcadeFee) {
		List<MotorcadeFee> list = motorcadeFeeDao.query(pageView, motorcadeFee);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeFee motorcadeFee) {
		motorcadeFeeDao.add(motorcadeFee);
	}

	public void delete(String id) {
		motorcadeFeeDao.delete(id);
	}

	public void modify(MotorcadeFee motorcadeFee) {
		motorcadeFeeDao.modify(motorcadeFee);
	}

	public MotorcadeFee getById(String id) {
		return motorcadeFeeDao.getById(id);
	}
}

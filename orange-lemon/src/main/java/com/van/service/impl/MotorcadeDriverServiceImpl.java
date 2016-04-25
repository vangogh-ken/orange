package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeDriverDao;
import com.van.halley.db.persistence.entity.MotorcadeDriver;
import com.van.service.MotorcadeDriverService;

@Transactional
@Service("motorcadeDriverService")
public class MotorcadeDriverServiceImpl implements MotorcadeDriverService {
	@Autowired
	private MotorcadeDriverDao motorcadeDriverDao;

	public List<MotorcadeDriver> getAll() {
		return motorcadeDriverDao.getAll();
	}

	public List<MotorcadeDriver> queryForList(MotorcadeDriver motorcadeDriver) {
		return motorcadeDriverDao.queryForList(motorcadeDriver);
	}

	public MotorcadeDriver queryForOne(MotorcadeDriver motorcadeDriver) {
		return motorcadeDriverDao.queryForOne(motorcadeDriver);
	}

	public PageView<MotorcadeDriver> query(PageView<MotorcadeDriver> pageView, MotorcadeDriver motorcadeDriver) {
		List<MotorcadeDriver> list = motorcadeDriverDao.query(pageView, motorcadeDriver);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeDriver motorcadeDriver) {
		motorcadeDriverDao.add(motorcadeDriver);
	}

	public void delete(String id) {
		motorcadeDriverDao.delete(id);
	}

	public void modify(MotorcadeDriver motorcadeDriver) {
		motorcadeDriverDao.modify(motorcadeDriver);
	}

	public MotorcadeDriver getById(String id) {
		return motorcadeDriverDao.getById(id);
	}
}

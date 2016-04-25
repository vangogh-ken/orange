package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeTruckDao;
import com.van.halley.db.persistence.entity.MotorcadeTruck;
import com.van.service.MotorcadeTruckService;

@Transactional
@Service("motorcadeTruckService")
public class MotorcadeTruckServiceImpl implements MotorcadeTruckService {
	@Autowired
	private MotorcadeTruckDao motorcadeTruckDao;

	public List<MotorcadeTruck> getAll() {
		return motorcadeTruckDao.getAll();
	}

	public List<MotorcadeTruck> queryForList(MotorcadeTruck motorcadeTruck) {
		return motorcadeTruckDao.queryForList(motorcadeTruck);
	}

	public MotorcadeTruck queryForOne(MotorcadeTruck motorcadeTruck) {
		return motorcadeTruckDao.queryForOne(motorcadeTruck);
	}

	public PageView<MotorcadeTruck> query(PageView<MotorcadeTruck> pageView, MotorcadeTruck motorcadeTruck) {
		List<MotorcadeTruck> list = motorcadeTruckDao.query(pageView, motorcadeTruck);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeTruck motorcadeTruck) {
		motorcadeTruckDao.add(motorcadeTruck);
	}

	public void delete(String id) {
		motorcadeTruckDao.delete(id);
	}

	public void modify(MotorcadeTruck motorcadeTruck) {
		motorcadeTruckDao.modify(motorcadeTruck);
	}

	public MotorcadeTruck getById(String id) {
		return motorcadeTruckDao.getById(id);
	}
}

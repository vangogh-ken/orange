package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeDriver;

public interface MotorcadeDriverService {
	public List<MotorcadeDriver> getAll();

	public List<MotorcadeDriver> queryForList(MotorcadeDriver motorcadeDriver);

	public MotorcadeDriver queryForOne(MotorcadeDriver motorcadeDriver);

	public PageView<MotorcadeDriver> query(PageView<MotorcadeDriver> pageView, MotorcadeDriver motorcadeDriver);

	public void add(MotorcadeDriver motorcadeDriver);

	public void delete(String id);

	public void modify(MotorcadeDriver motorcadeDriver);

	public MotorcadeDriver getById(String id);
}

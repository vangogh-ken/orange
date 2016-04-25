package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeTruck;

public interface MotorcadeTruckService {
	public List<MotorcadeTruck> getAll();

	public List<MotorcadeTruck> queryForList(MotorcadeTruck motorcadeTruck);

	public MotorcadeTruck queryForOne(MotorcadeTruck motorcadeTruck);

	public PageView<MotorcadeTruck> query(PageView<MotorcadeTruck> pageView, MotorcadeTruck motorcadeTruck);

	public void add(MotorcadeTruck motorcadeTruck);

	public void delete(String id);

	public void modify(MotorcadeTruck motorcadeTruck);

	public MotorcadeTruck getById(String id);
}

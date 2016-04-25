package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadePetrol;

public interface MotorcadePetrolService {
	public List<MotorcadePetrol> getAll();

	public List<MotorcadePetrol> queryForList(MotorcadePetrol motorcadePetrol);

	public MotorcadePetrol queryForOne(MotorcadePetrol motorcadePetrol);

	public PageView<MotorcadePetrol> query(PageView<MotorcadePetrol> pageView, MotorcadePetrol motorcadePetrol);

	public void add(MotorcadePetrol motorcadePetrol);

	public void delete(String id);

	public void modify(MotorcadePetrol motorcadePetrol);

	public MotorcadePetrol getById(String id);

	public List<List<String>> doneBatchExport(String[] motorcadePetrolIds);
}

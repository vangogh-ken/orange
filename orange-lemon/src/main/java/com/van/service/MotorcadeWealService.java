package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeWeal;

public interface MotorcadeWealService {
	public List<MotorcadeWeal> getAll();

	public List<MotorcadeWeal> queryForList(MotorcadeWeal motorcadeWeal);

	public MotorcadeWeal queryForOne(MotorcadeWeal motorcadeWeal);

	public PageView<MotorcadeWeal> query(PageView<MotorcadeWeal> pageView, MotorcadeWeal motorcadeWeal);

	public void add(MotorcadeWeal motorcadeWeal);

	public void delete(String id);

	public void modify(MotorcadeWeal motorcadeWeal);

	public MotorcadeWeal getById(String id);

	public List<List<String>> doneBatchExport(String[] motorcadeWealIds);
}

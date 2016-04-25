package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeToll;

public interface MotorcadeTollService {
	public List<MotorcadeToll> getAll();

	public List<MotorcadeToll> queryForList(MotorcadeToll motorcadeToll);

	public MotorcadeToll queryForOne(MotorcadeToll motorcadeToll);

	public PageView<MotorcadeToll> query(PageView<MotorcadeToll> pageView, MotorcadeToll motorcadeToll);

	public void add(MotorcadeToll motorcadeToll);

	public void delete(String id);

	public void modify(MotorcadeToll motorcadeToll);

	public MotorcadeToll getById(String id);

	public List<List<String>> doneBatchExport(String[] motorcadeTollIds);
}

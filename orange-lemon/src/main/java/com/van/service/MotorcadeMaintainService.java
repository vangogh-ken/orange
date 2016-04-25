package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeMaintain;

public interface MotorcadeMaintainService {
	public List<MotorcadeMaintain> getAll();

	public List<MotorcadeMaintain> queryForList(MotorcadeMaintain motorcadeMaintain);

	public MotorcadeMaintain queryForOne(MotorcadeMaintain motorcadeMaintain);

	public PageView<MotorcadeMaintain> query(PageView<MotorcadeMaintain> pageView, MotorcadeMaintain motorcadeMaintain);

	public void add(MotorcadeMaintain motorcadeMaintain);

	public void delete(String id);

	public void modify(MotorcadeMaintain motorcadeMaintain);

	public MotorcadeMaintain getById(String id);

	public List<List<String>> doneBatchExport(String[] motorcadeMaintainIds);
}

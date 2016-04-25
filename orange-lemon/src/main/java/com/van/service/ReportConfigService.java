package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportConfig;

public interface ReportConfigService {
	public List<ReportConfig> getAll();

	public List<ReportConfig> queryForList(ReportConfig reportConfig);

	public PageView query(PageView pageView, ReportConfig reportConfig);

	public void add(ReportConfig reportConfig);

	public void delete(String id);

	public void modify(ReportConfig reportConfig);

	public ReportConfig getById(String id);
}

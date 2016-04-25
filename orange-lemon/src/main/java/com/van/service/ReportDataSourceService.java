package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportDataSource;

public interface ReportDataSourceService {
	public List<ReportDataSource> getAll();

	public List<ReportDataSource> queryForList(ReportDataSource reportDataSource);

	public ReportDataSource queryForOne(ReportDataSource reportDataSource);

	public PageView query(PageView pageView, ReportDataSource reportDataSource);

	public void add(ReportDataSource reportDataSource);

	public void delete(String id);

	public void modify(ReportDataSource reportDataSource);

	public ReportDataSource getById(String id);

	public List<ReportDataSource> getByReportTemplateId(String reportTemplateId);
}

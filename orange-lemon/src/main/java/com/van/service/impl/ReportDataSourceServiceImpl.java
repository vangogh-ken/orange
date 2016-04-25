package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ReportDataSourceDao;
import com.van.halley.db.persistence.entity.ReportDataSource;
import com.van.service.ReportDataSourceService;

@Transactional
@Service("reportDataSourceService")
public class ReportDataSourceServiceImpl implements ReportDataSourceService {
	@Autowired
	private ReportDataSourceDao reportDataSourceDao;

	public List<ReportDataSource> getAll() {
		return reportDataSourceDao.getAll();
	}

	public List<ReportDataSource> queryForList(ReportDataSource reportDataSource) {
		return reportDataSourceDao.queryForList(reportDataSource);
	}

	public ReportDataSource queryForOne(ReportDataSource reportDataSource) {
		return reportDataSourceDao.queryForOne(reportDataSource);
	}

	public PageView query(PageView pageView, ReportDataSource reportDataSource) {
		List<ReportDataSource> list = reportDataSourceDao.query(pageView,
				reportDataSource);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ReportDataSource reportDataSource) {
		reportDataSourceDao.add(reportDataSource);
	}

	public void delete(String id) {
		reportDataSourceDao.delete(id);
	}

	public void modify(ReportDataSource reportDataSource) {
		reportDataSourceDao.modify(reportDataSource);
	}

	public ReportDataSource getById(String id) {
		return reportDataSourceDao.getById(id);
	}

	@Override
	public List<ReportDataSource> getByReportTemplateId(String reportTemplateId) {
		return reportDataSourceDao.getByReportTemplateId(reportTemplateId);
	}
}

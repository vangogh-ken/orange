package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ReportConfigDao;
import com.van.halley.db.persistence.entity.ReportConfig;
import com.van.service.ReportConfigService;

@Transactional
@Service("reportConfigService")
public class ReportConfigServiceImpl implements ReportConfigService {
	@Autowired
	private ReportConfigDao reportConfigDao;

	public List<ReportConfig> getAll() {
		return reportConfigDao.getAll();
	}

	public List<ReportConfig> queryForList(ReportConfig reportConfig) {
		return reportConfigDao.queryForList(reportConfig);
	}

	public PageView query(PageView pageView, ReportConfig reportConfig) {
		List<ReportConfig> list = reportConfigDao.query(pageView, reportConfig);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ReportConfig reportConfig) {
		reportConfigDao.add(reportConfig);
	}

	public void delete(String id) {
		reportConfigDao.delete(id);
	}

	public void modify(ReportConfig reportConfig) {
		reportConfigDao.modify(reportConfig);
	}

	public ReportConfig getById(String id) {
		return reportConfigDao.getById(id);
	}
}

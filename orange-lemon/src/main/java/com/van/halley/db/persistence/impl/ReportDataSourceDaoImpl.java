package com.van.halley.db.persistence.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportDataSourceDao;
import com.van.halley.db.persistence.entity.ReportDataSource;
@Repository("reportDataSourceDao")
public class ReportDataSourceDaoImpl extends BaseDaoImpl<ReportDataSource> implements ReportDataSourceDao {

	@Override
	public List<ReportDataSource> getByReportTemplateId(String reportTemplateId) {
		return getSqlSession().selectList("reportdatasource.getByReportTemplateId", reportTemplateId);
	}

	@Override
	public List<ReportDataSource> getParticipate() {
		ReportDataSource filter = new ReportDataSource();
		filter.setParticipate("T");
		filter.setStatus("T");
		return queryForList(filter);
	}}

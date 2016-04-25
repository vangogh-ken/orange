package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.ReportDataSource;

public interface ReportDataSourceDao extends BaseDao<ReportDataSource> {
	/**
	 * 通过模板获取数据源
	 * @param reportTemplateId
	 * @return
	 */
	public List<ReportDataSource> getByReportTemplateId(String reportTemplateId);
	/**
	 * 
	 * 获取共享的数据源
	 * @return
	 */
	public List<ReportDataSource> getParticipate();
}
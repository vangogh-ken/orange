package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.ReportSet;

public interface ReportSetDao extends BaseDao<ReportSet> {
	/**
	 * 通过模板获取
	 * @param reportTemplateId
	 * @return
	 */
	public List<ReportSet> getByReportTemplateId(String reportTemplateId);
}
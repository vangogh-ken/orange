package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.ReportParam;

public interface ReportParamDao extends BaseDao<ReportParam> {
	/**
	 * 获取报表所有的参数
	 * @param reportTemplateId
	 */
	public List<ReportParam> getByReportTemplateId(String reportTemplateId);
}
package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportIsDao;
import com.van.halley.db.persistence.entity.ReportIs;
@Repository("reportIsDao")
public class ReportIsDaoImpl extends BaseDaoImpl<ReportIs> implements ReportIsDao {

	@Override
	public void deleteByReportTemplateId(String reportTemplateId) {
		getSqlSession().delete("reportis.deleteByReportTemplateId", reportTemplateId);
	}

	@Override
	public ReportIs getByReportTemplateId(String reportTemplateId) {
		return getSqlSession().selectOne("reportis.getByReportTemplateId", reportTemplateId);
	}}

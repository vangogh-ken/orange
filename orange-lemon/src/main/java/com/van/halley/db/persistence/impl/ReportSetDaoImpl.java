package com.van.halley.db.persistence.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportSetDao;
import com.van.halley.db.persistence.entity.ReportSet;
@Repository("reportSetDao")
public class ReportSetDaoImpl extends BaseDaoImpl<ReportSet> implements ReportSetDao {

	@Override
	public List<ReportSet> getByReportTemplateId(String reportTemplateId) {
		return getSqlSession().selectList("reportset.getByReportTemplateId", reportTemplateId);
	}}

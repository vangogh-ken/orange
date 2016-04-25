package com.van.halley.db.persistence.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportParamDao;
import com.van.halley.db.persistence.entity.ReportParam;
@Repository("reportParamDao")
public class ReportParamDaoImpl extends BaseDaoImpl<ReportParam> implements ReportParamDao {

	@Override
	public List<ReportParam> getByReportTemplateId(String reportTemplateId) {
		return getSqlSession().selectList("reportparam.getByReportTemplateId", reportTemplateId);
	}}

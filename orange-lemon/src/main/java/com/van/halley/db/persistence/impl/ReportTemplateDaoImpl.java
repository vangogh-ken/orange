package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportTemplateDao;
import com.van.halley.db.persistence.entity.ReportTemplate;
import com.van.halley.db.persistence.entity.RoleReport;

@Repository("reportTemplateDao")
public class ReportTemplateDaoImpl extends BaseDaoImpl<ReportTemplate>
		implements ReportTemplateDao {
	@Override
	public List<ReportTemplate> getByRoleId(String roleId) {
		return getSqlSession().selectList("reporttemplate.getByRoleId", roleId);
	}

	@Override
	public void addRoleReport(String roleId, String reportTemplateId) {
		RoleReport roleReport = new RoleReport(roleId, reportTemplateId);
		getSqlSession().insert("reporttemplate.addRoleReport", roleReport);
	}

	@Override
	public void deleteRoleReport(String roleId) {
		getSqlSession().delete("reporttemplate.deleteRoleReport", roleId);
	}

	@Override
	public List<ReportTemplate> getByPositionId(String positionId) {
		return getSqlSession().selectList("reporttemplate.getByPositionId", positionId);
	}

	@Override
	public List<ReportTemplate> getAllStatistics() {
		List<ReportTemplate> list = Lists.newArrayList();
		ReportTemplate filter = new ReportTemplate();
		filter.setTemplateType("报表");
		filter.setStatus("T");
		list.addAll(queryForList(filter));
		//filter.setStatus("TBASIS");
		//list.addAll(queryForList(filter));
		return list;
	}
}

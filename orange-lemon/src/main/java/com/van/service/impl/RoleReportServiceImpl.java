package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.RoleReportDao;
import com.van.halley.db.persistence.entity.RoleReport;
import com.van.service.RoleReportService;

@Transactional
@Service("roleReportService")
public class RoleReportServiceImpl implements RoleReportService {
	@Autowired
	private RoleReportDao roleReportDao;

	public List<RoleReport> getAll() {
		return roleReportDao.getAll();
	}

	public List<RoleReport> queryForList(RoleReport roleReport) {
		return roleReportDao.queryForList(roleReport);
	}

	public RoleReport queryForOne(RoleReport roleReport) {
		return roleReportDao.queryForOne(roleReport);
	}

	public PageView query(PageView pageView, RoleReport roleReport) {
		List<RoleReport> list = roleReportDao.query(pageView, roleReport);
		pageView.setResults(list);
		return pageView;
	}

	public void add(RoleReport roleReport) {
		roleReportDao.add(roleReport);
	}

	public void delete(String id) {
		roleReportDao.delete(id);
	}

	public void modify(RoleReport roleReport) {
		roleReportDao.modify(roleReport);
	}

	public RoleReport getById(String id) {
		return roleReportDao.getById(id);
	}
}

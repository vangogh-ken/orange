package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.RoleReport;
public interface RoleReportService{
public List<RoleReport> getAll();
public List<RoleReport> queryForList(RoleReport roleReport);
public RoleReport queryForOne(RoleReport roleReport);
public PageView query(PageView pageView,RoleReport roleReport);
public void add(RoleReport roleReport);
public void delete(String id);
public void modify(RoleReport roleReport);
public RoleReport getById(String id);
}

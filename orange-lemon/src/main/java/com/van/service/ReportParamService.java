package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportParam;
public interface ReportParamService{
public List<ReportParam> getAll();
public List<ReportParam> queryForList(ReportParam reportParam);
public ReportParam queryForOne(ReportParam reportParam);
public PageView query(PageView pageView,ReportParam reportParam);
public void add(ReportParam reportParam);
public void delete(String id);
public void modify(ReportParam reportParam);
public ReportParam getById(String id);
}

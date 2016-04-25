package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportIs;
public interface ReportIsService{
public List<ReportIs> getAll();
public List<ReportIs> queryForList(ReportIs reportIs);
public ReportIs queryForOne(ReportIs reportIs);
public PageView query(PageView pageView,ReportIs reportIs);
public void add(ReportIs reportIs);
public void delete(String id);
public void modify(ReportIs reportIs);
public ReportIs getById(String id);
}

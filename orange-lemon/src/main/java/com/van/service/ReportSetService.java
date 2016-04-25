package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportSet;
public interface ReportSetService{
public List<ReportSet> getAll();
public List<ReportSet> queryForList(ReportSet reportSet);
public ReportSet queryForOne(ReportSet reportSet);
public PageView query(PageView pageView,ReportSet reportSet);
public void add(ReportSet reportSet);
public void delete(String id);
public void modify(ReportSet reportSet);
public ReportSet getById(String id);
}

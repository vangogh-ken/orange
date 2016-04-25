package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportCategory;
public interface ReportCategoryService{
public List<ReportCategory> getAll();
public List<ReportCategory> queryForList(ReportCategory reportCategory);
public ReportCategory queryForOne(ReportCategory reportCategory);
public PageView query(PageView pageView,ReportCategory reportCategory);
public void add(ReportCategory reportCategory);
public void delete(String id);
public void modify(ReportCategory reportCategory);
public ReportCategory getById(String id);
}

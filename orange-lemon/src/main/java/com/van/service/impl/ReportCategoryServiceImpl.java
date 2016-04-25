package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ReportCategoryDao;
import com.van.halley.db.persistence.entity.ReportCategory;
import com.van.service.ReportCategoryService;
@Transactional
@Service("reportCategoryService")
public class ReportCategoryServiceImpl implements ReportCategoryService {
@Autowired
private ReportCategoryDao reportCategoryDao;
public List<ReportCategory> getAll() {
return reportCategoryDao.getAll();
}
public List<ReportCategory> queryForList(ReportCategory reportCategory) { 
return reportCategoryDao.queryForList(reportCategory); 
}
public ReportCategory queryForOne(ReportCategory reportCategory) { 
return reportCategoryDao.queryForOne(reportCategory); 
}
public PageView query(PageView pageView, ReportCategory reportCategory) {
List<ReportCategory> list = reportCategoryDao.query(pageView, reportCategory);
pageView.setResults(list);
return pageView;
}
public void add(ReportCategory reportCategory) {
reportCategoryDao.add(reportCategory);
}
public void delete(String id) {
reportCategoryDao.delete(id);
}
public void modify(ReportCategory reportCategory) {
reportCategoryDao.modify(reportCategory);
}
public ReportCategory getById(String id) {
return reportCategoryDao.getById(id);
}
}

package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ReportIsDao;
import com.van.halley.db.persistence.entity.ReportIs;
import com.van.service.ReportIsService;
@Transactional
@Service("reportIsService")
public class ReportIsServiceImpl implements ReportIsService {
@Autowired
private ReportIsDao reportIsDao;
public List<ReportIs> getAll() {
return reportIsDao.getAll();
}
public List<ReportIs> queryForList(ReportIs reportIs) { 
return reportIsDao.queryForList(reportIs); 
}
public ReportIs queryForOne(ReportIs reportIs) { 
return reportIsDao.queryForOne(reportIs); 
}
public PageView query(PageView pageView, ReportIs reportIs) {
List<ReportIs> list = reportIsDao.query(pageView, reportIs);
pageView.setResults(list);
return pageView;
}
public void add(ReportIs reportIs) {
reportIsDao.add(reportIs);
}
public void delete(String id) {
reportIsDao.delete(id);
}
public void modify(ReportIs reportIs) {
reportIsDao.modify(reportIs);
}
public ReportIs getById(String id) {
return reportIsDao.getById(id);
}
}

package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ReportSetDao;
import com.van.halley.db.persistence.entity.ReportSet;
import com.van.service.ReportSetService;
@Transactional
@Service("reportSetService")
public class ReportSetServiceImpl implements ReportSetService {
@Autowired
private ReportSetDao reportSetDao;
public List<ReportSet> getAll() {
return reportSetDao.getAll();
}
public List<ReportSet> queryForList(ReportSet reportSet) { 
return reportSetDao.queryForList(reportSet); 
}
public ReportSet queryForOne(ReportSet reportSet) { 
return reportSetDao.queryForOne(reportSet); 
}
public PageView query(PageView pageView, ReportSet reportSet) {
List<ReportSet> list = reportSetDao.query(pageView, reportSet);
pageView.setResults(list);
return pageView;
}
public void add(ReportSet reportSet) {
reportSetDao.add(reportSet);
}
public void delete(String id) {
reportSetDao.delete(id);
}
public void modify(ReportSet reportSet) {
reportSetDao.modify(reportSet);
}
public ReportSet getById(String id) {
return reportSetDao.getById(id);
}
}

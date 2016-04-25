package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ReportParamDao;
import com.van.halley.db.persistence.entity.ReportParam;
import com.van.service.ReportParamService;
@Transactional
@Service("reportParamService")
public class ReportParamServiceImpl implements ReportParamService {
@Autowired
private ReportParamDao reportParamDao;
public List<ReportParam> getAll() {
return reportParamDao.getAll();
}
public List<ReportParam> queryForList(ReportParam reportParam) { 
return reportParamDao.queryForList(reportParam); 
}
public ReportParam queryForOne(ReportParam reportParam) { 
return reportParamDao.queryForOne(reportParam); 
}
public PageView query(PageView pageView, ReportParam reportParam) {
List<ReportParam> list = reportParamDao.query(pageView, reportParam);
pageView.setResults(list);
return pageView;
}
public void add(ReportParam reportParam) {
reportParamDao.add(reportParam);
}
public void delete(String id) {
reportParamDao.delete(id);
}
public void modify(ReportParam reportParam) {
reportParamDao.modify(reportParam);
}
public ReportParam getById(String id) {
return reportParamDao.getById(id);
}
}

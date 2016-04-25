package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.WorkCalPartDao;
import com.van.halley.db.persistence.entity.WorkCalPart;
import com.van.service.WorkCalPartService;
@Transactional
@Service("workcalPartService")
public class WorkCalPartServiceImpl implements WorkCalPartService {
@Autowired
private WorkCalPartDao workcalPartDao;
public List<WorkCalPart> getAll() {
return workcalPartDao.getAll();
}
public List<WorkCalPart> queryForList(WorkCalPart workcalPart) { 
return workcalPartDao.queryForList(workcalPart); 
}
public PageView query(PageView pageView, WorkCalPart workcalPart) {
List<WorkCalPart> list = workcalPartDao.query(pageView, workcalPart);
pageView.setResults(list);
return pageView;
}
public void add(WorkCalPart workcalPart) {
workcalPartDao.add(workcalPart);
}
public void delete(String id) {
workcalPartDao.delete(id);
}
public void modify(WorkCalPart workcalPart) {
workcalPartDao.modify(workcalPart);
}
public WorkCalPart getById(String id) {
return workcalPartDao.getById(id);
}
}

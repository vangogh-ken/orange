package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.WorkCalTypeDao;
import com.van.halley.db.persistence.entity.WorkCalType;
import com.van.service.WorkCalTypeService;
@Transactional
@Service("workcalTypeService")
public class WorkCalTypeServiceImpl implements WorkCalTypeService {
@Autowired
private WorkCalTypeDao workcalTypeDao;
public List<WorkCalType> getAll() {
return workcalTypeDao.getAll();
}
public List<WorkCalType> queryForList(WorkCalType workcalType) { 
return workcalTypeDao.queryForList(workcalType); 
}
public PageView query(PageView pageView, WorkCalType workcalType) {
List<WorkCalType> list = workcalTypeDao.query(pageView, workcalType);
pageView.setResults(list);
return pageView;
}
public void add(WorkCalType workcalType) {
workcalTypeDao.add(workcalType);
}
public void delete(String id) {
workcalTypeDao.delete(id);
}
public void modify(WorkCalType workcalType) {
workcalTypeDao.modify(workcalType);
}
public WorkCalType getById(String id) {
return workcalTypeDao.getById(id);
}
}

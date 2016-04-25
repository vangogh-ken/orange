package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.WorkCalRuleDao;
import com.van.halley.db.persistence.entity.WorkCalRule;
import com.van.service.WorkCalRuleService;
@Transactional
@Service("workcalRuleService")
public class WorkCalRuleServiceImpl implements WorkCalRuleService {
@Autowired
private WorkCalRuleDao workcalRuleDao;
public List<WorkCalRule> getAll() {
return workcalRuleDao.getAll();
}
public List<WorkCalRule> queryForList(WorkCalRule workcalRule) { 
return workcalRuleDao.queryForList(workcalRule); 
}
public PageView query(PageView pageView, WorkCalRule workcalRule) {
List<WorkCalRule> list = workcalRuleDao.query(pageView, workcalRule);
pageView.setResults(list);
return pageView;
}
public void add(WorkCalRule workcalRule) {
workcalRuleDao.add(workcalRule);
}
public void delete(String id) {
workcalRuleDao.delete(id);
}
public void modify(WorkCalRule workcalRule) {
workcalRuleDao.modify(workcalRule);
}
public WorkCalRule getById(String id) {
return workcalRuleDao.getById(id);
}
}

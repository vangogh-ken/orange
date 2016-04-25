package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.WorkCalRule;
public interface WorkCalRuleService{
public List<WorkCalRule> getAll();
public List<WorkCalRule> queryForList(WorkCalRule workcalRule);public PageView query(PageView pageView,WorkCalRule workcalRule);
public void add(WorkCalRule workcalRule);
public void delete(String id);
public void modify(WorkCalRule workcalRule);
public WorkCalRule getById(String id);
}

package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.WorkCalType;
public interface WorkCalTypeService{
public List<WorkCalType> getAll();
public List<WorkCalType> queryForList(WorkCalType workcalType);public PageView query(PageView pageView,WorkCalType workcalType);
public void add(WorkCalType workcalType);
public void delete(String id);
public void modify(WorkCalType workcalType);
public WorkCalType getById(String id);
}

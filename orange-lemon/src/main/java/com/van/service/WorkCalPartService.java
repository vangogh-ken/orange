package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.WorkCalPart;
public interface WorkCalPartService{
public List<WorkCalPart> getAll();
public List<WorkCalPart> queryForList(WorkCalPart workcalPart);public PageView query(PageView pageView,WorkCalPart workcalPart);
public void add(WorkCalPart workcalPart);
public void delete(String id);
public void modify(WorkCalPart workcalPart);
public WorkCalPart getById(String id);
}

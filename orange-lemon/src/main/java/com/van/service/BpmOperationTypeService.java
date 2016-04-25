package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmOperationType;
public interface BpmOperationTypeService{
public List<BpmOperationType> getAll();
public List<BpmOperationType> queryForList(BpmOperationType bpmOperationType);public PageView query(PageView pageView,BpmOperationType bpmOperationType);
public void add(BpmOperationType bpmOperationType);
public void delete(String id);
public void modify(BpmOperationType bpmOperationType);
public BpmOperationType getById(String id);
}

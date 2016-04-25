package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigOperation;
public interface BpmConfigOperationService{
public List<BpmConfigOperation> getAll();
public List<BpmConfigOperation> queryForList(BpmConfigOperation bpmConfigOperation);public PageView query(PageView pageView,BpmConfigOperation bpmConfigOperation);
public void add(BpmConfigOperation bpmConfigOperation);
public void delete(String id);
public void modify(BpmConfigOperation bpmConfigOperation);
public BpmConfigOperation getById(String id);
}

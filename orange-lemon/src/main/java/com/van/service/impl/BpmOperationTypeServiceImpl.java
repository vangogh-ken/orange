package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmOperationTypeDao;
import com.van.halley.db.persistence.entity.BpmOperationType;
import com.van.service.BpmOperationTypeService;
@Transactional
@Service("bpmOperationTypeService")
public class BpmOperationTypeServiceImpl implements BpmOperationTypeService {
@Autowired
private BpmOperationTypeDao bpmOperationTypeDao;
public List<BpmOperationType> getAll() {
return bpmOperationTypeDao.getAll();
}
public List<BpmOperationType> queryForList(BpmOperationType bpmOperationType) { 
return bpmOperationTypeDao.queryForList(bpmOperationType); 
}
public PageView query(PageView pageView, BpmOperationType bpmOperationType) {
List<BpmOperationType> list = bpmOperationTypeDao.query(pageView, bpmOperationType);
pageView.setResults(list);
return pageView;
}
public void add(BpmOperationType bpmOperationType) {
bpmOperationTypeDao.add(bpmOperationType);
}
public void delete(String id) {
bpmOperationTypeDao.delete(id);
}
public void modify(BpmOperationType bpmOperationType) {
bpmOperationTypeDao.modify(bpmOperationType);
}
public BpmOperationType getById(String id) {
return bpmOperationTypeDao.getById(id);
}
}

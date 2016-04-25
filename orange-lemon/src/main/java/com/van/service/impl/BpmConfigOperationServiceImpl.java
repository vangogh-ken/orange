package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigOperationDao;
import com.van.halley.db.persistence.entity.BpmConfigOperation;
import com.van.service.BpmConfigOperationService;
@Transactional
@Service("bpmConfigOperationService")
public class BpmConfigOperationServiceImpl implements BpmConfigOperationService {
@Autowired
private BpmConfigOperationDao bpmConfigOperationDao;
public List<BpmConfigOperation> getAll() {
return bpmConfigOperationDao.getAll();
}
public List<BpmConfigOperation> queryForList(BpmConfigOperation bpmConfigOperation) { 
return bpmConfigOperationDao.queryForList(bpmConfigOperation); 
}
public PageView query(PageView pageView, BpmConfigOperation bpmConfigOperation) {
List<BpmConfigOperation> list = bpmConfigOperationDao.query(pageView, bpmConfigOperation);
pageView.setResults(list);
return pageView;
}
public void add(BpmConfigOperation bpmConfigOperation) {
bpmConfigOperationDao.add(bpmConfigOperation);
}
public void delete(String id) {
bpmConfigOperationDao.delete(id);
}
public void modify(BpmConfigOperation bpmConfigOperation) {
bpmConfigOperationDao.modify(bpmConfigOperation);
}
public BpmConfigOperation getById(String id) {
return bpmConfigOperationDao.getById(id);
}
}

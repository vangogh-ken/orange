package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmConfigCategoryDao;
import com.van.halley.db.persistence.entity.BpmConfigCategory;
import com.van.service.BpmConfigCategoryService;
@Transactional
@Service("bpmConfigCategoryService")
public class BpmConfigCategoryServiceImpl implements BpmConfigCategoryService {
@Autowired
private BpmConfigCategoryDao bpmConfigCategoryDao;
public List<BpmConfigCategory> getAll() {
return bpmConfigCategoryDao.getAll();
}
public List<BpmConfigCategory> queryForList(BpmConfigCategory bpmConfigCategory) { 
return bpmConfigCategoryDao.queryForList(bpmConfigCategory); 
}
public PageView query(PageView pageView, BpmConfigCategory bpmConfigCategory) {
List<BpmConfigCategory> list = bpmConfigCategoryDao.query(pageView, bpmConfigCategory);
pageView.setResults(list);
return pageView;
}
public void add(BpmConfigCategory bpmConfigCategory) {
bpmConfigCategoryDao.add(bpmConfigCategory);
}
public void delete(String id) {
bpmConfigCategoryDao.delete(id);
}
public void modify(BpmConfigCategory bpmConfigCategory) {
bpmConfigCategoryDao.modify(bpmConfigCategory);
}
public BpmConfigCategory getById(String id) {
return bpmConfigCategoryDao.getById(id);
}
}

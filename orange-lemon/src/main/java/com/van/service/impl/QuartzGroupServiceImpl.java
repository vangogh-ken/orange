package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.QuartzGroupDao;
import com.van.halley.db.persistence.entity.QuartzGroup;
import com.van.service.QuartzGroupService;
@Transactional
@Service("quartzGroupService")
public class QuartzGroupServiceImpl implements QuartzGroupService {
@Autowired
private QuartzGroupDao quartzGroupDao;
public List<QuartzGroup> getAll() {
return quartzGroupDao.getAll();
}
public List<QuartzGroup> queryForList(QuartzGroup quartzGroup) { 
return quartzGroupDao.queryForList(quartzGroup); 
}
public QuartzGroup queryForOne(QuartzGroup quartzGroup) { 
return quartzGroupDao.queryForOne(quartzGroup); 
}
public PageView query(PageView pageView, QuartzGroup quartzGroup) {
List<QuartzGroup> list = quartzGroupDao.query(pageView, quartzGroup);
pageView.setResults(list);
return pageView;
}
public void add(QuartzGroup quartzGroup) {
quartzGroupDao.add(quartzGroup);
}
public void delete(String id) {
quartzGroupDao.delete(id);
}
public void modify(QuartzGroup quartzGroup) {
quartzGroupDao.modify(quartzGroup);
}
public QuartzGroup getById(String id) {
return quartzGroupDao.getById(id);
}
}

package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.QuartzTaskDao;
import com.van.halley.db.persistence.entity.QuartzTask;
import com.van.service.QuartzTaskService;
@Transactional
@Service("quartzTaskService")
public class QuartzTaskServiceImpl implements QuartzTaskService {
@Autowired
private QuartzTaskDao quartzTaskDao;
public List<QuartzTask> getAll() {
return quartzTaskDao.getAll();
}
public List<QuartzTask> queryForList(QuartzTask quartzTask) { 
return quartzTaskDao.queryForList(quartzTask); 
}
public QuartzTask queryForOne(QuartzTask quartzTask) { 
return quartzTaskDao.queryForOne(quartzTask); 
}
public PageView query(PageView pageView, QuartzTask quartzTask) {
List<QuartzTask> list = quartzTaskDao.query(pageView, quartzTask);
pageView.setResults(list);
return pageView;
}
public void add(QuartzTask quartzTask) {
quartzTaskDao.add(quartzTask);
}
public void delete(String id) {
quartzTaskDao.delete(id);
}
public void modify(QuartzTask quartzTask) {
quartzTaskDao.modify(quartzTask);
}
public QuartzTask getById(String id) {
return quartzTaskDao.getById(id);
}
}

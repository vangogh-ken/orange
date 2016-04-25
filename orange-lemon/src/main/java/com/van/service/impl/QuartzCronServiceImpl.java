package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.QuartzCronDao;
import com.van.halley.db.persistence.entity.QuartzCron;
import com.van.service.QuartzCronService;
@Transactional
@Service("quartzCronService")
public class QuartzCronServiceImpl implements QuartzCronService {
@Autowired
private QuartzCronDao quartzCronDao;
public List<QuartzCron> getAll() {
return quartzCronDao.getAll();
}
public List<QuartzCron> queryForList(QuartzCron quartzCron) { 
return quartzCronDao.queryForList(quartzCron); 
}
public QuartzCron queryForOne(QuartzCron quartzCron) { 
return quartzCronDao.queryForOne(quartzCron); 
}
public PageView query(PageView pageView, QuartzCron quartzCron) {
List<QuartzCron> list = quartzCronDao.query(pageView, quartzCron);
pageView.setResults(list);
return pageView;
}
public void add(QuartzCron quartzCron) {
quartzCronDao.add(quartzCron);
}
public void delete(String id) {
quartzCronDao.delete(id);
}
public void modify(QuartzCron quartzCron) {
quartzCronDao.modify(quartzCron);
}
public QuartzCron getById(String id) {
return quartzCronDao.getById(id);
}
}

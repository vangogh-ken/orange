package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightExpenseBoxDao;
import com.van.halley.db.persistence.entity.FreightExpenseBox;
import com.van.service.FreightExpenseBoxService;
@Transactional
@Service("freightExpenseBoxService")
public class FreightExpenseBoxServiceImpl implements FreightExpenseBoxService {
@Autowired
private FreightExpenseBoxDao freightExpenseBoxDao;
public List<FreightExpenseBox> getAll() {
return freightExpenseBoxDao.getAll();
}
public List<FreightExpenseBox> queryForList(FreightExpenseBox freightExpenseBox) { 
return freightExpenseBoxDao.queryForList(freightExpenseBox); 
}
public FreightExpenseBox queryForOne(FreightExpenseBox freightExpenseBox) { 
return freightExpenseBoxDao.queryForOne(freightExpenseBox); 
}
public PageView query(PageView pageView, FreightExpenseBox freightExpenseBox) {
List<FreightExpenseBox> list = freightExpenseBoxDao.query(pageView, freightExpenseBox);
pageView.setResults(list);
return pageView;
}
public void add(FreightExpenseBox freightExpenseBox) {
freightExpenseBoxDao.add(freightExpenseBox);
}
public void delete(String id) {
freightExpenseBoxDao.delete(id);
}
public void modify(FreightExpenseBox freightExpenseBox) {
freightExpenseBoxDao.modify(freightExpenseBox);
}
public FreightExpenseBox getById(String id) {
return freightExpenseBoxDao.getById(id);
}
}

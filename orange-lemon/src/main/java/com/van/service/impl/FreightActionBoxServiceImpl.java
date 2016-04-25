package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionBoxDao;
import com.van.halley.db.persistence.entity.FreightActionBox;
import com.van.service.FreightActionBoxService;
@Transactional
@Service("freightActionBoxService")
public class FreightActionBoxServiceImpl implements FreightActionBoxService {
@Autowired
private FreightActionBoxDao freightActionBoxDao;
public List<FreightActionBox> getAll() {
return freightActionBoxDao.getAll();
}
public List<FreightActionBox> queryForList(FreightActionBox freightActionBox) { 
return freightActionBoxDao.queryForList(freightActionBox); 
}
public FreightActionBox queryForOne(FreightActionBox freightActionBox) { 
return freightActionBoxDao.queryForOne(freightActionBox); 
}
public PageView query(PageView pageView, FreightActionBox freightActionBox) {
List<FreightActionBox> list = freightActionBoxDao.query(pageView, freightActionBox);
pageView.setResults(list);
return pageView;
}
public void add(FreightActionBox freightActionBox) {
freightActionBoxDao.add(freightActionBox);
}
public void delete(String id) {
freightActionBoxDao.delete(id);
}
public void modify(FreightActionBox freightActionBox) {
freightActionBoxDao.modify(freightActionBox);
}
public FreightActionBox getById(String id) {
return freightActionBoxDao.getById(id);
}
}

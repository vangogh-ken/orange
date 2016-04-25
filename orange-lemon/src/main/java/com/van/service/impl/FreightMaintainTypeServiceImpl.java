package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightMaintainTypeDao;
import com.van.halley.db.persistence.entity.FreightMaintainType;
import com.van.service.FreightMaintainTypeService;
@Transactional
@Service("freightMaintainTypeService")
public class FreightMaintainTypeServiceImpl implements FreightMaintainTypeService {
@Autowired
private FreightMaintainTypeDao freightMaintainTypeDao;
public List<FreightMaintainType> getAll() {
return freightMaintainTypeDao.getAll();
}
public List<FreightMaintainType> queryForList(FreightMaintainType freightMaintainType) { 
return freightMaintainTypeDao.queryForList(freightMaintainType); 
}
public PageView query(PageView pageView, FreightMaintainType freightMaintainType) {
List<FreightMaintainType> list = freightMaintainTypeDao.query(pageView, freightMaintainType);
pageView.setResults(list);
return pageView;
}
public void add(FreightMaintainType freightMaintainType) {
freightMaintainTypeDao.add(freightMaintainType);
}
public void delete(String id) {
freightMaintainTypeDao.delete(id);
}
public void modify(FreightMaintainType freightMaintainType) {
freightMaintainTypeDao.modify(freightMaintainType);
}
public FreightMaintainType getById(String id) {
return freightMaintainTypeDao.getById(id);
}
}

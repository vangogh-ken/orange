package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisSchemaDao;
import com.van.halley.db.persistence.entity.BasisSchema;
import com.van.service.BasisSchemaService;
@Transactional
@Service("basisSchemaService")
public class BasisSchemaServiceImpl implements BasisSchemaService {
@Autowired
private BasisSchemaDao basisSchemaDao;
public List<BasisSchema> getAll() {
return basisSchemaDao.getAll();
}
public List<BasisSchema> queryForList(BasisSchema basisSchema) { 
return basisSchemaDao.queryForList(basisSchema); 
}
public BasisSchema queryForOne(BasisSchema basisSchema) { 
return basisSchemaDao.queryForOne(basisSchema); 
}
public PageView query(PageView pageView, BasisSchema basisSchema) {
List<BasisSchema> list = basisSchemaDao.query(pageView, basisSchema);
pageView.setResults(list);
return pageView;
}
public void add(BasisSchema basisSchema) {
basisSchemaDao.add(basisSchema);
}
public void delete(String id) {
basisSchemaDao.delete(id);
}
public void modify(BasisSchema basisSchema) {
basisSchemaDao.modify(basisSchema);
}
public BasisSchema getById(String id) {
return basisSchemaDao.getById(id);
}
}

package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CmsCatalogDao;
import com.van.halley.db.persistence.entity.CmsCatalog;
import com.van.service.CmsCatalogService;
@Transactional
@Service("cmsCatalogService")
public class CmsCatalogServiceImpl implements CmsCatalogService {
@Autowired
private CmsCatalogDao cmsCatalogDao;
public List<CmsCatalog> getAll() {
return cmsCatalogDao.getAll();
}
public List<CmsCatalog> queryForList(CmsCatalog cmsCatalog) { 
return cmsCatalogDao.queryForList(cmsCatalog); 
}
public PageView query(PageView pageView, CmsCatalog cmsCatalog) {
List<CmsCatalog> list = cmsCatalogDao.query(pageView, cmsCatalog);
pageView.setResults(list);
return pageView;
}
public void add(CmsCatalog cmsCatalog) {
cmsCatalogDao.add(cmsCatalog);
}
public void delete(String id) {
cmsCatalogDao.delete(id);
}
public void modify(CmsCatalog cmsCatalog) {
cmsCatalogDao.modify(cmsCatalog);
}
public CmsCatalog getById(String id) {
return cmsCatalogDao.getById(id);
}
}

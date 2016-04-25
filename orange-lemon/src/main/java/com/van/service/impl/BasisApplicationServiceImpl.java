package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisApplicationDao;
import com.van.halley.db.persistence.entity.BasisApplication;
import com.van.service.BasisApplicationService;
@Transactional
@Service("basisApplicationService")
public class BasisApplicationServiceImpl implements BasisApplicationService {
@Autowired
private BasisApplicationDao basisApplicationDao;
public List<BasisApplication> getAll() {
return basisApplicationDao.getAll();
}
public List<BasisApplication> queryForList(BasisApplication basisApplication) { 
return basisApplicationDao.queryForList(basisApplication); 
}
public BasisApplication queryForOne(BasisApplication basisApplication) { 
return basisApplicationDao.queryForOne(basisApplication); 
}
public PageView query(PageView pageView, BasisApplication basisApplication) {
List<BasisApplication> list = basisApplicationDao.query(pageView, basisApplication);
pageView.setResults(list);
return pageView;
}
public void add(BasisApplication basisApplication) {
basisApplicationDao.add(basisApplication);
}
public void delete(String id) {
basisApplicationDao.delete(id);
}
public void modify(BasisApplication basisApplication) {
basisApplicationDao.modify(basisApplication);
}
public BasisApplication getById(String id) {
return basisApplicationDao.getById(id);
}
}

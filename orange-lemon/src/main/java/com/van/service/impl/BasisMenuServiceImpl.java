package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisMenuDao;
import com.van.halley.db.persistence.entity.BasisMenu;
import com.van.service.BasisMenuService;
@Transactional
@Service("basisMenuService")
public class BasisMenuServiceImpl implements BasisMenuService {
@Autowired
private BasisMenuDao basisMenuDao;
public List<BasisMenu> getAll() {
return basisMenuDao.getAll();
}
public List<BasisMenu> queryForList(BasisMenu basisMenu) { 
return basisMenuDao.queryForList(basisMenu); 
}
public BasisMenu queryForOne(BasisMenu basisMenu) { 
return basisMenuDao.queryForOne(basisMenu); 
}
public PageView query(PageView pageView, BasisMenu basisMenu) {
List<BasisMenu> list = basisMenuDao.query(pageView, basisMenu);
pageView.setResults(list);
return pageView;
}
public void add(BasisMenu basisMenu) {
basisMenuDao.add(basisMenu);
}
public void delete(String id) {
basisMenuDao.delete(id);
}
public void modify(BasisMenu basisMenu) {
basisMenuDao.modify(basisMenu);
}
public BasisMenu getById(String id) {
return basisMenuDao.getById(id);
}
}

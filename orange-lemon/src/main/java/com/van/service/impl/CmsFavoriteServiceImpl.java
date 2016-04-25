package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CmsFavoriteDao;
import com.van.halley.db.persistence.entity.CmsFavorite;
import com.van.service.CmsFavoriteService;
@Transactional
@Service("cmsFavoriteService")
public class CmsFavoriteServiceImpl implements CmsFavoriteService {
@Autowired
private CmsFavoriteDao cmsFavoriteDao;
public List<CmsFavorite> getAll() {
return cmsFavoriteDao.getAll();
}
public List<CmsFavorite> queryForList(CmsFavorite cmsFavorite) { 
return cmsFavoriteDao.queryForList(cmsFavorite); 
}
public PageView query(PageView pageView, CmsFavorite cmsFavorite) {
List<CmsFavorite> list = cmsFavoriteDao.query(pageView, cmsFavorite);
pageView.setResults(list);
return pageView;
}
public void add(CmsFavorite cmsFavorite) {
cmsFavoriteDao.add(cmsFavorite);
}
public void delete(String id) {
cmsFavoriteDao.delete(id);
}
public void modify(CmsFavorite cmsFavorite) {
cmsFavoriteDao.modify(cmsFavorite);
}
public CmsFavorite getById(String id) {
return cmsFavoriteDao.getById(id);
}
}

package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CmsCommentDao;
import com.van.halley.db.persistence.entity.CmsComment;
import com.van.service.CmsCommentService;
@Transactional
@Service("cmsCommentService")
public class CmsCommentServiceImpl implements CmsCommentService {
@Autowired
private CmsCommentDao cmsCommentDao;
public List<CmsComment> getAll() {
return cmsCommentDao.getAll();
}
public List<CmsComment> queryForList(CmsComment cmsComment) { 
return cmsCommentDao.queryForList(cmsComment); 
}
public PageView query(PageView pageView, CmsComment cmsComment) {
List<CmsComment> list = cmsCommentDao.query(pageView, cmsComment);
pageView.setResults(list);
return pageView;
}
public void add(CmsComment cmsComment) {
cmsCommentDao.add(cmsComment);
}
public void delete(String id) {
cmsCommentDao.delete(id);
}
public void modify(CmsComment cmsComment) {
cmsCommentDao.modify(cmsComment);
}
public CmsComment getById(String id) {
return cmsCommentDao.getById(id);
}
}

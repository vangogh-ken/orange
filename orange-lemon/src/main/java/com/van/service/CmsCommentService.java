package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CmsComment;
public interface CmsCommentService{
public List<CmsComment> getAll();
public List<CmsComment> queryForList(CmsComment cmsComment);public PageView query(PageView pageView,CmsComment cmsComment);
public void add(CmsComment cmsComment);
public void delete(String id);
public void modify(CmsComment cmsComment);
public CmsComment getById(String id);
}

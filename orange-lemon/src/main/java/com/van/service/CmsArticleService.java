package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CmsArticle;

public interface CmsArticleService {
	public List<CmsArticle> getAll();

	public List<CmsArticle> queryForList(CmsArticle cmsArticle);

	public PageView query(PageView pageView, CmsArticle cmsArticle);

	public void add(CmsArticle cmsArticle);

	public void delete(String id);

	public void modify(CmsArticle cmsArticle);

	public CmsArticle getById(String id);
}

package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CmsArticleDao;
import com.van.halley.db.persistence.entity.CmsArticle;
import com.van.service.CmsArticleService;

@Transactional
@Service("cmsArticleService")
public class CmsArticleServiceImpl implements CmsArticleService {
	@Autowired
	private CmsArticleDao cmsArticleDao;

	public List<CmsArticle> getAll() {
		return cmsArticleDao.getAll();
	}

	public List<CmsArticle> queryForList(CmsArticle cmsArticle) {
		return cmsArticleDao.queryForList(cmsArticle);
	}

	public PageView query(PageView pageView, CmsArticle cmsArticle) {
		List<CmsArticle> list = cmsArticleDao.query(pageView, cmsArticle);
		pageView.setResults(list);
		return pageView;
	}

	public void add(CmsArticle cmsArticle) {
		cmsArticleDao.add(cmsArticle);
	}

	public void delete(String id) {
		cmsArticleDao.delete(id);
	}

	public void modify(CmsArticle cmsArticle) {
		cmsArticleDao.modify(cmsArticle);
	}

	public CmsArticle getById(String id) {
		return cmsArticleDao.getById(id);
	}
}

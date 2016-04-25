package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ForumPostDao;
import com.van.halley.db.persistence.entity.ForumPost;
import com.van.service.ForumPostService;

@Transactional
@Service("forumPostService")
public class ForumPostServiceImpl implements ForumPostService {
	@Autowired
	private ForumPostDao forumPostDao;

	public List<ForumPost> getAll() {
		return forumPostDao.getAll();
	}

	public List<ForumPost> queryForList(ForumPost forumPost) {
		return forumPostDao.queryForList(forumPost);
	}

	public PageView<ForumPost> query(PageView<ForumPost> pageView, ForumPost forumPost) {
		List<ForumPost> list = forumPostDao.query(pageView, forumPost);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ForumPost forumPost) {
		forumPostDao.add(forumPost);
	}

	public void delete(String id) {
		forumPostDao.delete(id);
	}

	public void modify(ForumPost forumPost) {
		forumPostDao.modify(forumPost);
	}

	public ForumPost getById(String id) {
		return forumPostDao.getById(id);
	}
}

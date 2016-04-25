package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ForumTopicDao;
import com.van.halley.db.persistence.entity.ForumTopic;
import com.van.service.ForumTopicService;

@Transactional
@Service("forumTopicService")
public class ForumTopicServiceImpl implements ForumTopicService {
	@Autowired
	private ForumTopicDao forumTopicDao;

	public List<ForumTopic> getAll() {
		return forumTopicDao.getAll();
	}

	public List<ForumTopic> queryForList(ForumTopic forumTopic) {
		return forumTopicDao.queryForList(forumTopic);
	}

	public PageView<ForumTopic> query(PageView<ForumTopic> pageView, ForumTopic forumTopic) {
		List<ForumTopic> list = forumTopicDao.query(pageView, forumTopic);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ForumTopic forumTopic) {
		forumTopicDao.add(forumTopic);
	}

	public void delete(String id) {
		forumTopicDao.delete(id);
	}

	public void modify(ForumTopic forumTopic) {
		forumTopicDao.modify(forumTopic);
	}

	public ForumTopic getById(String id) {
		return forumTopicDao.getById(id);
	}
}

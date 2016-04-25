package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ForumTopic;

public interface ForumTopicService {
	public List<ForumTopic> getAll();

	public List<ForumTopic> queryForList(ForumTopic forumTopic);

	public PageView<ForumTopic> query(PageView<ForumTopic> pageView, ForumTopic forumTopic);

	public void add(ForumTopic forumTopic);

	public void delete(String id);

	public void modify(ForumTopic forumTopic);

	public ForumTopic getById(String id);
}

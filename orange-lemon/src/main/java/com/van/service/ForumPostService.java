package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ForumPost;

public interface ForumPostService {
	public List<ForumPost> getAll();

	public List<ForumPost> queryForList(ForumPost forumPost);

	public PageView<ForumPost> query(PageView<ForumPost> pageView, ForumPost forumPost);

	public void add(ForumPost forumPost);

	public void delete(String id);

	public void modify(ForumPost forumPost);

	public ForumPost getById(String id);
	
}

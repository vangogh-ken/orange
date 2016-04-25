package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ForumPostDao;
import com.van.halley.db.persistence.entity.ForumPost;

@Repository("forumPostDao")
public class ForumPostDaoImpl extends BaseDaoImpl<ForumPost> implements
		ForumPostDao {
}

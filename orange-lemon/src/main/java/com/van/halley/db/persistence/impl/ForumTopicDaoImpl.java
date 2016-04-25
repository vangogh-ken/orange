package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ForumTopicDao;
import com.van.halley.db.persistence.entity.ForumTopic;

@Repository("forumTopicDao")
public class ForumTopicDaoImpl extends BaseDaoImpl<ForumTopic> implements
		ForumTopicDao {
}

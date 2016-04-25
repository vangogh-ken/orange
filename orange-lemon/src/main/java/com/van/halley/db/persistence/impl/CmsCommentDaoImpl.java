package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CmsCommentDao;
import com.van.halley.db.persistence.entity.CmsComment;
@Repository("cmsCommentDao")
public class CmsCommentDaoImpl extends BaseDaoImpl<CmsComment> implements CmsCommentDao {}

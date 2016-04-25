package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CmsArticleDao;
import com.van.halley.db.persistence.entity.CmsArticle;
@Repository("cmsArticleDao")
public class CmsArticleDaoImpl extends BaseDaoImpl<CmsArticle> implements CmsArticleDao {}

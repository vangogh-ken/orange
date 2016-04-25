package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.QuartzGroupDao;
import com.van.halley.db.persistence.entity.QuartzGroup;
@Repository("quartzGroupDao")
public class QuartzGroupDaoImpl extends BaseDaoImpl<QuartzGroup> implements QuartzGroupDao {}

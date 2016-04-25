package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.QuartzJobDao;
import com.van.halley.db.persistence.entity.QuartzJob;
@Repository("quartzJobDao")
public class QuartzJobDaoImpl extends BaseDaoImpl<QuartzJob> implements QuartzJobDao {}

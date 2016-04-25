package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.QuartzCronDao;
import com.van.halley.db.persistence.entity.QuartzCron;

@Repository("quartzCronDao")
public class QuartzCronDaoImpl extends BaseDaoImpl<QuartzCron>implements QuartzCronDao {

	@Override
	public QuartzCron getByCronExpression(String cronExpression) {
		return getSqlSession().selectOne("quartzcron.getByTaskKey", cronExpression);
	}
}

package com.van.halley.db.persistence;
import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.QuartzCron;
public interface QuartzCronDao extends BaseDao<QuartzCron> {
	public QuartzCron getByCronExpression(String cronExpression);
}
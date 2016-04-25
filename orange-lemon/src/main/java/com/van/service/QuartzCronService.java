package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.QuartzCron;

public interface QuartzCronService {
	public List<QuartzCron> getAll();

	public List<QuartzCron> queryForList(QuartzCron quartzCron);

	public QuartzCron queryForOne(QuartzCron quartzCron);

	public PageView query(PageView pageView, QuartzCron quartzCron);

	public void add(QuartzCron quartzCron);

	public void delete(String id);

	public void modify(QuartzCron quartzCron);

	public QuartzCron getById(String id);
}

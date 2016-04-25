package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.QuartzTask;

public interface QuartzTaskService {
	public List<QuartzTask> getAll();

	public List<QuartzTask> queryForList(QuartzTask quartzTask);

	public QuartzTask queryForOne(QuartzTask quartzTask);

	public PageView query(PageView pageView, QuartzTask quartzTask);

	public void add(QuartzTask quartzTask);

	public void delete(String id);

	public void modify(QuartzTask quartzTask);

	public QuartzTask getById(String id);
}

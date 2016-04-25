package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.QuartzGroup;

public interface QuartzGroupService {
	public List<QuartzGroup> getAll();

	public List<QuartzGroup> queryForList(QuartzGroup quartzGroup);

	public QuartzGroup queryForOne(QuartzGroup quartzGroup);

	public PageView query(PageView pageView, QuartzGroup quartzGroup);

	public void add(QuartzGroup quartzGroup);

	public void delete(String id);

	public void modify(QuartzGroup quartzGroup);

	public QuartzGroup getById(String id);
}

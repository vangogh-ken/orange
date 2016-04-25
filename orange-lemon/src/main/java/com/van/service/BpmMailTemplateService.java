package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmMailTemplate;

public interface BpmMailTemplateService {
	public List<BpmMailTemplate> getAll();

	public List<BpmMailTemplate> queryForList(
			BpmMailTemplate bpmMailTemplate);

	public PageView query(PageView pageView, BpmMailTemplate bpmMailTemplate);

	public void add(BpmMailTemplate bpmMailTemplate);

	public void delete(String id);

	public void modify(BpmMailTemplate bpmMailTemplate);

	public BpmMailTemplate getById(String id);
}

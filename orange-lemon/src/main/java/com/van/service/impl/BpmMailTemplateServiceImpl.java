package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BpmMailTemplateDao;
import com.van.halley.db.persistence.entity.BpmMailTemplate;
import com.van.service.BpmMailTemplateService;

@Transactional
@Service("bpmMailTemplateService")
public class BpmMailTemplateServiceImpl implements BpmMailTemplateService {
	@Autowired
	private BpmMailTemplateDao bpmMailTemplateDao;

	public List<BpmMailTemplate> getAll() {
		return bpmMailTemplateDao.getAll();
	}

	public List<BpmMailTemplate> queryForList(
			BpmMailTemplate bpmMailTemplate) {
		return bpmMailTemplateDao.queryForList(bpmMailTemplate);
	}

	public PageView query(PageView pageView, BpmMailTemplate bpmMailTemplate) {
		List<BpmMailTemplate> list = bpmMailTemplateDao.query(pageView,
				bpmMailTemplate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BpmMailTemplate bpmMailTemplate) {
		bpmMailTemplateDao.add(bpmMailTemplate);
	}

	public void delete(String id) {
		bpmMailTemplateDao.delete(id);
	}

	public void modify(BpmMailTemplate bpmMailTemplate) {
		bpmMailTemplateDao.modify(bpmMailTemplate);
	}

	public BpmMailTemplate getById(String id) {
		return bpmMailTemplateDao.getById(id);
	}
}

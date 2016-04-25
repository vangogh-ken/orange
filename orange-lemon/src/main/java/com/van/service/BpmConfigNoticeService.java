package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigNotice;

public interface BpmConfigNoticeService {
	public List<BpmConfigNotice> getAll();

	public List<BpmConfigNotice> queryForList(
			BpmConfigNotice bpmConfigNotice);

	public PageView query(PageView pageView, BpmConfigNotice bpmConfigNotice);

	public void add(BpmConfigNotice bpmConfigNotice);

	public void delete(String id);

	public void modify(BpmConfigNotice bpmConfigNotice);

	public BpmConfigNotice getById(String id);

	public List<BpmConfigNotice> getByNodeId(String nodeId);
}

package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigDelegate;

public interface BpmConfigDelegateService {
	public List<BpmConfigDelegate> getAll();

	public List<BpmConfigDelegate> queryForList(
			BpmConfigDelegate bpmConfigDelegate);

	public PageView query(PageView pageView, BpmConfigDelegate bpmConfigDelegate);

	public void add(BpmConfigDelegate bpmConfigDelegate);

	public void delete(String id);

	public void modify(BpmConfigDelegate bpmConfigDelegate);

	public BpmConfigDelegate getById(String id);

	public BpmConfigDelegate queryForOne(BpmConfigDelegate filter);
}

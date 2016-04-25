package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigAuth;

public interface BpmConfigAuthService {
	public List<BpmConfigAuth> getAll();

	public List<BpmConfigAuth> queryForList(BpmConfigAuth bpmConfigAuth);

	public BpmConfigAuth queryForOne(BpmConfigAuth bpmConfigAuth);

	public PageView query(PageView pageView, BpmConfigAuth bpmConfigAuth);

	public void add(BpmConfigAuth bpmConfigAuth);

	public void delete(String id);

	public void modify(BpmConfigAuth bpmConfigAuth);

	public BpmConfigAuth getById(String id);
}

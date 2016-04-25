package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.OrgType;

public interface OrgTypeService {
	public List<OrgType> getAll();

	public List<OrgType> queryForList(OrgType orgType);

	public PageView query(PageView pageView, OrgType orgType);

	public void add(OrgType orgType);

	public void delete(String id);

	public void modify(OrgType orgType);

	public OrgType getById(String id);
}

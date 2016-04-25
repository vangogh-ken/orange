package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.RoleResource;

public interface RoleResourceService {
	public List<RoleResource> getAll();

	public List<RoleResource> queryForList(RoleResource roleResource);

	public RoleResource queryForOne(RoleResource roleResource);

	public PageView query(PageView pageView, RoleResource roleResource);

	public void add(RoleResource roleResource);

	public void delete(String id);

	public void modify(RoleResource roleResource);

	public RoleResource getById(String id);
}

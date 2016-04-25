package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ResourceDao;
import com.van.halley.db.persistence.RoleResourceDao;
import com.van.halley.db.persistence.entity.RoleResource;
import com.van.service.ResourceService;
import com.van.service.RoleResourceService;

@Transactional
@Service("roleResourceService")
public class RoleResourceServiceImpl implements RoleResourceService {
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleResourceDao roleResourceDao;
	@Autowired
	private ResourceDao resourceDao;

	public List<RoleResource> getAll() {
		return roleResourceDao.getAll();
	}

	public List<RoleResource> queryForList(RoleResource roleResource) {
		return roleResourceDao.queryForList(roleResource);
	}

	public RoleResource queryForOne(RoleResource roleResource) {
		return roleResourceDao.queryForOne(roleResource);
	}

	public PageView query(PageView pageView, RoleResource roleResource) {
		List<RoleResource> list = roleResourceDao.query(pageView, roleResource);
		pageView.setResults(list);
		return pageView;
	}

	public void add(RoleResource roleResource) {
		roleResourceDao.add(roleResource);
	}

	public void delete(String id) {
		roleResourceDao.delete(id);
	}

	public void modify(RoleResource roleResource) {
		roleResourceDao.modify(roleResource);
	}

	public RoleResource getById(String id) {
		return roleResourceDao.getById(id);
	}
}

package com.van.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.RoleQuartzDao;
import com.van.halley.db.persistence.entity.RoleQuartz;
import com.van.service.RoleQuartzService;

@Transactional
@Service("roleQuartzService")
public class RoleQuartzServiceImpl implements RoleQuartzService {
	@Autowired
	private RoleQuartzDao roleQuartzDao;

	public List<RoleQuartz> getAll() {
		return roleQuartzDao.getAll();
	}

	public List<RoleQuartz> queryForList(RoleQuartz roleQuartz) {
		return roleQuartzDao.queryForList(roleQuartz);
	}

	public RoleQuartz queryForOne(RoleQuartz roleQuartz) {
		return roleQuartzDao.queryForOne(roleQuartz);
	}

	public PageView query(PageView pageView, RoleQuartz roleQuartz) {
		List<RoleQuartz> list = roleQuartzDao.query(pageView, roleQuartz);
		pageView.setResults(list);
		return pageView;
	}

	public void add(RoleQuartz roleQuartz) {
		roleQuartzDao.add(roleQuartz);
	}

	public void delete(String id) {
		roleQuartzDao.delete(id);
	}

	public void modify(RoleQuartz roleQuartz) {
		roleQuartzDao.modify(roleQuartz);
	}

	public RoleQuartz getById(String id) {
		return roleQuartzDao.getById(id);
	}
}

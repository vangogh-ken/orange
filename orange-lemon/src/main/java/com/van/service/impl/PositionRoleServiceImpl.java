package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.PositionRoleDao;
import com.van.halley.db.persistence.entity.PositionRole;
import com.van.service.PositionRoleService;

@Transactional
@Service("positionRoleService")
public class PositionRoleServiceImpl implements PositionRoleService {
	@Autowired
	private PositionRoleDao positionRoleDao;

	public List<PositionRole> getAll() {
		return positionRoleDao.getAll();
	}

	public List<PositionRole> queryForList(PositionRole positionRole) {
		return positionRoleDao.queryForList(positionRole);
	}

	public PositionRole queryForOne(PositionRole positionRole) {
		return positionRoleDao.queryForOne(positionRole);
	}

	public PageView query(PageView pageView, PositionRole positionRole) {
		List<PositionRole> list = positionRoleDao.query(pageView, positionRole);
		pageView.setResults(list);
		return pageView;
	}

	public void add(PositionRole positionRole) {
		positionRoleDao.add(positionRole);
	}

	public void delete(String id) {
		positionRoleDao.delete(id);
	}

	public void modify(PositionRole positionRole) {
		positionRoleDao.modify(positionRole);
	}

	public PositionRole getById(String id) {
		return positionRoleDao.getById(id);
	}

}

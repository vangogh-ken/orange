package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.PositionDao;
import com.van.halley.db.persistence.RoleDao;
import com.van.halley.db.persistence.entity.Position;
import com.van.service.PositionService;

@Transactional
@Service("positionService")
public class PositionServiceImpl implements PositionService {
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private RoleDao roleDao;

	public List<Position> getAll() {
		return positionDao.getAll();
	}

	public List<Position> queryForList(Position position) {
		return positionDao.queryForList(position);
	}

	public PageView<Position> query(PageView<Position> pageView, Position position) {
		List<Position> list = positionDao.query(pageView, position);
		pageView.setResults(list);
		return pageView;
	}

	public void add(Position position) {
		positionDao.add(position);
	}

	public void delete(String id) {
		positionDao.delete(id);
	}

	public void modify(Position position) {
		positionDao.modify(position);
	}

	public Position getById(String id) {
		return positionDao.getById(id);
	}

	@Override
	public void allocation(String positionId, String[] roleIds) {
		roleDao.deletePositionRole(positionId);
		if(roleIds.length > 0){
			for(String roleId : roleIds){
				roleDao.addPositionRole(positionId, roleId);
			}
			
		}
	}
}

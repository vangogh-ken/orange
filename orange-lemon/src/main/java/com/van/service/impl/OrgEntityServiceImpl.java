package com.van.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.OrgEntityDao;
import com.van.halley.db.persistence.PositionDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.Position;
import com.van.halley.util.StringUtil;
import com.van.service.OrgEntityService;

@Transactional
@Service("orgEntityService")
public class OrgEntityServiceImpl implements OrgEntityService {
	@Autowired
	private OrgEntityDao orgEntityDao;
	@Autowired
	private PositionDao positionDao;
	@Autowired
	private UserDao userDao;

	public List<OrgEntity> getAll() {
		return orgEntityDao.getAll();
	}

	public List<OrgEntity> queryForList(OrgEntity orgEntity) {
		return orgEntityDao.queryForList(orgEntity);
	}

	public PageView query(PageView pageView, OrgEntity orgEntity) {
		List<OrgEntity> list = orgEntityDao.query(pageView, orgEntity);
		pageView.setResults(list);
		return pageView;
	}

	public void add(OrgEntity orgEntity) {
		orgEntityDao.add(orgEntity);
	}

	public void delete(String id) {
		orgEntityDao.delete(id);
	}

	public void modify(OrgEntity orgEntity) {
		orgEntityDao.modify(orgEntity);
	}

	public OrgEntity getById(String id) {
		return orgEntityDao.getById(id);
	}

	@Override
	public Map<String, Object> toAddGaffer(String orgEntityId) {
		Map<String, Object> map = new  HashMap<String, Object>();
		map.put("hasAddData", userDao.getGafferByOrgEntityId(orgEntityId));
		map.put("users", userDao.getAll());
		return map;
	}

	@Override
	public void doneAddGaffer(String orgEntityId, String userId) {
		userDao.addGaffer(orgEntityId, userId);
	}

	@Override
	public void doneRemoveGaffer(String orgEntityId) {
		userDao.deleteGaffer(orgEntityId);
	}

	@Override
	public Map<String, Object> toAddPosition(String orgEntityId) {
		Map<String, Object> map = new HashMap<String, Object>();
		OrgEntity orgEntity = orgEntityDao.getById(orgEntityId);
		Position filter = new Position();
		filter.setOrgEntity(orgEntity);
		map.put("hasAddData", positionDao.queryForList(filter));
		map.put("orgEntity", orgEntity);
		return map;
	}

	@Override
	public void doneAddPosition(String orgEntityId, Position position) {
		OrgEntity orgEntity = orgEntityDao.getById(orgEntityId);
		position.setOrgEntity(orgEntity);
		if(StringUtil.isNullOrEmpty(position.getId())){
			positionDao.add(position);
		}else{
			positionDao.modify(position);
		}
		
	}

	@Override
	public void doneRemovePosition(String[] positionIds) {
		for(String positionId : positionIds){
			positionDao.delete(positionId);
		}
	}

	@Override
	public Map<String, Object> toRevisePosition(String positionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("position", positionDao.getById(positionId));
		return map;
	}
}

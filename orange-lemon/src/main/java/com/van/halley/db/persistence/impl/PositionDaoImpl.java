package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.PositionDao;
import com.van.halley.db.persistence.entity.Position;

@Repository("positionDao")
public class PositionDaoImpl extends BaseDaoImpl<Position> implements
		PositionDao {

	@Override
	public List<Position> getByBpmConfigNodeId(String bpmConfigNodeId) {
		return getSqlSession().selectList("position.getByBpmConfigNodeId", bpmConfigNodeId);
	}
}

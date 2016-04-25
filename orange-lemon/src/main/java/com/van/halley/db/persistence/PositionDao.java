package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.Position;

public interface PositionDao extends BaseDao<Position> {
	/**
	 * 获取流程节点角色权限
	 * @param bpmConfigNodeId
	 * @return
	 */
	public List<Position> getByBpmConfigNodeId(String bpmConfigNodeId);
}
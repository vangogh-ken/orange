package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.ConstantAuth;

public interface ConstantAuthDao extends BaseDao<ConstantAuth> {
	/**
	 * 获取流程节点常量权限
	 * @param bpmConfigNodeId
	 * @return
	 */
	public List<ConstantAuth> getByBpmConfigNodeId(String bpmConfigNodeId);
}
package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.BpmConfigAuth;

public interface BpmConfigAuthDao extends BaseDao<BpmConfigAuth> {
	/**
	 * 删除节点权限
	 * @param bpmConfigNodeId
	 * @param authId
	 */
	public void doneDeleteAuth(String bpmConfigNodeId, String authId);
	/**
	 * 根据流程节点ID获取
	 * @param bpmConfigNodeId
	 * @return
	 */
	public List<BpmConfigAuth> getByBpmConfigNodeId(String bpmConfigNodeId);
}
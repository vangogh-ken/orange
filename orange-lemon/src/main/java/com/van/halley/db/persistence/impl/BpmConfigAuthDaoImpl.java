package com.van.halley.db.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigAuthDao;
import com.van.halley.db.persistence.entity.BpmConfigAuth;

@Repository("bpmConfigAuthDao")
public class BpmConfigAuthDaoImpl extends BaseDaoImpl<BpmConfigAuth> implements
		BpmConfigAuthDao {

	@Override
	public void doneDeleteAuth(String bpmConfigNodeId, String authId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bpmConfigNodeId", bpmConfigNodeId);
		map.put("authId", authId);
		getSqlSession().delete("doneDeleteAuth", map);
	}

	@Override
	public List<BpmConfigAuth> getByBpmConfigNodeId(String bpmConfigNodeId) {
		BpmConfigAuth filter = new BpmConfigAuth();
		filter.setBpmNodeId(bpmConfigNodeId);
		return queryForList(filter);
	}
}

package com.van.halley.db.persistence.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigNodeDao;
import com.van.halley.db.persistence.entity.BpmConfigNode;

@Repository("bpmConfigNodeDao")
public class BpmConfigNodeDaoImpl extends BaseDaoImpl<BpmConfigNode> implements
		BpmConfigNodeDao {

	@Override
	public void deleteByPdId(String pdId) {
		getSqlSession().delete("bpmconfignode.deleteByPdId", pdId);
	}

	@Override
	public BpmConfigNode getByPdIdAndTdKey(String pdId, String tdKey) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pdId", pdId);
		map.put("tdKey", tdKey);
		
		return getSqlSession().selectOne("bpmconfignode.getByPdIdAndTdKey", map);
	}
}

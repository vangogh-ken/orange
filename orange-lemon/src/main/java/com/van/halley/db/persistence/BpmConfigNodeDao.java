package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.BpmConfigNode;

public interface BpmConfigNodeDao extends BaseDao<BpmConfigNode> {
	
	public void deleteByPdId(String pdId);

	public BpmConfigNode getByPdIdAndTdKey(String pdId, String tdKey);
}
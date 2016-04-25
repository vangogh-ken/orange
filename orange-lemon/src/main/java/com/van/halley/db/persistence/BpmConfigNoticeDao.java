package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.BpmConfigNotice;

public interface BpmConfigNoticeDao extends BaseDao<BpmConfigNotice> {

	public List<BpmConfigNotice> getByNodeId(String nodeId);
}
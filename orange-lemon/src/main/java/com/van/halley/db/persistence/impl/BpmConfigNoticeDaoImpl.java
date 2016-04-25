package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigNoticeDao;
import com.van.halley.db.persistence.entity.BpmConfigNotice;

@Repository("bpmConfigNoticeDao")
public class BpmConfigNoticeDaoImpl extends BaseDaoImpl<BpmConfigNotice>
		implements BpmConfigNoticeDao {

	@Override
	public List<BpmConfigNotice> getByNodeId(String nodeId) {
		return getSqlSession().selectList("bpmconfignotice.getByNodeId", nodeId);
	}
}

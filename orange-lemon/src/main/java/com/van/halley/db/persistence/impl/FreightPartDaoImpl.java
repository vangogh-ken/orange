package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.entity.FreightPart;

@Repository("freightPartDao")
public class FreightPartDaoImpl extends BaseDaoImpl<FreightPart> implements
		FreightPartDao {

	@Override
	public FreightPart getByOrgEntityId(String orgEntityId) {
		return getSqlSession().selectOne("freightpart.getByOrgEntityId", orgEntityId);
	}

	@Override
	public FreightPart getByPartName(String partName) {
		FreightPart filter = new FreightPart();
		filter.setPartName(partName);
		return queryForOne(filter);
	}
}

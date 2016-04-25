package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightStatementOffsetDao;
import com.van.halley.db.persistence.entity.FreightStatementOffset;

@Repository("freightStatementOffsetDao")
public class FreightStatementOffsetDaoImpl extends
		BaseDaoImpl<FreightStatementOffset> implements
		FreightStatementOffsetDao {

	@Override
	public void deleteStatementOffset(
			FreightStatementOffset freightStatementOffset) {
		getSqlSession().delete("freightstatementoffset.deleteStatementOffset", freightStatementOffset);
	}
}

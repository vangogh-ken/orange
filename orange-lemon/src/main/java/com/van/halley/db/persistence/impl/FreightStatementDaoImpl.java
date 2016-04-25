package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightStatementDao;
import com.van.halley.db.persistence.entity.FreightStatement;

@Repository("freightStatementDao")
public class FreightStatementDaoImpl extends BaseDaoImpl<FreightStatement>
		implements FreightStatementDao {

	@Override
	public List<FreightStatement> getHasOffsetStatement(String freightStatementId) {
		return getSqlSession().selectList("freightstatement.getHasOffsetStatement", freightStatementId);
	}
}

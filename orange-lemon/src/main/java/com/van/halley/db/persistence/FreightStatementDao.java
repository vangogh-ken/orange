package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightStatement;

public interface FreightStatementDao extends BaseDao<FreightStatement> {
	/**
	 * 获取账单冲抵过的账单
	 * @return
	 */
	public List<FreightStatement> getHasOffsetStatement(String freightStatementId);
}
package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightStatementOffset;

public interface FreightStatementOffsetDao extends BaseDao<FreightStatementOffset> {
	/**
	 * 删除账单冲抵
	 * @param freightStatementOffset
	 */
	public void deleteStatementOffset(FreightStatementOffset freightStatementOffset);
}
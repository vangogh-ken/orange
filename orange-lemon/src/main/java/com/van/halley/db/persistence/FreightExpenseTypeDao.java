package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightExpenseType;

public interface FreightExpenseTypeDao extends BaseDao<FreightExpenseType> {
	/**
	 * 通过名称获取费用类型
	 * @param string
	 * @return
	 */
	public FreightExpenseType getByTypeName(String typeName);
}
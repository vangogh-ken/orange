package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FasInvoiceType;
public interface FasInvoiceTypeDao extends BaseDao<FasInvoiceType> {
	/**
	 * 通过名称获取
	 * @param typeName
	 * @return
	 */
	public FasInvoiceType getByTypeName(String typeName);
}
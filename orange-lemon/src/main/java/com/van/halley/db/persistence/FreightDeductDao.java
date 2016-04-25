package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightDeduct;

public interface FreightDeductDao extends BaseDao<FreightDeduct> {
	/**
	 * 跟进订单号
	 * @param freightOrderId
	 * @return
	 */
	public FreightDeduct getByFreightOrderId(String freightOrderId);

}
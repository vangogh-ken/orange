package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightOrder;

public interface FreightOrderDao extends BaseDao<FreightOrder> {
	/**
	 * 获取与账单关联的订单
	 * @param freightStatementId
	 * @return
	 */
	public List<FreightOrder> getByFreightStatementId(String freightStatementId);
}
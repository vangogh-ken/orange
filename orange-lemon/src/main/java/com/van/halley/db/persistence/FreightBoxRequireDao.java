package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightBoxRequire;

public interface FreightBoxRequireDao extends BaseDao<FreightBoxRequire> {
	/**
	 * 获取订单相关的所有箱需
	 * @param freightOrderId
	 * @return
	 */
	public List<FreightBoxRequire> getByFreightOrderId(String freightOrderId);
}
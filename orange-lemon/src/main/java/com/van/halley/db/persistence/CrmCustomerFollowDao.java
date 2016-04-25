package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.CrmCustomerFollow;

public interface CrmCustomerFollowDao extends BaseDao<CrmCustomerFollow> {
	/**
	 * 获取最近一次的访问
	 * @return
	 */
	public CrmCustomerFollow getLastByCrmCusotmerId(String crmCusotmerId, String follwerId);
}
package com.van.halley.db.persistence.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CrmCustomerFollowDao;
import com.van.halley.db.persistence.entity.CrmCustomerFollow;
@Repository("crmCustomerFollowDao")
public class CrmCustomerFollowDaoImpl extends BaseDaoImpl<CrmCustomerFollow> implements CrmCustomerFollowDao {

	@Override
	public CrmCustomerFollow getLastByCrmCusotmerId(String crmCustomerId, String follwerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crmCustomerId", crmCustomerId);
		map.put("follwerId", follwerId);
		return getSqlSession().selectOne("crmcustomerfollow.getLastByCrmCusotmerId", map);
	}}

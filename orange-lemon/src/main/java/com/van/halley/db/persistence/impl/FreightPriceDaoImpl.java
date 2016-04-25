package com.van.halley.db.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightPriceDao;
import com.van.halley.db.persistence.entity.FreightPrice;

@Repository("freightPriceDao")
public class FreightPriceDaoImpl extends BaseDaoImpl<FreightPrice> implements
		FreightPriceDao {

	/*@Override
	public List<FreightPrice> getForCache() {
		return getSqlSession().selectList("freightprice.getForCache");
	}*/

	@Override
	public FreightPrice getOriginalPrice(String freightPartId,
			String freightExpenseTypeId, String countUnit) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("freightPartId", freightPartId);
		map.put("freightExpenseTypeId", freightExpenseTypeId);
		map.put("countUnit", countUnit);
		return getSqlSession().selectOne("freightprice.getOriginalPrice", map);
	}

	@Override
	public List<FreightPrice> getByOriginalTime() {
		return getSqlSession().selectList("freightprice.getByOriginalTime");
	}

	@Override
	public List<FreightPrice> getByOriginalStatus() {
		return getSqlSession().selectList("freightprice.getByOriginalStatus");
	}
}

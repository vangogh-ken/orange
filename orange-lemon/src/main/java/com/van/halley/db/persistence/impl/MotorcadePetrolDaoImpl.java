package com.van.halley.db.persistence.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadePetrolDao;
import com.van.halley.db.persistence.entity.MotorcadePetrol;
@Repository("motorcadePetrolDao")
public class MotorcadePetrolDaoImpl extends BaseDaoImpl<MotorcadePetrol> implements MotorcadePetrolDao {

	@Override
	public Map<String, Object> getFuelConsumption(String motorcadePetrolId) {
		MotorcadePetrol motorcadePetrol = getById(motorcadePetrolId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lubricateTime", motorcadePetrol.getLubricateTime());
		map.put("motorcadeTruckId", motorcadePetrol.getMotorcadeTruck().getId());
		return getSqlSession().selectOne("motorcadepetrol.getFuelConsumption", map);
	}}

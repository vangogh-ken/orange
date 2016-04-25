package com.van.halley.db.persistence;
import java.util.Map;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.MotorcadePetrol;

public interface MotorcadePetrolDao extends BaseDao<MotorcadePetrol> {
	/**
	 * 获取上次油耗和月均油耗
	 * @param motorcadePetrolId
	 * @return
	 */
	public Map<String, Object> getFuelConsumption(String motorcadePetrolId);
}
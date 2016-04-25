package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.MotorcadeFee;

public interface MotorcadeFeeDao extends BaseDao<MotorcadeFee> {

	/**
	 * 获取派单相关的费用
	 * @param motorcadeDispatchId
	 */
	public List<MotorcadeFee> getByMotorcadeDispatchId(String motorcadeDispatchId);
}
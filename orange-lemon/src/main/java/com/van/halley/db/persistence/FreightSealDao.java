package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightSeal;

public interface FreightSealDao extends BaseDao<FreightSeal> {
	/**
	 * 获取紧紧挨着的封号，需要信息：单位，类型，状态
	 * @return
	 */
	public FreightSeal getFreightSealProximate(FreightSeal freightSeal);
}
package com.van.halley.db.persistence;

import java.util.Date;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FasExchangeRate;

public interface FasExchangeRateDao extends BaseDao<FasExchangeRate> {
	/**
	 * 获取汇率
	 * @param currency
	 * @param expectTime
	 * @return
	 */
	public double getExchangeRate(String currency, Date expectTime);
}
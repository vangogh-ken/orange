package com.van.service;

import java.util.Date;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasExchangeRate;

public interface FasExchangeRateService {
	public List<FasExchangeRate> getAll();

	public List<FasExchangeRate> queryForList(FasExchangeRate fasExchangeRate);

	public FasExchangeRate queryForOne(FasExchangeRate fasExchangeRate);

	public PageView query(PageView pageView, FasExchangeRate fasExchangeRate);

	public void add(FasExchangeRate fasExchangeRate);

	public void delete(String id);
	/**
	 * 批量删除
	 * @param fasExchangeRateIds
	 */
	public void removeFasExchangeRate(String[] fasExchangeRateIds);

	public void modify(FasExchangeRate fasExchangeRate);

	public FasExchangeRate getById(String id);
	
	public double getExchangeRate(String currency, Date expectTime);
}

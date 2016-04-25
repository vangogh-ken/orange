package com.van.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasExchangeRateDao;
import com.van.halley.db.persistence.entity.FasExchangeRate;
import com.van.service.FasExchangeRateService;

@Transactional
@Service("fasExchangeRateService")
public class FasExchangeRateServiceImpl implements FasExchangeRateService {
	@Autowired
	private FasExchangeRateDao fasExchangeRateDao;
	public List<FasExchangeRate> getAll() {
		return fasExchangeRateDao.getAll();
	}

	public List<FasExchangeRate> queryForList(FasExchangeRate fasExchangeRate) {
		return fasExchangeRateDao.queryForList(fasExchangeRate);
	}

	public FasExchangeRate queryForOne(FasExchangeRate fasExchangeRate) {
		return fasExchangeRateDao.queryForOne(fasExchangeRate);
	}

	public PageView query(PageView pageView, FasExchangeRate fasExchangeRate) {
		List<FasExchangeRate> list = fasExchangeRateDao.query(pageView,
				fasExchangeRate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasExchangeRate fasExchangeRate) {
		fasExchangeRateDao.add(fasExchangeRate);
	}

	public void delete(String id) {
		fasExchangeRateDao.delete(id);
	}
	
	@Override
	public void removeFasExchangeRate(String[] fasExchangeRateIds) {
		for(String fasExchangeRateId : fasExchangeRateIds){
			fasExchangeRateDao.delete(fasExchangeRateId);
		}
	}

	public void modify(FasExchangeRate fasExchangeRate) {
		fasExchangeRateDao.modify(fasExchangeRate);
	}

	public FasExchangeRate getById(String id) {
		return fasExchangeRateDao.getById(id);
	}

	@Override
	public double getExchangeRate(String currency, Date expectTime) {
		return fasExchangeRateDao.getExchangeRate(currency, expectTime);
	}
	
}

package com.van.halley.db.persistence.impl;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasExchangeRateDao;
import com.van.halley.db.persistence.entity.FasExchangeRate;
@Repository("fasExchangeRateDao")
public class FasExchangeRateDaoImpl extends BaseDaoImpl<FasExchangeRate> implements FasExchangeRateDao {
	@Override
	public double getExchangeRate(String currency, Date expectTime) {
		List<FasExchangeRate> list = getAll();
		for(FasExchangeRate item : list){
			if(currency.equals(item.getCurrency())){
				if(expectTime == null){
					return item.getExchangeRate();
				}else if(expectTime.after(item.getStartTime())){
					return item.getExchangeRate();
				}
			}
		}
		//此种情况应该是之前没有设置汇率
		for(FasExchangeRate item : list){
			if(currency.equals(item.getCurrency())){
				return item.getExchangeRate();
			}
		}
		return 0;
	}}

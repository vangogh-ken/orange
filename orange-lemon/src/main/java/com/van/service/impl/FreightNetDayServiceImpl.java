package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightNetDayDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.entity.FreightNetDay;
import com.van.service.FreightNetDayService;

@Transactional
@Service("freightNetDayService")
public class FreightNetDayServiceImpl implements FreightNetDayService {
	@Autowired
	private FreightNetDayDao freightNetDayDao;
	@Autowired
	private FreightPartDao freightPartDao;

	public List<FreightNetDay> getAll() {
		return freightNetDayDao.getAll();
	}

	public List<FreightNetDay> queryForList(FreightNetDay freightNetDay) {
		return freightNetDayDao.queryForList(freightNetDay);
	}

	public FreightNetDay queryForOne(FreightNetDay freightNetDay) {
		return freightNetDayDao.queryForOne(freightNetDay);
	}

	public PageView query(PageView pageView, FreightNetDay freightNetDay) {
		List<FreightNetDay> list = freightNetDayDao.query(pageView,
				freightNetDay);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightNetDay freightNetDay) {
		freightNetDayDao.add(freightNetDay);
	}

	public void delete(String id) {
		freightNetDayDao.delete(id);
	}

	public void modify(FreightNetDay freightNetDay) {
		freightNetDayDao.modify(freightNetDay);
	}

	public FreightNetDay getById(String id) {
		return freightNetDayDao.getById(id);
	}

	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 7){
				continue;
			}
			FreightNetDay freightNetDay = new FreightNetDay();
			freightNetDay.setFreightPart(freightPartDao.getByPartName(singleValue.get(0)));
			freightNetDay.setIncomeOrExpense(singleValue.get(1));
			freightNetDay.setCurrency(singleValue.get(2));
			freightNetDay.setRegular(singleValue.get(3));
			freightNetDay.setPeriod(Integer.parseInt(singleValue.get(4)));
			freightNetDay.setRegularDay(Integer.parseInt(singleValue.get(5)));
			freightNetDay.setDelayMonth(Integer.parseInt(singleValue.get(6)));
			freightNetDay.setCreateTime(new Date());
			freightNetDayDao.add(freightNetDay);
		}

	}

	@Override
	public List<List<String>> toExport(List<FreightNetDay> freightNetDays) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightNetDay freightNetDay : freightNetDays){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightNetDay.getFreightPart().getPartName());
			singleValue.add(freightNetDay.getIncomeOrExpense());
			singleValue.add(freightNetDay.getCurrency());
			singleValue.add(freightNetDay.getRegular());
			singleValue.add(freightNetDay.getPeriod() + "");
			singleValue.add(freightNetDay.getRegularDay() + "");
			singleValue.add(freightNetDay.getDelayMonth() + "");
			values.add(singleValue);
		}
		return values;
	}
}

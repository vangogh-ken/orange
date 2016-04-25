package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeTollDao;
import com.van.halley.db.persistence.entity.MotorcadeToll;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeTollService;

@Transactional
@Service("motorcadeTollService")
public class MotorcadeTollServiceImpl implements MotorcadeTollService {
	@Autowired
	private MotorcadeTollDao motorcadeTollDao;

	public List<MotorcadeToll> getAll() {
		return motorcadeTollDao.getAll();
	}

	public List<MotorcadeToll> queryForList(MotorcadeToll motorcadeToll) {
		return motorcadeTollDao.queryForList(motorcadeToll);
	}

	public MotorcadeToll queryForOne(MotorcadeToll motorcadeToll) {
		return motorcadeTollDao.queryForOne(motorcadeToll);
	}

	public PageView<MotorcadeToll> query(PageView<MotorcadeToll> pageView, MotorcadeToll motorcadeToll) {
		List<MotorcadeToll> list = motorcadeTollDao.query(pageView, motorcadeToll);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeToll motorcadeToll) {
		motorcadeTollDao.add(motorcadeToll);
	}

	public void delete(String id) {
		motorcadeTollDao.delete(id);
	}

	public void modify(MotorcadeToll motorcadeToll) {
		motorcadeTollDao.modify(motorcadeToll);
	}

	public MotorcadeToll getById(String id) {
		return motorcadeTollDao.getById(id);
	}

	@Override
	public List<List<String>> doneBatchExport(String[] motorcadeTollIds) {
		PageView pageView = new PageView(motorcadeTollIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String motorcadeTollId : motorcadeTollIds){
			filterText.append("'" +motorcadeTollId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<MotorcadeToll> motorcadeTolls = motorcadeTollDao.query(pageView, new MotorcadeToll());
		List<List<String>> values = new ArrayList<List<String>>();
		for(MotorcadeToll motorcadeToll : motorcadeTolls){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(motorcadeToll.getMotorcadeDriver().getDisplayName());
			singleValue.add(motorcadeToll.getMotorcadeTruck().getHeadstockNumber());
			singleValue.add(motorcadeToll.getTruckType());
			singleValue.add(String.valueOf(motorcadeToll.getTotalWeight()));
			singleValue.add(motorcadeToll.getBeginStation());
			singleValue.add(StringUtil.parseTimeStamp(motorcadeToll.getBeginTime()));
			singleValue.add(motorcadeToll.getArriveStation());
			singleValue.add(StringUtil.parseTimeStamp(motorcadeToll.getArriveTime()));
			singleValue.add(String.valueOf(motorcadeToll.getMoneyCount()));
			singleValue.add(motorcadeToll.getFasInvoiceType().getTypeName());
			singleValue.add(motorcadeToll.getDescn());
			
			values.add(singleValue);
		}
		
		return values;
	}
}

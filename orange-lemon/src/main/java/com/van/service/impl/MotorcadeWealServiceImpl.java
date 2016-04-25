package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeWealDao;
import com.van.halley.db.persistence.entity.MotorcadeWeal;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeWealService;

@Transactional
@Service("motorcadeWealService")
public class MotorcadeWealServiceImpl implements MotorcadeWealService {
	@Autowired
	private MotorcadeWealDao motorcadeWealDao;

	public List<MotorcadeWeal> getAll() {
		return motorcadeWealDao.getAll();
	}

	public List<MotorcadeWeal> queryForList(MotorcadeWeal motorcadeWeal) {
		return motorcadeWealDao.queryForList(motorcadeWeal);
	}

	public MotorcadeWeal queryForOne(MotorcadeWeal motorcadeWeal) {
		return motorcadeWealDao.queryForOne(motorcadeWeal);
	}

	public PageView<MotorcadeWeal> query(PageView<MotorcadeWeal> pageView, MotorcadeWeal motorcadeWeal) {
		List<MotorcadeWeal> list = motorcadeWealDao.query(pageView, motorcadeWeal);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeWeal motorcadeWeal) {
		motorcadeWealDao.add(motorcadeWeal);
	}

	public void delete(String id) {
		motorcadeWealDao.delete(id);
	}

	public void modify(MotorcadeWeal motorcadeWeal) {
		motorcadeWealDao.modify(motorcadeWeal);
	}

	public MotorcadeWeal getById(String id) {
		return motorcadeWealDao.getById(id);
	}

	@Override
	public List<List<String>> doneBatchExport(String[] motorcadeWealIds) {
		PageView pageView = new PageView(motorcadeWealIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String motorcadeWealId : motorcadeWealIds){
			filterText.append("'" +motorcadeWealId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<MotorcadeWeal> motorcadeWeals = motorcadeWealDao.query(pageView, new MotorcadeWeal());
		List<List<String>> values = new ArrayList<List<String>>();
		for(MotorcadeWeal motorcadeWeal : motorcadeWeals){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(motorcadeWeal.getMotorcadeDriver().getDisplayName());
			singleValue.add(motorcadeWeal.getWealType());
			singleValue.add(StringUtil.parseDateTime(motorcadeWeal.getWealTime()));
			singleValue.add(String.valueOf(motorcadeWeal.getMoneyCount()));
			singleValue.add(motorcadeWeal.getFasInvoiceType().getTypeName());
			singleValue.add(motorcadeWeal.getDescn());
			values.add(singleValue);
		}
		
		return values;
	}
}

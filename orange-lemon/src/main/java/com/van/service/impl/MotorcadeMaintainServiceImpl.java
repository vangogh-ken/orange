package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeMaintainDao;
import com.van.halley.db.persistence.entity.MotorcadeMaintain;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeMaintainService;

@Transactional
@Service("motorcadeMaintainService")
public class MotorcadeMaintainServiceImpl implements MotorcadeMaintainService {
	@Autowired
	private MotorcadeMaintainDao motorcadeMaintainDao;

	public List<MotorcadeMaintain> getAll() {
		return motorcadeMaintainDao.getAll();
	}

	public List<MotorcadeMaintain> queryForList(MotorcadeMaintain motorcadeMaintain) {
		return motorcadeMaintainDao.queryForList(motorcadeMaintain);
	}

	public MotorcadeMaintain queryForOne(MotorcadeMaintain motorcadeMaintain) {
		return motorcadeMaintainDao.queryForOne(motorcadeMaintain);
	}

	public PageView<MotorcadeMaintain> query(PageView<MotorcadeMaintain> pageView, MotorcadeMaintain motorcadeMaintain) {
		List<MotorcadeMaintain> list = motorcadeMaintainDao.query(pageView, motorcadeMaintain);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeMaintain motorcadeMaintain) {
		motorcadeMaintainDao.add(motorcadeMaintain);
	}

	public void delete(String id) {
		motorcadeMaintainDao.delete(id);
	}

	public void modify(MotorcadeMaintain motorcadeMaintain) {
		motorcadeMaintainDao.modify(motorcadeMaintain);
	}

	public MotorcadeMaintain getById(String id) {
		return motorcadeMaintainDao.getById(id);
	}

	@Override
	public List<List<String>> doneBatchExport(String[] motorcadeMaintainIds) {
		PageView pageView = new PageView(motorcadeMaintainIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String motorcadeMaintainId : motorcadeMaintainIds){
			filterText.append("'" +motorcadeMaintainId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<MotorcadeMaintain> motorcadeMaintains = motorcadeMaintainDao.query(pageView, new MotorcadeMaintain());
		List<List<String>> values = new ArrayList<List<String>>();
		for(MotorcadeMaintain motorcadeMaintain : motorcadeMaintains){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(motorcadeMaintain.getMotorcadeDriver().getDisplayName());
			singleValue.add(motorcadeMaintain.getMotorcadeTruck().getHeadstockNumber());
			singleValue.add(motorcadeMaintain.getMaintainItem());
			singleValue.add(StringUtil.parseTimeStamp(motorcadeMaintain.getMaintainTime()));
			singleValue.add(motorcadeMaintain.getMaintainUnit());
			singleValue.add(String.valueOf(motorcadeMaintain.getMaintainCount()));
			singleValue.add(String.valueOf(motorcadeMaintain.getMoneyCount()));
			singleValue.add(String.valueOf(motorcadeMaintain.getMoneyCount()* motorcadeMaintain.getMaintainCount()));
			singleValue.add(motorcadeMaintain.getFasInvoiceType().getTypeName());
			singleValue.add(motorcadeMaintain.getDescn());
			
			values.add(singleValue);
		}
		
		return values;
	}
}

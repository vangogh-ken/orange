package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightExpenseTypeDao;
import com.van.halley.db.persistence.entity.FreightExpenseType;
import com.van.service.FreightExpenseTypeService;

@Transactional
@Service("freightExpenseTypeService")
public class FreightExpenseTypeServiceImpl implements FreightExpenseTypeService {
	@Autowired
	private FreightExpenseTypeDao freightExpenseTypeDao;
	
	public List<FreightExpenseType> getAll() {
		return freightExpenseTypeDao.getAll();
	}

	public List<FreightExpenseType> queryForList(
			FreightExpenseType freightExpenseType) {
		return freightExpenseTypeDao.queryForList(freightExpenseType);
	}

	public FreightExpenseType queryForOne(FreightExpenseType freightExpenseType) {
		return freightExpenseTypeDao.queryForOne(freightExpenseType);
	}

	public PageView<FreightExpenseType> query(PageView<FreightExpenseType> pageView,
			FreightExpenseType freightExpenseType) {
		List<FreightExpenseType> list = freightExpenseTypeDao.query(pageView,
				freightExpenseType);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightExpenseType freightExpenseType) {
		freightExpenseTypeDao.add(freightExpenseType);
	}

	public void delete(String id) {
		freightExpenseTypeDao.delete(id);
	}

	public void modify(FreightExpenseType freightExpenseType) {
		freightExpenseTypeDao.modify(freightExpenseType);
	}

	public FreightExpenseType getById(String id) {
		return freightExpenseTypeDao.getById(id);
	}

	@Override
	public void remove(String[] freightExpenseTypeIds) {
		for(String freightExpenseTypeId : freightExpenseTypeIds){
			freightExpenseTypeDao.delete(freightExpenseTypeId);
		}
	}

	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 1){
				continue;
			}
			FreightExpenseType filter = new FreightExpenseType();//如果有重复的数据直接跳过
			filter.setTypeName(singleValue.get(0));
			if(freightExpenseTypeDao.count(filter) == 0){
				FreightExpenseType freightExpenseType = new FreightExpenseType();
				freightExpenseType.setTypeName(singleValue.get(0));
				freightExpenseType.setCreateTime(new Date());
				freightExpenseTypeDao.add(freightExpenseType);
			}
		}
	}

	@Override
	public List<List<String>> toExport(List<FreightExpenseType> freightExpenseTypes) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightExpenseType freightExpenseType : freightExpenseTypes){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightExpenseType.getTypeName());
			values.add(singleValue);
		}
		return values;
	}

	@Override
	public FreightExpenseType getByTypeName(String typeName) {
		FreightExpenseType filter = new FreightExpenseType();
		filter.setTypeName(typeName);
		return freightExpenseTypeDao.queryForOne(filter);
	}
}

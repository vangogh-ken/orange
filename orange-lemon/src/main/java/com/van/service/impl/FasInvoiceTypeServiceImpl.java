package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.entity.FasInvoiceType;
import com.van.service.FasInvoiceTypeService;

@Transactional
@Service("fasInvoiceTypeService")
public class FasInvoiceTypeServiceImpl implements FasInvoiceTypeService {
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;

	public List<FasInvoiceType> getAll() {
		return fasInvoiceTypeDao.getAll();
	}

	public List<FasInvoiceType> queryForList(FasInvoiceType fasInvoiceType) {
		return fasInvoiceTypeDao.queryForList(fasInvoiceType);
	}

	public FasInvoiceType queryForOne(FasInvoiceType fasInvoiceType) {
		return fasInvoiceTypeDao.queryForOne(fasInvoiceType);
	}

	public PageView<FasInvoiceType> query(PageView<FasInvoiceType> pageView, FasInvoiceType fasInvoiceType) {
		List<FasInvoiceType> list = fasInvoiceTypeDao.query(pageView,
				fasInvoiceType);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasInvoiceType fasInvoiceType) {
		fasInvoiceTypeDao.add(fasInvoiceType);
	}

	public void delete(String id) {
		fasInvoiceTypeDao.delete(id);
	}

	public void modify(FasInvoiceType fasInvoiceType) {
		fasInvoiceTypeDao.modify(fasInvoiceType);
	}

	public FasInvoiceType getById(String id) {
		return fasInvoiceTypeDao.getById(id);
	}

	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 2){
				continue;
			}
			FasInvoiceType fasInvoiceType = new FasInvoiceType();
			fasInvoiceType.setTypeName(singleValue.get(0));
			fasInvoiceType.setTaxRate(Double.parseDouble(singleValue.get(1)));
			fasInvoiceType.setCreateTime(new Date());
			fasInvoiceTypeDao.add(fasInvoiceType);
		}
	}

	@Override
	public List<List<String>> toExport(List<FasInvoiceType> fasInvoiceTypes) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FasInvoiceType fasInvoiceType : fasInvoiceTypes){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(fasInvoiceType.getTypeName());
			singleValue.add(fasInvoiceType.getTaxRate() + "");
			values.add(singleValue);
		}
		return values;
	}
	
}

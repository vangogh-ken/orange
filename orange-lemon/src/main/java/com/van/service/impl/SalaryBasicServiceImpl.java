package com.van.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.SalaryBasicDao;
import com.van.halley.db.persistence.entity.SalaryBasic;
import com.van.service.SalaryBasicService;

@Transactional
@Service("salaryBasicService")
public class SalaryBasicServiceImpl implements SalaryBasicService {
	@Autowired
	private SalaryBasicDao salaryBasicDao;

	public List<SalaryBasic> getAll() {
		return salaryBasicDao.getAll();
	}

	public List<SalaryBasic> queryForList(SalaryBasic salaryBasic) {
		return salaryBasicDao.queryForList(salaryBasic);
	}
	
	@Override
	public int count(SalaryBasic salaryBasic) {
		return salaryBasicDao.count(salaryBasic);
	}
	
	public SalaryBasic queryForOne(SalaryBasic salaryBasic) {
		return salaryBasicDao.queryForOne(salaryBasic);
	}
	

	public PageView<SalaryBasic> query(PageView<SalaryBasic> pageView, SalaryBasic salaryBasic) {
		List<SalaryBasic> list = salaryBasicDao.query(pageView, salaryBasic);
		pageView.setResults(list);
		return pageView;
	}

	public void add(SalaryBasic salaryBasic) {
		salaryBasicDao.add(salaryBasic);
	}

	public void delete(String id) {
		salaryBasicDao.delete(id);
	}

	public void modify(SalaryBasic salaryBasic) {
		salaryBasicDao.modify(salaryBasic);
	}

	public SalaryBasic getById(String id) {
		return salaryBasicDao.getById(id);
	}

	
}

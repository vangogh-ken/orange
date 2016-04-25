package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.SalaryBasic;

public interface SalaryBasicService {
	public List<SalaryBasic> getAll();

	public List<SalaryBasic> queryForList(SalaryBasic salaryBasic);
	
	public int count(SalaryBasic salaryBasic);

	public SalaryBasic queryForOne(SalaryBasic salaryBasic);

	public PageView<SalaryBasic> query(PageView<SalaryBasic> pageView, SalaryBasic salaryBasic);

	public void add(SalaryBasic salaryBasic);

	public void delete(String id);

	public void modify(SalaryBasic salaryBasic);

	public SalaryBasic getById(String id);
}

package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.SalaryBonus;

public interface SalaryBonusService {
	public List<SalaryBonus> getAll();

	public List<SalaryBonus> queryForList(SalaryBonus salaryBonus);
	
	public int count(SalaryBonus salaryBonus);

	public SalaryBonus queryForOne(SalaryBonus salaryBonus);

	public PageView<SalaryBonus> query(PageView<SalaryBonus> pageView, SalaryBonus salaryBonus);

	public void add(SalaryBonus salaryBonus);

	public void delete(String id);

	public void modify(SalaryBonus salaryBonus);

	public SalaryBonus getById(String id);
}

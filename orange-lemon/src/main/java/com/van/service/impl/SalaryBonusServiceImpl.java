package com.van.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.SalaryBonusDao;
import com.van.halley.db.persistence.entity.SalaryBonus;
import com.van.service.SalaryBonusService;

@Transactional
@Service("salaryBonusService")
public class SalaryBonusServiceImpl implements SalaryBonusService {
	@Autowired
	private SalaryBonusDao salaryBonusDao;

	public List<SalaryBonus> getAll() {
		return salaryBonusDao.getAll();
	}

	public List<SalaryBonus> queryForList(SalaryBonus salaryBonus) {
		return salaryBonusDao.queryForList(salaryBonus);
	}
	
	@Override
	public int count(SalaryBonus salaryBonus) {
		return salaryBonusDao.count(salaryBonus);
	}

	public SalaryBonus queryForOne(SalaryBonus salaryBonus) {
		return salaryBonusDao.queryForOne(salaryBonus);
	}

	public PageView<SalaryBonus> query(PageView<SalaryBonus> pageView, SalaryBonus salaryBonus) {
		List<SalaryBonus> list = salaryBonusDao.query(pageView, salaryBonus);
		pageView.setResults(list);
		return pageView;
	}

	public void add(SalaryBonus salaryBonus) {
		salaryBonusDao.add(salaryBonus);
	}

	public void delete(String id) {
		salaryBonusDao.delete(id);
	}

	public void modify(SalaryBonus salaryBonus) {
		salaryBonusDao.modify(salaryBonus);
	}

	public SalaryBonus getById(String id) {
		return salaryBonusDao.getById(id);
	}

	
}

package com.van.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.SalaryGradeDao;
import com.van.halley.db.persistence.entity.SalaryGrade;
import com.van.service.SalaryGradeService;

@Transactional
@Service("salaryGradeService")
public class SalaryGradeServiceImpl implements SalaryGradeService {
	@Autowired
	private SalaryGradeDao salaryGradeDao;

	public List<SalaryGrade> getAll() {
		return salaryGradeDao.getAll();
	}

	public List<SalaryGrade> queryForList(SalaryGrade salaryGrade) {
		return salaryGradeDao.queryForList(salaryGrade);
	}

	public SalaryGrade queryForOne(SalaryGrade salaryGrade) {
		return salaryGradeDao.queryForOne(salaryGrade);
	}

	public PageView<SalaryGrade> query(PageView<SalaryGrade> pageView, SalaryGrade salaryGrade) {
		List<SalaryGrade> list = salaryGradeDao.query(pageView, salaryGrade);
		pageView.setResults(list);
		return pageView;
	}

	public void add(SalaryGrade salaryGrade) {
		SalaryGrade filter = new SalaryGrade();
		filter.setUser(salaryGrade.getUser());
		List<SalaryGrade> salaryGrades = salaryGradeDao.queryForList(filter);
		if(salaryGrades != null && !salaryGrades.isEmpty()){
			for(SalaryGrade item : salaryGrades){
				item.setStatus("F");
				salaryGradeDao.modify(item);
			}
		}
		salaryGradeDao.add(salaryGrade);
	}

	public void delete(String id) {
		salaryGradeDao.delete(id);
	}

	public void modify(SalaryGrade salaryGrade) {
		salaryGradeDao.modify(salaryGrade);
	}

	public SalaryGrade getById(String id) {
		return salaryGradeDao.getById(id);
	}
}

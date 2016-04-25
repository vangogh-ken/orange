package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.SalaryGrade;

public interface SalaryGradeService {
	public List<SalaryGrade> getAll();

	public List<SalaryGrade> queryForList(SalaryGrade salaryGrade);

	public SalaryGrade queryForOne(SalaryGrade salaryGrade);

	public PageView<SalaryGrade> query(PageView<SalaryGrade> pageView, SalaryGrade salaryGrade);

	public void add(SalaryGrade salaryGrade);

	public void delete(String id);

	public void modify(SalaryGrade salaryGrade);

	public SalaryGrade getById(String id);
}

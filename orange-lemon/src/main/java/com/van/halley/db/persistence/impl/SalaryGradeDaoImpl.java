package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.SalaryGradeDao;
import com.van.halley.db.persistence.entity.SalaryGrade;
@Repository("salaryGradeDao")
public class SalaryGradeDaoImpl extends BaseDaoImpl<SalaryGrade> implements SalaryGradeDao {}

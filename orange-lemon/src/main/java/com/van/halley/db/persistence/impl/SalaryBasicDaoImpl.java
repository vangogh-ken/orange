package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.SalaryBasicDao;
import com.van.halley.db.persistence.entity.SalaryBasic;
@Repository("salaryBasicDao")
public class SalaryBasicDaoImpl extends BaseDaoImpl<SalaryBasic> implements SalaryBasicDao {}

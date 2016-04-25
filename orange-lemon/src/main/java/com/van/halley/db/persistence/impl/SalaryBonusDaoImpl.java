package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.SalaryBonusDao;
import com.van.halley.db.persistence.entity.SalaryBonus;
@Repository("salaryBonusDao")
public class SalaryBonusDaoImpl extends BaseDaoImpl<SalaryBonus> implements SalaryBonusDao {}

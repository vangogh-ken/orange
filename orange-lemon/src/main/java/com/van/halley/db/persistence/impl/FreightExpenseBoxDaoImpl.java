package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightExpenseBoxDao;
import com.van.halley.db.persistence.entity.FreightExpenseBox;
@Repository("freightExpenseBoxDao")
public class FreightExpenseBoxDaoImpl extends BaseDaoImpl<FreightExpenseBox> implements FreightExpenseBoxDao {}

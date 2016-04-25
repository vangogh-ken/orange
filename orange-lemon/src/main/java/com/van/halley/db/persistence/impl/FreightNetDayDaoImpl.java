package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightNetDayDao;
import com.van.halley.db.persistence.entity.FreightNetDay;
@Repository("freightNetDayDao")
public class FreightNetDayDaoImpl extends BaseDaoImpl<FreightNetDay> implements FreightNetDayDao {}

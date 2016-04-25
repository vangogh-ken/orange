package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightMaintainDao;
import com.van.halley.db.persistence.entity.FreightMaintain;
@Repository("freightMaintainDao")
public class FreightMaintainDaoImpl extends BaseDaoImpl<FreightMaintain> implements FreightMaintainDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightMaintainTypeDao;
import com.van.halley.db.persistence.entity.FreightMaintainType;
@Repository("freightMaintainTypeDao")
public class FreightMaintainTypeDaoImpl extends BaseDaoImpl<FreightMaintainType> implements FreightMaintainTypeDao {}

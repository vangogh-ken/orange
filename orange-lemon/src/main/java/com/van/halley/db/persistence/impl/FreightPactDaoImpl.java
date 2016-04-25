package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightPactDao;
import com.van.halley.db.persistence.entity.FreightPact;
@Repository("freightPactDao")
public class FreightPactDaoImpl extends BaseDaoImpl<FreightPact> implements FreightPactDao {}

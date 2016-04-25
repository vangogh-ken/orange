package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightActionBoxDao;
import com.van.halley.db.persistence.entity.FreightActionBox;
@Repository("freightActionBoxDao")
public class FreightActionBoxDaoImpl extends BaseDaoImpl<FreightActionBox> implements FreightActionBoxDao {}

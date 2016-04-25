package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightBoxDao;
import com.van.halley.db.persistence.entity.FreightBox;
@Repository("freightBoxDao")
public class FreightBoxDaoImpl extends BaseDaoImpl<FreightBox> implements FreightBoxDao {}

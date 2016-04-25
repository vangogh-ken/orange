package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeMaintainDao;
import com.van.halley.db.persistence.entity.MotorcadeMaintain;
@Repository("motorcadeMaintainDao")
public class MotorcadeMaintainDaoImpl extends BaseDaoImpl<MotorcadeMaintain> implements MotorcadeMaintainDao {}

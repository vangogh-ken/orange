package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeWealDao;
import com.van.halley.db.persistence.entity.MotorcadeWeal;
@Repository("motorcadeWealDao")
public class MotorcadeWealDaoImpl extends BaseDaoImpl<MotorcadeWeal> implements MotorcadeWealDao {}

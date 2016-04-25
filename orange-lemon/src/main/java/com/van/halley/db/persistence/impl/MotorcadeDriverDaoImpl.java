package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeDriverDao;
import com.van.halley.db.persistence.entity.MotorcadeDriver;
@Repository("motorcadeDriverDao")
public class MotorcadeDriverDaoImpl extends BaseDaoImpl<MotorcadeDriver> implements MotorcadeDriverDao {}

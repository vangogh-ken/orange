package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeTruckDao;
import com.van.halley.db.persistence.entity.MotorcadeTruck;
@Repository("motorcadeTruckDao")
public class MotorcadeTruckDaoImpl extends BaseDaoImpl<MotorcadeTruck> implements MotorcadeTruckDao {}

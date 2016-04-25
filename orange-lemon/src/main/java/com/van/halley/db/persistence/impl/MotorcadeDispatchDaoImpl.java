package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeDispatchDao;
import com.van.halley.db.persistence.entity.MotorcadeDispatch;
@Repository("motorcadeDispatchDao")
public class MotorcadeDispatchDaoImpl extends BaseDaoImpl<MotorcadeDispatch> implements MotorcadeDispatchDao {}

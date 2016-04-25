package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.WorkCalTypeDao;
import com.van.halley.db.persistence.entity.WorkCalType;
@Repository("workcalTypeDao")
public class WorkCalTypeDaoImpl extends BaseDaoImpl<WorkCalType> implements WorkCalTypeDao {}

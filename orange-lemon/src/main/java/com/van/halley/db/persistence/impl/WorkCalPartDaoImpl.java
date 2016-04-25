package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.WorkCalPartDao;
import com.van.halley.db.persistence.entity.WorkCalPart;
@Repository("workcalPartDao")
public class WorkCalPartDaoImpl extends BaseDaoImpl<WorkCalPart> implements WorkCalPartDao {}

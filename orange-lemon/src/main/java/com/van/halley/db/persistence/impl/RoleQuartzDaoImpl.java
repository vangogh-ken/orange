package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.RoleQuartzDao;
import com.van.halley.db.persistence.entity.RoleQuartz;
@Repository("roleQuartzDao")
public class RoleQuartzDaoImpl extends BaseDaoImpl<RoleQuartz> implements RoleQuartzDao {}

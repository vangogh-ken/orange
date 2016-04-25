package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.OrgEntityDao;
import com.van.halley.db.persistence.entity.OrgEntity;
@Repository("orgEntityDao")
public class OrgEntityDaoImpl extends BaseDaoImpl<OrgEntity> implements OrgEntityDao {}

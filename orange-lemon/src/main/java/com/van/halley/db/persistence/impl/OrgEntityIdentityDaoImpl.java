package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.OrgEntityIdentityDao;
import com.van.halley.db.persistence.entity.OrgEntityIdentity;
@Repository("orgEntityIdentityDao")
public class OrgEntityIdentityDaoImpl extends BaseDaoImpl<OrgEntityIdentity> implements OrgEntityIdentityDao {}

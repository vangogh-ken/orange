package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.OrgTypeDao;
import com.van.halley.db.persistence.entity.OrgType;
@Repository("orgTypeDao")
public class OrgTypeDaoImpl extends BaseDaoImpl<OrgType> implements OrgTypeDao {}

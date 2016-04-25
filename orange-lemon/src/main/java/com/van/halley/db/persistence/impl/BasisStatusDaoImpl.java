package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisStatusDao;
import com.van.halley.db.persistence.entity.BasisStatus;
@Repository("basisStatusDao")
public class BasisStatusDaoImpl extends BaseDaoImpl<BasisStatus> implements BasisStatusDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisApplicationDao;
import com.van.halley.db.persistence.entity.BasisApplication;
@Repository("basisApplicationDao")
public class BasisApplicationDaoImpl extends BaseDaoImpl<BasisApplication> implements BasisApplicationDao {}

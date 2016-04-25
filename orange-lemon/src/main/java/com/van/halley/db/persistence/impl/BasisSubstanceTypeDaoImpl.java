package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisSubstanceTypeDao;
import com.van.halley.db.persistence.entity.BasisSubstanceType;
@Repository("basisSubstanceTypeDao")
public class BasisSubstanceTypeDaoImpl extends BaseDaoImpl<BasisSubstanceType> implements BasisSubstanceTypeDao {}

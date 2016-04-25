package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisSubstanceDao;
import com.van.halley.db.persistence.entity.BasisSubstance;
@Repository("basisSubstanceDao")
public class BasisSubstanceDaoImpl extends BaseDaoImpl<BasisSubstance> implements BasisSubstanceDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisStatusAttributeDao;
import com.van.halley.db.persistence.entity.BasisStatusAttribute;
@Repository("basisStatusAttributeDao")
public class BasisStatusAttributeDaoImpl extends BaseDaoImpl<BasisStatusAttribute> implements BasisStatusAttributeDao {}

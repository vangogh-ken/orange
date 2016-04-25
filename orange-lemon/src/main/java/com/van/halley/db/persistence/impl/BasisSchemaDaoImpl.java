package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisSchemaDao;
import com.van.halley.db.persistence.entity.BasisSchema;
@Repository("basisSchemaDao")
public class BasisSchemaDaoImpl extends BaseDaoImpl<BasisSchema> implements BasisSchemaDao {}

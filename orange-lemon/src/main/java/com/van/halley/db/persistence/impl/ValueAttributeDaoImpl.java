package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ValueAttributeDao;
import com.van.halley.db.persistence.entity.ValueAttribute;

@Repository("valueAttributeDao")
public class ValueAttributeDaoImpl extends BaseDaoImpl<ValueAttribute>
		implements ValueAttributeDao {
}

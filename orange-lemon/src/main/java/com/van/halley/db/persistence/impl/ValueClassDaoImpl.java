package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ValueClassDao;
import com.van.halley.db.persistence.entity.ValueClass;

@Repository("valueClassDao")
public class ValueClassDaoImpl extends BaseDaoImpl<ValueClass> implements
		ValueClassDao {
}

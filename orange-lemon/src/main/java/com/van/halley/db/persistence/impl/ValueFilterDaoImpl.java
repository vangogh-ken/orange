package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ValueFilterDao;
import com.van.halley.db.persistence.entity.ValueFilter;
@Repository("valueFilterDao")
public class ValueFilterDaoImpl extends BaseDaoImpl<ValueFilter> implements ValueFilterDao {}

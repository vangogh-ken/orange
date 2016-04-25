package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ValueDictionaryDao;
import com.van.halley.db.persistence.entity.ValueDictionary;

@Repository("valueDictionaryDao")
public class ValueDictionaryDaoImpl extends BaseDaoImpl<ValueDictionary>
		implements ValueDictionaryDao {
}

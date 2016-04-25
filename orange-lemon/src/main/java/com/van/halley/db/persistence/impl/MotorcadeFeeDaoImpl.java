package com.van.halley.db.persistence.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeFeeDao;
import com.van.halley.db.persistence.entity.MotorcadeFee;
@Repository("motorcadeFeeDao")
public class MotorcadeFeeDaoImpl extends BaseDaoImpl<MotorcadeFee> implements MotorcadeFeeDao {

	@Override
	public List<MotorcadeFee> getByMotorcadeDispatchId(String motorcadeDispatchId) {
		return getSqlSession().selectList("motorcadefee.getByMotorcadeDispatchId", motorcadeDispatchId);
	}}

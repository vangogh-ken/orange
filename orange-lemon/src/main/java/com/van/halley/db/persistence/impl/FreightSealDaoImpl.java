package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightSealDao;
import com.van.halley.db.persistence.entity.FreightSeal;

@Repository("freightSealDao")
public class FreightSealDaoImpl extends BaseDaoImpl<FreightSeal> implements
		FreightSealDao {

	@Override
	public FreightSeal getFreightSealProximate(FreightSeal freightSeal) {
		return getSqlSession().selectOne("freightseal.getFreightSealProximate", freightSeal);
	}
	
}

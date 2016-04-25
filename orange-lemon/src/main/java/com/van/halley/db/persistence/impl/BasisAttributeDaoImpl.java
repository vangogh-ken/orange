package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisAttributeDao;
import com.van.halley.db.persistence.entity.BasisAttribute;

@Repository("basisAttributeDao")
public class BasisAttributeDaoImpl extends BaseDaoImpl<BasisAttribute>
		implements BasisAttributeDao {

	@Override
	public List<BasisAttribute> getByBasisSubstanceTypeId(
			String basisSubstanceTypeId) {
		return getSqlSession().selectList(
				"basisattribute.getByBasisSubstanceTypeId",
				basisSubstanceTypeId);
	}
}

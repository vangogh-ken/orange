package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.BasisAttribute;

public interface BasisAttributeDao extends BaseDao<BasisAttribute> {
	/**
	 * 获取所有字段
	 * @param id
	 * @return
	 */
	public List<BasisAttribute> getByBasisSubstanceTypeId(String basisSubstanceTypeId);
}
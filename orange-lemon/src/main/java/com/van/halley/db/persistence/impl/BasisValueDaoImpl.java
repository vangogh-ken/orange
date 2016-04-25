package com.van.halley.db.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisValueDao;
import com.van.halley.db.persistence.entity.BasisValue;

@Repository("basisValueDao")
public class BasisValueDaoImpl extends BaseDaoImpl<BasisValue> implements
		BasisValueDao {

	@Override
	public BasisValue getSingleValue(String basisSubstanceId, String idOrColumn, boolean isAttributeId) {
		if(isAttributeId){
			Map<String, String> map = new HashMap<String, String>();
			map.put("basisSubstanceId", basisSubstanceId);
			map.put("basisAttributeId", idOrColumn);
			return getSqlSession().selectOne("basisvalue.getByBasisAttributeId", map);
		}else{
			Map<String, String> map = new HashMap<String, String>();
			map.put("basisSubstanceId", basisSubstanceId);
			map.put("basisAttributeColumn", idOrColumn);
			return getSqlSession().selectOne("basisvalue.getByBasisAttributeColumn", map);
		}
	}

	@Override
	public List<BasisValue> getByBasisSubstanceId(String basisSubstanceId) {
		return getSqlSession().selectList("basisvalue.getByBasisSubstanceId", basisSubstanceId);
	}

	@Override
	public void deleteByBasisSubstanceId(String basisSubstanceId) {
		getSqlSession().delete("basisvalue.deleteByBasisSubstanceId", basisSubstanceId);
	}

}

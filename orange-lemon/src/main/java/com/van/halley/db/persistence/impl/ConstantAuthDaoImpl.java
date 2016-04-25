package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ConstantAuthDao;
import com.van.halley.db.persistence.entity.ConstantAuth;

@Repository("constantAuthDao")
public class ConstantAuthDaoImpl extends BaseDaoImpl<ConstantAuth> implements
		ConstantAuthDao {
	@Override
	public List<ConstantAuth> getByBpmConfigNodeId(String bpmConfigNodeId) {
		return getSqlSession().selectList("constantauth.getByBpmConfigNodeId",
				bpmConfigNodeId);
	}
}

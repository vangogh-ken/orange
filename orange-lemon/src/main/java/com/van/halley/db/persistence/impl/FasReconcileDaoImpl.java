package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasReconcileDao;
import com.van.halley.db.persistence.entity.FasReconcile;

@Repository("fasReconcileDao")
public class FasReconcileDaoImpl extends BaseDaoImpl<FasReconcile> implements
		FasReconcileDao {
}

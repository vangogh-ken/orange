package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasAccountDao;
import com.van.halley.db.persistence.entity.FasAccount;

@Repository("fasAccountDao")
public class FasAccountDaoImpl extends BaseDaoImpl<FasAccount> implements
		FasAccountDao {
}

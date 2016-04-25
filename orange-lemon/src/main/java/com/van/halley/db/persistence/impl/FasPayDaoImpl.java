package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasPayDao;
import com.van.halley.db.persistence.entity.FasPay;
@Repository("fasPayDao")
public class FasPayDaoImpl extends BaseDaoImpl<FasPay> implements FasPayDao {}

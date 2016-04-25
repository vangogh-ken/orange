package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.LoginLogDao;
import com.van.halley.db.persistence.entity.LoginLog;

@Repository("loginLogDao")
public class LoginLogDaoImpl extends BaseDaoImpl<LoginLog> implements
		LoginLogDao {

}

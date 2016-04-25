package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ServerInfoDao;
import com.van.halley.db.persistence.entity.ServerInfo;

@Repository("serverInfoDao")
public class ServerInfoDaoImpl extends BaseDaoImpl<ServerInfo> implements
		ServerInfoDao {

}

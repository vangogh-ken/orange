package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.OutMsgInfoDao;
import com.van.halley.db.persistence.entity.OutMsgInfo;
@Repository("outMsgInfoDao")
public class OutMsgInfoDaoImpl extends BaseDaoImpl<OutMsgInfo> implements OutMsgInfoDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.DiskInfoDao;
import com.van.halley.db.persistence.entity.DiskInfo;
@Repository("diskInfoDao")
public class DiskInfoDaoImpl extends BaseDaoImpl<DiskInfo> implements DiskInfoDao {}

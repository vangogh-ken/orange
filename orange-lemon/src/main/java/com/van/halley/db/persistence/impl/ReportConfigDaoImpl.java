package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportConfigDao;
import com.van.halley.db.persistence.entity.ReportConfig;

@Repository("reportConfigDao")
public class ReportConfigDaoImpl extends BaseDaoImpl<ReportConfig> implements
		ReportConfigDao {
}

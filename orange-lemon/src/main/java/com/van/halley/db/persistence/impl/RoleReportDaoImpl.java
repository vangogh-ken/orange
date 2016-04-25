package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.RoleReportDao;
import com.van.halley.db.persistence.entity.RoleReport;
@Repository("roleReportDao")
public class RoleReportDaoImpl extends BaseDaoImpl<RoleReport> implements RoleReportDao {}

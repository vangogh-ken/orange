package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ReportCategoryDao;
import com.van.halley.db.persistence.entity.ReportCategory;
@Repository("reportCategoryDao")
public class ReportCategoryDaoImpl extends BaseDaoImpl<ReportCategory> implements ReportCategoryDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.WorkCalRuleDao;
import com.van.halley.db.persistence.entity.WorkCalRule;
@Repository("workcalRuleDao")
public class WorkCalRuleDaoImpl extends BaseDaoImpl<WorkCalRule> implements WorkCalRuleDao {}

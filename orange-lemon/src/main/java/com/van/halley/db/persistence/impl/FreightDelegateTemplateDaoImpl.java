package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightDelegateTemplateDao;
import com.van.halley.db.persistence.entity.FreightDelegateTemplate;
@Repository("freightDelegateTemplateDao")
public class FreightDelegateTemplateDaoImpl extends BaseDaoImpl<FreightDelegateTemplate> implements FreightDelegateTemplateDao {}

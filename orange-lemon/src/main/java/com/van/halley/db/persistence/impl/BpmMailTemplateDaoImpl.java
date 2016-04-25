package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmMailTemplateDao;
import com.van.halley.db.persistence.entity.BpmMailTemplate;

@Repository("bpmMailTemplateDao")
public class BpmMailTemplateDaoImpl extends BaseDaoImpl<BpmMailTemplate>
		implements BpmMailTemplateDao {
}

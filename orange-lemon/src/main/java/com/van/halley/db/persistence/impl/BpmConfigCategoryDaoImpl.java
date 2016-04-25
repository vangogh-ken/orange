package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigCategoryDao;
import com.van.halley.db.persistence.entity.BpmConfigCategory;
@Repository("bpmConfigCategoryDao")
public class BpmConfigCategoryDaoImpl extends BaseDaoImpl<BpmConfigCategory> implements BpmConfigCategoryDao {}

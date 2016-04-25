package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigOperationDao;
import com.van.halley.db.persistence.entity.BpmConfigOperation;
@Repository("bpmConfigOperationDao")
public class BpmConfigOperationDaoImpl extends BaseDaoImpl<BpmConfigOperation> implements BpmConfigOperationDao {}

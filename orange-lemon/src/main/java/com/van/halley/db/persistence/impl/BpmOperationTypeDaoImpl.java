package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmOperationTypeDao;
import com.van.halley.db.persistence.entity.BpmOperationType;
@Repository("bpmOperationTypeDao")
public class BpmOperationTypeDaoImpl extends BaseDaoImpl<BpmOperationType> implements BpmOperationTypeDao {}

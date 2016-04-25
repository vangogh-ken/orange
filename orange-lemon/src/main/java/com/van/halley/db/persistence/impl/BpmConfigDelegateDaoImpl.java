package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BpmConfigDelegateDao;
import com.van.halley.db.persistence.entity.BpmConfigDelegate;
@Repository("bpmConfigDelegateDao")
public class BpmConfigDelegateDaoImpl extends BaseDaoImpl<BpmConfigDelegate> implements BpmConfigDelegateDao {}

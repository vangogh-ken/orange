package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CrmCustomerDao;
import com.van.halley.db.persistence.entity.CrmCustomer;
@Repository("crmCustomerDao")
public class CrmCustomerDaoImpl extends BaseDaoImpl<CrmCustomer> implements CrmCustomerDao {}

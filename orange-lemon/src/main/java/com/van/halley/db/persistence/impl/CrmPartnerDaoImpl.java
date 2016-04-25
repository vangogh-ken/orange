package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CrmPartnerDao;
import com.van.halley.db.persistence.entity.CrmPartner;
@Repository("crmPartnerDao")
public class CrmPartnerDaoImpl extends BaseDaoImpl<CrmPartner> implements CrmPartnerDao {}

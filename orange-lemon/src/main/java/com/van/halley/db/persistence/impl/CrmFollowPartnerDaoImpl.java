package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CrmPartnerFollowDao;
import com.van.halley.db.persistence.entity.CrmPartnerFollow;
@Repository("crmPartnerFollowDao")
public class CrmFollowPartnerDaoImpl extends BaseDaoImpl<CrmPartnerFollow> implements CrmPartnerFollowDao {

	@Override
	public CrmPartnerFollow getLastByCrmPartnerId(String crmPartnerId) {
		return getSqlSession().selectOne("crmpartnerfollow.getLastByCrmPartnerId", crmPartnerId);
	}}

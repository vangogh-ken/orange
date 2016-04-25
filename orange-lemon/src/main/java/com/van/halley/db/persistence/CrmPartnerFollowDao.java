package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.CrmPartnerFollow;

public interface CrmPartnerFollowDao extends BaseDao<CrmPartnerFollow> {
	/**
	 * 获取最近一次的拜访
	 * @return
	 */
	public CrmPartnerFollow getLastByCrmPartnerId(String crmPartnerId);
}
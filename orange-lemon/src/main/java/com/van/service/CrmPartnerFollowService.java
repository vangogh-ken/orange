package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CrmPartnerFollow;

public interface CrmPartnerFollowService {
	public List<CrmPartnerFollow> getAll();

	public List<CrmPartnerFollow> queryForList(CrmPartnerFollow crmPartnerFollow);

	public CrmPartnerFollow queryForOne(CrmPartnerFollow crmPartnerFollow);

	public PageView query(PageView pageView, CrmPartnerFollow crmPartnerFollow);

	public void add(CrmPartnerFollow crmPartnerFollow);

	public void delete(String id);

	public void modify(CrmPartnerFollow crmPartnerFollow);

	public CrmPartnerFollow getById(String id);
	
	/**
	 * 获取最近一次的跟进信息
	 * @param crmPartnerId
	 * @return
	 */
	public CrmPartnerFollow getLastByCrmPartnerId(String crmPartnerId);
}

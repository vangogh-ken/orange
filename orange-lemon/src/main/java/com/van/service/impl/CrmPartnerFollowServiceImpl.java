package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CrmPartnerDao;
import com.van.halley.db.persistence.CrmPartnerFollowDao;
import com.van.halley.db.persistence.entity.CrmPartnerFollow;
import com.van.service.CrmPartnerFollowService;

@Transactional
@Service("crmPartnerFollowService")
public class CrmPartnerFollowServiceImpl implements CrmPartnerFollowService {
	@Autowired
	private CrmPartnerFollowDao crmPartnerFollowDao;
	@Autowired
	private CrmPartnerDao crmPartnerDao;

	public List<CrmPartnerFollow> getAll() {
		return crmPartnerFollowDao.getAll();
	}

	public List<CrmPartnerFollow> queryForList(CrmPartnerFollow crmPartnerFollow) {
		return crmPartnerFollowDao.queryForList(crmPartnerFollow);
	}

	public CrmPartnerFollow queryForOne(CrmPartnerFollow crmPartnerFollow) {
		return crmPartnerFollowDao.queryForOne(crmPartnerFollow);
	}

	public PageView query(PageView pageView, CrmPartnerFollow crmPartnerFollow) {
		List<CrmPartnerFollow> list = crmPartnerFollowDao.query(pageView, crmPartnerFollow);
		pageView.setResults(list);
		return pageView;
	}

	public void add(CrmPartnerFollow crmPartnerFollow) {
		crmPartnerFollowDao.add(crmPartnerFollow);
	}

	public void delete(String id) {
		crmPartnerFollowDao.delete(id);
	}

	public void modify(CrmPartnerFollow crmPartnerFollow) {
		crmPartnerFollowDao.modify(crmPartnerFollow);
	}

	public CrmPartnerFollow getById(String id) {
		return crmPartnerFollowDao.getById(id);
	}

	@Override
	public CrmPartnerFollow getLastByCrmPartnerId(String crmPartnerId) {
		CrmPartnerFollow filter = new CrmPartnerFollow();
		filter.setCrmPartner(crmPartnerDao.getById(crmPartnerId));
		List<CrmPartnerFollow> list = crmPartnerFollowDao.queryForList(filter);
		if(list == null || list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
}

package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CrmPartnerDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.CrmPartner;
import com.van.halley.db.persistence.entity.User;
import com.van.service.CrmPartnerService;

@Transactional
@Service("crmPartnerService")
public class CrmPartnerServiceImpl implements CrmPartnerService {
	private static Logger logger = LoggerFactory.getLogger(CrmCustomerServiceImpl.class);
	@Autowired
	private CrmPartnerDao crmPartnerDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<CrmPartner> getAll() {
		return crmPartnerDao.getAll();
	}

	public List<CrmPartner> queryForList(CrmPartner crmPartner) {
		return crmPartnerDao.queryForList(crmPartner);
	}

	public PageView query(PageView pageView, CrmPartner crmPartner) {
		List<CrmPartner> list = crmPartnerDao.query(pageView, crmPartner);
		pageView.setResults(list);
		return pageView;
	}

	public void add(CrmPartner crmPartner) {
		crmPartnerDao.add(crmPartner);
	}

	public void delete(String id) {
		crmPartnerDao.delete(id);
	}

	public void modify(CrmPartner crmPartner) {
		crmPartnerDao.modify(crmPartner);
	}

	public CrmPartner getById(String id) {
		return crmPartnerDao.getById(id);
	}
	
	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 15){
				continue;
			}
			CrmPartner filter = new CrmPartner();
			filter.setPartnerName(singleValue.get(1));
			if(crmPartnerDao.count(filter) == 0){
				CrmPartner crmPartner = new CrmPartner();
				crmPartner.setPartnerType(singleValue.get(0));
				crmPartner.setPartnerName(singleValue.get(1));
				crmPartner.setPartnerGrade(singleValue.get(2));
				crmPartner.setEngageScope(singleValue.get(3));
				crmPartner.setCoreAdvantage(singleValue.get(4));
				
				crmPartner.setAddress(singleValue.get(5));
				crmPartner.setCountry(singleValue.get(6));;
				crmPartner.setProvince(singleValue.get(7));
				crmPartner.setCity(singleValue.get(8));
				crmPartner.setContactPerson(singleValue.get(9));
				crmPartner.setContactPosition(singleValue.get(10));
				crmPartner.setContactPhone(singleValue.get(11));
				crmPartner.setContactMail(singleValue.get(12));
				
				User follower = userDao.getByDisplayName(singleValue.get(14));
				crmPartner.setOrgEntity(follower == null ? null : follower.getOrgEntity());
				crmPartner.setFollower(follower);
				crmPartnerDao.add(crmPartner);
			}
		}
	}
	@Override
	public List<List<String>> toExport(List<CrmPartner> crmPartners) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(CrmPartner crmPartner : crmPartners){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(crmPartner.getPartnerType());
			singleValue.add(crmPartner.getPartnerName());
			singleValue.add(crmPartner.getPartnerGrade());
			singleValue.add(crmPartner.getEngageScope());
			singleValue.add(crmPartner.getCoreAdvantage());
			
			singleValue.add(crmPartner.getAddress());
			singleValue.add(crmPartner.getCountry());
			singleValue.add(crmPartner.getProvince());
			singleValue.add(crmPartner.getCity());
			singleValue.add(crmPartner.getContactPerson());
			singleValue.add(crmPartner.getContactPosition());
			singleValue.add(crmPartner.getContactPhone());
			singleValue.add(crmPartner.getContactMail());
			
			singleValue.add(crmPartner.getOrgEntity() == null ? "" : crmPartner.getOrgEntity().getOrgEntityName());
			singleValue.add(crmPartner.getFollower() == null ? "" : crmPartner.getFollower().getDisplayName());
			values.add(singleValue);
		}
		
		return values;
	}
}

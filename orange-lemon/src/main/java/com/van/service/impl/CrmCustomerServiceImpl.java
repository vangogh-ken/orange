package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CrmCustomerDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.CrmCustomer;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.SpringSecurityUtil;
import com.van.service.CrmCustomerService;

@Transactional
@Service("crmCustomerService")
public class CrmCustomerServiceImpl implements CrmCustomerService {
	private static Logger logger = LoggerFactory.getLogger(CrmCustomerServiceImpl.class);
	@Autowired
	private CrmCustomerDao crmCustomerDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<CrmCustomer> getAll() {
		return crmCustomerDao.getAll();
	}

	public List<CrmCustomer> queryForList(CrmCustomer crmCustomer) {
		return crmCustomerDao.queryForList(crmCustomer);
	}

	public PageView query(PageView pageView, CrmCustomer crmCustomer) {
		List<CrmCustomer> list = crmCustomerDao.query(pageView, crmCustomer);
		pageView.setResults(list);
		return pageView;
	}

	public void add(CrmCustomer crmCustomer) {
		crmCustomerDao.add(crmCustomer);
	}

	public void delete(String id) {
		crmCustomerDao.delete(id);
	}

	public void modify(CrmCustomer crmCustomer) {
		crmCustomerDao.modify(crmCustomer);
	}

	public CrmCustomer getById(String id) {
		return crmCustomerDao.getById(id);
	}
	
	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 16 && singleValue.size() != 14){
				continue;
			}
			CrmCustomer filter = new CrmCustomer();
			filter.setCustomerName(singleValue.get(1));
			if(crmCustomerDao.count(filter) == 0){
				CrmCustomer crmCustomer = new CrmCustomer();
				crmCustomer.setCustomerType(singleValue.get(0));
				crmCustomer.setCustomerName(singleValue.get(1));
				crmCustomer.setCustomerGrade(singleValue.get(2));
				crmCustomer.setCreditGrade(singleValue.get(3));
				crmCustomer.setCargoCondition(singleValue.get(4));
				crmCustomer.setTransportType(singleValue.get(5));
				
				crmCustomer.setAddress(singleValue.get(6));
				crmCustomer.setCountry(singleValue.get(7));;
				crmCustomer.setProvince(singleValue.get(8));
				crmCustomer.setCity(singleValue.get(9));
				crmCustomer.setContactPerson(singleValue.get(10));
				crmCustomer.setContactPosition(singleValue.get(11));
				crmCustomer.setContactPhone(singleValue.get(12));
				crmCustomer.setContactMail(singleValue.get(13));
				
				User follower = userDao.getByDisplayName(singleValue.get(15));
				crmCustomer.setOrgEntity(follower == null ? null : follower.getOrgEntity());
				crmCustomer.setFollower(follower);
				crmCustomerDao.add(crmCustomer);
			}
		}
	}
	/*@Override
	public List<List<String>> toExport(List<CrmCustomer> crmCustomers) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(CrmCustomer crmCustomer : crmCustomers){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(crmCustomer.getCustomerType());
			singleValue.add(crmCustomer.getCustomerName());
			singleValue.add(crmCustomer.getCustomerGrade());
			singleValue.add(crmCustomer.getCreditGrade());
			singleValue.add(crmCustomer.getCargoCondition());
			singleValue.add(crmCustomer.getTransportType());
			
			singleValue.add(crmCustomer.getAddress());
			singleValue.add(crmCustomer.getCountry());
			singleValue.add(crmCustomer.getProvince());
			singleValue.add(crmCustomer.getCity());
			singleValue.add(crmCustomer.getContactPerson());
			singleValue.add(crmCustomer.getContactPosition());
			singleValue.add(crmCustomer.getContactPhone());
			singleValue.add(crmCustomer.getContactMail());
			
			singleValue.add(crmCustomer.getOrgEntity() == null ? "" : crmCustomer.getOrgEntity().getOrgEntityName());
			singleValue.add(crmCustomer.getFollower() == null ? "" : crmCustomer.getFollower().getDisplayName());
			values.add(singleValue);
		}
		return values;
	}*/

	@Override
	public boolean toApplyFollow(String[] crmCustomerIds) {
		boolean flag = true;
		for(String crmCustomerId : crmCustomerIds){
			CrmCustomer crmCustomer = crmCustomerDao.getById(crmCustomerId);
			if("公海".equals(crmCustomer.getCustomerType()) && "未跟进".equals(crmCustomer.getStatus())){
				crmCustomer.setStatus("申请中");
				User user = userDao.getByUserName(SpringSecurityUtil.getCurrentUserName());
				crmCustomer.setFollower(user);
				crmCustomer.setOrgEntity(user.getOrgEntity());
				crmCustomerDao.modify(crmCustomer);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean toAgreeFollow(String[] crmCustomerIds) {
		boolean flag = true;
		for(String crmCustomerId : crmCustomerIds){
			CrmCustomer crmCustomer = crmCustomerDao.getById(crmCustomerId);
			if("公海".equals(crmCustomer.getCustomerType()) && "申请中".equals(crmCustomer.getStatus())){
				crmCustomer.setCustomerType("跟进");
				crmCustomer.setStatus("已跟进");
				//User user = userDao.getByUserName(SpringSecurityUtil.getCurrentUserName());
				//crmCustomer.setFollower(user);
				//crmCustomer.setOrgEntity(user.getOrgEntity());
				crmCustomerDao.modify(crmCustomer);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean toRefuseFollow(String[] crmCustomerIds) {
		boolean flag = true;
		for(String crmCustomerId : crmCustomerIds){
			CrmCustomer crmCustomer = crmCustomerDao.getById(crmCustomerId);
			if(("公海".equals(crmCustomer.getCustomerType()) && "申请中".equals(crmCustomer.getStatus())) ||
					(("跟进".equals(crmCustomer.getCustomerType()) || "合作".equals(crmCustomer.getCustomerType())) && "已跟进".equals(crmCustomer.getStatus()))){
				crmCustomer.setCustomerType("公海");
				crmCustomer.setStatus("未跟进");
				User user = new User();
				user.setId("A");
				crmCustomer.setFollower(user);
				OrgEntity orgEntity = new OrgEntity();
				orgEntity.setId("A");
				crmCustomer.setOrgEntity(orgEntity);
				crmCustomerDao.modify(crmCustomer);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public List<List<String>> toBatchExport(String[] crmCustomerIds) {
		PageView pageView = new PageView(crmCustomerIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String crmCustomerId : crmCustomerIds){
			filterText.append("'" + crmCustomerId + "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<CrmCustomer> crmCustomers = crmCustomerDao.query(pageView, new CrmCustomer());
		List<List<String>> values = new ArrayList<List<String>>();
		for(CrmCustomer crmCustomer : crmCustomers){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(crmCustomer.getCustomerType());
			singleValue.add(crmCustomer.getCustomerName());
			singleValue.add(crmCustomer.getCustomerGrade());
			singleValue.add(crmCustomer.getCreditGrade());
			singleValue.add(crmCustomer.getCargoCondition());
			singleValue.add(crmCustomer.getTransportType());
			
			singleValue.add(crmCustomer.getAddress());
			singleValue.add(crmCustomer.getCountry());
			singleValue.add(crmCustomer.getProvince());
			singleValue.add(crmCustomer.getCity());
			singleValue.add(crmCustomer.getContactPerson());
			singleValue.add(crmCustomer.getContactPosition());
			singleValue.add(crmCustomer.getContactPhone());
			singleValue.add(crmCustomer.getContactMail());
			
			singleValue.add(crmCustomer.getOrgEntity() == null ? "" : crmCustomer.getOrgEntity().getOrgEntityName());
			singleValue.add(crmCustomer.getFollower() == null ? "" : crmCustomer.getFollower().getDisplayName());
			values.add(singleValue);
		}
		return values;
	}
}

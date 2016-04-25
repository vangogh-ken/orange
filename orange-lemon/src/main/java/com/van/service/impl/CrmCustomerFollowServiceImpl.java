package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.CrmCustomerDao;
import com.van.halley.db.persistence.CrmCustomerFollowDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.CrmCustomerFollow;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.SpringSecurityUtil;
import com.van.halley.util.StringUtil;
import com.van.service.CrmCustomerFollowService;

@Transactional
@Service("crmCustomerFollowService")
public class CrmCustomerFollowServiceImpl implements CrmCustomerFollowService {
	@Autowired
	private CrmCustomerFollowDao crmCustomerFollowDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CrmCustomerDao crmCustomerDao;

	public List<CrmCustomerFollow> getAll() {
		return crmCustomerFollowDao.getAll();
	}

	public List<CrmCustomerFollow> queryForList(CrmCustomerFollow crmCustomerFollow) {
		return crmCustomerFollowDao.queryForList(crmCustomerFollow);
	}

	public CrmCustomerFollow queryForOne(CrmCustomerFollow crmCustomerFollow) {
		return crmCustomerFollowDao.queryForOne(crmCustomerFollow);
	}

	public PageView query(PageView pageView, CrmCustomerFollow crmCustomerFollow) {
		List<CrmCustomerFollow> list = crmCustomerFollowDao.query(pageView, crmCustomerFollow);
		pageView.setResults(list);
		return pageView;
	}

	public void add(CrmCustomerFollow crmCustomerFollow) {
		crmCustomerFollowDao.add(crmCustomerFollow);
	}

	public void delete(String id) {
		crmCustomerFollowDao.delete(id);
	}

	public void modify(CrmCustomerFollow crmCustomerFollow) {
		crmCustomerFollowDao.modify(crmCustomerFollow);
	}

	public CrmCustomerFollow getById(String id) {
		return crmCustomerFollowDao.getById(id);
	}

	@Override
	public CrmCustomerFollow getLastByCrmCustomerId(String crmCustomerId, String follwerId) {
		return crmCustomerFollowDao.getLastByCrmCusotmerId(crmCustomerId, follwerId);
		/*
		 * CrmFollowCustomer filter = new CrmFollowCustomer();
		 * filter.setCrmCustomer(crmCustomerDao.getById(crmCustomerId));
		 * List<CrmFollowCustomer> list =
		 * crmFollowCustomerDao.queryForList(filter); if(list == null ||
		 * list.isEmpty()){ return null; }else{ return list.get(0); }
		 */
	}

	@Override
	public boolean toSuperiorSuggest(String[] crmCustomerFollowIds) {
		boolean flag = true;
		for (String crmCustomerFollowId : crmCustomerFollowIds) {
			CrmCustomerFollow crmCustomerFollow = crmCustomerFollowDao.getById(crmCustomerFollowId);
			if ("未提交".equals(crmCustomerFollow.getStatus())) {
				crmCustomerFollow.setStatus("上级建议");
				crmCustomerFollowDao.modify(crmCustomerFollow);
			} else {
				flag = false;
				break;
			}
		}

		if (!flag) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean toShepherSuggest(String[] crmCustomerFollowIds) {
		boolean flag = true;
		for (String crmCustomerFollowId : crmCustomerFollowIds) {
			CrmCustomerFollow crmCustomerFollow = crmCustomerFollowDao.getById(crmCustomerFollowId);
			// 处理人必须为当前跟进人的上级
			List<User> superiors = userDao.getDirectSuperior(crmCustomerFollow.getFollower().getId());
			String current = SpringSecurityUtil.getCurrentUserName();
			boolean isSuperior = false;
			for (User superior : superiors) {
				if (current.equals(superior.getUserName())) {
					isSuperior = true;
					break;
				}
			}
			if (isSuperior && "上级建议".equals(crmCustomerFollow.getStatus())) {
				crmCustomerFollow.setStatus("领导建议");
				crmCustomerFollowDao.modify(crmCustomerFollow);
			} else {
				flag = false;
				break;
			}
		}

		if (!flag) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean toFiling(String[] crmCustomerFollowIds) {
		boolean flag = true;
		for (String crmCustomerFollowId : crmCustomerFollowIds) {
			CrmCustomerFollow crmCustomerFollow = crmCustomerFollowDao.getById(crmCustomerFollowId);
			String current = SpringSecurityUtil.getCurrentUserName();
			if(current.equals(crmCustomerFollow.getFollower().getUserName()) 
					&& "上级建议".equals(crmCustomerFollow.getStatus())
					&& crmCustomerFollow.getChanceSuggest() != null 
					&& !"无".equals(crmCustomerFollow.getChanceSuggest())){
				crmCustomerFollow.setStatus("已归档");
				crmCustomerFollowDao.modify(crmCustomerFollow);
			}else {
				flag = false;
				break;
			}
			// 处理人必须为当前跟进人的领导
			/*List<User> shepherds = userDao.getDirectShepherd(crmCustomerFollow.getFollower().getId());
			String current = SpringSecurityUtil.getCurrentUserName();
			boolean isShepherd = false;
			for (User shepherd : shepherds) {
				if (current.equals(shepherd.getUserName())) {
					isShepherd = true;
					break;
				}
			}
			if (isShepherd && "领导建议".equals(crmCustomerFollow.getStatus())) {
				crmCustomerFollow.setStatus("已归档");
				crmCustomerFollowDao.modify(crmCustomerFollow);
			} else {
				flag = false;
				break;
			}*/
		}

		if (!flag) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public List<List<String>> toBatchExport(String[] crmCustomerFollowIds) {
		List<List<String>> values = new ArrayList<List<String>>();

		StringBuilder filterText = new StringBuilder();
		filterText.append(" ID IN(");
		for (String crmCustomerFollowId : crmCustomerFollowIds) {
			filterText.append("'" + crmCustomerFollowId + "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		PageView pageView = new PageView(crmCustomerFollowIds.length, 1);
		pageView.setFilterText(filterText.toString());
		List<CrmCustomerFollow> crmCustomerFollows = crmCustomerFollowDao.query(pageView, new CrmCustomerFollow());

		for (CrmCustomerFollow item : crmCustomerFollows) {
			List<String> singleValue = new ArrayList<String>();

			singleValue.add(item.getCrmCustomer().getCustomerName());
			singleValue.add(item.getContactPerson());
			singleValue.add(item.getContactPosition());
			singleValue.add(item.getContactPhone());
			singleValue.add(StringUtil.parseDateTime(item.getCurrentFollowTime()));
			singleValue.add(StringUtil.parseDateTime(item.getLastFollowTime()));
			singleValue.add(StringUtil.parseDateTime(item.getNextFollowTime()));
			singleValue.add(item.getFollowContent());
			singleValue.add(item.getChancePlan());
			singleValue.add(item.getChanceSuggest());
			values.add(singleValue);
		}

		return values;
	}
}

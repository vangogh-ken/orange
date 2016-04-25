package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CrmCustomer;

public interface CrmCustomerService {
	public List<CrmCustomer> getAll();

	public List<CrmCustomer> queryForList(CrmCustomer crmCustomer);

	public PageView query(PageView pageView, CrmCustomer crmCustomer);

	public void add(CrmCustomer crmCustomer);

	public void delete(String id);

	public void modify(CrmCustomer crmCustomer);

	public CrmCustomer getById(String id);
	
	public void toImport(List<List<String>> values);

	/**
	 * 申请跟进
	 * @param crmCustomerIds
	 * @return
	 */
	public boolean toApplyFollow(String[] crmCustomerIds);

	/**
	 * 同意跟进
	 * @param crmCustomerIds
	 * @return
	 */
	public boolean toAgreeFollow(String[] crmCustomerIds);

	/**
	 * 拒绝跟进
	 * @param crmCustomerIds
	 * @return
	 */
	public boolean toRefuseFollow(String[] crmCustomerIds);
	
	//public List<List<String>> toExport(List<CrmCustomer> crmCustomers);

	public List<List<String>> toBatchExport(String[] selectedItem);
}

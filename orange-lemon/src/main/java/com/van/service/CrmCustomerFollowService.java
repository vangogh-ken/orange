package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CrmCustomerFollow;

public interface CrmCustomerFollowService {
	public List<CrmCustomerFollow> getAll();

	public List<CrmCustomerFollow> queryForList(CrmCustomerFollow crmCustomerFollow);

	public CrmCustomerFollow queryForOne(CrmCustomerFollow crmCustomerFollow);

	public PageView query(PageView pageView, CrmCustomerFollow crmCustomerFollow);

	public void add(CrmCustomerFollow crmCustomerFollow);

	public void delete(String id);

	public void modify(CrmCustomerFollow crmCustomerFollow);

	public CrmCustomerFollow getById(String id);
	
	/**
	 * 获取最近一次的跟进信息
	 * @param crmPartnerId
	 * @return
	 */
	public CrmCustomerFollow getLastByCrmCustomerId(String crmCustomerId, String follwerId);

	/**
	 * 提交上级建议 状态：  未提交>上级建议
	 * @param crmFollowCustomerIds
	 * @return
	 */
	public boolean toSuperiorSuggest(String[] crmCustomerFollowIds);

	/**
	 * 提交领导建议 状态： 上级建议>领导建议
	 * @param crmFollowCustomerIds
	 * @return
	 */
	public boolean toShepherSuggest(String[] crmCustomerFollowIds);

	/**
	 * 提交归档 状态： 领导建议>已归档
	 * @param crmFollowCustomerIds
	 * @return
	 */
	public boolean toFiling(String[] crmCustomerFollowIds);

	/**
	 * 批量导出
	 * @param crmFollowCustomerIds
	 * @return
	 */
	public List<List<String>> toBatchExport(String[] crmCustomerFollowIds);
}

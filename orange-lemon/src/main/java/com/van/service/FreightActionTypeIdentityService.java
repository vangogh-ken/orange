package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;

public interface FreightActionTypeIdentityService {
	public List<FreightActionTypeIdentity> getAll();

	public List<FreightActionTypeIdentity> queryForList(
			FreightActionTypeIdentity freightActionTypeIdentity);

	public PageView query(PageView pageView,
			FreightActionTypeIdentity freightActionTypeIdentity);

	public void add(FreightActionTypeIdentity freightActionTypeIdentity);

	public void delete(String id);

	public void modify(FreightActionTypeIdentity freightActionTypeIdentity);

	public FreightActionTypeIdentity getById(String id);
	/**
	 * 根据ActionTypeId全部删除
	 * @param freightActionTypeId
	 */
	public void deleteByFreightActionTypeId(String freightActionTypeId);
	/**
	 * 根据ActionTypeId获取唯一
	 * @param id
	 * @return
	 */
	public FreightActionTypeIdentity getByFreightActionTypeId(String freightActionTypeId);
	
}

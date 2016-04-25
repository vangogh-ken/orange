package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightMaintainAction;

public interface FreightMaintainActionService {
	public List<FreightMaintainAction> getAll();

	public List<FreightMaintainAction> queryForList(
			FreightMaintainAction freightMaintainAction);

	public PageView query(PageView pageView,
			FreightMaintainAction freightMaintainAction);

	public void add(FreightMaintainAction freightMaintainAction);

	public void delete(String id);

	public void modify(FreightMaintainAction freightMaintainAction);

	public FreightMaintainAction getById(String id);
	/**
	 * 根据操作类型ID和动作类型ID删除
	 * @param freightMaintainTypeId
	 * @param freightActionTypeId
	 */
	public void deleteByMaintainAndAction(String freightMaintainTypeId, String freightActionTypeId);
	/**
	 * 批量增加,只加载一次缓存,提高响应速度
	 * @param list
	 */
	public void batchAdd(List<FreightMaintainAction> list);
	/**
	 * 批量删除,只加载一次缓存,提高响应速度
	 * @param list
	 */
	public void batchDelete(List<FreightMaintainAction> list);
	/**
	 * 批量删除,只加载一次缓存,提高响应速度
	 * @param list
	 */
	public void batchDeleteByMaintainAndAction(List<FreightMaintainAction> list);
}

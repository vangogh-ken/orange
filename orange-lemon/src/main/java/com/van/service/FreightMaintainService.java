package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightMaintain;

public interface FreightMaintainService {
	public List<FreightMaintain> getAll();

	public List<FreightMaintain> queryForList(FreightMaintain freightMaintain);

	public PageView query(PageView pageView, FreightMaintain freightMaintain);

	public void add(FreightMaintain freightMaintain);

	public void delete(String id);

	public void modify(FreightMaintain freightMaintain);

	public FreightMaintain getById(String id);
	
	/**
	 * 删除操作范围并删除相关动作，更新其他操作范围的顺序
	 * @param freightMaintainIds
	 */
	public boolean doneRemoveMaintain(String[] freightMaintainIds);

	/**
	 * 批量添加操作范围
	 * @param freightMaintain
	 * @param freightOrderId
	 * @param parentMaintainId
	 * @param freightMaintainTypeIds
	 */
	public void doneAddMaintain(FreightMaintain freightMaintain, String freightOrderId, String parentMaintainId,
			String[] freightMaintainTypeIds);
}

package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigBasis;

public interface BpmConfigBasisService {
	public List<BpmConfigBasis> getAll();

	public List<BpmConfigBasis> queryForList(BpmConfigBasis bpmConfigBasis);

	public BpmConfigBasis queryForOne(BpmConfigBasis bpmConfigBasis);

	public PageView query(PageView pageView, BpmConfigBasis bpmConfigBasis);

	public void add(BpmConfigBasis bpmConfigBasis);

	public void delete(String id);

	public void modify(BpmConfigBasis bpmConfigBasis);

	public BpmConfigBasis getById(String id);
	/**
	 * 通过流程KEY获取对应配置
	 * @param bpmKey
	 * @return
	 */
	public BpmConfigBasis getByBpmKey(String bpmKey);
	/**
	 * 获取角色关联配置
	 * @param roleId
	 * @return
	 */
	public List<BpmConfigBasis> getByRoleId(String roleId);
	/**
	 * 获取个人关联的配置
	 * @param userId
	 * @return
	 */
	public List<BpmConfigBasis> getByUserId(String userId);
}

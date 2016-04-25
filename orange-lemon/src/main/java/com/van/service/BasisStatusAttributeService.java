package com.van.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisStatusAttribute;

public interface BasisStatusAttributeService {
	public List<BasisStatusAttribute> getAll();

	public List<BasisStatusAttribute> queryForList(
			BasisStatusAttribute basisStatusAttribute);

	public BasisStatusAttribute queryForOne(
			BasisStatusAttribute basisStatusAttribute);

	public PageView query(PageView pageView,
			BasisStatusAttribute basisStatusAttribute);

	public void add(BasisStatusAttribute basisStatusAttribute);

	public void delete(String id);

	public void modify(BasisStatusAttribute basisStatusAttribute);

	public BasisStatusAttribute getById(String id);

	/**
	 * 配置状态属性
	 * @param basisStatusId
	 * @return
	 */
	public Map<String, Object> toConfigAttribute(String basisStatusId);
	/**
	 * 保存配置信息
	 * @param request
	 */
	public void doneConfigAttribute(String basisStatusId, HttpServletRequest request);
}

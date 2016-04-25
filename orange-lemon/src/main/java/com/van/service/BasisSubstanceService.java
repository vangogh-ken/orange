package com.van.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.BasisSubstanceType;

public interface BasisSubstanceService {
	public List<BasisSubstance> getAll();

	public List<BasisSubstance> queryForList(BasisSubstance basisSubstance);

	public BasisSubstance queryForOne(BasisSubstance basisSubstance);

	public PageView<BasisSubstance> query(PageView<BasisSubstance> pageView, BasisSubstance basisSubstance);

	public void add(BasisSubstance basisSubstance);

	public void delete(String id);

	public void modify(BasisSubstance basisSubstance);

	public BasisSubstance getById(String id);
	
	//******************操作时各种行为*********************//
	/**
	 * 返回数据ID
	 * @return
	 */
	public String toAddBpm(BasisSubstanceType basisSubstanceType, String status);
	/**
	 * 创建之前
	 */
	public void doBeforeCreateBehaviors(String id);
	/**
	 * 创建之后
	 */
	public void doAfterCreateBehaviors(String id);
	/**
	 * 修改之前
	 */
	public void doBeforeModifyBehaviors(String id);
	/**
	 * 修改之后
	 */
	public void doAfterModifyBehaviors(String id);
	/**
	 * 删除之前
	 */
	public void doBeforeDeleteBehaviors(String id);
	/**
	 * 删除之后
	 */
	public void doAfterDeleteBehaviors(String id);
	/**
	 * 执行sql行为
	 */
	public void doBehaviors(String id, String sqls);
	/**
	 * 获取值
	 * @param businessKey
	 * @return
	 */
	public Map<String, Object> getBasisValueMap(String basisSubstanceId);
	/**
	 * 根据类型获取唯一的指对
	 * @param basisSubstanceTypeId
	 * @param filterText
	 * @return
	 */
	public Map<String, Object> getBasisValueMapSingle(String basisSubstanceTypeId, String filterText);
	/**
	 * 根据类型获取值对集合
	 * @param basisSubstanceTypeId
	 * @param filterText
	 * @return
	 */
	public List<Map<String, Object>> getBasisValueMapList(String basisSubstanceTypeId, String filterText);
	/**
	 * 保存填报信息
	 * @param basisSubstanceId
	 * @param request
	 */
	public boolean donePrimeSubstance(String basisSubstanceId, HttpServletRequest request);
}

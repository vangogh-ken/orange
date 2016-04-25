package com.van.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightPart;

public interface FreightPartService {
	/**
	 * 单位缓存
	 * key为null时取全部数据, 
	 * key或者为各种费用类型ID,取在成本中有此费用的单位
	 */
	public static Map<String, List<FreightPart>> cacheFreightPart = new HashMap<String, List<FreightPart>>();
	
	public List<FreightPart> getAll();

	public List<FreightPart> queryForList(FreightPart freightPart);

	public PageView query(PageView pageView, FreightPart freightPart);

	public void add(FreightPart freightPart);

	public void delete(String id);

	public void modify(FreightPart freightPart);

	public FreightPart getById(String id);
	//刷新缓存
	public void refreshCache();
	/**
	 * 通过组织机构Id获取映射的freightPart
	 * @return
	 */
	public FreightPart getByOrgEntityId(String orgEntityId);

	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FreightPart> freightParts);

	/**
	 * 根据名称获取
	 * @param partName
	 * @return
	 */
	public FreightPart getByPartName(String partName);
}

package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightExpenseType;

public interface FreightExpenseTypeService {
	public List<FreightExpenseType> getAll();

	public List<FreightExpenseType> queryForList(
			FreightExpenseType freightExpenseType);

	public FreightExpenseType queryForOne(FreightExpenseType freightExpenseType);

	public PageView<FreightExpenseType> query(PageView<FreightExpenseType> pageView,
			FreightExpenseType freightExpenseType);

	public void add(FreightExpenseType freightExpenseType);

	public void delete(String id);

	public void modify(FreightExpenseType freightExpenseType);

	public FreightExpenseType getById(String id);
	/**
	 * 批量删除，缓存只刷新一次提高响应速度
	 * @param freightExpenseType
	 */
	public void remove(String[] freightExpenseTypeIds);

	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FreightExpenseType> freightExpenseTypes);

	/**
	 * 根据名称获取
	 * @param typeName
	 * @return
	 */
	public FreightExpenseType getByTypeName(String typeName);
}

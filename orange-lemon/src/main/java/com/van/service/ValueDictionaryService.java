package com.van.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ValueDictionary;

public interface ValueDictionaryService {
	public static Map<String, List<ValueDictionary>> cacheDictionary = new HashMap<String, List<ValueDictionary>>();
	
	public List<ValueDictionary> getAll();

	public List<ValueDictionary> queryForList(ValueDictionary valueDictionary);

	public PageView query(PageView pageView, ValueDictionary valueDictionary);

	public void add(ValueDictionary valueDictionary);

	public void delete(String id);

	public void modify(ValueDictionary valueDictionary);

	public ValueDictionary getById(String id);
	
	/**
	 * 通过配置的valueClass获取
	 * @param valueClassId
	 * @param valueColumn
	 * @return
	 */
	public List<ValueDictionary> getByValueClassId(String valueClassId, String valueColumn, String vFilterId);
	/**
	 * 刷新缓存
	 */
	public void refreshCache();
	/**
	 * 按属性批量添加值
	 * @param values
	 * @param valueAttributeId
	 */
	public void batchAddDictionary(List<String> values, String valueAttributeId);
	/**
	 * 批量删除
	 * @param valueDictionaryId
	 */
	public void batchDeleteDictionary(String[] valueDictionaryIds);
	/**
	 * 所有批量添加
	 * @param values
	 */
	public void batchAddDictionary(List<List<String>> values);
}

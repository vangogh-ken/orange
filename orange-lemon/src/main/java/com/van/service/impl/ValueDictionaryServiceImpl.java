package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ValueAttributeDao;
import com.van.halley.db.persistence.ValueClassDao;
import com.van.halley.db.persistence.ValueDictionaryDao;
import com.van.halley.db.persistence.ValueFilterDao;
import com.van.halley.db.persistence.entity.ValueAttribute;
import com.van.halley.db.persistence.entity.ValueClass;
import com.van.halley.db.persistence.entity.ValueDictionary;
import com.van.halley.db.persistence.entity.ValueFilter;
import com.van.halley.util.StringUtil;
import com.van.service.ValueDictionaryService;

/**
 * @author Think
 * 使用缓存
 */
@Transactional
@Service("valueDictionaryService")
public class ValueDictionaryServiceImpl implements ValueDictionaryService {
	private static Logger logger = LoggerFactory.getLogger(ValueDictionaryServiceImpl.class);
	@Autowired
	private ValueDictionaryDao valueDictionaryDao;
	@Autowired
	private ValueAttributeDao valueAttributeDao;
	@Autowired
	private ValueFilterDao valueFilterDao;
	@Autowired
	private ValueClassDao valueClassDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void init(){
		refreshCache();
	}
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		List<ValueAttribute> valueAttributes = valueAttributeDao.getAll();
		ValueDictionary filter = new ValueDictionary();
		for(ValueAttribute valueAttribute : valueAttributes){
			filter.setValueAttribute(valueAttribute); 
			cacheDictionary.put(valueAttribute.getId(), valueDictionaryDao.queryForList(filter));
		}
		
		logger.info("Dictionary cache loaded!");
	}
	

	public List<ValueDictionary> getAll() {
		return valueDictionaryDao.getAll();
	}

	public List<ValueDictionary> queryForList(ValueDictionary valueDictionary) {
		return valueDictionaryDao.queryForList(valueDictionary);
	}

	public PageView query(PageView pageView, ValueDictionary valueDictionary) {
		List<ValueDictionary> list = valueDictionaryDao.query(pageView,
				valueDictionary);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ValueDictionary valueDictionary) {
		valueDictionaryDao.add(valueDictionary);
		refreshCache();
	}

	public void delete(String id) {
		valueDictionaryDao.delete(id);
		refreshCache();
	}

	public void modify(ValueDictionary valueDictionary) {
		valueDictionaryDao.modify(valueDictionary);
		refreshCache();
	}

	public ValueDictionary getById(String id) {
		return valueDictionaryDao.getById(id);
	}
	
	@Override
	public List<ValueDictionary> getByValueClassId(String valueClassId, String valueColumn, String vFilterId) {
		ValueClass valueClass = valueClassDao.getById(valueClassId);
		valueClass.getTableName();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT ");
		sql.append(valueColumn);
		sql.append(" FROM ");
		sql.append(valueClass.getTableName());
		if(!StringUtil.isNullOrEmpty(vFilterId)){
			ValueFilter valueFilter = valueFilterDao.getById(vFilterId);
			if(valueFilter.getValueClass().getId().equals(valueClass.getId())){
				sql.append(" WHERE " + valueFilter.getFilterText());
			}else{
				logger.error("取值类型与过滤条件不符，取消过滤。valueClassId：{},  vFilterId: {}" , valueClassId, vFilterId);
			}
		}
		List<String> values = jdbcTemplate.queryForList(sql.toString(), String.class);
		List<ValueDictionary> list = new ArrayList<ValueDictionary>();
		for(String value : values){
			ValueDictionary valueDictionary = new ValueDictionary();
			valueDictionary.setValueContent(value);
			list.add(valueDictionary);
		}
		return list;
	}
	
	@Override
	public void batchAddDictionary(List<String> values, String valueAttributeId) {
		Assert.notNull(valueAttributeId);
		ValueAttribute valueAttribute = valueAttributeDao.getById(valueAttributeId);
		int count = ValueDictionaryService.cacheDictionary.get(valueAttributeId).size() + 1;
		for(String valueContent : values){
			ValueDictionary value = new ValueDictionary();
			value.setValueContent(valueContent);
			value.setValueAttribute(valueAttribute);
			value.setStatus("已启用");
			value.setDisplayIndex(count);
			valueDictionaryDao.add(value);
			count++;
		}
		refreshCache();
	}
	@Override
	public void batchDeleteDictionary(String[] valueDictionaryIds) {
		Assert.notNull(valueDictionaryIds);
		for(String valueDictionaryId : valueDictionaryIds){
			valueDictionaryDao.delete(valueDictionaryId);
		}
		refreshCache();
	}
	
	@Override
	public void batchAddDictionary(List<List<String>> values) {
		int count = 1;
		for(List<String> value : values){
			ValueDictionary valueDictionary = new ValueDictionary();
			valueDictionary.setValueContent(value.get(0));
			valueDictionary.setValueAttribute(valueAttributeDao.getById(value.get(1)));
			valueDictionary.setStatus("已启用");
			valueDictionary.setDisplayIndex(count);
			valueDictionaryDao.add(valueDictionary);
			count++;
		}
		
	}
}

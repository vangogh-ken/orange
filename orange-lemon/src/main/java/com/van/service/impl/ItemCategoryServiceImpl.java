package com.van.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.db.persistence.ItemAttributeDao;
import com.van.halley.db.persistence.ItemCategoryDao;
import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.halley.db.persistence.entity.ItemCategory;
import com.van.service.ItemCategoryService;
import com.van.halley.core.page.PageView;

@Transactional
@Service("itemCategoryService")
public class ItemCategoryServiceImpl implements ItemCategoryService {
	@Autowired
	private ItemCategoryDao itemCategoryDao;
	@Autowired
	private ItemAttributeDao itemAttributeDao;

	public List<ItemCategory> getAll() {
		return itemCategoryDao.getAll();
	}

	public List<ItemCategory> queryForList(ItemCategory itemCategory) {
		return itemCategoryDao.queryForList(itemCategory);
	}

	public int count(ItemCategory itemCategory) {
		return itemCategoryDao.count(itemCategory);
	}

	public ItemCategory queryForOne(ItemCategory itemCategory) {
		return itemCategoryDao.queryForOne(itemCategory);
	}

	public PageView<ItemCategory> query(PageView<ItemCategory> pageView, ItemCategory itemCategory) {
		List<ItemCategory> list = itemCategoryDao.query(pageView, itemCategory);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ItemCategory itemCategory) {
		itemCategoryDao.add(itemCategory);
	}

	public void delete(String id) {
		itemCategoryDao.delete(id);
	}

	public void modify(ItemCategory itemCategory) {
		itemCategoryDao.modify(itemCategory);
	}

	public ItemCategory getById(String id) {
		return itemCategoryDao.getById(id);
	}

	@Override
	public Map<String, Object> toAddAttribute(String itemCategoryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ItemAttribute filter = new ItemAttribute();
		filter.setItemCategoryId(itemCategoryId);
		map.put("ItemAttributes", itemAttributeDao.queryForList(filter));
		return map;
	}

	@Override
	public Map<String, Object> toReviseAttribute(String itemAttributeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ItemAttribute filter = new ItemAttribute();
		filter.setItemCategoryId(itemAttributeDao.getById(itemAttributeId).getItemCategoryId());
		map.put("itemAttributes", itemAttributeDao.queryForList(filter));
		map.put("itemAttribute", itemAttributeDao.getById(itemAttributeId));
		return map;
	}

	@Override
	public void doneRemoveAttribute(String[] itemAttributeIds) {
		for(String id : itemAttributeIds){
			itemAttributeDao.delete(id);
		}
		
	}

	@Override
	public void doneAddAttribute(ItemAttribute itemAttribute, String itemAttributeId) {
		if(StringUtils.isNotBlank(itemAttributeId)){
			itemAttribute.setId(itemAttributeId);
			itemAttributeDao.modify(itemAttribute);
		}else{
			itemAttributeDao.add(itemAttribute);
		}
		
	}
}

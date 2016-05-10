package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.halley.db.persistence.entity.ItemCategory;
import com.van.halley.core.page.PageView;

public interface ItemCategoryService {
	public List<ItemCategory> getAll();

	public List<ItemCategory> queryForList(ItemCategory itemCategory);

	public int count(ItemCategory itemCategory);

	public ItemCategory queryForOne(ItemCategory itemCategory);

	public PageView<ItemCategory> query(PageView<ItemCategory> pageView, ItemCategory itemCategory);

	public void add(ItemCategory itemCategory);

	public void delete(String id);

	public void modify(ItemCategory itemCategory);

	public ItemCategory getById(String id);

	public Map<String, Object> toAddAttribute(String itemCategoryId);

	public Map<String, Object> toReviseAttribute(String itemAttributeId);

	public void doneRemoveAttribute(String[] itemAttributeIds);

	public void doneAddAttribute(ItemAttribute itemAttribute, String itemAttributeId);
}

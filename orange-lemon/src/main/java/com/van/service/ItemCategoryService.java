package com.van.service;
import java.util.List;
import com.van.halley.db.persistence.entity.ItemCategory;
import com.van.halley.core.page.PageView;
public interface ItemCategoryService{
public List<ItemCategory> getAll();
public List<ItemCategory> queryForList(ItemCategory itemCategory);
public int count(ItemCategory itemCategory);
public ItemCategory queryForOne(ItemCategory itemCategory);
public PageView<ItemCategory>  query(PageView<ItemCategory>  pageView,ItemCategory itemCategory);
public void add(ItemCategory itemCategory);
public void delete(String id);
public void modify(ItemCategory itemCategory);
public ItemCategory getById(String id);
}

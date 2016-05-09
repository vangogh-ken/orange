package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.van.halley.db.persistence.ItemCategoryDao;
import com.van.halley.db.persistence.entity.ItemCategory;
import com.van.service.ItemCategoryService;
import com.van.halley.core.page.PageView;
@Transactional
@Service("itemCategoryService")
public class ItemCategoryServiceImpl implements ItemCategoryService {
@Autowired
private ItemCategoryDao itemCategoryDao;
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
public PageView<ItemCategory>  query(PageView<ItemCategory>  pageView, ItemCategory itemCategory) {
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
}

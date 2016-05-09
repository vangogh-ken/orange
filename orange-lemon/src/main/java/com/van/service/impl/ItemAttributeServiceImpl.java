package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.van.halley.db.persistence.ItemAttributeDao;
import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.service.ItemAttributeService;
import com.van.halley.core.page.PageView;
@Transactional
@Service("itemAttributeService")
public class ItemAttributeServiceImpl implements ItemAttributeService {
@Autowired
private ItemAttributeDao itemAttributeDao;
public List<ItemAttribute> getAll() {
return itemAttributeDao.getAll();
}
public List<ItemAttribute> queryForList(ItemAttribute itemAttribute) { 
return itemAttributeDao.queryForList(itemAttribute); 
}
public int count(ItemAttribute itemAttribute) { 
return itemAttributeDao.count(itemAttribute); 
}
public ItemAttribute queryForOne(ItemAttribute itemAttribute) { 
return itemAttributeDao.queryForOne(itemAttribute); 
}
public PageView<ItemAttribute>  query(PageView<ItemAttribute>  pageView, ItemAttribute itemAttribute) {
List<ItemAttribute> list = itemAttributeDao.query(pageView, itemAttribute);
pageView.setResults(list);
return pageView;
}
public void add(ItemAttribute itemAttribute) {
itemAttributeDao.add(itemAttribute);
}
public void delete(String id) {
itemAttributeDao.delete(id);
}
public void modify(ItemAttribute itemAttribute) {
itemAttributeDao.modify(itemAttribute);
}
public ItemAttribute getById(String id) {
return itemAttributeDao.getById(id);
}
}

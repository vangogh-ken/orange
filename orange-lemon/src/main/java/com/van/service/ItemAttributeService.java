package com.van.service;
import java.util.List;
import com.van.halley.db.persistence.entity.ItemAttribute;
import com.van.halley.core.page.PageView;
public interface ItemAttributeService{
public List<ItemAttribute> getAll();
public List<ItemAttribute> queryForList(ItemAttribute itemAttribute);
public int count(ItemAttribute itemAttribute);
public ItemAttribute queryForOne(ItemAttribute itemAttribute);
public PageView<ItemAttribute>  query(PageView<ItemAttribute>  pageView,ItemAttribute itemAttribute);
public void add(ItemAttribute itemAttribute);
public void delete(String id);
public void modify(ItemAttribute itemAttribute);
public ItemAttribute getById(String id);
}

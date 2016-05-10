package com.van.service;
import java.util.List;
import com.van.halley.db.persistence.entity.ItemImage;
import com.van.halley.core.page.PageView;
public interface ItemImageService{
public List<ItemImage> getAll();
public List<ItemImage> queryForList(ItemImage itemImage);
public int count(ItemImage itemImage);
public ItemImage queryForOne(ItemImage itemImage);
public PageView<ItemImage>  query(PageView<ItemImage>  pageView,ItemImage itemImage);
public void add(ItemImage itemImage);
public void delete(String id);
public void modify(ItemImage itemImage);
public ItemImage getById(String id);
}

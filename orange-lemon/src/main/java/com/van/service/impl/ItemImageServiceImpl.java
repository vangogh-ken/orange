package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.van.halley.db.persistence.ItemImageDao;
import com.van.halley.db.persistence.entity.ItemImage;
import com.van.service.ItemImageService;
import com.van.halley.core.page.PageView;
@Transactional
@Service("itemImageService")
public class ItemImageServiceImpl implements ItemImageService {
@Autowired
private ItemImageDao itemImageDao;
public List<ItemImage> getAll() {
return itemImageDao.getAll();
}
public List<ItemImage> queryForList(ItemImage itemImage) { 
return itemImageDao.queryForList(itemImage); 
}
public int count(ItemImage itemImage) { 
return itemImageDao.count(itemImage); 
}
public ItemImage queryForOne(ItemImage itemImage) { 
return itemImageDao.queryForOne(itemImage); 
}
public PageView<ItemImage>  query(PageView<ItemImage>  pageView, ItemImage itemImage) {
List<ItemImage> list = itemImageDao.query(pageView, itemImage);
pageView.setResults(list);
return pageView;
}
public void add(ItemImage itemImage) {
itemImageDao.add(itemImage);
}
public void delete(String id) {
itemImageDao.delete(id);
}
public void modify(ItemImage itemImage) {
itemImageDao.modify(itemImage);
}
public ItemImage getById(String id) {
return itemImageDao.getById(id);
}
}

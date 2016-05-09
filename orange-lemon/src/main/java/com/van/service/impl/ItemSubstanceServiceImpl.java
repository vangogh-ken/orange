package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.van.halley.db.persistence.ItemSubstanceDao;
import com.van.halley.db.persistence.entity.ItemSubstance;
import com.van.service.ItemSubstanceService;
import com.van.halley.core.page.PageView;
@Transactional
@Service("itemSubstanceService")
public class ItemSubstanceServiceImpl implements ItemSubstanceService {
@Autowired
private ItemSubstanceDao itemSubstanceDao;
public List<ItemSubstance> getAll() {
return itemSubstanceDao.getAll();
}
public List<ItemSubstance> queryForList(ItemSubstance itemSubstance) { 
return itemSubstanceDao.queryForList(itemSubstance); 
}
public int count(ItemSubstance itemSubstance) { 
return itemSubstanceDao.count(itemSubstance); 
}
public ItemSubstance queryForOne(ItemSubstance itemSubstance) { 
return itemSubstanceDao.queryForOne(itemSubstance); 
}
public PageView<ItemSubstance>  query(PageView<ItemSubstance>  pageView, ItemSubstance itemSubstance) {
List<ItemSubstance> list = itemSubstanceDao.query(pageView, itemSubstance);
pageView.setResults(list);
return pageView;
}
public void add(ItemSubstance itemSubstance) {
itemSubstanceDao.add(itemSubstance);
}
public void delete(String id) {
itemSubstanceDao.delete(id);
}
public void modify(ItemSubstance itemSubstance) {
itemSubstanceDao.modify(itemSubstance);
}
public ItemSubstance getById(String id) {
return itemSubstanceDao.getById(id);
}
}

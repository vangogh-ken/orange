package com.van.service;
import java.util.List;
import com.van.halley.db.persistence.entity.ItemSubstance;
import com.van.halley.core.page.PageView;
public interface ItemSubstanceService{
public List<ItemSubstance> getAll();
public List<ItemSubstance> queryForList(ItemSubstance itemSubstance);
public int count(ItemSubstance itemSubstance);
public ItemSubstance queryForOne(ItemSubstance itemSubstance);
public PageView<ItemSubstance>  query(PageView<ItemSubstance>  pageView,ItemSubstance itemSubstance);
public void add(ItemSubstance itemSubstance);
public void delete(String id);
public void modify(ItemSubstance itemSubstance);
public ItemSubstance getById(String id);
}

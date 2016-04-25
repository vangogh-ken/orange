package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ValueFilter;
public interface ValueFilterService{
public List<ValueFilter> getAll();
public List<ValueFilter> queryForList(ValueFilter valueFilter);
public ValueFilter queryForOne(ValueFilter valueFilter);
public PageView query(PageView pageView,ValueFilter valueFilter);
public void add(ValueFilter valueFilter);
public void delete(String id);
public void modify(ValueFilter valueFilter);
public ValueFilter getById(String id);
}

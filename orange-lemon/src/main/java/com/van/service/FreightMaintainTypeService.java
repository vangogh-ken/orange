package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightMaintainType;
public interface FreightMaintainTypeService{
public List<FreightMaintainType> getAll();
public List<FreightMaintainType> queryForList(FreightMaintainType freightMaintainType);public PageView query(PageView pageView,FreightMaintainType freightMaintainType);
public void add(FreightMaintainType freightMaintainType);
public void delete(String id);
public void modify(FreightMaintainType freightMaintainType);
public FreightMaintainType getById(String id);
}

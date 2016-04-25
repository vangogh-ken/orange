package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightActionBox;
public interface FreightActionBoxService{
public List<FreightActionBox> getAll();
public List<FreightActionBox> queryForList(FreightActionBox freightActionBox);
public FreightActionBox queryForOne(FreightActionBox freightActionBox);
public PageView query(PageView pageView,FreightActionBox freightActionBox);
public void add(FreightActionBox freightActionBox);
public void delete(String id);
public void modify(FreightActionBox freightActionBox);
public FreightActionBox getById(String id);
}

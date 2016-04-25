package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightExpenseBox;
public interface FreightExpenseBoxService{
public List<FreightExpenseBox> getAll();
public List<FreightExpenseBox> queryForList(FreightExpenseBox freightExpenseBox);
public FreightExpenseBox queryForOne(FreightExpenseBox freightExpenseBox);
public PageView query(PageView pageView,FreightExpenseBox freightExpenseBox);
public void add(FreightExpenseBox freightExpenseBox);
public void delete(String id);
public void modify(FreightExpenseBox freightExpenseBox);
public FreightExpenseBox getById(String id);
}

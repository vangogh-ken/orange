package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightDataTemplateActionValue;
public interface FreightDataTemplateActionValueService{
public List<FreightDataTemplateActionValue> getAll();
public List<FreightDataTemplateActionValue> queryForList(FreightDataTemplateActionValue freightDataTemplateActionValue);
public FreightDataTemplateActionValue queryForOne(FreightDataTemplateActionValue freightDataTemplateActionValue);
public PageView query(PageView pageView,FreightDataTemplateActionValue freightDataTemplateActionValue);
public void add(FreightDataTemplateActionValue freightDataTemplateActionValue);
public void delete(String id);
public void modify(FreightDataTemplateActionValue freightDataTemplateActionValue);
public FreightDataTemplateActionValue getById(String id);
}

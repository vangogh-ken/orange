package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BpmConfigCategory;
public interface BpmConfigCategoryService{
public List<BpmConfigCategory> getAll();
public List<BpmConfigCategory> queryForList(BpmConfigCategory bpmConfigCategory);public PageView query(PageView pageView,BpmConfigCategory bpmConfigCategory);
public void add(BpmConfigCategory bpmConfigCategory);
public void delete(String id);
public void modify(BpmConfigCategory bpmConfigCategory);
public BpmConfigCategory getById(String id);
}

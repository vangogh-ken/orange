package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CmsCatalog;
public interface CmsCatalogService{
public List<CmsCatalog> getAll();
public List<CmsCatalog> queryForList(CmsCatalog cmsCatalog);public PageView query(PageView pageView,CmsCatalog cmsCatalog);
public void add(CmsCatalog cmsCatalog);
public void delete(String id);
public void modify(CmsCatalog cmsCatalog);
public CmsCatalog getById(String id);
}

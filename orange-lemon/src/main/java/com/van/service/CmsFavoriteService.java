package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CmsFavorite;
public interface CmsFavoriteService{
public List<CmsFavorite> getAll();
public List<CmsFavorite> queryForList(CmsFavorite cmsFavorite);public PageView query(PageView pageView,CmsFavorite cmsFavorite);
public void add(CmsFavorite cmsFavorite);
public void delete(String id);
public void modify(CmsFavorite cmsFavorite);
public CmsFavorite getById(String id);
}

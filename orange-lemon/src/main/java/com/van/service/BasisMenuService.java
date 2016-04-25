package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisMenu;
public interface BasisMenuService{
public List<BasisMenu> getAll();
public List<BasisMenu> queryForList(BasisMenu basisMenu);
public BasisMenu queryForOne(BasisMenu basisMenu);
public PageView query(PageView pageView,BasisMenu basisMenu);
public void add(BasisMenu basisMenu);
public void delete(String id);
public void modify(BasisMenu basisMenu);
public BasisMenu getById(String id);
}

package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisApplication;
public interface BasisApplicationService{
public List<BasisApplication> getAll();
public List<BasisApplication> queryForList(BasisApplication basisApplication);
public BasisApplication queryForOne(BasisApplication basisApplication);
public PageView query(PageView pageView,BasisApplication basisApplication);
public void add(BasisApplication basisApplication);
public void delete(String id);
public void modify(BasisApplication basisApplication);
public BasisApplication getById(String id);
}

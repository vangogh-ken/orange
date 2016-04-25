package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisSchema;
public interface BasisSchemaService{
public List<BasisSchema> getAll();
public List<BasisSchema> queryForList(BasisSchema basisSchema);
public BasisSchema queryForOne(BasisSchema basisSchema);
public PageView query(PageView pageView,BasisSchema basisSchema);
public void add(BasisSchema basisSchema);
public void delete(String id);
public void modify(BasisSchema basisSchema);
public BasisSchema getById(String id);
}

package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightStatementOffset;
public interface FreightStatementOffsetService{
public List<FreightStatementOffset> getAll();
public List<FreightStatementOffset> queryForList(FreightStatementOffset freightStatementOffset);
public FreightStatementOffset queryForOne(FreightStatementOffset freightStatementOffset);
public PageView query(PageView pageView,FreightStatementOffset freightStatementOffset);
public void add(FreightStatementOffset freightStatementOffset);
public void delete(String id);
public void modify(FreightStatementOffset freightStatementOffset);
public FreightStatementOffset getById(String id);
}

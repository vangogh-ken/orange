package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;
public interface FreightInvoiceOffsetService{
public List<FreightInvoiceOffset> getAll();
public List<FreightInvoiceOffset> queryForList(FreightInvoiceOffset freightInvoiceOffset);
public FreightInvoiceOffset queryForOne(FreightInvoiceOffset freightInvoiceOffset);
public PageView query(PageView pageView,FreightInvoiceOffset freightInvoiceOffset);
public void add(FreightInvoiceOffset freightInvoiceOffset);
public void delete(String id);
public void modify(FreightInvoiceOffset freightInvoiceOffset);
public FreightInvoiceOffset getById(String id);
}

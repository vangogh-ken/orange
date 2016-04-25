package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasInvoiceType;

public interface FasInvoiceTypeService {
	public List<FasInvoiceType> getAll();

	public List<FasInvoiceType> queryForList(FasInvoiceType fasInvoiceType);

	public FasInvoiceType queryForOne(FasInvoiceType fasInvoiceType);

	public PageView<FasInvoiceType> query(PageView<FasInvoiceType> pageView, FasInvoiceType fasInvoiceType);

	public void add(FasInvoiceType fasInvoiceType);

	public void delete(String id);

	public void modify(FasInvoiceType fasInvoiceType);

	public FasInvoiceType getById(String id);
	
	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FasInvoiceType> fasInvoiceTypes);
}

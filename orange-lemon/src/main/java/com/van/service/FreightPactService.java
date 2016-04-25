package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightPact;

public interface FreightPactService {
	public List<FreightPact> getAll();

	public List<FreightPact> queryForList(FreightPact freightPact);

	public PageView query(PageView pageView, FreightPact freightPact);

	public void add(FreightPact freightPact);

	public void delete(String id);

	public void modify(FreightPact freightPact);

	public FreightPact getById(String id);
	
	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FreightPact> freightPacts);
}

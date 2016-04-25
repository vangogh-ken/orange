package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightNetDay;

public interface FreightNetDayService {
	public List<FreightNetDay> getAll();

	public List<FreightNetDay> queryForList(FreightNetDay freightNetDay);

	public FreightNetDay queryForOne(FreightNetDay freightNetDay);

	public PageView query(PageView pageView, FreightNetDay freightNetDay);

	public void add(FreightNetDay freightNetDay);

	public void delete(String id);

	public void modify(FreightNetDay freightNetDay);

	public FreightNetDay getById(String id);
	
	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FreightNetDay> freightNetDays);
}

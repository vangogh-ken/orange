package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightBox;

public interface FreightBoxService {
	public List<FreightBox> getAll();

	public List<FreightBox> queryForList(FreightBox freightBox);

	public PageView query(PageView pageView, FreightBox freightBox);

	public void add(FreightBox freightBox);

	public void delete(String id);

	public void modify(FreightBox freightBox);

	public FreightBox getById(String id);

	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FreightBox> freightBoxs);
}

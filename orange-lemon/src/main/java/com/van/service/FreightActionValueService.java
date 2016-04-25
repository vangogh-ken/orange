package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightActionValue;

public interface FreightActionValueService {
	public List<FreightActionValue> getAll();

	public List<FreightActionValue> queryForList(
			FreightActionValue freightActionValue);

	public PageView query(PageView pageView,
			FreightActionValue freightActionValue);

	public void add(FreightActionValue freightActionValue);

	public void delete(String id);

	public void modify(FreightActionValue freightActionValue);

	public FreightActionValue getById(String id);
}

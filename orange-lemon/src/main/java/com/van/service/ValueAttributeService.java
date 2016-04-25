package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ValueAttribute;

public interface ValueAttributeService {
	public List<ValueAttribute> getAll();

	public List<ValueAttribute> queryForList(ValueAttribute valueAttribute);

	public PageView query(PageView pageView, ValueAttribute valueAttribute);

	public void add(ValueAttribute valueAttribute);

	public void delete(String id);

	public void modify(ValueAttribute valueAttribute);

	public ValueAttribute getById(String id);
}

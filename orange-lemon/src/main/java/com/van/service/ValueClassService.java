package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ValueClass;

public interface ValueClassService {
	public List<ValueClass> getAll();

	public List<ValueClass> queryForList(ValueClass valueClass);

	public PageView query(PageView pageView, ValueClass valueClass);

	public void add(ValueClass valueClass);

	public void delete(String id);

	public void modify(ValueClass valueClass);

	public ValueClass getById(String id);
}

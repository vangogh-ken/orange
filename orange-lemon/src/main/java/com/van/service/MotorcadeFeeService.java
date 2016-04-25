package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeFee;

public interface MotorcadeFeeService {
	public List<MotorcadeFee> getAll();

	public List<MotorcadeFee> queryForList(MotorcadeFee motorcadeFee);

	public MotorcadeFee queryForOne(MotorcadeFee motorcadeFee);

	public PageView<MotorcadeFee> query(PageView<MotorcadeFee> pageView, MotorcadeFee motorcadeFee);

	public void add(MotorcadeFee motorcadeFee);

	public void delete(String id);

	public void modify(MotorcadeFee motorcadeFee);

	public MotorcadeFee getById(String id);
}

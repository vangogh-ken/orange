package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.PositionRole;

public interface PositionRoleService {
	public List<PositionRole> getAll();

	public List<PositionRole> queryForList(PositionRole positionRole);

	public PositionRole queryForOne(PositionRole positionRole);

	public PageView query(PageView pageView, PositionRole positionRole);

	public void add(PositionRole positionRole);

	public void delete(String id);

	public void modify(PositionRole positionRole);

	public PositionRole getById(String id);
}

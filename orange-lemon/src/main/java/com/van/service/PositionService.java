package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.Position;

public interface PositionService {
	public List<Position> getAll();

	public List<Position> queryForList(Position position);

	public PageView<Position> query(PageView<Position> pageView, Position position);

	public void add(Position position);

	public void delete(String id);

	public void modify(Position position);

	public Position getById(String id);
	/**
	 * 分配角色
	 * @param positionId
	 * @param roleIds
	 */
	public void allocation(String positionId, String[] roleIds);
}

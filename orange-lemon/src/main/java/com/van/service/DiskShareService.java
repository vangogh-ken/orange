package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.DiskShare;

public interface DiskShareService {
	public List<DiskShare> getAll();

	public List<DiskShare> queryForList(DiskShare diskShare);

	public int count(DiskShare diskShare);

	public DiskShare queryForOne(DiskShare diskShare);

	public PageView<DiskShare> query(PageView<DiskShare> pageView, DiskShare diskShare);

	public void add(DiskShare diskShare);

	public void delete(String id);

	public void modify(DiskShare diskShare);

	public DiskShare getById(String id);
}

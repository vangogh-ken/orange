package com.van.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.DiskShareDao;
import com.van.halley.db.persistence.entity.DiskShare;
import com.van.service.DiskShareService;

@Transactional
@Service("diskShareService")
public class DiskShareServiceImpl implements DiskShareService {
	@Autowired
	private DiskShareDao diskShareDao;

	public List<DiskShare> getAll() {
		return diskShareDao.getAll();
	}

	public List<DiskShare> queryForList(DiskShare diskShare) {
		return diskShareDao.queryForList(diskShare);
	}

	public int count(DiskShare diskShare) {
		return diskShareDao.count(diskShare);
	}

	public DiskShare queryForOne(DiskShare diskShare) {
		return diskShareDao.queryForOne(diskShare);
	}

	public PageView<DiskShare> query(PageView<DiskShare> pageView, DiskShare diskShare) {
		List<DiskShare> list = diskShareDao.query(pageView, diskShare);
		pageView.setResults(list);
		return pageView;
	}

	public void add(DiskShare diskShare) {
		diskShareDao.add(diskShare);
	}

	public void delete(String id) {
		diskShareDao.delete(id);
	}

	public void modify(DiskShare diskShare) {
		diskShareDao.modify(diskShare);
	}

	public DiskShare getById(String id) {
		return diskShareDao.getById(id);
	}
}

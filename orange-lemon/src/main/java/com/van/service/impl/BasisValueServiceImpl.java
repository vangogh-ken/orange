package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisValueDao;
import com.van.halley.db.persistence.entity.BasisValue;
import com.van.service.BasisValueService;

@Transactional
@Service("basisValueService")
public class BasisValueServiceImpl implements BasisValueService {
	@Autowired
	private BasisValueDao basisValueDao;

	public List<BasisValue> getAll() {
		return basisValueDao.getAll();
	}

	public List<BasisValue> queryForList(BasisValue basisValue) {
		return basisValueDao.queryForList(basisValue);
	}

	public BasisValue queryForOne(BasisValue basisValue) {
		return basisValueDao.queryForOne(basisValue);
	}

	public PageView query(PageView pageView, BasisValue basisValue) {
		List<BasisValue> list = basisValueDao.query(pageView, basisValue);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BasisValue basisValue) {
		basisValueDao.add(basisValue);
	}

	public void delete(String id) {
		basisValueDao.delete(id);
	}

	public void modify(BasisValue basisValue) {
		basisValueDao.modify(basisValue);
	}

	public BasisValue getById(String id) {
		return basisValueDao.getById(id);
	}

	@Override
	public BasisValue getSingleValue(String basisSubstanceId,
			String idOrColumn, boolean isAttributeId) {
		return basisValueDao.getSingleValue(basisSubstanceId, idOrColumn, isAttributeId);
	}
}

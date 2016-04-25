package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisSubstanceTypeDao;
import com.van.halley.db.persistence.entity.BasisSubstanceType;
import com.van.service.BasisSubstanceTypeService;

@Transactional
@Service("basisSubstanceTypeService")
public class BasisSubstanceTypeServiceImpl implements BasisSubstanceTypeService {
	@Autowired
	private BasisSubstanceTypeDao basisSubstanceTypeDao;

	public List<BasisSubstanceType> getAll() {
		return basisSubstanceTypeDao.getAll();
	}

	public List<BasisSubstanceType> queryForList(BasisSubstanceType basisSubstanceType) {
		return basisSubstanceTypeDao.queryForList(basisSubstanceType);
	}

	public BasisSubstanceType queryForOne(BasisSubstanceType basisSubstanceType) {
		return basisSubstanceTypeDao.queryForOne(basisSubstanceType);
	}

	public PageView<BasisSubstanceType> query(PageView<BasisSubstanceType> pageView, BasisSubstanceType basisSubstanceType) {
		List<BasisSubstanceType> list = basisSubstanceTypeDao.query(pageView, basisSubstanceType);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BasisSubstanceType basisSubstanceType) {
		basisSubstanceTypeDao.add(basisSubstanceType);
	}

	public void delete(String id) {
		basisSubstanceTypeDao.delete(id);
	}

	public void modify(BasisSubstanceType basisSubstanceType) {
		basisSubstanceTypeDao.modify(basisSubstanceType);
	}

	public BasisSubstanceType getById(String id) {
		return basisSubstanceTypeDao.getById(id);
	}
}

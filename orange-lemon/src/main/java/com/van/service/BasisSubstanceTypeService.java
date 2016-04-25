package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisSubstanceType;

public interface BasisSubstanceTypeService {
	public List<BasisSubstanceType> getAll();

	public List<BasisSubstanceType> queryForList(BasisSubstanceType basisSubstanceType);

	public BasisSubstanceType queryForOne(BasisSubstanceType basisSubstanceType);

	public PageView<BasisSubstanceType> query(PageView<BasisSubstanceType> pageView,
			BasisSubstanceType basisSubstanceType);

	public void add(BasisSubstanceType basisSubstanceType);

	public void delete(String id);

	public void modify(BasisSubstanceType basisSubstanceType);

	public BasisSubstanceType getById(String id);
}

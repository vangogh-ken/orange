package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisAttribute;

public interface BasisAttributeService {
	public List<BasisAttribute> getAll();

	public List<BasisAttribute> queryForList(BasisAttribute basisAttribute);

	public BasisAttribute queryForOne(BasisAttribute basisAttribute);

	public PageView query(PageView pageView, BasisAttribute basisAttribute);

	public void add(BasisAttribute basisAttribute);

	public void delete(String id);

	public void modify(BasisAttribute basisAttribute);

	public BasisAttribute getById(String id);

	public List<BasisAttribute> getByBasisSubstanceTypeId(String basisSubstanceTypeId);

	/**
	 * 批量添加属性
	 * @param basisAttribute
	 * @param batchCount
	 * @return
	 */
	public boolean doneAddBatch(BasisAttribute basisAttribute, int batchCount, int batchStart, int batchEnd);
}

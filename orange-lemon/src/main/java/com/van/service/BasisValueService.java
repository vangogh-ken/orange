package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisValue;

public interface BasisValueService {
	public List<BasisValue> getAll();

	public List<BasisValue> queryForList(BasisValue basisValue);

	public BasisValue queryForOne(BasisValue basisValue);

	public PageView query(PageView pageView, BasisValue basisValue);

	public void add(BasisValue basisValue);

	public void delete(String id);

	public void modify(BasisValue basisValue);

	public BasisValue getById(String id);

	/**
	 * 获取单个
	 * 
	 * @param basisSubstanceId
	 * @param idOrColumn
	 * @param isAttributeId
	 * @return
	 */
	public BasisValue getSingleValue(String basisSubstanceId,
			String idOrColumn, boolean isAttributeId);
}

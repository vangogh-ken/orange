package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightSeal;

public interface FreightSealService {
	public List<FreightSeal> getAll();

	public List<FreightSeal> queryForList(FreightSeal freightSeal);

	public PageView query(PageView pageView, FreightSeal freightSeal);

	public void add(FreightSeal freightSeal);

	public void delete(String id);

	public void modify(FreightSeal freightSeal);

	public FreightSeal getById(String id);

	/**
	 * 批量新增封号
	 * @param freightSeal
	 * @param startSealNumber
	 * @param endSealNumber
	 */
	public int addBatch(FreightSeal freightSeal, String startSealNumber, String endSealNumber);
}

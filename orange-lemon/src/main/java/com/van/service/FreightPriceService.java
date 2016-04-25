package com.van.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightPrice;

public interface FreightPriceService {
	public static Map<String, List<FreightPrice>> cacheFreightPrice = new HashMap<String, List<FreightPrice>>();
	
	public List<FreightPrice> getAll();

	public List<FreightPrice> queryForList(FreightPrice freightPrice);

	public PageView<FreightPrice> query(PageView<FreightPrice> pageView, FreightPrice freightPrice);

	public void add(FreightPrice freightPrice);

	public void delete(String id);

	public void modify(FreightPrice freightPrice);

	public FreightPrice getById(String id);
	
	public void refreshCache();
	/**
	 * 获取成本
	 * @param freightPartId
	 * @param freightExpenseTypeId
	 * @param countUnit
	 * @return
	 */
	public FreightPrice getOriginalPrice(String freightPartId, String freightExpenseTypeId, String countUnit);
	/**
	 * 按时间获取原始成本信息
	 * @return
	 */
	public List<FreightPrice> getByOriginalTime();
	/**
	 * 以状态获取原始成本信息
	 * @return
	 */
	public List<FreightPrice> getByOriginalStatus();
	/**
	 * 批量删除，缓存只刷新一次提高响应速度
	 * @param freightPriceIds
	 */
	public void remove(String[] freightPriceIds);
	
	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FreightPrice> freightPrices);

	/**
	 * 按箱费用批量新增
	 * @param freightPrice
	 * @param moneyCount20
	 * @param moneyCount40
	 */
	public void doneAddBatch(FreightPrice freightPrice, double moneyCount20, double moneyCount40);
}

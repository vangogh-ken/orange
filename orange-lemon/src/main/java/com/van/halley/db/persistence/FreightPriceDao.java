package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightPrice;

public interface FreightPriceDao extends BaseDao<FreightPrice> {
	/**
	 * 缓存数据，值选取按单位，费用类型，发票票种，计价方式分组之后最新的数据；此数据是以CREATE_TIME的时间戳进行分别，同一时间戳下有多条数据，可能出现脏数据。
	 * @return
	 */
	/*public List<FreightPrice> getForCache();*/
	/**
	 * 获取原始成本信息
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
	
}
package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightActionValue;

public interface FreightActionValueDao extends BaseDao<FreightActionValue> {
	/**
	 * 删除与该动作相关的所有值，一般用于该动作被删除时；
	 * @param freightActionId
	 */
	public void deleteByFreightActionId(String freightActionId);
	/**
	 * 删除与动作和箱封相关的值，一般出现在修改的动作关联的箱封时；
	 * @param freightActionId
	 * @param id
	 */
	public void deleteForOrderBox(String freightActionId, String freightOrderBoxId);
	/**
	 * 获取共享字段值
	 * @param fieldColumn
	 * @param freightActionId
	 * @return
	 */
	public FreightActionValue getParticipateValue(String fieldColumn, String freightActionId);
	/**
	 * 获取forbox的共享字段，如果获取不到同为forbox的共享字段，则直接获取normal数据
	 * @param fieldColumn
	 * @param freightActionId
	 * @param freightOrderBoxId
	 * @return
	 */
	public FreightActionValue getParticipateValue(String fieldColumn, String freightActionId, String freightOrderBoxId);
	/**
	 * 获取所有的共享字段
	 * @param freightActionId
	 * @return
	 */
	public List<FreightActionValue> getAllParticipateValue(String freightOrderId);
	
	/**
	 * 获取动作一般值
	 * @param freightActionId
	 * @return
	 */
	public List<FreightActionValue> getNormalByFreightActionId(String freightActionId);
	/**
	 * 获取动作含箱值
	 * @param freightActionId
	 * @return
	 */
	public List<FreightActionValue> getForBoxByFreightActionId(String freightActionId);
	/**
	 * 数据模板获取
	 * @param freightDataTemplateId
	 * @return
	 */
	public List<FreightActionValue> getByFreightDataTemplateId(String freightDataTemplateId);
	/**
	 * 添加与数据模板关联
	 * @param freightDataTemplateId
	 * @param freightActionValueId
	 */
	public void addDataTemplateValue(String freightDataTemplateId, String freightActionValueId);
	/**
	 * 删除数据模板的关联
	 * @param freightDataTemplateId
	 */
	public void deleteDataTemplateValue(String freightDataTemplateId);
	
	/**
	 * 获取与订单相关的某个字段的信息
	 * @param freightOrderId
	 * @param fieldColumn
	 * @return
	 */
	public String getSingleValue(String freightOrderId, String fieldColumn);
	/**
	 * 删除与某箱封关联的所有动作值
	 * @param freightOrderBoxId
	 */
	public void deleteByFreightOrderBoxId(String freightOrderBoxId);
	
}
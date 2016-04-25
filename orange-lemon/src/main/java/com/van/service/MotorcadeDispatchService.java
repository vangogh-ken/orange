package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeDispatch;
import com.van.halley.db.persistence.entity.MotorcadeFee;

public interface MotorcadeDispatchService {
	public List<MotorcadeDispatch> getAll();

	public List<MotorcadeDispatch> queryForList(
			MotorcadeDispatch motorcadeDispatch);

	public MotorcadeDispatch queryForOne(MotorcadeDispatch motorcadeDispatch);

	public PageView query(PageView pageView, MotorcadeDispatch motorcadeDispatch);

	public void add(MotorcadeDispatch motorcadeDispatch);

	public void delete(String id);

	public void modify(MotorcadeDispatch motorcadeDispatch);

	public MotorcadeDispatch getById(String id);

	/**
	 * 根据用户名获取下个订单号 订单组合 CY-PD-用户名-年(2位)-月(2位)-周(2位)-001
	 * @param userName
	 * @return
	 */
	public String getNextDispatchNumber(String userName);
	/**
	 * 复制新增派单
	 * @param motorcadeDispatchId
	 * @return
	 */
	public Map<String, Object> toAddDispatchByCopy(String motorcadeDispatchId);
	/**
	 * 新增派单
	 * 
	 * @param motorcadeDispatchId
	 * @return
	 */
	public String doneAddDispatchByCopy(String motorcadeDispatchId, String[] motorcadeFeeIds, String userName);
	/**
	 * 添加派单费用
	 * @param motorcadeDispatchId
	 * @return
	 */
	public Map<String, Object> toAddFee(String motorcadeDispatchId);
	/**
	 * 完成添加派单费用
	 * @param motorcadeFee
	 * @return
	 */
	public boolean doneAddFee(String motorcadeDispatchId, MotorcadeFee motorcadeFee);

	/**
	 * 校验派单是否可以删除，无费用关联即可
	 * @param motorcadeDispatchId
	 * @return
	 */
	public boolean toRemoveDispatch(String[] motorcadeDispatchIds);

	public boolean doneConfirmDispatch(String[] motorcadeDispatchIds);

	public boolean doneRecallDispatch(String[] motorcadeDispatchIds);
}

package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.User;

public interface FreightOrderService {
	public List<FreightOrder> getAll();

	public List<FreightOrder> queryForList(FreightOrder freightOrder);

	public PageView<FreightOrder> query(PageView<FreightOrder> pageView, FreightOrder freightOrder);

	public void add(FreightOrder freightOrder);

	public void delete(String id);

	public void modify(FreightOrder freightOrder);

	public FreightOrder getById(String id);
	/**
	 * 状态变为:审核中
	 */
	public boolean toOrderAudit(String[] freightOrderIds);
	/**
	 * 状态变为:已审核
	 */
	public boolean doneOrderAudit(String[] freightOrderIds);
	/**
	 * 状态变为：已退回
	 */
	public boolean backOrderAudit(String[] freightOrderIds);
	/**
	 * 追回订单，转至操作。状态变为：操作确认；该订单锁定
	 */
	public boolean toOrderRecover(String[] freightOrderIds);
	/**
	 * 确认追回，转至业务。状态变为：已追回；订单解除锁定
	 */
	public boolean doneOrderRecover(String[] freightOrderIds);
	/**
	 * 状态变为:审核中
	 * 对费用状态进行处理
	 */
	public boolean toExpenseAudit(String[] freightOrderIds);
	/**
	 * 状态变为:已审核
	 * 对费用状态进行处理
	 */
	public boolean doneExpenseAudit(String[] freightOrderIds);
	/**
	 * 状态变为：已退回
	 * 对费用状态进行处理
	 */
	public boolean backExpenseAudit(String[] freightOrderIds);
	/**
	 * 费用添加完毕，检查订单费用状况，如果出现亏损，则要求填写情况说明。
	 */
	public boolean doneCompleteExpense(String[] freightOrderIds);
	/**
	 * 取消费用添加完毕，订单未提成
	 * @param freightOrderId
	 * @return
	 */
	public boolean recallCompleteExpenseWithoutDeduct(String[] freightOrderIds);
	/**
	 * 取消费用添加完毕，订单已提成
	 * @param freightOrderIds
	 * @return
	 */
	public boolean recallCompleteExpenseWithDeduct(String[] freightOrderIds);
	/**
	 * 状态变为:已完成；所有动作执行完成，费用且也录入完毕。
	 * 返回不能成功提交的订单编号
	 */
	public boolean finishOrder(String[] freightOrderIds);
	/**
	 * 状态变为:已作废，作废，未提交，已退回，已追回，且相关费用没有对账，则可进行
	 * 结果将删除已生成的委托，添加的非对账费用
	 */
	public boolean invalidOrder(String[] freightOrderIds);
	/**
	 * 根据用户名获取下个订单号
	 * 订单组合 CY-用户名-年(2位)-月(2位)-周(2位)-001
	 * @param userName
	 * @return
	 */
	public String getNextOrderNumber(String userName);

	/**
	 * 批量删除订单，删除之前判断状态，状态为未提交或业务退回，尚未提交人和审批时可以删除，其他时候的删除将已作废处理；
	 * 是否需要对费用和委托进行判断？//FIXME
	 * @param selectedItem
	 */
	public boolean doneRemoveOrder(String[] freightOrderIds);
	/**
	 * 复制新增订单，返回信息包括：订单基本信息，操作，动作，箱需，费用，动作信息
	 * @param freightOrderId
	 * @return
	 */
	public Map<String, Object> toAddOrderByCopy(String freightOrderId);

	/**
	 * 保存复制的内容，此复制动作和操作默认全部复制；返回新订单ID
	 * @param freightOrderId
	 * @param freightBoxRequireId
	 * @param freightExpenseId
	 * @param freightActionValueId
	 */
	public String doneAddOrderByCopy(String freightOrderId,
			String[] freightBoxRequireIds, String[] freightExpenseIds,
			String[] freightActionValueIds, User creator);

	/**
	 * 复制费用
	 * @param freightOrderId
	 * @return
	 */
	public Map<String, Object> toCopyExpense(String freightOrderId);

	/**
	 * @param freightExpenseId
	 * @param targetId 目标订单
	 * @param sheatheAllBox 按箱费用是否覆盖所有箱子 T/F
	 * @return
	 */
	public boolean doneCopyExpense(String sourceOrderId, String[] targetOrderIds, String[] freightExpenseIds,  String sheatheAllBox);

	/**
	 * 获取一些关键数据，如启运港，目的港
	 * @param freightActionId
	 * @return
	 */
	public Map<String, String> getKeyData(String freightOrderId);
	
	//订单复审、终审、及其退回
	public boolean doneRehearOrder(String[] freightOrderIds);
	public boolean backRehearOrder(String[] freightOrderIds);
	public boolean doneEventideOrder(String[] freightOrderIds);
	public boolean backEventideOrder(String[] freightOrderIds);

	/**
	 * 费用添加完毕，处于亏损时填写情况说明，此保存该说明。
	 * @param freightOrderId
	 * @param deficitReason
	 * @return
	 */
	public boolean doneAddDeficitReason(String freightOrderId, String deficitReason);

	/**
	 * 批量导出订单信息，此信息包含除特殊费用之和的金额
	 * @param freightOrderIds
	 * @return
	 */
	public List<List<String>> toBatchOrderExport(String[] freightOrderIds);
}

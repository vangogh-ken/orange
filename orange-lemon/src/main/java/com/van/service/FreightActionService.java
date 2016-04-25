package com.van.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightAction;

public interface FreightActionService {
	public List<FreightAction> getAll();

	public List<FreightAction> queryForList(FreightAction freightAction);

	public PageView<FreightAction> query(PageView<FreightAction> pageView, FreightAction freightAction);

	public void add(FreightAction freightAction);

	public void delete(String id);

	public void modify(FreightAction freightAction);

	public FreightAction getById(String id);
	/**
	 * 根据订单获取所有的Action
	 * @return
	 */
	public List<FreightAction> getByOrderId(String freightOrderId);
	/**
	 * 保存action填报的数据
	 * @param freightActionId
	 * @param request
	 */
	//public void saveActionValueByActionId(String freightActionId, HttpServletRequest request);
	/**
	 * 获取action填报信息,第一次打开填报界面时查询是否有共享字段且取值
	 * @param freightActionId
	 * @return
	 */
	public Map<String, Object> initPrime(String freightActionId);

	/**
	 * 发送动作, 动作状态变为 “已发送”, 相关委托书状态变为“待执行”；变为此状态之前，需重新生成一次委托（暂无）；
	 * @param id
	 */
	public boolean doneSendAction(String freightActionId);
	/**
	 * 执行动作，动作状态变为“已执行”，相关委托状态变为“已执行”；
	 * @return
	 */
	public boolean doneExecuteAction(String freightActionId);
	/**
	 * 批量执行动作
	 * @param freightActionIds
	 * @return
	 */
	public boolean doneExecuteBatch(String[] freightActionIds);
	/**
	 * 批量增加动作
	 * @param freightAction
	 * @param freightMaintainId
	 * @param freightActionTypeIds
	 */
	public void doneAddAction(FreightAction freightAction, String freightMaintainId, String[] freightActionTypeIds);

	/**
	 * 删除动作，更新其他同级动作的显示顺序，删除其未执行之委托
	 * 状态为：未执行，预备执行
	 * @param freightActionId
	 */
	public boolean doneRemoveAction(String freightActionId);
	/**
	 * 预备委托
	 * @param freightActionId
	 * @return
	 */
	public boolean toPrepareAction(String freightActionId);
	/**
	 * 预备完成
	 * @param freightActionId
	 * @return
	 */
	public boolean donePrepareAction(String freightActionId);

	/**
	 * 准备动作信息填报，首选判断是否有含箱的字段；如果有，则需要返回箱封，且要求必须选择好箱封之后，再重新生成填报界面；
	 * @param freightActionId
	 * @return
	 */
	public Map<String, Object> toPrimeAction(String freightActionId);

	/**
	 * 选择或修改，重新生成填报数据
	 * @param freightActionId
	 * @param freightOrderBoxId
	 * @return
	 */
	public boolean toPrimeBox(String freightActionId, String[] freightOrderBoxIds);
	/**
	 * 保存所有的填报数据
	 * @param freightActionId
	 * @param request
	 */
	public boolean donePrimeAction(String freightActionId, HttpServletRequest request);
	/**
	 * 查看是否已填报数据
	 * @param freightActionId
	 * @return
	 */
	public boolean hasPrimeAction(String freightActionId);
	
}

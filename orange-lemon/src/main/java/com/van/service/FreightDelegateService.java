package com.van.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightDelegate;
import com.van.halley.db.persistence.entity.FreightPart;

public interface FreightDelegateService {
	public List<FreightDelegate> getAll();

	public List<FreightDelegate> queryForList(FreightDelegate freightDelegate);

	public PageView query(PageView pageView, FreightDelegate freightDelegate);

	public void add(FreightDelegate freightDelegate);

	public void delete(String id);

	public void modify(FreightDelegate freightDelegate);

	public FreightDelegate getById(String id);
	/**
	 * 通过动作ID获取
	 * @param freightActionId
	 * @return
	 */
	public FreightDelegate getByFreightActionId(String freightActionId);
	/**
	 * 获取生成委托书的数据源，如果freightBoxRequireIds为空，则默认为全部
	 * @param freightActionId
	 * @param freightBoxRequireIds
	 * @return
	 */
	public Map<String, Object> getDataSource(String freightActionId);
	/**
	 * 初始化委托；如果委托不存在，则创建新的，如果存在，则刷新最新数据。
	 * @param freightActionId
	 * @return
	 */
	public FreightDelegate initDelegate(String freightActionId);
	/**
	 * 获取文件数据
	 * @param freightActionId
	 * @return
	 */
	public InputStream initStream(String freightActionId);
	/**
	 * 通过动作和箱需获取完全的数据源,然后根据委托模板进行创建
	 * @param freightActionId
	 * @param freightOrderBoxRequireIds
	 * @return
	 */
	public InputStream initFile(String freightActionId, boolean hasRegionParam, String regionParam);
	/**
	 * 通过动作信息获取收托单位
	 * @param freightActionId
	 * @return
	 */
	public FreightPart initAcceptPart(String freightActionId);
	/**
	 * 通过动作ID获取相应的委托文件数据
	 * 集装箱信息还需要根据箱需ID, 以逗号隔开
	 * @param freightActionId
	 * @return
	 */
	//public InputStream fetchDeletegateFileStream(String freightActionId);
	/**
	 * 仅获取委托数据文件
	 */
	//public String fetchDeletegateFileName(String freightActionId);
	/**
	 * 通过订单获取下一个委托编号
	 * @param freightOrderId
	 * @return
	 */
	public String getNextDelegateNumber(String freightOrderId);
	/**
	 * 通过委托，班列时间，批量导出班列计划。
	 * @param freightDelegateId
	 * @param turnTime
	 */
	public InputStream toExportDelegateRail(String freightActionTypeId, Date turnTime, String[] freightDelegateIds);
	/**
	 * 撤销委托，委托状态为已执行或者未执行，状态变为确认撤销，相关动作状态暂时不变；前台已对状态进行判断，后台不必再判断
	 * @param freightDelegateIds
	 * @return
	 */
	public boolean toRecoverDelegate(String[] freightDelegateIds);
	/**
	 * 执行委托方确认撤销委托，委托状态变为操作，相关动作状态变为未执行；前台已对状态进行判断，后台不必再判断
	 * @param freightDelegateIds
	 * @return
	 */
	public boolean doneRecoverDelegate(String[] freightDelegateIds);
	/**
	 * 
	 * @param receiverUserId
	 * @param freightDelegateId
	 * @return
	 */
	public boolean doneTransferDelegate(String receiverUserId, String[] freightDelegateIds);

	/**
	 * 对委托进行标记，更改executeStatus
	 * @param freightDelegateId
	 * @return
	 */
	public boolean doneMarkDelegate(String[] freightDelegateIds);

	/**
	 * 批量导出数据
	 * @param freightDelegateIds
	 * @return
	 */
	public List<List<String>> doneBatchExport(String[] freightDelegateIds);
}

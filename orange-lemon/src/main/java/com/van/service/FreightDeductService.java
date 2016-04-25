package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightDeduct;

public interface FreightDeductService {
	public List<FreightDeduct> getAll();

	public List<FreightDeduct> queryForList(FreightDeduct freightDeduct);

	public FreightDeduct queryForOne(FreightDeduct freightDeduct);

	public PageView<FreightDeduct> query(PageView<FreightDeduct> pageView, FreightDeduct freightDeduct);

	public void add(FreightDeduct freightDeduct);

	public void delete(String id);

	public void modify(FreightDeduct freightDeduct);

	public FreightDeduct getById(String id);
	/**
	 * 同步订单提成，只要是非已作废的订单，都将进入提成计算流程，操作提成同时纳入。
	 */
	public void synOrderDeduct();
	/**
	 * 同步箱量提成
	 */
	@Deprecated
	public void synBoxDeduct();
	/**
	 * 计算订单提成 20160201启用新的组织架构之前的计算，也只针对2月之前的数据进行
	 */
	public void calculateOrderDeductV1();
	/**
	 * 计算订单提成 20160201启用新的组织架构之后的计算，也只针对2月之后的数据进行
	 */
	public void calculateOrderDeductV2();
	/**
	 * 计算标箱提成
	 */
	@Deprecated
	public void calculateBoxDeduct();
	/**
	 * 完成订单提成
	 */
	public void doneOrderDeduct();
	/**
	 * 完成箱量提成
	 */
	@Deprecated
	public void doneBoxDeduct();
	/**
	 * 计算销账时间以及提成时间
	 * 原则：所有费用已对账，所有账单已开票或已冲抵销账，所有开票已销账或已冲抵销账或已批量冲抵销账
	 */
	public void settleDeductTime();
	/**
	 * 标记销售完成提成
	 * @param freightDeductIds
	 * @return
	 */
	public boolean doneSalesmanDeduct(String[] freightDeductIds);
	/**
	 * 标记操作完成提成
	 * @param freightDeductIds
	 * @return
	 */
	public boolean doneManipulatorDeduct(String[] freightDeductIds);
	/**
	 * 取消销售标记
	 * @param freightDeductIds
	 * @return
	 */
	public boolean recallSalesmanDeduct(String[] freightDeductIds);
	/**
	 * 取消操作标记
	 * @param freightDeductIds
	 * @return
	 */
	public boolean recallManipulatorDeduct(String[] freightDeductIds);

	/**
	 * 导出数据，返回生成的文件数据
	 * @param freightDeductIds
	 * @return
	 */
	public List<List<String>> doneBatchExport(String[] freightDeductIds);

}

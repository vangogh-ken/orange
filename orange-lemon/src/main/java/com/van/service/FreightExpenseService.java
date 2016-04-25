package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightExpenseType;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.db.persistence.entity.User;

public interface FreightExpenseService {
	public List<FreightExpense> getAll();

	public List<FreightExpense> queryForList(FreightExpense freightExpense);

	public PageView<FreightExpense> query(PageView<FreightExpense> pageView, FreightExpense freightExpense);

	public void add(FreightExpense freightExpense);

	public void delete(String id);

	public void modify(FreightExpense freightExpense);

	public FreightExpense getById(String id);
	/**
	 * 通过订单获取下一个费用编号，包括一般费用，内部费用，特殊费用
	 * @param freightOrderId
	 * @param addType: FY, NB, TS 是否协助操作订单时发生的费用，也就是其他部门参与业务时发生的费用
	 * @return
	 */
	public String getNextExpenseNumber(String freightOrderId, String addType);
	
	/**
	 * 获取与业务关联的费用（仅业务添加）
	 * @param freightOrderId
	 * @return
	 */
	public List<FreightExpense> getByFreightOrderId(String freightOrderId);
	/**
	 * 账单关联费用
	 * @param freightStatementId
	 * @return
	 */
	public List<FreightExpense> getByFreightStatementId(String freightStatementId);
	
	/**
	 * 内部部门执行委托时添加费用;
	 * 关于成本信息的处理是复制一个新的成本信息与费用相关联。
	 * 成本的获取都以最老的版本为准，并且是可以修改。
	 * @param freightExpense
	 * @param freightActionId
	 * @param freightPartId
	 * @param freightExpenseTypeId
	 * @param freightPriceId
	 * @param freightBoxRequireId
	 * @param request
	 * @return
	 */
	public boolean doneAddInternal(FreightExpense freightExpense, String freightActionId,String freightPartId,
			String freightExpenseTypeId, String freightPriceId, 
			String fasInvoiceTypeId, String[] freightOrderBoxIds, User creator);
	
	/**
	 * 业务员对订单添加的费用，如果FreightExpense的ID不为空，则说明是修改单条费用。只需要修改即可
	 * 关于成本信息的处理是复制一个新的成本信息与费用相关联。
	 * 成本的获取都以最老的版本为准，并且是可以修改。
	 * @param freightExpense
	 * @param freightOrderId
	 * @param freightActionId
	 * @param freightPartId
	 * @param freightPriceId
	 * @param freightBoxRequireId
	 * @param freightExpenseTypeId
	 * @param request
	 * @return
	 */
	public boolean doneAddNormal(FreightExpense freightExpense, String freightOrderId, String freightActionId, 
			String freightPartId, String freightPriceId, String freightExpenseTypeId,
			String fasInvoiceTypeId, String[] freightOrderBoxIds, User creator);
	
	/**
	 * 批量按箱添加普通费用
	 * @param freightExpense
	 * @param freightOrderId
	 * @param freightActionId
	 * @param freightPartId
	 * @param freightPriceId
	 * @param freightExpenseTypeId
	 * @param fasInvoiceTypeId
	 * @param freightOrderBoxId
	 * @param creator
	 */
	public boolean doneBatchNormal(FreightExpense freightExpense,
			String freightOrderId, String freightActionId,
			String freightPartId, String freightPriceId,
			String freightExpenseTypeId, String fasInvoiceTypeId,
			String[] freightOrderBoxIds, User creator);
	
	/**
	 * 批量添加内部费用
	 * @param freightExpense
	 * @param freightOrderId
	 * @param freightActionId
	 * @param freightPartId
	 * @param freightPriceId
	 * @param freightExpenseTypeId
	 * @param fasInvoiceTypeId
	 * @param freightOrderBoxIds
	 * @param creator
	 * @return
	 */
	public boolean doneBatchInternal(FreightExpense freightExpense, String freightActionId, String freightPartId, 
			String freightExpenseTypeId, String freightPriceId, String fasInvoiceTypeId, String[] freightOrderBoxIds, User creator);
	
	/**比较内部部门收取费用是否与业务填报一致，如果不一致，则将对部门添加的内部费用进行标记为与实际不一致。
	 * @param isInternal 是否是内部费用，此项根据单位A，单位B是否映射到公司内部部门以及订单是否为空(其他部门提交的内部费用没有与订单相关联)
	 * @param freightOrder 相关订单
	 * @param freightExpenseType 费用类型
	 * @param freightPartA 发起费用对象
	 * @param freightPartB 收付费用对象
	 * @return 返回是否进行了比对，不是返回结果
	 */
	public boolean compareInternalExpense(boolean isInternal, FreightOrder freightOrder, FreightExpenseType freightExpenseType, FreightPart freightPartA, FreightPart freightPartB);
	/**
	 * 根据订单，费用类型，收付对象获取内部费用
	 * @param freightOrder
	 * @param freightExpenseType
	 * @param freightPartA
	 * @param freightPartB
	 * @return
	 */
	public List<FreightExpense> getInternalExpense(FreightOrder freightOrder, FreightExpenseType freightExpenseType, FreightPart freightPartA, FreightPart freightPartB);
	/**
	 * 根据订单，费用类型，收付对象获取普通费用
	 * @param freightOrder
	 * @param freightExpenseType
	 * @param freightPartA
	 * @param freightPartB
	 * @return
	 */
	public List<FreightExpense> getNormalExpense(FreightOrder freightOrder, FreightExpenseType freightExpenseType, FreightPart freightPartA, FreightPart freightPartB);

	/**
	 * 批量校验费用是否能够批量修改, 只有已审核、异常费用的状态下可以进行批量修改。
	 * 费用名称、收付、票种、单位都必须一致
	 * @param freightExpenseIds
	 * @return
	 */
	public boolean validBatchForRevise(String[] freightExpenseIds);
	/**
	 * 批量修改预计金额，费用状态为：异常费用>初审中(一般异常)
	 * @param selectedItem
	 * @param predictCount
	 */
	public boolean revisePredictCountBatch(String[] freightExpenseIds, double predictCount);
	/**
	 * 修改预计费用
	 * @param freightExpenseId
	 * @param predictCount
	 * @return
	 */
	public boolean revisePredictCount(FreightExpense freightExpense);

	/**
	 * 批量修改实际金额，费用状态为：已审核>异常费用
	 * @param selectedItem
	 * @param actualCount
	 */
	public boolean reviseActualCountBatch(String[] freightExpenseIds, double actualCount);
	/**
	 * 修改实际费用
	 * @param freightExpenseId
	 * @param actualCount
	 * @return
	 */
	public boolean reviseActualCount(FreightExpense freightExpense);
	
	/**
	 * 修改一个费用的一致信息，是否与实际一致，是否与报价一致
	 * @param freightExpenseId
	 */
	public void checkUnanimity(String freightExpenseId);

	/**
	 * 标记为特殊费用，并更新对应的成本，以及新生成一个成本
	 * @param freightExpenseId 费用ID
	 * @param actualCount 对应成本的实际价格
	 */
	//public boolean reviseSpecialExpense(String freightExpenseId, double actualCount);
	/**
	 * 批量标记特殊费用;如果可以批量，则返回一条费用信息。只有付、已审核的同费用类型、同单位、同票种、同金额的费用才能批量
	 * @param freightExpenseIds
	 * @return
	 */
	public Map<String, Object> toBatchSpecial(String[] freightExpenseIds);
	/**
	 * 完成批量标记特殊费用
	 * @param freightExpenseIds
	 * @param actualCount
	 * @return
	 */
	public boolean doneBatchSpecial(String[] freightExpenseIds, double actualCount);
	/**
	 * 提交费用审核，只对未提交的费用进行处理。其他状态的费用另外处理，如异常费用
	 * @param split
	 * @return
	 */
	public boolean toAuditExpense(String[] freightExpenseIds);
	
	/**
	 * 完成费用审核包括异常的初审
	 * @param freightExpenseIds
	 * @return
	 */
	public boolean doneAuditExpense(String[] freightExpenseIds);
	/**
	 * 退回审核
	 * @param freightExpenseIds
	 * @return
	 */
	public boolean backAuditExpense(String[] freightExpenseIds);
	
	/**
	 * 完成特殊异常费用的复审
	 * @param freightExpenseIds
	 * @return
	 */
	public boolean doneRehearExpense(String[] freightExpenseIds);
	
	/**
	 * 退回复审
	 * @param freightExpenseIds
	 * @return
	 */
	public boolean backRehearExpense(String[] freightExpenseIds);

	/**
	 * 从账单中移除，涉及到的账单费用另外计算
	 * @param split
	 */
	//public void removeFromStatement(String[] freightExpenseIds);

	/**
	 * 根据比较为特殊费用的费用生成新的费用
	 * @param freightExpenseIds
	 * @return
	 */
	public boolean createExpenseSpecial(String[] freightExpenseIds, User creator);
	/**
	 * 取消特殊费用的标记，必须是已审核，未对账，未生成特殊费用的才能取消
	 * @param freightExpenseId
	 * @return
	 */
	public boolean doneRecallSpecial(String[] freightExpenseIds);
	/**
	 * 删除费用，内部费用或者正常费用，删除后对内部费用进行比对；
	 * @param freightExpenseIds
	 */
	public void doneRemoveExpense(String[] freightExpenseIds);
	/**
	 * 添加普通费用
	 * @param freightOrderId
	 * @return
	 */
	public Map<String, Object> toAddNormal(String freightOrderId);
	/**
	 * 添加内部费用
	 * @param freightActionId
	 * @return
	 */
	public Map<String, Object> toAddInternal(String freightActionId);
	/**
	 * 查看订单费用
	 * @param freightOrderId
	 * @return
	 */
	public Map<String, Object> toViewExpense(String freightOrderId);

	/**
	 * 导出费用
	 * @param freightExpenses
	 * @return
	 */
	public List<List<String>> toExport(List<FreightExpense> freightExpenses);
	/**
	 * 根据账单导出相关费用，带集装箱号，提单号等
	 * @param freightStatementId
	 * @return
	 */
	public List<List<String>> toExportByFreightStatementId(String freightStatementId);
	/**
	 * 将按箱费用分开
	 * @param freightStatementId
	 * @return
	 */
	public List<List<String>> toExportByFreightStatementIdWithSplit(String freightStatementId);
	/**
	 * 根据各种过滤条件进行过滤
	 * @param freightExpense
	 * @param typeName
	 * @param partName
	 * @param orderNumber
	 * @param boxNumber
	 * @param TDH
	 * @param CMHC
	 * @param orderCreateTimeStart 订单时间段
	 * @param orderCreateTimeEnd
	 * @return
	 */
	public List<List<String>> toExportByFilterText(FreightExpense freightExpense, String typeName, String partName,
			String orderNumber, String boxNumber, String TDH, String CMHC, String orderCreateTimeStart, String orderCreateTimeEnd);

	/**
	 * 直接导出数据
	 * @param freightExpenseIds
	 * @return
	 */
	public List<List<String>> toExportDetails(String[] freightExpenseIds);
}

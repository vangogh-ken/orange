package com.van.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;

public interface FreightStatementService {
	public List<FreightStatement> getAll();

	public List<FreightStatement> queryForList(FreightStatement freightStatement);

	public PageView<FreightStatement> query(PageView<FreightStatement> pageView, FreightStatement freightStatement);

	public void add(FreightStatement freightStatement);

	public void delete(String id);

	public void modify(FreightStatement freightStatement);

	public FreightStatement getById(String id);
	/**
	 * 计算对账单金额
	 */
	public void calculateStatement(String freightStatementId);
	/**
	 * 开票申请
	 * @param freightStatementId
	 */
	public boolean toIssueInvoice(String freightStatementId);
	
	/**
	 * 获取下一个付款账单编号
	 * @return
	 */
	public String getNextStatementNumberForExpense();
	/**
	 * 获取下一个收款账单编号
	 * @return
	 */
	public String getNextStatementNumberForIncome();
	/**
	 * 账单冲抵，单位、币种、收付过滤
	 * @param freightStatementId
	 * @return
	 */
	public Map<String, Object> toAddOffset(String freightStatementId);
	/**
	 * 账单冲抵，冲抵原则：
	 * 账单与账单之间只能冲第一次；
	 * 金额大的账单作为source，小的作为target；
	 * 有余额的账单状态为冲抵过；
	 * 全被冲抵后的账单状态为已冲抵销账；
	 * 冲抵过的账单不能删除，作废；
	 * 冲抵过的账单仅能作为source；
	 * 
	 * @param sourceStatementId
	 * @param targetStatementId
	 * @param offsetType careCurrency ignoreCurrency
	 */
	public boolean doneAddOffset(String sourceStatementId, String targetStatementId, String offsetType);
	/**
	 * 取消账单冲抵
	 * @param sourceStatementId
	 * @param targetStatementId
	 */
	public boolean doneRemoveOffset(String sourceStatementId, String targetStatementId);
	/**
	 * 生成账单
	 * @param freightStatement
	 * @param freightPartBId
	 * @param fasInvoiceTypeId
	 * @param creator
	 */
	public void doneAddStatement(FreightStatement freightStatement, String freightPartBId, String fasInvoiceTypeId, User creator);
	
	/**
	 * 作废账单，同时解除与此账单关联的费用，若添加了开票和发票信息，则要删除或者回复至原状态(收款付款都可)
	 * @param freightStatementIds
	 * @return
	 */
	public boolean doneInvalidStatement(String[] freightStatementIds);
	/**
	 * 删除账单，类似于作废账单，只是最后将账单删除。
	 * @param freightStatementIds
	 * @return
	 */
	public boolean doneRemoveStatement(String[] freightStatementIds);

	/**
	 * 付款：涉及开票状态为付款初审中，账单状态为付款审核中
	 * releaseInvoiceTime 为开票时间，将作为账期计算依据
	 */
	public void toPayStatement(String freightStatementId, String descn, Date releaseTime);

	/**
	 * 获取已冲抵过的账单
	 * @param freightStatementId
	 * @return
	 */
	public List<FreightStatement> getHasOffsetStatement(String freightStatementId);
	/**
	 * 提交审核
	 * @param freightStatementId
	 * @return
	 */
	public boolean toStatementAudit(String[] freightStatementIds);
	/**
	 * 审核账单
	 * @param freightStatementId
	 * @return
	 */
	public boolean doneStatementAudit(String[] freightStatementIds);

	/**
	 * 退回账单
	 * @param freightStatementId
	 * @return
	 */
	public boolean backStatementAudit(String[] freightStatementIds);
	/**
	 * 获取可添加至账单的费用
	 * @param freightStatementId
	 * @param FYMC 费用名称
	 * @param DDH 订单号
	 * @param XH 箱号
	 * @param TDH 提单号
	 * @param CMHC 船名航次
	 * @return
	 */
	public Map<String, Object> toAddExpense(String freightStatementId, String FYMC, String DDH, String PM, String WTDW, String XH, String XS, String TDH, String CMHC, String NB);
	
	/**
	 * 客服添加，过滤自己的费用
	 * @param freightStatementId
	 * @param fYMC
	 * @param dDH
	 * @param xH
	 * @param tDH
	 * @param cMHC
	 * @return
	 */
	public Map<String, Object> toAddExpenseFilter(String freightStatementId, String FYMC, String DDH, String XH, String TDH, String CMHC);
	/**
	 * 关联费用，在未提交，审核中，已审核状态下都可以添加费用，但是账单状态又将转换为未提交
	 * @param freightStatementId
	 * @param freightExpenseId
	 */
	public void doneAddExpense(String freightStatementId, String[] freightExpenseIds);
	/**
	 * 删除费用关联
	 * @param freightExpenseIds
	 */
	public void doneDeleteExpense(String[] freightExpenseIds);

	/**
	 * 取消复核，状态为已审核且无开票，可取消复核
	 * @param freightStatementId
	 * @return
	 */
	public boolean doneRecallStatement(String[] freightStatementIds);

	/**
	 * 税务退回开票申请
	 * @param freightStatementId
	 * @return
	 */
	public boolean backInvoiceStatement(String[] freightStatementIds);

	/**
	 * 添加费用中修改费用的实际金额
	 * @param freightExpenseId
	 * @param actualCount
	 * @return
	 */
	public boolean doneReviseExpense(String freightExpenseId, double actualCount);

}

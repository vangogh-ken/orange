package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasPay;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.User;

public interface FreightInvoiceService {
	public List<FreightInvoice> getAll();

	public List<FreightInvoice> queryForList(FreightInvoice freightInvoice);

	public PageView<FreightInvoice> query(PageView<FreightInvoice> pageView, FreightInvoice freightInvoice);

	public void add(FreightInvoice freightInvoice);

	public void delete(String id);

	public void modify(FreightInvoice freightInvoice);

	public FreightInvoice getById(String id);
	
	/**
	 * 新增开票任务,必须要等到该账单金额全部被开票之后，其开票才会变为未开票状态
	 */
	public boolean doneAddInvoice(FreightInvoice freightInvoice, String freightStatementId, String fasInvoiceTypeId);
	/**
	 * 删除相应的开票任务
	 * @param freightInvoiceIds
	 */
	public boolean doneDeleteInvoice(String[] freightInvoiceIds);

	/**
	 * 开票任务只针对收入的费用
	 * 批量开票,自动获取相应类型，且发票号最小的税务发票，然后就发票信息补充进开票任务
	 * @param selectedItem
	 */
	public int batchReleaseInvoice(String[] freightInvoiceIds);

	/**
	 * 给某个发票任务开具一个特定的税务发票
	 * @param freightInvoiceId
	 * @param fasInvoiceId
	 * @return
	 */
	public boolean singleReleaseInvoice(String freightInvoiceId, String fasInvoiceId);
	/**
	 * 开票冲抵
	 * @param freightInvoiceIdSourceId
	 * @return
	 */
	public Map<String, Object> toOffsetInvoice(String freightInvoiceId);
	/**
	 * 开票冲抵，根据不同的冲抵类型进行冲抵计算，并且将冲抵记录；
	 * 两个开票只能冲抵一次。
	 * @param freightInvoiceIdSourceId
	 * @param freightInvoiceIdOffsetId
	 * @param offsetType 冲抵类型：仅同币种，折后冲抵 
	 */
	public boolean offsetInvoice(String freightInvoiceIdSourceId,String freightInvoiceIdOffsetId, String offsetType);
	/**
	 * 取消冲抵
	 * @param sourceInvoiceId
	 * @param targetInvoiceId
	 */
	public void deleteInvoiceOffset(String sourceInvoiceId, String targetInvoiceId);
	
	/**
	 * 批量冲抵
	 * @param freightInvoiceIds
	 * @return
	 */
	public Map<String, Object> toBatchoffset(String[] freightInvoiceIds);
	/**
	 * 批量冲抵开票
	 * 将收、付的发票全部冲抵销账，收付的发票必须为待销账，未冲抵，新生成收付的新的开票，并赋以虚拟发票号
	 * @param freightInvoiceIdsIn 收款
	 * @param freightInvoiceIdsOut 付款
	 * @return
	 */
	public Map<String, Object> doneBatchoffset(String[] freightInvoiceIdsIn, String[] freightInvoiceIdsOut, String offsetType);
	/**
	 * 取消批量冲抵
	 * 使用新生成的开票来取消，将新生成的所有发票删除，将涉及到的所有发票恢复至待销账
	 * @param freightInvoiceId
	 * @return
	 */
	public boolean deleteBatchOffset(String freightInvoiceId);
	/**
	 * 将已开票的开票任务转到销账
	 * @param freightInvoiceId
	 * @return
	 */
	public boolean toInvoiceReconcile(String[] freightInvoiceIds);
	/**
	 * 将待销账发票转至已开票
	 * @param freightInvoiceId
	 * @return
	 */
	public boolean toInvoiceRelease(String[] freightInvoiceIds);
	/**
	 * 在待销账中作废，涉及到账单，其他待销账，税务发票，费用。最终费用恢复到未提交状态，其他作废。
	 * @param freightInvoiceId
	 * @return
	 */
	public boolean invalidInvoice(String[] freightInvoiceIds);
	
	/**
	 * 付款初审，状态 付款初审中 》 付款复审中
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean doneAuditInvoicePay(String[] freightInvoiceIds);
	/**
	 * 初审退回，状态 付款初审中 》 未提交
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean backAuditInvoicePay(String[] freightInvoiceIds);
	/**
	 * 付款复审，状态 付款复审中 》 付款终审中
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean doneRehearInvoicePay(String[] freightInvoiceIds);
	/**
	 * 复审退回，状态 付款复审中 》 付款初审中
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean backRehearInvoicePay(String[] freightInvoiceIds);
	/**
	 * 付款终审，状态 付款终审中 》 待销账
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean doneEventideInvoicePay(String[] freightInvoiceIds);
	/**
	 * 终审退回，状态 付款终审中 》付款复审中
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean backEventideInvoicePay(String[] freightInvoiceIds);

	/**
	 * 付款：添加发票号。
	 * @param freightInvoice
	 * @param freightStatementId
	 * @param freightPartId
	 * @param fasInvoiceTypeId
	 * @param fasInvoiceNumber 为空则取虚拟发票号
	 */
	public boolean doneAddNumber(FreightInvoice freightInvoice, String freightStatementId, String fasInvoiceNumber);

	/**
	 * 付款：删除发票号。
	 * @param freighInvoiceIds
	 */
	public void doneDeleteNumber(String[] freightInvoiceIds);

	/**
	 * 获取已冲抵的开票
	 * @param freightInvoiceId
	 * @return
	 */
	public List<FreightInvoice> getHasOffsetInvoice(String freightInvoiceId);

	/**
	 * 直接修改开具的税务发票
	 * @param freightInvoice
	 * @param fasInvoiceId
	 */
	public void reviseFasInvoice(FreightInvoice freightInvoice, String fasInvoiceId);

	/**
	 * 生成付款申请书。
	 * @param freightInvoiceId
	 * @return
	 */
	public FasPay toInvoicePay(String[] freightInvoiceIds, User proposer);

	/**
	 * 保存付款申请书
	 * @param fasPayInvoice
	 */
	//public String doneInvoicePay(FasPay fasPay);

	/**
	 * 根据一些过滤条件进行导出
	 * @param freightInvoice
	 * @param orderNumber
	 * @param partName
	 * @param invoiceNumber
	 * @param netDayTimeStart
	 * @param netDayTimeEnd
	 * @param releaseTimeStart
	 * @param releaseTimeEnd
	 * @return
	 */
	public List<List<String>> toExportByFilterText(FreightInvoice freightInvoice, String orderNumber, String partName,
			String invoiceNumber, String netDayTimeStart, String netDayTimeEnd, String releaseTimeStart, String releaseTimeEnd);

	/**
	 * 预报导出
	 * @param freightInvoiceIds
	 * @return
	 */
	public List<List<String>> toForecastExport(String[] freightInvoiceIds);

	/**
	 * 取消付款审核 状态 待销账>付款初审中
	 * @param freightInvoiceIds
	 * @return
	 */
	public boolean backAuditInvoice(String[] freightInvoiceIds);

	/**
	 * 根据批量冲抵之后生成的开票信息进行导出所有相关冲抵
	 * @param freightInvoiceId
	 * @return
	 */
	public List<List<String>> toBatchOffsetExport(String freightInvoiceId);
}

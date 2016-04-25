package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasDue;

public interface FasDueService {
	public List<FasDue> getAll();

	public List<FasDue> queryForList(FasDue fasDue);

	public FasDue queryForOne(FasDue fasDue);

	public PageView<FasDue> query(PageView<FasDue> pageView, FasDue fasDue);

	public void add(FasDue fasDue);

	public void delete(String id);

	public void modify(FasDue fasDue);

	public FasDue getById(String id);
	/**
	 * 收款销账
	 * @param fasDueId 银行收款
	 * @param partName 通过单位来过滤
	 * @return
	 */
	public Map<String, Object> toAddReconcile(String fasDueId, String partName, String invoiceNumber);
	/**
	 * @param sourceId freightInvoiceId
	 * @param targetId 银行收款
	 * @param moneyCount 金额
	 */
	public boolean doneAddReconcile(String sourceId, String targetId, double moneyCount);
	/**
	 * 删除销账
	 * @param fasReconcileId
	 */
	public void doneDeleteReconcile(String[] fasReconcileIds);
	/**
	 * 确认收款，实际金额将于收据金额一致
	 * @param fasDueId
	 */
	public boolean doneConfirmDue(String[] fasDueIds);
	/**
	 * 取消收款
	 * @param fasDueIds
	 * @return
	 */
	public boolean doneRecallDue(String[] fasDueIds);
	
	/**
	 * 强制销账，对于有汇率差折算之后有余数的进行强制销账，将状态销账中改为已销账
	 * @param fasDueIds
	 * @return
	 */
	public boolean doneForceReconcile(String[] fasDueIds);
	/**
	 * 取消销账，将已销账该为销账中
	 * @param fasDueIds
	 * @return
	 */
	public boolean doneRecallReconcile(String[] fasDueIds);
	
	public String getNextDueNumber();

	/**
	 * 导出银行收款明细数据
	 * @param fasDueId
	 * @return
	 */
	public List<List<String>> toExport(String[] fasDueIds);
}

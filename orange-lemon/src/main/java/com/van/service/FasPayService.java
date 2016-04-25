package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasPay;

public interface FasPayService {
	public List<FasPay> getAll();

	public List<FasPay> queryForList(FasPay fasPay);

	public FasPay queryForOne(FasPay fasPay);

	public PageView query(PageView pageView, FasPay fasPay);

	public void add(FasPay fasPay);

	public void delete(String id);

	public void modify(FasPay fasPay);

	public FasPay getById(String id);
	/**
	 * 标记已付款
	 * @param fasPayId
	 */
	public void confirmPay(String[] fasPayIds);
	/**
	 * 添加销账
	 * @param fasPayId
	 * @return
	 */
	public Map<String, Object> toAddReconcile(String fasPayId);
	/**
	 * 销账
	 * @param freightInvoiceId
	 * @param fasPayId
	 * @param moneyCount
	 * @return
	 */
	public boolean doneAddReconcile(String freightInvoiceId, String fasPayId, double moneyCount);
	/**
	 * 删除销账
	 * @param fasReconcileId
	 */
	public void doneDeleteReconcile(String[] fasReconcileId);
	/**
	 * 初始化付款申请信息，主要是用于生成具体的文件
	 * @param fasPayId
	 */
	public void initFasPay(String fasPayId);
	
	public String getNextPayNumber();
}

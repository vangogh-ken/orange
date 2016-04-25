package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasReconcile;

public interface FasReconcileService {
	public List<FasReconcile> getAll();

	public List<FasReconcile> queryForList(FasReconcile fasReconcile);

	public FasReconcile queryForOne(FasReconcile fasReconcile);

	public PageView query(PageView pageView, FasReconcile fasReconcile);

	public void add(FasReconcile fasReconcile);

	public void delete(String id);

	public void modify(FasReconcile fasReconcile);

	public FasReconcile getById(String id);
	/**
	 * 获取付款申请的销账记录
	 * @param fasPayId
	 * @return
	 */
	public List<List<String>> toExportByFasPayID(String fasPayId);

	/**
	 * 获取银行收款的销账记录
	 * @param fasDueId
	 * @return
	 */
	public List<List<String>> toExportByFasDueID(String fasDueId);

	/**
	 * 对某个开票任务进行销账，对开票任务的销账金额进行处理；此处要注意开票任务与销账之间的币种可能不一致；
	 * @param fasReconcile
	 * @param freightInvoiceId 开票任务ID
	 * @param fasAccountId 银行账户ID
	 */
	//public void addReconcile(FasReconcile fasReconcile, String freightInvoiceId, String fasAccountId);

	/**
	 * 删除关联的销账信息
	 * @param fasReconcileIds
	 */
	//public void removeReconcile(String[] fasReconcileIds);
}

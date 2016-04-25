package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightInvoice;

public interface FreightInvoiceDao extends BaseDao<FreightInvoice> {
	/**
	 * 获取与该销账冲抵过的
	 * @param freightInvoiceId
	 * @return
	 */
	public List<FreightInvoice> getHasOffsetInvoice(String freightInvoiceId);
	
	/**
	 * 收款销账相关发票
	 * @param fasDueId
	 * @return
	 */
	public List<FreightInvoice> getByFasDueId(String fasDueId);
	
	/**
	 * 付款销账相关发票
	 * @param fasPayId
	 * @return
	 */
	public List<FreightInvoice> getByFasPayId(String fasPayId);

	/**
	 * 获取账单相关开票
	 * @param freightStatementId
	 * @return
	 */
	public List<FreightInvoice> getByFreightStatementId(String freightStatementId);
}
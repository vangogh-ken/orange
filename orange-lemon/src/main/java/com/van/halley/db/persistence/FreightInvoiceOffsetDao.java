package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;

public interface FreightInvoiceOffsetDao extends BaseDao<FreightInvoiceOffset> {
	/**
	 * 删除发票冲抵
	 * @param freightInvoiceOffset
	 */
	public void deleteInvoiceOffset(FreightInvoiceOffset freightInvoiceOffset);
}
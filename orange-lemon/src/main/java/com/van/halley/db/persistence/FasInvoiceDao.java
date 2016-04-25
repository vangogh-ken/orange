package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.FasInvoice;

public interface FasInvoiceDao extends BaseDao<FasInvoice> {
	/**
	 * 获取最近的发票号
	 * @param fasInvoice 需要信息：类型, 状态
	 * @return
	 */
	public FasInvoice getFasInvoiceProximate(FasInvoice fasInvoice);
}
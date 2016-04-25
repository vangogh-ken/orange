package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasInvoice;

public interface FasInvoiceService {
	public List<FasInvoice> getAll();

	public List<FasInvoice> queryForList(FasInvoice fasInvoice);

	public FasInvoice queryForOne(FasInvoice fasInvoice);

	public PageView query(PageView pageView, FasInvoice fasInvoice);

	public void add(FasInvoice fasInvoice);

	public void delete(String id);

	public void modify(FasInvoice fasInvoice);

	public FasInvoice getById(String id);
	
	/**
	 * 批量添加发票
	 * @param fasInvoice
	 * @param startInvoiceNumber
	 * @param endInvoiceNumber
	 * @return
	 */
	public int addBatch(FasInvoice fasInvoice, String startInvoiceNumber, String endInvoiceNumber);
	
	/**
	 * 获取需拟发票号
	 * @return
	 */
	public String getNextVirtualInvoiceNumber();
	/**
	 * 获取批量冲抵新生成的发票号
	 * @return
	 */
	public String getNextOffsetInvoiceNumber();
}

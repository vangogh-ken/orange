package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FasAccount;

public interface FasAccountService {
	public List<FasAccount> getAll();

	public List<FasAccount> queryForList(FasAccount fasAccount);

	public FasAccount queryForOne(FasAccount fasAccount);

	public PageView<FasAccount> query(PageView<FasAccount> pageView, FasAccount fasAccount);

	public void add(FasAccount fasAccount);

	public void delete(String id);

	public void modify(FasAccount fasAccount);

	public FasAccount getById(String id);

	/**
	 * 获取自己的银行账户
	 * @return
	 */
	public List<FasAccount> getOwnAccount();
	/**
	 * 获取某单位的银行账户
	 * @return
	 */
	public List<FasAccount> getPartAccount(String freightPartId);

	public void toImport(List<List<String>> values);

	public List<List<String>> toExport(List<FasAccount> fasAccounts);
}

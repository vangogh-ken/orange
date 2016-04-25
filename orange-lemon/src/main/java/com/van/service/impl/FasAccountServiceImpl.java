package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasAccountDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.entity.FasAccount;
import com.van.halley.util.StringUtil;
import com.van.service.FasAccountService;

@Transactional
@Service("fasAccountService")
public class FasAccountServiceImpl implements FasAccountService {
	@Autowired
	private FasAccountDao fasAccountDao;
	@Autowired
	private FreightPartDao freightPartDao;

	public List<FasAccount> getAll() {
		return fasAccountDao.getAll();
	}

	public List<FasAccount> queryForList(FasAccount fasAccount) {
		return fasAccountDao.queryForList(fasAccount);
	}

	public FasAccount queryForOne(FasAccount fasAccount) {
		return fasAccountDao.queryForOne(fasAccount);
	}

	public PageView query(PageView pageView, FasAccount fasAccount) {
		List<FasAccount> list = fasAccountDao.query(pageView, fasAccount);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasAccount fasAccount) {
		fasAccountDao.add(fasAccount);
	}

	public void delete(String id) {
		fasAccountDao.delete(id);
	}

	public void modify(FasAccount fasAccount) {
		fasAccountDao.modify(fasAccount);
	}

	public FasAccount getById(String id) {
		return fasAccountDao.getById(id);
	}

	@Override
	public List<FasAccount> getOwnAccount() {
		FasAccount filter = new FasAccount();
		filter.setStatus("公司账户");
		return fasAccountDao.queryForList(filter);
	}

	@Override
	public List<FasAccount> getPartAccount(String freightPartId) {
		FasAccount filter = new FasAccount();
		filter.setFreightPart(freightPartDao.getById(freightPartId));
		return fasAccountDao.queryForList(filter);
	}

	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 4 || freightPartDao.getByPartName(singleValue.get(0)) == null
					|| StringUtil.isNullOrEmpty(singleValue.get(2)) || StringUtil.isNullOrEmpty(singleValue.get(3))){
				continue;
			}
			FasAccount fasAccount = new FasAccount();
			fasAccount.setFreightPart(freightPartDao.getByPartName(singleValue.get(0)));
			fasAccount.setCurrency(singleValue.get(1));
			fasAccount.setBankName(singleValue.get(2));
			fasAccount.setAccountNumber(singleValue.get(3));
			fasAccount.setCreateTime(new Date());
			fasAccountDao.add(fasAccount);
		}
	}

	@Override
	public List<List<String>> toExport(List<FasAccount> fasAccounts) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FasAccount fasAccount : fasAccounts){
			if(fasAccount.getFreightPart() == null){
				continue;
			}
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(fasAccount.getFreightPart().getPartName());
			singleValue.add(fasAccount.getCurrency());
			singleValue.add(fasAccount.getBankName());
			singleValue.add(fasAccount.getAccountNumber());
			values.add(singleValue);
		}
		return values;
	}
}

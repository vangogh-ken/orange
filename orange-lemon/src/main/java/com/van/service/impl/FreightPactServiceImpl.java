package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightPactDao;
import com.van.halley.db.persistence.entity.FreightPact;
import com.van.halley.util.StringUtil;
import com.van.service.FreightPactService;

@Transactional
@Service("freightPactService")
public class FreightPactServiceImpl implements FreightPactService {
	@Autowired
	private FreightPactDao freightPactDao;

	public List<FreightPact> getAll() {
		return freightPactDao.getAll();
	}

	public List<FreightPact> queryForList(FreightPact freightPact) {
		return freightPactDao.queryForList(freightPact);
	}

	public PageView query(PageView pageView, FreightPact freightPact) {
		List<FreightPact> list = freightPactDao.query(pageView, freightPact);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightPact freightPact) {
		freightPactDao.add(freightPact);
	}

	public void delete(String id) {
		freightPactDao.delete(id);
	}

	public void modify(FreightPact freightPact) {
		freightPactDao.modify(freightPact);
	}

	public FreightPact getById(String id) {
		return freightPactDao.getById(id);
	}

	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 12){
				continue;
			}
			FreightPact freightPact = new FreightPact();
			freightPact.setPactNumber(singleValue.get(0));
			freightPact.setPactTitle(singleValue.get(1));
			freightPact.setPactType(singleValue.get(2));
			freightPact.setPartA(singleValue.get(3));
			freightPact.setPartB(singleValue.get(4));
			freightPact.setPartC(singleValue.get(5));
			freightPact.setTransactor(singleValue.get(6));
			freightPact.setSignDate(StringUtil.convert(singleValue.get(7)));
			freightPact.setEffectDate(StringUtil.convert(singleValue.get(8)));
			freightPact.setCutOffDate(StringUtil.convert(singleValue.get(9)));
			freightPact.setPayType(singleValue.get(10));
			freightPact.setPactContent(singleValue.get(11));
			freightPact.setCreateTime(new Date());
			freightPact.setStatus("有效");
			freightPactDao.add(freightPact);
		}

	}

	@Override
	public List<List<String>> toExport(List<FreightPact> freightPacts) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightPact freightPact : freightPacts){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightPact.getPactNumber());
			singleValue.add(freightPact.getPactTitle());
			singleValue.add(freightPact.getPactType());
			singleValue.add(freightPact.getPartA());
			singleValue.add(freightPact.getPartB());
			singleValue.add(freightPact.getPartC());
			singleValue.add(freightPact.getTransactor());
			singleValue.add(StringUtil.parseDateTime(freightPact.getSignDate()));
			singleValue.add(StringUtil.parseDateTime(freightPact.getEffectDate()));
			singleValue.add(StringUtil.parseDateTime(freightPact.getCutOffDate()));
			singleValue.add(freightPact.getPactType());
			singleValue.add(freightPact.getPactContent());
			
			values.add(singleValue);
		}
		return values;
	}
}

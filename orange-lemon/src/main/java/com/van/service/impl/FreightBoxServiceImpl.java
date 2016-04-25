package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightBoxDao;
import com.van.halley.db.persistence.entity.FreightBox;
import com.van.service.FreightBoxService;

@Transactional
@Service("freightBoxService")
public class FreightBoxServiceImpl implements FreightBoxService {
	@Autowired
	private FreightBoxDao freightBoxDao;

	public List<FreightBox> getAll() {
		return freightBoxDao.getAll();
	}

	public List<FreightBox> queryForList(FreightBox freightBox) {
		return freightBoxDao.queryForList(freightBox);
	}

	public PageView query(PageView pageView, FreightBox freightBox) {
		List<FreightBox> list = freightBoxDao.query(pageView, freightBox);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightBox freightBox) {
		freightBoxDao.add(freightBox);
	}

	public void delete(String id) {
		freightBoxDao.delete(id);
	}

	public void modify(FreightBox freightBox) {
		freightBoxDao.modify(freightBox);
	}

	public FreightBox getById(String id) {
		return freightBoxDao.getById(id);
	}

	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 1){
				
			}
			FreightBox freightBox = new FreightBox();
			freightBox.setBoxSource(singleValue.get(0));
			freightBox.setBoxNumber(singleValue.get(1));
			freightBox.setBoxType(singleValue.get(2));
			freightBox.setBoxBelong(singleValue.get(3));
			freightBox.setStatus(singleValue.get(4));
			freightBox.setCreateTime(new Date());
			freightBoxDao.add(freightBox);
		}
		
	}

	@Override
	public List<List<String>> toExport(List<FreightBox> freightBoxs) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightBox freightBox : freightBoxs){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightBox.getBoxSource());
			singleValue.add(freightBox.getBoxNumber());
			singleValue.add(freightBox.getBoxType());
			singleValue.add(freightBox.getBoxBelong());
			singleValue.add(freightBox.getStatus());
			values.add(singleValue);
		}
		return values;
	}
}

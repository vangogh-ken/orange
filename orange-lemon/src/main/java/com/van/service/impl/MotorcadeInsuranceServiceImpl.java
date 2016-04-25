package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadeInsuranceDao;
import com.van.halley.db.persistence.entity.MotorcadeInsurance;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeInsuranceService;

@Transactional
@Service("motorcadeInsuranceService")
public class MotorcadeInsuranceServiceImpl implements MotorcadeInsuranceService {
	@Autowired
	private MotorcadeInsuranceDao motorcadeInsuranceDao;

	public List<MotorcadeInsurance> getAll() {
		return motorcadeInsuranceDao.getAll();
	}

	public List<MotorcadeInsurance> queryForList(MotorcadeInsurance motorcadeInsurance) {
		return motorcadeInsuranceDao.queryForList(motorcadeInsurance);
	}

	public MotorcadeInsurance queryForOne(MotorcadeInsurance motorcadeInsurance) {
		return motorcadeInsuranceDao.queryForOne(motorcadeInsurance);
	}

	public PageView<MotorcadeInsurance> query(PageView<MotorcadeInsurance> pageView, MotorcadeInsurance motorcadeInsurance) {
		List<MotorcadeInsurance> list = motorcadeInsuranceDao.query(pageView, motorcadeInsurance);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeInsurance motorcadeInsurance) {
		motorcadeInsuranceDao.add(motorcadeInsurance);
	}

	public void delete(String id) {
		motorcadeInsuranceDao.delete(id);
	}

	public void modify(MotorcadeInsurance motorcadeInsurance) {
		motorcadeInsuranceDao.modify(motorcadeInsurance);
	}

	public MotorcadeInsurance getById(String id) {
		return motorcadeInsuranceDao.getById(id);
	}

	@Override
	public List<List<String>> doneBatchExport(String[] motorcadeInsuranceIds) {
		PageView pageView = new PageView(motorcadeInsuranceIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String motorcadeInsuranceId : motorcadeInsuranceIds){
			filterText.append("'" +motorcadeInsuranceId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<MotorcadeInsurance> motorcadeInsurances = motorcadeInsuranceDao.query(pageView, new MotorcadeInsurance());
		List<List<String>> values = new ArrayList<List<String>>();
		for(MotorcadeInsurance motorcadeInsurance : motorcadeInsurances){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(motorcadeInsurance.getMotorcadeTruck().getHeadstockNumber());
			singleValue.add(StringUtil.parseTimeStamp(motorcadeInsurance.getPurchaseTime()));
			singleValue.add(motorcadeInsurance.getInsuranceType());
			singleValue.add(motorcadeInsurance.getInsuranceCompany());
			singleValue.add(StringUtil.parseDateTime(motorcadeInsurance.getStartTime()));
			singleValue.add(StringUtil.parseDateTime(motorcadeInsurance.getEndTime()));
			singleValue.add(String.valueOf(motorcadeInsurance.getMoneyCount()));
			singleValue.add(String.valueOf(motorcadeInsurance.getMoneyCount()/12));
			singleValue.add(motorcadeInsurance.getFasInvoiceType().getTypeName());
			singleValue.add(motorcadeInsurance.getDescn());
			
			values.add(singleValue);
		}
		
		return values;
	}
}

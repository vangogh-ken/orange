package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MotorcadePetrolDao;
import com.van.halley.db.persistence.entity.MotorcadePetrol;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadePetrolService;

@Transactional
@Service("motorcadePetrolService")
public class MotorcadePetrolServiceImpl implements MotorcadePetrolService {
	@Autowired
	private MotorcadePetrolDao motorcadePetrolDao;

	public List<MotorcadePetrol> getAll() {
		return motorcadePetrolDao.getAll();
	}

	public List<MotorcadePetrol> queryForList(MotorcadePetrol motorcadePetrol) {
		return motorcadePetrolDao.queryForList(motorcadePetrol);
	}

	public MotorcadePetrol queryForOne(MotorcadePetrol motorcadePetrol) {
		return motorcadePetrolDao.queryForOne(motorcadePetrol);
	}

	public PageView<MotorcadePetrol> query(PageView<MotorcadePetrol> pageView, MotorcadePetrol motorcadePetrol) {
		List<MotorcadePetrol> list = motorcadePetrolDao.query(pageView,
				motorcadePetrol);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadePetrol motorcadePetrol) {
		if(StringUtil.isNullOrEmpty(motorcadePetrol.getId())){
			motorcadePetrol.setId(StringUtil.getUUID());
		}
		motorcadePetrolDao.add(motorcadePetrol);
		
		if(motorcadePetrol.getFuelConsumptionLast() == 0 || motorcadePetrol.getFuelConsumptionMonth() == 0){
			Map<String, Object> map = motorcadePetrolDao.getFuelConsumption(motorcadePetrol.getId());
			if(map == null){
				motorcadePetrol.setFuelConsumptionLast(0);
				motorcadePetrol.setFuelConsumptionMonth(0);
			}else{
				motorcadePetrol.setFuelConsumptionLast(map.get("fuelConsumptionLast") == null ? 0 : Double.parseDouble(String.format("%.2f", (Double)map.get("fuelConsumptionLast"))));
				motorcadePetrol.setFuelConsumptionMonth(map.get("fuelConsumptionMonth") == null ? 0 : Double.parseDouble(String.format("%.2f", (Double)map.get("fuelConsumptionMonth"))));
			}
			motorcadePetrolDao.modify(motorcadePetrol);
		}
	}

	public void delete(String id) {
		motorcadePetrolDao.delete(id);
	}

	public void modify(MotorcadePetrol motorcadePetrol) {
		motorcadePetrolDao.modify(motorcadePetrol);
	}

	public MotorcadePetrol getById(String id) {
		return motorcadePetrolDao.getById(id);
	}

	@Override
	public List<List<String>> doneBatchExport(String[] motorcadePetrolIds) {
		PageView pageView = new PageView(motorcadePetrolIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String motorcadePetrolId : motorcadePetrolIds){
			filterText.append("'" +motorcadePetrolId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<MotorcadePetrol> motorcadePetrols = motorcadePetrolDao.query(pageView, new MotorcadePetrol());
		List<List<String>> values = new ArrayList<List<String>>();
		for(MotorcadePetrol motorcadePetrol : motorcadePetrols){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(motorcadePetrol.getMotorcadeTruck().getHeadstockNumber());
			singleValue.add(motorcadePetrol.getMotorcadeDriver().getDisplayName());
			singleValue.add(String.valueOf(motorcadePetrol.getLubricateCapacity()));
			singleValue.add(StringUtil.parseDateTime(motorcadePetrol.getLubricateTime()));
			singleValue.add(String.valueOf(motorcadePetrol.getKilometerCount()));
			singleValue.add(String.valueOf(motorcadePetrol.getFuelConsumptionLast()));
			singleValue.add(String.valueOf(motorcadePetrol.getFuelConsumptionMonth()));
			singleValue.add(String.valueOf(motorcadePetrol.getMoneyCount()));
			singleValue.add(motorcadePetrol.getFasInvoiceType().getTypeName());
			singleValue.add(motorcadePetrol.getDescn());
			
			values.add(singleValue);
		}
		
		return values;
	}
}

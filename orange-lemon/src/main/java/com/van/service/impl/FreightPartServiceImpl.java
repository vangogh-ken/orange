package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightExpenseTypeDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.FreightPriceDao;
import com.van.halley.db.persistence.entity.FreightExpenseType;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.db.persistence.entity.FreightPrice;
import com.van.service.FreightPartService;

@Transactional
@Service("freightPartService")
public class FreightPartServiceImpl implements FreightPartService {
	private static Logger logger = LoggerFactory.getLogger(FreightPartServiceImpl.class);
	@Autowired
	private FreightPartDao freightPartDao;
	@Autowired
	private FreightPriceDao freightPriceDao;
	@Autowired
	private FreightExpenseTypeDao freightExpenseTypeDao;
	
	
	//@PostConstruct 初始化成本信息就已经加载了单位缓存
	public void init(){
		refreshCache();
	}
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		List<FreightPart> freightParts = freightPartDao.getAll();
		cacheFreightPart.put(null, freightParts);
		List<FreightPrice> freightPrices = freightPriceDao.getByOriginalStatus();
		List<FreightExpenseType> expenseTypes = freightExpenseTypeDao.getAll();
		for(FreightExpenseType freightExpenseType : expenseTypes){
			String freightExpenseTypeId = freightExpenseType.getId();
			String[] boxTypes = {"票", "20'FR","20'GP","20'HC","20'OT","20'RF","40'FR","40'GP","40'HC","40'OT","40'RF","45'HC"};
			for(String boxType : boxTypes){
				List<FreightPart> list = new ArrayList<FreightPart>();
				List<String> partIds = new ArrayList<String>();
				for(FreightPrice freightPrice : freightPrices){
					if(freightPrice.getFreightExpenseType() != null 
							&& freightExpenseTypeId.equals(freightPrice.getFreightExpenseType().getId())
							&& boxType.equals(freightPrice.getCountUnit())
							&& freightPrice.getFreightPart() != null 
							&& !partIds.contains(freightPrice.getFreightPart().getId())){
						list.add(freightPrice.getFreightPart());
						partIds.add(freightPrice.getFreightPart().getId());
					}
				}
				if("票".equals(boxType)){
					cacheFreightPart.put(freightExpenseType.getId(), list);
				}else{
					cacheFreightPart.put(freightExpenseType.getId() + boxType, list);
				}
			}
		}
		logger.info("FreightPart cache loaded!");
	}

	public List<FreightPart> getAll() {
		return freightPartDao.getAll();
	}

	public List<FreightPart> queryForList(FreightPart freightPart) {
		return freightPartDao.queryForList(freightPart);
	}

	public PageView query(PageView pageView, FreightPart freightPart) {
		List<FreightPart> list = freightPartDao.query(pageView, freightPart);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightPart freightPart) {
		freightPartDao.add(freightPart);
		refreshCache();
	}

	public void delete(String id) {
		freightPartDao.delete(id);
		refreshCache();
	}

	public void modify(FreightPart freightPart) {
		freightPartDao.modify(freightPart);
		refreshCache();
	}

	public FreightPart getById(String id) {
		return freightPartDao.getById(id);
	}
	@Override
	public FreightPart getByOrgEntityId(String orgEntityId) {
		return freightPartDao.getByOrgEntityId(orgEntityId);
	}
	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 5){
				continue;
			}
			FreightPart filter = new FreightPart();
			filter.setPartName(singleValue.get(0));
			if(freightPartDao.count(filter) == 0){
				FreightPart freightPart = new FreightPart();
				freightPart.setPartName(singleValue.get(0));
				freightPart.setBankNameRmb(singleValue.get(1));
				freightPart.setBankAccountRmb(singleValue.get(2));
				freightPart.setBankNameDollar(singleValue.get(3));
				freightPart.setBankAccountDollar(singleValue.get(4));
				freightPart.setCreateTime(new Date());
				freightPart.setInternal("F");
				freightPartDao.add(freightPart);
			}
		}
	}
	@Override
	public List<List<String>> toExport(List<FreightPart> freightParts) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightPart freightPart : freightParts){
			if("T".equals(freightPart.getInternal())){
				continue;
			}
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightPart.getPartName());
			
			singleValue.add(freightPart.getBankNameRmb());
			singleValue.add(freightPart.getBankAccountRmb());
			
			singleValue.add(freightPart.getBankNameDollar());
			singleValue.add(freightPart.getBankAccountDollar());
			values.add(singleValue);
		}
		return values;
	}
	@Override
	public FreightPart getByPartName(String partName) {
		FreightPart filter = new FreightPart();
		filter.setPartName(partName);
		return freightPartDao.queryForOne(filter);
	}
}

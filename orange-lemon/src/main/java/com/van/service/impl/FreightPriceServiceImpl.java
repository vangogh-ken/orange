package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.FreightExpenseTypeDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.FreightPriceDao;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.db.persistence.entity.FreightPrice;
import com.van.halley.util.StringUtil;
import com.van.service.FreightPartService;
import com.van.service.FreightPriceService;

@Transactional
@Service("freightPriceService")
public class FreightPriceServiceImpl implements FreightPriceService {
	private static Logger logger = LoggerFactory.getLogger(FreightPriceServiceImpl.class);
	@Autowired
	private FreightPriceDao freightPriceDao;
	@Autowired
	private FreightPartDao freightPartDao;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;
	@Autowired
	private FreightExpenseTypeDao freightExpenseTypeDao;
	
	@PostConstruct
	public void init(){
		refreshCache();
	}
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		freightPartService.refreshCache();//刷新单位缓存
		List<FreightPart> freightParts = freightPartDao.getAll();
		//List<FreightPrice> freightPrices = freightPriceDao.getForCache();
		List<FreightPrice> freightPrices = freightPriceDao.getByOriginalStatus();//获取带有标记的成本信息存入缓存
		
		for(FreightPart freightPart : freightParts){
			String freightPartId = freightPart.getId();
			//priceId FreightPrice
			Map<String, FreightPrice> priceMap = new HashMap<String, FreightPrice>();
			//expenseTypeId priceId
			Map<String, String> expenseTypeMap = new HashMap<String, String>();
			
			for(FreightPrice freightPrice : freightPrices){
				if(freightPrice.getFreightPart() == null){
					continue;
				}
				if(freightPartId.equals(freightPrice.getFreightPart().getId())){
					if(freightPrice.getFreightExpenseType() != null){
						String priceId = expenseTypeMap.get(freightPrice.getFreightExpenseType().getId());
						if(priceId == null){
							expenseTypeMap.put(freightPrice.getFreightExpenseType().getId(), priceId);
							priceMap.put(freightPrice.getId(), freightPrice);
						}else{
							//如果之前保存的价格新建时间较早,则更新数据,否则就跳过
							if(priceMap.get(priceId).getCreateTime().after(freightPrice.getCreateTime())){
								expenseTypeMap.put(freightPrice.getFreightExpenseType().getId(), freightPrice.getId());
								priceMap.put(freightPrice.getId(), freightPrice);
							}
						}
					}
				}
			}
			List<FreightPrice> list = new ArrayList<FreightPrice>();
			list.addAll(priceMap.values());
			cacheFreightPrice.put(freightPartId, list);
		}
		cacheFreightPrice.put(null, freightPrices);//将所有的数据也缓存起来
		logger.info("FreightPrice cache loaded!");
	}
	
	public List<FreightPrice> getAll() {
		return freightPriceDao.getAll();
	}

	public List<FreightPrice> queryForList(FreightPrice freightPrice) {
		return freightPriceDao.queryForList(freightPrice);
	}

	public PageView<FreightPrice> query(PageView<FreightPrice> pageView, FreightPrice freightPrice) {
		List<FreightPrice> list = freightPriceDao.query(pageView, freightPrice);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightPrice freightPrice) {
		freightPriceDao.add(freightPrice);
		
		if("O".equals(freightPrice.getStatus())){
			refreshCache();
		}
	}

	public void delete(String id) {
		FreightPrice freightPrice = freightPriceDao.getById(id);
		
		freightPriceDao.delete(id);
		
		if("O".equals(freightPrice.getStatus())){
			refreshCache();
		}
	}

	public void modify(FreightPrice freightPrice) {
		freightPriceDao.modify(freightPrice);
		
		freightPrice = freightPriceDao.getById(freightPrice.getId());
		if("O".equals(freightPrice.getStatus())){
			refreshCache();
		}
	}

	public FreightPrice getById(String id) {
		return freightPriceDao.getById(id);
	}
	@Override
	public void remove(String[] freightPriceIds) {
		boolean flag = false;
		
		for(String freightPriceId : freightPriceIds){
			FreightPrice freightPrice = freightPriceDao.getById(freightPriceId);
			if("O".equals(freightPrice.getStatus())){
				flag = true;
			}
			freightPriceDao.delete(freightPriceId);
		}
		
		if(flag){
			refreshCache();
		}
	}
	@Override
	public void toImport(List<List<String>> values) {
		for(List<String> singleValue : values){
			if(singleValue.size() != 7){
				continue;
			}
			FreightPrice freightPrice = new FreightPrice();
			freightPrice.setFreightExpenseType(freightExpenseTypeDao.getByTypeName(singleValue.get(0)));
			freightPrice.setFreightPart(freightPartDao.getByPartName(singleValue.get(1)));
			freightPrice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName(singleValue.get(2)));
			freightPrice.setCountUnit(singleValue.get(3));
			freightPrice.setCurrency(singleValue.get(4));
			freightPrice.setMoneyCount(Double.parseDouble(StringUtil.isNullOrEmpty(singleValue.get(5)) ? "0" : singleValue.get(5)));
			freightPrice.setRegular(singleValue.get(6));
			freightPrice.setCreateTime(new Date());
			
			if("票".equals(freightPrice.getCountUnit())){
				freightPriceDao.add(freightPrice);
			}else if("箱".equals(freightPrice.getCountUnit())){
				String[] boxType = {"20'FR","20'GP","20'HC","20'OT","20'RF","40'FR","40'GP","40'HC","40'OT","40'RF","45'HC"};
				for(int i=0, len=boxType.length; i<len; i++){
					freightPrice.setCountUnit(boxType[i]);
					freightPriceDao.add(freightPrice);
				}
			}else if("GP/HC".equals(freightPrice.getCountUnit())){
				freightPrice.setCountUnit("40'GP");
				freightPriceDao.add(freightPrice);
				
				freightPrice.setCountUnit("40'HC");
				freightPriceDao.add(freightPrice);
			}else{
				freightPriceDao.add(freightPrice);
			}
			
		}
		
		refreshCache();
	}
	@Override
	public List<List<String>> toExport(List<FreightPrice> freightPrices) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightPrice freightPrice : freightPrices){
			if(freightPrice.getFreightPart() == null || freightPrice.getFreightExpenseType() == null
					|| freightPrice.getFasInvoiceType() == null){
				continue;
			}
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightPrice.getFreightExpenseType().getTypeName());
			singleValue.add(freightPrice.getFreightPart().getPartName());
			singleValue.add(freightPrice.getFasInvoiceType().getTypeName());
			singleValue.add(freightPrice.getCountUnit());
			singleValue.add(freightPrice.getCurrency());
			singleValue.add(freightPrice.getMoneyCount() + "");
			singleValue.add(freightPrice.getRegular());
			values.add(singleValue);
		}
		return values;
	}
	@Override
	public void doneAddBatch(FreightPrice freightPrice, double moneyCount20,
			double moneyCount40) {
		String[] boxTypes = {"20'FR","20'GP","20'HC","20'OT","20'RF","40'FR","40'GP","40'HC","40'OT","40'RF","45'HC"};
		for(String boxType : boxTypes){
			if(boxType.startsWith("2")){
				freightPrice.setCountUnit(boxType);
				freightPrice.setMoneyCount(moneyCount20);
			}else if(boxType.startsWith("4")){
				freightPrice.setCountUnit(boxType);
				freightPrice.setMoneyCount(moneyCount40);
			}
			
			freightPriceDao.add(freightPrice);
		}
		
		refreshCache();
	}
	@Override
	public FreightPrice getOriginalPrice(String freightPartId, String freightExpenseTypeId, String countUnit) {
		return freightPriceDao.getOriginalPrice(freightPartId, freightExpenseTypeId, countUnit);
	}
	
	@Override
	public List<FreightPrice> getByOriginalTime() {
		return freightPriceDao.getByOriginalTime();
	}
	
	@Override
	public List<FreightPrice> getByOriginalStatus() {
		return freightPriceDao.getByOriginalStatus();
	}
}

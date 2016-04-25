package com.van.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightSealDao;
import com.van.halley.db.persistence.entity.FreightSeal;
import com.van.service.FreightSealService;

@Transactional
@Service("freightSealService")
public class FreightSealServiceImpl implements FreightSealService {
	private static Logger logger = LoggerFactory.getLogger(FreightSealServiceImpl.class);
	@Autowired
	private FreightSealDao freightSealDao;

	public List<FreightSeal> getAll() {
		return freightSealDao.getAll();
	}

	public List<FreightSeal> queryForList(FreightSeal freightSeal) {
		return freightSealDao.queryForList(freightSeal);
	}

	public PageView query(PageView pageView, FreightSeal freightSeal) {
		List<FreightSeal> list = freightSealDao.query(pageView, freightSeal);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightSeal freightSeal) {
		freightSealDao.add(freightSeal);
	}

	public void delete(String id) {
		freightSealDao.delete(id);
	}

	public void modify(FreightSeal freightSeal) {
		freightSealDao.modify(freightSeal);
	}

	public FreightSeal getById(String id) {
		return freightSealDao.getById(id);
	}

	@Override
	public int addBatch(FreightSeal freightSeal, String startSealNumber, String endSealNumber) {
		try{
			if(startSealNumber.length() != endSealNumber.length() && Integer.parseInt(startSealNumber) >= Integer.parseInt(startSealNumber)){
				return 0;
			}else{
				freightSeal.setStatus("未使用");
				int length = startSealNumber.length();
				int start = Integer.parseInt(startSealNumber);
				int end = Integer.parseInt(endSealNumber);
				int count = 0;
				for(int i=start; i<end+1; i++){
					StringBuilder newSealNumber = new StringBuilder(String.valueOf(i));
					if(length > newSealNumber.length()){
						for(int j=0, len=length-newSealNumber.length(); j<len; j++){
							newSealNumber.insert(0, 0);
						}
					}
					freightSeal.setSealNumber(newSealNumber.toString());
					if(freightSealDao.count(freightSeal) > 0){
						break;
					}else{
						freightSeal.setSealNumber(newSealNumber.toString());
						freightSealDao.add(freightSeal);
						count++;
					}
				}
				
				return count;
			}
		}catch(NumberFormatException e){
			logger.info("批量导入发票出错: 起始发票号:{}, 终止发票号:{}, 错误信息{}", startSealNumber, endSealNumber, e);
			return 0;
		}
	}
}

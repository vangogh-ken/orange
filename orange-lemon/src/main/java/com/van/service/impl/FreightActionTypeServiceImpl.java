package com.van.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionTypeDao;
import com.van.halley.db.persistence.FreightMaintainTypeDao;
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.halley.db.persistence.entity.FreightMaintainType;
import com.van.service.FreightActionTypeService;

@Transactional
@Service("freightActionTypeService")
public class FreightActionTypeServiceImpl implements FreightActionTypeService {
	@Autowired
	private FreightActionTypeDao freightActionTypeDao;
	@Autowired
	private FreightMaintainTypeDao freightMaintainTypeDao;
	
	private static Logger logger = LoggerFactory.getLogger(FreightActionTypeServiceImpl.class);
	
	@PostConstruct
	public void init(){
		refreshCache();
	}
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		List<FreightMaintainType> freightMaintainTypes = freightMaintainTypeDao.getAll();
		for(FreightMaintainType freightMaintainType : freightMaintainTypes){
			cacheActionType.put(freightMaintainType.getId(), freightActionTypeDao.getByMaintainTypeId(freightMaintainType.getId()));
		}
		
		cacheActionType.put(null, freightActionTypeDao.getAll());
		
		logger.info("MaintainAction cache loaded!");
	}
	

	public List<FreightActionType> getAll() {
		return freightActionTypeDao.getAll();
	}

	public List<FreightActionType> queryForList(
			FreightActionType freightActionType) {
		return freightActionTypeDao.queryForList(freightActionType);
	}

	public PageView query(PageView pageView, FreightActionType freightActionType) {
		List<FreightActionType> list = freightActionTypeDao.query(pageView,
				freightActionType);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightActionType freightActionType) {
		freightActionTypeDao.add(freightActionType);
	}

	public void delete(String id) {
		freightActionTypeDao.delete(id);
	}

	public void modify(FreightActionType freightActionType) {
		freightActionTypeDao.modify(freightActionType);
	}

	public FreightActionType getById(String id) {
		return freightActionTypeDao.getById(id);
	}
	@Override
	public void deleteByMaintainTypeId(String freightMaintainTypeId) {
		freightActionTypeDao.deleteByMaintainTypeId(freightMaintainTypeId);
		
	}
}

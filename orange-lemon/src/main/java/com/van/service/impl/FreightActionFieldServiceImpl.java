package com.van.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionFieldDao;
import com.van.halley.db.persistence.FreightActionTypeDao;
import com.van.halley.db.persistence.entity.FreightActionField;
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.service.FreightActionFieldService;

@Transactional
@Service("freightActionFieldService")
public class FreightActionFieldServiceImpl implements FreightActionFieldService {
	@Autowired
	private FreightActionFieldDao freightActionFieldDao;
	@Autowired
	private FreightActionTypeDao freightActionTypeDao;
	
	private static Logger logger = LoggerFactory.getLogger(ValueDictionaryServiceImpl.class);
	
	@PostConstruct
	public void init(){
		refreshCache();
	}
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		List<FreightActionType> freightActionTypes = freightActionTypeDao.getAll();
		for(FreightActionType freightActionType : freightActionTypes){
			cacheActionField.put(freightActionType.getId(), freightActionFieldDao.getByFreightActionTypeId(freightActionType.getId()));
		}
		
		logger.info("ActionField cache loaded!");
	}

	public List<FreightActionField> getAll() {
		return freightActionFieldDao.getAll();
	}

	public List<FreightActionField> queryForList(
			FreightActionField freightActionField) {
		return freightActionFieldDao.queryForList(freightActionField);
	}

	public PageView query(PageView pageView,
			FreightActionField freightActionField) {
		List<FreightActionField> list = freightActionFieldDao.query(pageView,
				freightActionField);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightActionField freightActionField) {
		freightActionFieldDao.add(freightActionField);
		String freightActionTypeId = freightActionField.getFreightActionType().getId();
		FreightActionFieldService.cacheActionField.put(freightActionTypeId, freightActionFieldDao.getByFreightActionTypeId(freightActionTypeId));
	}

	public void delete(String id) {
		String freightActionTypeId = freightActionFieldDao.getById(id).getFreightActionType().getId();
		freightActionFieldDao.delete(id);
		FreightActionFieldService.cacheActionField.put(freightActionTypeId, freightActionFieldDao.getByFreightActionTypeId(freightActionTypeId));
	}

	public void modify(FreightActionField freightActionField) {
		freightActionFieldDao.modify(freightActionField);
		String freightActionTypeId = freightActionField.getFreightActionType().getId();
		FreightActionFieldService.cacheActionField.put(freightActionTypeId, freightActionFieldDao.getByFreightActionTypeId(freightActionTypeId));
	}

	public FreightActionField getById(String id) {
		return freightActionFieldDao.getById(id);
	}

	@Override
	public List<FreightActionField> getByActionTypeId(String actionTypeId) {
		//return freightActionFieldDao.getByActionTypeId(actionTypeId);
		return cacheActionField.get(actionTypeId);
	}

	@Override
	public List<Map<String, Object>> getFieldAndValueByActionId(String actionId) {
		return freightActionFieldDao.getFieldAndValueByActionId(actionId);
	}
}

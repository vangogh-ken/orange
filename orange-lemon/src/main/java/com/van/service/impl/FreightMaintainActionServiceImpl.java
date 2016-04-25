package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightMaintainActionDao;
import com.van.halley.db.persistence.FreightMaintainTypeDao;
import com.van.halley.db.persistence.entity.FreightMaintainAction;
import com.van.service.FreightActionTypeService;
import com.van.service.FreightMaintainActionService;

@Transactional
@Service("freightMaintainActionService")
public class FreightMaintainActionServiceImpl implements
		FreightMaintainActionService {
	@Autowired
	private FreightActionTypeService freightActionTypeService;
	@Autowired
	private FreightMaintainActionDao freightMaintainActionDao;
	@Autowired
	private FreightMaintainTypeDao freightMaintainTypeDao;

	public List<FreightMaintainAction> getAll() {
		return freightMaintainActionDao.getAll();
	}

	public List<FreightMaintainAction> queryForList(
			FreightMaintainAction freightMaintainAction) {
		return freightMaintainActionDao.queryForList(freightMaintainAction);
	}

	public PageView query(PageView pageView,
			FreightMaintainAction freightMaintainAction) {
		List<FreightMaintainAction> list = freightMaintainActionDao.query(
				pageView, freightMaintainAction);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightMaintainAction freightMaintainAction) {
		freightMaintainActionDao.add(freightMaintainAction);
		//freightActionTypeService.refreshCache();
	}

	public void delete(String id) {
		freightMaintainActionDao.delete(id);
		//freightActionTypeService.refreshCache();
	}

	public void modify(FreightMaintainAction freightMaintainAction) {
		freightMaintainActionDao.modify(freightMaintainAction);
		//freightActionTypeService.refreshCache();
	}

	public FreightMaintainAction getById(String id) {
		return freightMaintainActionDao.getById(id);
	}

	@Override
	public void deleteByMaintainAndAction(String freightMaintainTypeId,
			String freightActionTypeId) {
		freightMaintainActionDao.deleteByMaintainAndAction(freightMaintainTypeId, freightActionTypeId);
		//freightActionTypeService.refreshCache();
	}

	@Override
	public void batchAdd(List<FreightMaintainAction> list) {
		for(FreightMaintainAction freightMaintainAction : list){
			add(freightMaintainAction);
		}
		freightActionTypeService.refreshCache();
	}

	@Override
	public void batchDelete(List<FreightMaintainAction> list) {
		for(FreightMaintainAction freightMaintainAction : list){
			delete(freightMaintainAction.getId());
		}
		freightActionTypeService.refreshCache();
	}

	@Override
	public void batchDeleteByMaintainAndAction(List<FreightMaintainAction> list) {
		for(FreightMaintainAction freightMaintainAction : list){
			deleteByMaintainAndAction(freightMaintainAction.getFreightMaintainTypeId(), freightMaintainAction.getFreightActionTypeId());
		}
		freightActionTypeService.refreshCache();
	}
}

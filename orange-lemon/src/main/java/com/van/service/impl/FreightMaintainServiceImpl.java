package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionDao;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.FreightDelegateDao;
import com.van.halley.db.persistence.FreightMaintainDao;
import com.van.halley.db.persistence.FreightMaintainTypeDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.halley.db.persistence.entity.FreightDelegate;
import com.van.halley.db.persistence.entity.FreightMaintain;
import com.van.halley.util.StringUtil;
import com.van.service.FreightActionTypeService;
import com.van.service.FreightMaintainService;

@Transactional
@Service("freightMaintainService")
public class FreightMaintainServiceImpl implements FreightMaintainService {
	private static Logger logger = LoggerFactory.getLogger(FreightMaintainServiceImpl.class);
	
	@Autowired
	private FreightMaintainDao freightMaintainDao;
	@Autowired
	private FreightActionDao freightActionDao;
	@Autowired
	private FreightActionValueDao freightActionValueDao;
	@Autowired
	private FreightMaintainTypeDao freightMaintainTypeDao;
	@Autowired
	private FreightDelegateDao freightDelegateDao;
	@Autowired
	private FreightOrderDao freightOrderDao;

	public List<FreightMaintain> getAll() {
		return freightMaintainDao.getAll();
	}

	public List<FreightMaintain> queryForList(FreightMaintain freightMaintain) {
		return freightMaintainDao.queryForList(freightMaintain);
	}

	public PageView query(PageView pageView, FreightMaintain freightMaintain) {
		List<FreightMaintain> list = freightMaintainDao.query(pageView,
				freightMaintain);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightMaintain freightMaintain) {
		freightMaintainDao.add(freightMaintain);
	}

	public void delete(String id) {
		freightMaintainDao.delete(id);
	}

	public void modify(FreightMaintain freightMaintain) {
		freightMaintainDao.modify(freightMaintain);
	}

	public FreightMaintain getById(String id) {
		return freightMaintainDao.getById(id);
	}

	@Override
	public boolean doneRemoveMaintain(String[] freightMaintainIds) {
		boolean flag = true;
		for(String freightMaintainId : freightMaintainIds){
			FreightMaintain freightMaintain = freightMaintainDao.getById(freightMaintainId);
			FreightMaintain filter = new FreightMaintain();
			filter.setFreightOrder(freightMaintain.getFreightOrder());
			List<FreightMaintain> list = null;
			if(freightMaintain.getParentMaintain() != null){
				filter.setParentMaintain(freightMaintain.getParentMaintain());///获取到同父级的操作
				list = freightMaintainDao.queryForList(filter);
			}else{
				list = freightMaintainDao.queryForList(filter);//获取都只在订单下的操作
				List<FreightMaintain> tempList = new ArrayList<FreightMaintain>();
				for(FreightMaintain item : list){
					if(item.getParentMaintain() == null){//去除有父操作的操作
						tempList.add(item);
					}
				}
				
				list = tempList;
			}
			int soruceIndex = freightMaintain.getDisplayIndex();
			for(FreightMaintain item : list){
				int index = item.getDisplayIndex();
				if(index > soruceIndex){
					item.setDisplayIndex(index - 1);
					freightMaintainDao.modify(item);
				}
			}
			
			FreightAction filterAction = new FreightAction();
			filterAction.setFreightMaintain(freightMaintain);
			List<FreightAction> freightActions = freightActionDao.queryForList(filterAction);
			for(FreightAction freightAction : freightActions){
				FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightAction.getId());
				if(freightDelegate != null){
					if(!"草稿".equals(freightDelegate.getStatus())){
						logger.warn("删除操作： {} 有委托尚未撤回， 委托编号： {} ", freightMaintain.getFreightOrder().getOrderNumber(), freightDelegate.getDelegateNumber());
						flag = false;
						break;
					}
					freightDelegateDao.delete(freightDelegate.getId());
				}
				
				freightActionDao.delete(freightAction.getId());
				freightActionValueDao.deleteByFreightActionId(freightAction.getId());
			}
			
			freightMaintainDao.delete(freightMaintainId);
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}

	@Override
	public void doneAddMaintain(FreightMaintain freightMaintain, String freightOrderId, 
			String parentMaintainId, String[] freightMaintainTypeIds) {
		if(!StringUtil.isNullOrEmpty(parentMaintainId)){
			freightMaintain.setParentMaintain(freightMaintainDao.getById(parentMaintainId));
		}
		freightMaintain.setFreightOrder(freightOrderDao.getById(freightOrderId));
		for(String freightMaintainTypeId : freightMaintainTypeIds){
			freightMaintain.setFreightMaintainType(freightMaintainTypeDao.getById(freightMaintainTypeId));
			String id = StringUtil.getUUID();
			freightMaintain.setId(id);
			freightMaintainDao.add(freightMaintain);
			freightMaintain.setDisplayIndex(freightMaintain.getDisplayIndex() + 1);//递增显示顺序
			//添加默认动作
			List<FreightActionType> actionTypes = FreightActionTypeService.cacheActionType.get(freightMaintainTypeId);
			int count = 1;
			for(FreightActionType freightActionType : actionTypes){
				FreightAction freightAction = new FreightAction();
				freightAction.setFreightActionType(freightActionType);
				freightAction.setPrime(freightActionType.getPrime());
				freightAction.setInternal(freightActionType.getInternal());
				freightAction.setDelegate(freightActionType.getDelegate());
				freightAction.setFreightMaintain(freightMaintain);
				freightAction.setDisplayIndex(count);
				freightAction.setStatus("未执行");
				freightActionDao.add(freightAction);
				count++;
			}
		}
		
	}
}

package com.van.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.cache.Cache;
import com.van.halley.core.cache.LocalCacheSupport;
import com.van.halley.db.persistence.BasisAttributeDao;
import com.van.halley.db.persistence.BasisStatusAttributeDao;
import com.van.halley.db.persistence.BasisValueDao;
import com.van.halley.db.persistence.BpmConfigNodeDao;
import com.van.halley.db.persistence.entity.BasisAttribute;
import com.van.halley.db.persistence.entity.BasisStatusAttribute;
import com.van.halley.db.persistence.entity.BasisValue;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.service.AttributeTagService;

@Transactional
@Service("attributeTagService")
public class AttributeTagServiceImpl implements
		AttributeTagService {
	private static Logger logger = LoggerFactory.getLogger(AttributeTagServiceImpl.class);
	@Autowired
	private TaskService taskService;
	@Autowired
	private BpmConfigNodeDao bpmConfigNodeDao;
	@Autowired
	private BasisStatusAttributeDao basisStatusAttributeDao;
	@Autowired
	private BasisValueDao basisValueDao;
	@Autowired
	private BasisAttributeDao basisAttributeDao;
	@Autowired
	private LocalCacheSupport localCacheSupport;
	
	
	//初始化缓存
	@PostConstruct
	public void initCache(){
		refreshCache();
	}
	
	//如果taskId 不为空, 获取是否可读, 则通过节点配置信息来获取
	/*@Override
	public boolean isReadonly(String status, String attribute, String clsId, String controllerId, String taskId) {
		if(StringUtil.isNullOrEmpty(taskId)){
			return isReadonlyForNormal(status, attribute, clsId, controllerId);
		}else{
			return isReadonlyForTask(attribute, clsId, controllerId, taskId);
		}
	}


	@Override
	public boolean isReadonlyForNormal(String status, String attribute, String clsId,
			String controllerId) {
		boolean flag = true;
		if(StringUtil.isNullOrEmpty(status) || StringUtil.isNullOrEmpty(clsId) || StringUtil.isNullOrEmpty(controllerId)){
			return flag;
		}

		BusinessController businessController = businessControllerDao.getById(controllerId);
		if(businessController.getControlDetails().equals("normal")){
			//如果有变动的话应当重新取一次。
			String attrId = attributeCache.get(clsId).get(attribute);
			if(attrId == null){
				return flag;
			}
			
			//如果有变动的话应当重新取一次。
			String statusId = statusCache.get(clsId).get(status);
			if(statusId == null){
				return flag;
			}
			
			String type = attributeStatusCache.get(attrId + statusId);
			if(type == null){
				return flag;
			}else{
				if("normal".equals(type)){
					flag = false;
				}
			}
		}
		
		return flag;
	}


	 
	 * 通过taskId 获取状态,判断是否只读
	 
	@Override
	public boolean isReadonlyForTask(String attribute, String clsId,
			String controllerId, String taskId) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		BpmConfigNode bpmConfigNode = bpmConfigNodeDao.getByPdIdAndTdKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		String status = bpmConfigNode.getSourceStatus();
		
		return isReadonlyForNormal(status, attribute, clsId, controllerId);
	}


	@Override
	public List<String> getTypeAndValue(String attribute, String clsId) {
		BusinessAttribute filter = new BusinessAttribute();
		filter.setBusinessClass(businessClassDao.getById(clsId));
		filter.setColumnName(attribute);
		List<BusinessAttribute> attributes = businessAttributeDao.queryForList(filter);
		
		List<String> values = new ArrayList<String>();
		if(attributes != null && !attributes.isEmpty()){
			BusinessAttribute businessAttribute = attributes.get(0);
			//枚举类型
			if("ENUMERATION".equals(businessAttribute.getValueType())){
				BusinessAttributeValue filterValue = new BusinessAttributeValue();
				filterValue.setBusinessAttribute(businessAttribute);
				List<BusinessAttributeValue> businessAttributeValues = businessAttributeValueDao.queryForList(filterValue);
				for(BusinessAttributeValue businessAttributeValue : businessAttributeValues){
					values.add(businessAttributeValue.getValueContent());
				}
			}
		}
		
		return values;
	}*/
	// 状态, 属性 获取对应ID >  ID组成字符串 > 获取是否可读的类型
	@Override
	public void refreshCache(){
		/*List<BusinessClass> clses = businessClassDao.getAll();
		for(BusinessClass cls : clses){
			BusinessAttribute filter = new BusinessAttribute();
			filter.setBusinessClass(cls);
			Map<String, String> map = new HashMap<String, String>();
			for(BusinessAttribute attr : businessAttributeDao.queryForList(filter)){
				map.put(attr.getColumnName(), attr.getId());
			}
			attributeCache.put(cls.getId(), map);
		}
		logger.info("Attribute cache loaded! ");
		
		for(BusinessClass cls : clses){
			BusinessStatus filter = new BusinessStatus();
			filter.setBusinessClass(cls);
			Map<String, String> map = new HashMap<String, String>();
			for(BusinessStatus sta : businessStatusDao.queryForList(filter)){
				map.put(sta.getStatus(), sta.getId());
			}
			statusCache.put(cls.getId(), map);
		}
		logger.info("Status cache loaded! ");
		
		for(BusinessAttributeStatus attrStatus : businessAttributeStatusDao.getAll()){
			//有可能为null,字段被删除或者状态被删除
			if(attrStatus.getBusinessAttribute() == null || attrStatus.getBusinessStatus() == null){
				continue;
			}
			
			attributeStatusCache.put(attrStatus.getBusinessAttribute().getId() + attrStatus.getBusinessStatus().getId(), 
					attrStatus.getType());
		}*/
		
		//缓存属性
		/*List<BasisSubstanceType> basisSubstanceTypes = basisSubstanceTypeDao.getAll();
		for(BasisSubstanceType basisSubstanceType : basisSubstanceTypes){
			List<BasisAttribute> basisAttributes = basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceType.getId());
			for(BasisAttribute basisAttribute : basisAttributes){
				attributeCache.put(basisAttribute.getId(), basisAttribute.getAttributeColumn());
			}
		}*/
		
		//获取所有属性
		Map<String, String> attributeMap = new HashMap<String, String>();
		List<BasisAttribute> basisAttributes = basisAttributeDao.getAll();
		for(BasisAttribute basisAttribute : basisAttributes){
			attributeMap.put(basisAttribute.getId(), basisAttribute.getAttributeColumn());
		}
		//缓存状态字段关联
		List<BasisStatusAttribute> basisStatusAttributes = basisStatusAttributeDao.getAll();
		for(BasisStatusAttribute basisStatusAttribute : basisStatusAttributes){
			attributeStatusCache.put(basisStatusAttribute.getBasisStatusId() 
					+ attributeMap.get(basisStatusAttribute.getBasisAttributeId()), basisStatusAttribute.getReadonly());
		}
		logger.info("AttributeStatus cache loaded! ");
		
	}

	@Override
	public Map<String, String> getAttributeProperty(String basisSubstanceId, String attributeColumn, String taskId) {
		//TODO 暂时将一些数据放入static的缓存中，有必要将其放入threadlocal进行缓存。
		Map<String, String> map = new HashMap<String, String>();
		
		BpmConfigNode bpmConfigNode = null;
		//添加缓存支持
		Cache cacheBpmConfigNode = localCacheSupport.getCacheStrategy().getCache(Task.class.getName());
		if(cacheBpmConfigNode.get(taskId) == null){
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			bpmConfigNode = bpmConfigNodeDao.getByPdIdAndTdKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
			cacheBpmConfigNode.put(taskId, bpmConfigNode);
		}else{
			bpmConfigNode = cacheBpmConfigNode.get(taskId);
		}
		
		
		if(bpmConfigNode.getBasisStatus() == null){
			map.put("readonly", "T");
			map.put("valueId", "A");
		}else{
			//添加缓存支持
			Cache cacheBasisValue = localCacheSupport.getCacheStrategy().getCache(BasisValue.class.getName());
			if(cacheBasisValue.get(basisSubstanceId) == null){
				List<BasisValue> basisValues = basisValueDao.getByBasisSubstanceId(basisSubstanceId);
				Map<String, BasisValue> valueMap = new HashMap<String, BasisValue>();
				for(BasisValue item : basisValues){
					valueMap.put(basisSubstanceId + item.getBasisAttribute().getAttributeColumn(), item);
				}
				
				cacheBasisValue.put(basisSubstanceId, valueMap);
			}
			BasisValue basisValue = ((Map<String, BasisValue>)cacheBasisValue.get(basisSubstanceId)).get(basisSubstanceId + attributeColumn);
			
			//BasisValue basisValue = basisValueDao.getSingleValue(basisSubstanceId, attributeColumn, false);
			if(basisValue != null){
				BasisAttribute basisAttribute = basisValue.getBasisAttribute();
				map.put("attributeType", basisAttribute.getAttributeType());
				map.put("readonly", attributeStatusCache.get(bpmConfigNode.getBasisStatus().getId() + attributeColumn));
				map.put("valueId", basisValue.getId());
				map.put("canSelect", basisAttribute.getCanSelect());
				map.put("vAttrId", basisAttribute.getvAttrId() == null ? "" : basisAttribute.getvAttrId());
				map.put("vClsId", basisAttribute.getvClsId() == null ? "" : basisAttribute.getvClsId());
				map.put("vColumn", basisAttribute.getvColumn() == null ? "" : basisAttribute.getvColumn());
				map.put("vFilterId", basisAttribute.getvFilterId() == null ? "" : basisAttribute.getvFilterId());
			}
		}
		
		return map;
	}
	
	

}

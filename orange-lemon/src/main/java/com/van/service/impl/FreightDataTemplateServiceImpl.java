package com.van.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionDao;
import com.van.halley.db.persistence.FreightActionFieldDao;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.FreightDataTemplateDao;
import com.van.halley.db.persistence.entity.FreightActionValue;
import com.van.halley.db.persistence.entity.FreightDataTemplate;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.FreightDataTemplateService;

@Transactional
@Service("freightDataTemplateService")
public class FreightDataTemplateServiceImpl implements
		FreightDataTemplateService {
	@Autowired
	private FreightDataTemplateDao freightDataTemplateDao;
	@Autowired
	private FreightActionFieldDao freightActionFieldDao;
	@Autowired
	private FreightActionValueDao freightActionValueDao;
	@Autowired
	private FreightActionDao freightActionDao;

	public List<FreightDataTemplate> getAll() {
		return freightDataTemplateDao.getAll();
	}

	public List<FreightDataTemplate> queryForList(
			FreightDataTemplate freightDataTemplate) {
		return freightDataTemplateDao.queryForList(freightDataTemplate);
	}

	public PageView query(PageView pageView,
			FreightDataTemplate freightDataTemplate) {
		List<FreightDataTemplate> list = freightDataTemplateDao.query(pageView,
				freightDataTemplate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightDataTemplate freightDataTemplate) {
		freightDataTemplateDao.add(freightDataTemplate);
	}

	public void delete(String id) {
		freightDataTemplateDao.delete(id);
	}

	public void modify(FreightDataTemplate freightDataTemplate) {
		freightDataTemplateDao.modify(freightDataTemplate);
	}

	public FreightDataTemplate getById(String id) {
		return freightDataTemplateDao.getById(id);
	}

	@Override
	public Map<String, Object> toAddTemplate(String freightActionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String freightActionTypeId = freightActionDao.getById(freightActionId).getFreightActionType().getId();
		map.put("normalField", freightActionFieldDao.getNormalByFreightActionTypeId(freightActionTypeId));
		map.put("foxBoxField", freightActionFieldDao.getForBoxByFreightActionTypeId(freightActionTypeId));
		
		map.put("normalValue", freightActionValueDao.getNormalByFreightActionId(freightActionId));
		map.put("foxBoxValue", freightActionValueDao.getForBoxByFreightActionId(freightActionId));
		return map;
	}

	@Override
	public void doneAddTemplate(String templateName, User owner, String[] freightActionValueIds) {
		String freightDataTemplateId = StringUtil.getUUID();
		FreightDataTemplate freightDataTemplate = new FreightDataTemplate();
		freightDataTemplate.setId(freightDataTemplateId);
		freightDataTemplate.setOwner(owner);
		freightDataTemplate.setFreightAction(freightActionValueDao.getById(freightActionValueIds[0]).getFreightAction());//保存一个动作信息
		freightDataTemplate.setTemplateName(templateName);
		freightDataTemplateDao.add(freightDataTemplate);
		
		if(freightActionValueIds != null && freightActionValueIds.length > 0){
			for(String freightActionValueId : freightActionValueIds){
				freightActionValueDao.addDataTemplateValue(freightDataTemplateId, freightActionValueId);
			}
		}
	}

	@Override
	public boolean doneCopyTemplate(String freightActionId, String freightDataTemplateId, String sheathe) {
		List<FreightActionValue> normalValue = freightActionValueDao.getNormalByFreightActionId(freightActionId);
		List<FreightActionValue> foxBoxValue = freightActionValueDao.getForBoxByFreightActionId(freightActionId);
		
		List<FreightActionValue> sourceValue = freightActionValueDao.getByFreightDataTemplateId(freightDataTemplateId);
		Map<String, FreightActionValue> valueMap = new HashMap<String, FreightActionValue>();
		for(FreightActionValue item : sourceValue){
			valueMap.put(item.getFreightActionField().getId(), item);
		}
		
		for(FreightActionValue target : normalValue){
			FreightActionValue source = valueMap.get(target.getFreightActionField().getId());
			if(source != null){
				String fieldType = target.getFreightActionField().getFieldType();
				Object targetValue = null;
				if(fieldType.equals("VARCHAR")){
					targetValue = target.getStringValue();
					target.setStringValue(source.getStringValue());
				}else if(fieldType.equals("TEXT")){
					targetValue = target.getTextValue();
					target.setTextValue(source.getTextValue());
				}else if(fieldType.equals("DOUBLE")){
					targetValue = (target.getDoubleValue() == 0 ? null : target.getDoubleValue());
					target.setDoubleValue(source.getDoubleValue());
				}else if(fieldType.equals("INT")){
					targetValue = (target.getIntValue() == 0 ? null : target.getIntValue());
					target.setIntValue(source.getIntValue());
				}else if(fieldType.equals("DATETIME") || fieldType.equals("TIMESTAMP")){
					targetValue = target.getDateValue();
					target.setDateValue(source.getDateValue());
				}
				
				if("T".equals(sheathe)){
					target.setStatus("T");//标注已经填报过
					freightActionValueDao.modify(target);
				}else{
					if(targetValue == null){//现有值为空时，才保存数据
						target.setStatus("T");//标注已经填报过
						freightActionValueDao.modify(target);
					}
				}
			}
		}
		
		for(FreightActionValue target : foxBoxValue){
			FreightActionValue source = valueMap.get(target.getFreightActionField().getId());
			if(source != null){
				String fieldType = target.getFreightActionField().getFieldType();
				if(fieldType.equals("VARCHAR")){
					target.setStringValue(source.getStringValue());
				}else if(fieldType.equals("TEXT")){
					target.setTextValue(source.getTextValue());
				}else if(fieldType.equals("DOUBLE")){
					target.setDoubleValue(source.getDoubleValue());
				}else if(fieldType.equals("INT")){
					target.setIntValue(source.getIntValue());
				}else if(fieldType.equals("DATETIME") || fieldType.equals("TIMESTAMP")){
					target.setDateValue(source.getDateValue());
				}
				target.setStatus("T");//标注已经填报过
				freightActionValueDao.modify(target);
			}
		}
		return true;
	}

	@Override
	public Map<String, Object> toCopyTempate(String freightActionId, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dataTemplates", freightDataTemplateDao.getByFreightActionId(freightActionId, userId));
		return map;
	}
}

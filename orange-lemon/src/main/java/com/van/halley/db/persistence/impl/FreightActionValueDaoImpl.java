package com.van.halley.db.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.entity.FreightActionBox;
import com.van.halley.db.persistence.entity.FreightActionValue;
import com.van.halley.db.persistence.entity.FreightDataTemplateActionValue;
import com.van.halley.util.StringUtil;

@Repository("freightActionValueDao")
public class FreightActionValueDaoImpl extends BaseDaoImpl<FreightActionValue>
		implements FreightActionValueDao {

	@Override
	public void deleteByFreightActionId(String freightActionId) {
		getSqlSession().delete("freightactionvalue.deleteByFreightActionId", freightActionId);
	}

	@Override
	public void deleteForOrderBox(String freightActionId, String freightOrderBoxId) {
		FreightActionBox freightActionBox = new FreightActionBox(freightActionId, freightOrderBoxId);
		getSqlSession().delete("freightactionvalue.deleteForOrderBox", freightActionBox);
	}

	@Override
	public FreightActionValue getParticipateValue(String fieldColumn, String freightActionId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fieldColumn", fieldColumn);
		map.put("freightActionId", freightActionId);
		List<FreightActionValue> participateValues = getSqlSession().selectList("freightactionvalue.getParticipateValue", map);
		if(participateValues == null || participateValues.isEmpty()){
			return null;
		}else{
			for(FreightActionValue participateValue : participateValues){
				String fieldType = participateValue.getFreightActionField().getFieldType();
				if("VARCHAR".equals(fieldType)){
					if(!StringUtil.isNullOrEmpty(participateValue.getStringValue())){
						return participateValue;
					}
				}else if("TEXT".equals(fieldType)){
					if(!StringUtil.isNullOrEmpty(participateValue.getTextValue())){
						return participateValue;
					}
				}else if("INT".equals(fieldType)){
					if(0 != participateValue.getIntValue()){
						return participateValue;
					}
				}else if("DOUBLE".equals(fieldType)){
					if(0 != participateValue.getDoubleValue()){
						return participateValue;
					}
				}else if("DATETIME".equals(fieldType) || "TIMESTAMP".equals(fieldType)){
					if(participateValue.getDateValue() != null){
						return participateValue;
					}
				}
			}
			
			return null;
		}
	}
	
	@Override
	public FreightActionValue getParticipateValue(String fieldColumn, String freightActionId, String freightOrderBoxId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fieldColumn", fieldColumn);
		map.put("freightActionId", freightActionId);
		map.put("freightOrderBoxId", freightOrderBoxId);
		return getSqlSession().selectOne("freightactionvalue.getParticipateValue", map);
	}

	@Override
	public List<FreightActionValue> getNormalByFreightActionId(String freightActionId) {
		return getSqlSession().selectList("freightactionvalue.getNormalByFreightActionId", freightActionId);
	}

	@Override
	public List<FreightActionValue> getForBoxByFreightActionId(String freightActionId) {
		return getSqlSession().selectList("freightactionvalue.getForBoxByFreightActionId", freightActionId);
	}

	@Override
	public List<FreightActionValue> getByFreightDataTemplateId(
			String freightDataTemplateId) {
		return getSqlSession().selectList("freightactionvalue.getByFreightDataTemplateId", freightDataTemplateId);
	}

	@Override
	public void addDataTemplateValue(String freightDataTemplateId,
			String freightActionValueId) {
		FreightDataTemplateActionValue freightDataTemplateActionValue = 
				new FreightDataTemplateActionValue(freightDataTemplateId, freightActionValueId);
		getSqlSession().insert("freightactionvalue.addDataTemplateValue", freightDataTemplateActionValue);
	}

	@Override
	public void deleteDataTemplateValue(String freightDataTemplateId) {
		getSqlSession().delete("freightactionvalue.deleteDataTemplateValue", freightDataTemplateId);
	}

	@Override
	public String getSingleValue(String freightOrderId, String fieldColumn) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("freightOrderId", freightOrderId);
		map.put("fieldColumn", fieldColumn);
		return getSqlSession().selectOne("freightactionvalue.getSingleValue", map);
	}

	@Override
	public void deleteByFreightOrderBoxId(String freightOrderBoxId) {
		getSqlSession().delete("freightactionvalue.deleteByFreightOrderBoxId", freightOrderBoxId);
	}

	@Override
	public List<FreightActionValue> getAllParticipateValue(String freightOrderId) {
		return getSqlSession().selectList("freightactionvalue.getAllParticipateValue", freightOrderId);
	}
}

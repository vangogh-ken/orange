package com.van.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisAttributeDao;
import com.van.halley.db.persistence.BasisStatusAttributeDao;
import com.van.halley.db.persistence.BasisStatusDao;
import com.van.halley.db.persistence.entity.BasisAttribute;
import com.van.halley.db.persistence.entity.BasisStatus;
import com.van.halley.db.persistence.entity.BasisStatusAttribute;
import com.van.service.AttributeTagService;
import com.van.service.BasisStatusAttributeService;

@Transactional
@Service("basisStatusAttributeService")
public class BasisStatusAttributeServiceImpl implements
		BasisStatusAttributeService {
	@Autowired
	private BasisStatusAttributeDao basisStatusAttributeDao;
	@Autowired
	private BasisAttributeDao basisAttributeDao;
	@Autowired
	private BasisStatusDao basisStatusDao;
	@Autowired
	private AttributeTagService attributeStatusControlService;

	public List<BasisStatusAttribute> getAll() {
		return basisStatusAttributeDao.getAll();
	}

	public List<BasisStatusAttribute> queryForList(
			BasisStatusAttribute basisStatusAttribute) {
		return basisStatusAttributeDao.queryForList(basisStatusAttribute);
	}

	public BasisStatusAttribute queryForOne(
			BasisStatusAttribute basisStatusAttribute) {
		return basisStatusAttributeDao.queryForOne(basisStatusAttribute);
	}

	public PageView query(PageView pageView,
			BasisStatusAttribute basisStatusAttribute) {
		List<BasisStatusAttribute> list = basisStatusAttributeDao.query(
				pageView, basisStatusAttribute);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BasisStatusAttribute basisStatusAttribute) {
		basisStatusAttributeDao.add(basisStatusAttribute);
	}

	public void delete(String id) {
		basisStatusAttributeDao.delete(id);
	}

	public void modify(BasisStatusAttribute basisStatusAttribute) {
		basisStatusAttributeDao.modify(basisStatusAttribute);
	}

	public BasisStatusAttribute getById(String id) {
		return basisStatusAttributeDao.getById(id);
	}

	@Override
	public Map<String, Object> toConfigAttribute(String basisStatusId) {
		Map<String, Object> map = new HashMap<String, Object>();
		BasisStatus basisStatus = basisStatusDao.getById(basisStatusId);
		map.put("basisStatus", basisStatus);
		
		List<BasisAttribute> basisAttributes = 
				basisAttributeDao.getByBasisSubstanceTypeId(basisStatus.getBasisSubstanceType().getId());
		map.put("basisAttributes", basisAttributes);
		
		for(BasisAttribute basisAttribute : basisAttributes){
			BasisStatusAttribute filterIsHave = new BasisStatusAttribute();
			filterIsHave.setBasisStatusId(basisStatusId);
			filterIsHave.setBasisAttributeId(basisAttribute.getId());
			if(basisStatusAttributeDao.count(filterIsHave) == 0){//如果不存在则添加
				BasisStatusAttribute basisStatusAttribute = new BasisStatusAttribute();
				basisStatusAttribute.setBasisStatusId(basisStatusId);
				basisStatusAttribute.setBasisAttributeId(basisAttribute.getId());
				basisStatusAttribute.setReadonly("F");
				basisStatusAttributeDao.add(basisStatusAttribute);
			}
		}
		
		BasisStatusAttribute filter = new BasisStatusAttribute();
		filter.setBasisStatusId(basisStatusId);
		map.put("basisStatusAttributes", basisStatusAttributeDao.queryForList(filter));
		return map;
	}

	@Override
	public void doneConfigAttribute(String basisStatusId, HttpServletRequest request) {
		BasisStatusAttribute filter = new BasisStatusAttribute();
		filter.setBasisStatusId(basisStatusId);
		List<BasisStatusAttribute> basisStatusAttributes = basisStatusAttributeDao.queryForList(filter);
		for(BasisStatusAttribute basisStatusAttribute : basisStatusAttributes){
			basisStatusAttribute.setReadonly(request.getParameter(basisStatusAttribute.getId()));
			basisStatusAttributeDao.modify(basisStatusAttribute);
		}
		
		attributeStatusControlService.refreshCache();
	}
}

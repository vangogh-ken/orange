package com.van.service.impl;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisAttributeDao;
import com.van.halley.db.persistence.entity.BasisAttribute;
import com.van.service.BasisAttributeService;

@Transactional
@Service("basisAttributeService")
public class BasisAttributeServiceImpl implements BasisAttributeService {
	@Autowired
	private BasisAttributeDao basisAttributeDao;

	public List<BasisAttribute> getAll() {
		return basisAttributeDao.getAll();
	}

	public List<BasisAttribute> queryForList(BasisAttribute basisAttribute) {
		return basisAttributeDao.queryForList(basisAttribute);
	}

	public BasisAttribute queryForOne(BasisAttribute basisAttribute) {
		return basisAttributeDao.queryForOne(basisAttribute);
	}

	public PageView query(PageView pageView, BasisAttribute basisAttribute) {
		List<BasisAttribute> list = basisAttributeDao.query(pageView,
				basisAttribute);
		pageView.setResults(list);
		return pageView;
	}

	public void add(BasisAttribute basisAttribute) {
		basisAttributeDao.add(basisAttribute);
	}

	public void delete(String id) {
		basisAttributeDao.delete(id);
	}

	public void modify(BasisAttribute basisAttribute) {
		basisAttributeDao.modify(basisAttribute);
	}

	public BasisAttribute getById(String id) {
		return basisAttributeDao.getById(id);
	}

	@Override
	public List<BasisAttribute> getByBasisSubstanceTypeId(String basisSubstanceTypeId) {
		return basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId);
	}

	@Override
	public boolean doneAddBatch(BasisAttribute basisAttribute, int batchCount, int batchStart, int batchEnd) {
		Assert.assertTrue(batchCount > 1 || (batchStart > 0 && batchEnd > 0 && batchStart < batchEnd));
		String attributeName = basisAttribute.getAttributeName();
		String attributeColumn = basisAttribute.getAttributeColumn();
		if(batchEnd == 0 || batchEnd == 0 || batchStart >= batchEnd){
			for(int i=0; i<batchCount; i++){
				basisAttribute.setAttributeName(attributeName + (1 + i));
				basisAttribute.setAttributeColumn(attributeColumn + (1 + i));
				basisAttribute.setDisplayIndex(basisAttribute.getDisplayIndex() + 1);
				basisAttributeDao.add(basisAttribute);
			}
		}else{
			for(int i=batchStart; i < (batchEnd + 1); i++){
				basisAttribute.setAttributeName(attributeName + i);
				basisAttribute.setAttributeColumn(attributeColumn + i);
				basisAttribute.setDisplayIndex(basisAttribute.getDisplayIndex() + 1);
				basisAttributeDao.add(basisAttribute);
			}
		}
		return false;
	}
}

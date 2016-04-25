package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.BasisStatusDao;
import com.van.halley.db.persistence.entity.BasisStatus;
import com.van.service.BasisStatusService;
@Transactional
@Service("basisStatusService")
public class BasisStatusServiceImpl implements BasisStatusService {
@Autowired
private BasisStatusDao basisStatusDao;
public List<BasisStatus> getAll() {
return basisStatusDao.getAll();
}
public List<BasisStatus> queryForList(BasisStatus basisStatus) { 
return basisStatusDao.queryForList(basisStatus); 
}
public BasisStatus queryForOne(BasisStatus basisStatus) { 
return basisStatusDao.queryForOne(basisStatus); 
}
public PageView query(PageView pageView, BasisStatus basisStatus) {
List<BasisStatus> list = basisStatusDao.query(pageView, basisStatus);
pageView.setResults(list);
return pageView;
}
public void add(BasisStatus basisStatus) {
basisStatusDao.add(basisStatus);
}
public void delete(String id) {
basisStatusDao.delete(id);
}
public void modify(BasisStatus basisStatus) {
basisStatusDao.modify(basisStatus);
}
public BasisStatus getById(String id) {
return basisStatusDao.getById(id);
}
}

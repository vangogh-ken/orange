package com.van.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.DiskAclDao;
import com.van.halley.db.persistence.entity.DiskAcl;
import com.van.service.DiskAclService;
@Transactional
@Service("diskAclService")
public class DiskAclServiceImpl implements DiskAclService {
@Autowired
private DiskAclDao diskAclDao;
public List<DiskAcl> getAll() {
return diskAclDao.getAll();
}
public List<DiskAcl> queryForList(DiskAcl diskAcl) { 
return diskAclDao.queryForList(diskAcl); 
}
public int count(DiskAcl diskAcl) { 
return diskAclDao.count(diskAcl); 
}
public DiskAcl queryForOne(DiskAcl diskAcl) { 
return diskAclDao.queryForOne(diskAcl); 
}
public PageView<DiskAcl>  query(PageView<DiskAcl>  pageView, DiskAcl diskAcl) {
List<DiskAcl> list = diskAclDao.query(pageView, diskAcl);
pageView.setResults(list);
return pageView;
}
public void add(DiskAcl diskAcl) {
diskAclDao.add(diskAcl);
}
public void delete(String id) {
diskAclDao.delete(id);
}
public void modify(DiskAcl diskAcl) {
diskAclDao.modify(diskAcl);
}
public DiskAcl getById(String id) {
return diskAclDao.getById(id);
}
}

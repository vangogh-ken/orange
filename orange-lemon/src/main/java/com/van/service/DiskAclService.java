package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.DiskAcl;
public interface DiskAclService{
public List<DiskAcl> getAll();
public List<DiskAcl> queryForList(DiskAcl diskAcl);
public int count(DiskAcl diskAcl);
public DiskAcl queryForOne(DiskAcl diskAcl);
public PageView<DiskAcl>  query(PageView<DiskAcl>  pageView,DiskAcl diskAcl);
public void add(DiskAcl diskAcl);
public void delete(String id);
public void modify(DiskAcl diskAcl);
public DiskAcl getById(String id);
}

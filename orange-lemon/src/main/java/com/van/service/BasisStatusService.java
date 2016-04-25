package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.BasisStatus;
public interface BasisStatusService{
public List<BasisStatus> getAll();
public List<BasisStatus> queryForList(BasisStatus basisStatus);
public BasisStatus queryForOne(BasisStatus basisStatus);
public PageView query(PageView pageView,BasisStatus basisStatus);
public void add(BasisStatus basisStatus);
public void delete(String id);
public void modify(BasisStatus basisStatus);
public BasisStatus getById(String id);
}

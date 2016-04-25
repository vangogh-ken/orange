package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.OrgEntityIdentity;
public interface OrgEntityIdentityService{
public List<OrgEntityIdentity> getAll();
public List<OrgEntityIdentity> queryForList(OrgEntityIdentity orgEntityIdentity);
public OrgEntityIdentity queryForOne(OrgEntityIdentity orgEntityIdentity);
public PageView query(PageView pageView,OrgEntityIdentity orgEntityIdentity);
public void add(OrgEntityIdentity orgEntityIdentity);
public void delete(String id);
public void modify(OrgEntityIdentity orgEntityIdentity);
public OrgEntityIdentity getById(String id);
}

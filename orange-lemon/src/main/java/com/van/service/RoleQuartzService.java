package com.van.service;
import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.RoleQuartz;
public interface RoleQuartzService{
public List<RoleQuartz> getAll();
public List<RoleQuartz> queryForList(RoleQuartz roleQuartz);
public RoleQuartz queryForOne(RoleQuartz roleQuartz);
public PageView query(PageView pageView,RoleQuartz roleQuartz);
public void add(RoleQuartz roleQuartz);
public void delete(String id);
public void modify(RoleQuartz roleQuartz);
public RoleQuartz getById(String id);
}

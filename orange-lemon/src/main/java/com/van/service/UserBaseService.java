package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.UserBase;

public interface UserBaseService {
	public List<UserBase> getAll();

	public List<UserBase> queryForList(UserBase userBase);

	public PageView query(PageView pageView, UserBase userBase);

	public void add(UserBase userBase);

	public void delete(String id);

	public void modify(UserBase userBase);

	public UserBase getById(String id);

	public UserBase getByUserId(String userId);
	
	public UserBase getByWeixinName(String weixinName);
}

package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ConstantAuth;
import com.van.halley.db.persistence.entity.User;

public interface ConstantAuthService {
	public List<ConstantAuth> getAll();

	public List<ConstantAuth> queryForList(ConstantAuth constantAuth);

	public ConstantAuth queryForOne(ConstantAuth constantAuth);

	public PageView query(PageView pageView, ConstantAuth constantAuth);

	public void add(ConstantAuth constantAuth);

	public void delete(String id);

	public void modify(ConstantAuth constantAuth);

	public ConstantAuth getById(String id);
	
	/**
	 * 通过常量获取对应的用户
	 * @param constantId
	 * @return
	 */
	public List<User> getUserByConstantId(String constantId, String userId);
}

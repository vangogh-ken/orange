package com.van.halley.db.persistence;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.UserBase;

public interface UserBaseDao extends BaseDao<UserBase> {
	public UserBase getByUserId(String userId);
	
	public void deleteByUserId(String userId);
	
	/**
	 * 跟进微信账号获取系统信息
	 * @param weixinName
	 * @return
	 */
	public UserBase getByWeixinName(String weixinName);
}
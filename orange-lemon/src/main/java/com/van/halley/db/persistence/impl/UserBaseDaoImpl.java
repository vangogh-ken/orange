package com.van.halley.db.persistence.impl;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.UserBaseDao;
import com.van.halley.db.persistence.entity.UserBase;

@Repository("userBaseDao")
public class UserBaseDaoImpl extends BaseDaoImpl<UserBase> implements
		UserBaseDao {

	@Override
	public UserBase getByUserId(String userId) {
		return getSqlSession().selectOne("userbase.getByUserId", userId);
	}

	@Override
	public void deleteByUserId(String userId) {
		getSqlSession().delete("userbase.deleteByUserId", userId);
	}

	@Override
	public UserBase getByWeixinName(String weixinName) {
		return getSqlSession().selectOne("userbase.getByWeixinName", weixinName);
	}
}

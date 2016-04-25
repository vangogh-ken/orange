package com.van.halley.core.session;

import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.User;

public class SessionContextImpl implements SessionContext {
	private Setting setting;
	private User user;

	@Override
	public Setting getSetting() {
		return this.setting;
	}

	@Override
	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	@Override
	public User getCurrentUser() {
		return this.user;
	}

	@Override
	public void setCurrentUser(User user) {
		this.user = user;
	}

	@Override
	public OrgEntity getCurrentOrgEntity() {
		return this.user.getOrgEntity();
	}

	@Override
	public String getCurrentUserId() {
		return this.user.getId();
	}
	
	

}

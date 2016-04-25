package com.van.halley.core.session;

import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.User;

public interface SessionContext {
	Setting getSetting();
	
	void setSetting(Setting setting);
	
	void setCurrentUser(User user);
	
	User getCurrentUser();
	
	OrgEntity getCurrentOrgEntity();
	
	String getCurrentUserId();
}

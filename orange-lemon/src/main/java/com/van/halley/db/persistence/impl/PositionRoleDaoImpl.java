package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.PositionRoleDao;
import com.van.halley.db.persistence.entity.PositionRole;
@Repository("positionRoleDao")
public class PositionRoleDaoImpl extends BaseDaoImpl<PositionRole> implements PositionRoleDao {}

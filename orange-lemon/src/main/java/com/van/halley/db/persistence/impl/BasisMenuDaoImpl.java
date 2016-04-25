package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.BasisMenuDao;
import com.van.halley.db.persistence.entity.BasisMenu;
@Repository("basisMenuDao")
public class BasisMenuDaoImpl extends BaseDaoImpl<BasisMenu> implements BasisMenuDao {}

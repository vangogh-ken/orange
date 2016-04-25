package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FasDueDao;
import com.van.halley.db.persistence.entity.FasDue;
@Repository("fasDueDao")
public class FasDueDaoImpl extends BaseDaoImpl<FasDue> implements FasDueDao {}

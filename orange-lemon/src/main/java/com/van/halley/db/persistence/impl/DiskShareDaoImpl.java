package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.DiskShareDao;
import com.van.halley.db.persistence.entity.DiskShare;
@Repository("diskShareDao")
public class DiskShareDaoImpl extends BaseDaoImpl<DiskShare> implements DiskShareDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.DiskAclDao;
import com.van.halley.db.persistence.entity.DiskAcl;
@Repository("diskAclDao")
public class DiskAclDaoImpl extends BaseDaoImpl<DiskAcl> implements DiskAclDao {}

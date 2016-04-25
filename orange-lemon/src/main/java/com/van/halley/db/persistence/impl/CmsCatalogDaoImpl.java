package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CmsCatalogDao;
import com.van.halley.db.persistence.entity.CmsCatalog;
@Repository("cmsCatalogDao")
public class CmsCatalogDaoImpl extends BaseDaoImpl<CmsCatalog> implements CmsCatalogDao {}

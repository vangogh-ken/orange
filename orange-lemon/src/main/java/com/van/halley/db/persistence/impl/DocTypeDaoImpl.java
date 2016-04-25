package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.DocTypeDao;
import com.van.halley.db.persistence.entity.DocType;
@Repository("docTypeDao")
public class DocTypeDaoImpl extends BaseDaoImpl<DocType> implements DocTypeDao {}

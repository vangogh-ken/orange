package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.CmsFavoriteDao;
import com.van.halley.db.persistence.entity.CmsFavorite;
@Repository("cmsFavoriteDao")
public class CmsFavoriteDaoImpl extends BaseDaoImpl<CmsFavorite> implements CmsFavoriteDao {}

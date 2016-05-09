package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;
import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ItemCategoryDao;
import com.van.halley.db.persistence.entity.ItemCategory;
@Repository("itemCategoryDao")
public class ItemCategoryDaoImpl extends BaseDaoImpl<ItemCategory> implements ItemCategoryDao {}

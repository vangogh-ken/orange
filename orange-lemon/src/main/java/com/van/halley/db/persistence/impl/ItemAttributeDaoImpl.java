package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;
import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ItemAttributeDao;
import com.van.halley.db.persistence.entity.ItemAttribute;
@Repository("itemAttributeDao")
public class ItemAttributeDaoImpl extends BaseDaoImpl<ItemAttribute> implements ItemAttributeDao {}

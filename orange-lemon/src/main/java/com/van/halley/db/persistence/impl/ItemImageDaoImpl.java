package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;
import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ItemImageDao;
import com.van.halley.db.persistence.entity.ItemImage;
@Repository("itemImageDao")
public class ItemImageDaoImpl extends BaseDaoImpl<ItemImage> implements ItemImageDao {}

package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;
import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.ItemSubstanceDao;
import com.van.halley.db.persistence.entity.ItemSubstance;
@Repository("itemSubstanceDao")
public class ItemSubstanceDaoImpl extends BaseDaoImpl<ItemSubstance> implements ItemSubstanceDao {}

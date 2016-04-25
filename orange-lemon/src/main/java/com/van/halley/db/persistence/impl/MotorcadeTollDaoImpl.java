package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeTollDao;
import com.van.halley.db.persistence.entity.MotorcadeToll;
@Repository("motorcadeTollDao")
public class MotorcadeTollDaoImpl extends BaseDaoImpl<MotorcadeToll> implements MotorcadeTollDao {}

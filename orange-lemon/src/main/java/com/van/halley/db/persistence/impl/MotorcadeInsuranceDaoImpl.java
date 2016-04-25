package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MotorcadeInsuranceDao;
import com.van.halley.db.persistence.entity.MotorcadeInsurance;
@Repository("motorcadeInsuranceDao")
public class MotorcadeInsuranceDaoImpl extends BaseDaoImpl<MotorcadeInsurance> implements MotorcadeInsuranceDao {}

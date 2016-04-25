package com.van.halley.rep.data;

import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.db.persistence.entity.MotorcadeInsurance;
import com.van.service.MotorcadeDispatchService;
import com.van.service.MotorcadeFeeService;
import com.van.service.MotorcadeMaintainService;
import com.van.service.MotorcadePetrolService;
import com.van.service.MotorcadeTollService;
import com.van.service.MotorcadeTruckService;
import com.van.service.MotorcadeWealService;

public class MotorDataSource {
	@Autowired
	private MotorcadeDispatchService motorcadeDispatchService;
	
	@Autowired
	private MotorcadeFeeService motorcadeFeeService;
	
	@Autowired
	private MotorcadeInsurance motorcadeInsurance;
	
	@Autowired
	private MotorcadeMaintainService motorcadeMaintainService;
	
	@Autowired
	private MotorcadePetrolService motorcadePetrolService;
	
	@Autowired
	private MotorcadeTollService motorcadeTollService;
	
	@Autowired
	private MotorcadeTruckService motorcadeTruckService;
	
	@Autowired
	private MotorcadeWealService motorcadeWealService;
}

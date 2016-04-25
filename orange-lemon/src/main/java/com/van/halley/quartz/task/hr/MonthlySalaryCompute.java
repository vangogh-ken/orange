package com.van.halley.quartz.task.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.quartz.support.AbstractQuartzTarget;

public class MonthlySalaryCompute extends AbstractQuartzTarget{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void execute(){
		
	}
}

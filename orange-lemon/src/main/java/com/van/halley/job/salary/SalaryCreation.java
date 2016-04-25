package com.van.halley.job.salary;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.SalaryBasic;
import com.van.halley.db.persistence.entity.SalaryBonus;
import com.van.halley.db.persistence.entity.SalaryGrade;
import com.van.service.SalaryBasicService;
import com.van.service.SalaryBonusService;
import com.van.service.SalaryGradeService;

/**
 * 
 * salary data creation base on salary grade
 * @author anxinxx
 *
 */
@Component
public class SalaryCreation {
	private static Logger LOG = LoggerFactory.getLogger(SalaryCreation.class);
	
	@Autowired
	private SalaryGradeService salaryGradeService;
	@Autowired
	private SalaryBasicService salaryBasicService;
	@Autowired
	private SalaryBonusService salaryBonusService;
	
	
	@Scheduled(cron="0 0 0 1,5 * ?")//每月1, 5日触发
	public void basic(){
		SalaryGrade filter = new SalaryGrade();
		filter.setStatus("T");
		List<SalaryGrade> salaryGrades = salaryGradeService.queryForList(filter);
		
		SalaryBasic filterBasic = new SalaryBasic();
		for(SalaryGrade salaryGrade : salaryGrades){
			filterBasic.setSalaryGrade(salaryGrade);
			int count = salaryBasicService.count(filterBasic);
			if(count == 0){
				//~ ADD
				
				SalaryBasic salaryBasic = new SalaryBasic();
				salaryBasic.setSalaryGrade(salaryGrade);
				//~ setters
				
				salaryBasicService.add(salaryBasic);
			}else if(count == 1){
				//
			}else{
				LOG.error("duplicated salary basic data, displayName: {}", salaryGrade.getUser().getDisplayName());
			}
		}
		
	}
	
	@Scheduled(cron="0 0 0 1,5 * ?")//每月1, 5日触发
	public void bonus(){
		SalaryGrade filter = new SalaryGrade();
		filter.setStatus("T");
		List<SalaryGrade> salaryGrades = salaryGradeService.queryForList(filter);
		
		SalaryBonus filterBonus = new SalaryBonus();
		for(SalaryGrade salaryGrade : salaryGrades){
			filterBonus.setSalaryGrade(salaryGrade);
			int count = salaryBonusService.count(filterBonus);
			if(count == 0){
				//~ ADD
				
				SalaryBasic salaryBasic = new SalaryBasic();
				salaryBasic.setSalaryGrade(salaryGrade);
				//~ setters
				
				salaryBasicService.add(salaryBasic);
			}else if(count == 1){
				//
			}else{
				LOG.error("duplicated salary basic data, displayName: {}", salaryGrade.getUser().getDisplayName());
			}
		}
		
	}
}

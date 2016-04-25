package com.van.halley.quartz.task;

import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.quartz.support.AbstractQuartzTarget;
import com.van.service.QuartzJobService;

public class SalaryCompute extends AbstractQuartzTarget {
	@Autowired
	private QuartzJobService quartzJobService;
	
	public void execute(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(quartzJobService == null ? null : quartzJobService.toString());
		System.out.println("current thread : " + Thread.currentThread().getId() + " jobUserDisplayName: " + getQuartzJob().getOwner().getDisplayName());
	}

}

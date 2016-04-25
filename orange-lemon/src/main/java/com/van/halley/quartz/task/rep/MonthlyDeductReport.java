package com.van.halley.quartz.task.rep;

import java.util.Calendar;

import com.van.halley.quartz.support.AbstractQuartzTarget;

public class MonthlyDeductReport extends AbstractQuartzTarget{
	public void execute(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
	}
	
	public static void main(String[] args) {
		new MonthlyDeductReport().execute();
	}
}

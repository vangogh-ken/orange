package com.van.halley.quartz.task.hr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.core.store.InputStreamDataSource;
import com.van.halley.db.persistence.entity.QuartzJob;
import com.van.halley.quartz.support.AbstractQuartzTarget;
import com.van.halley.util.StringUtil;
import com.van.service.DiskInfoService;
import com.van.service.ReportTemplateService;

public class MonthAttendanceCompute extends AbstractQuartzTarget{
	@Autowired
	private ReportTemplateService reportTemplateService;
	@Autowired
	private DiskInfoService diskInfoService;
	
	public void attendence(){
		QuartzJob quartzJob = getQuartzJob();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String KSSJ = StringUtil.parseDateTime(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		String JSSJ = StringUtil.parseDateTime(cal.getTime());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("KSSJ", KSSJ);
		params.put("JSSJ", JSSJ);
		params.put("CURRENT_USER_ID", quartzJob.getOwner().getId());
		
		InputStream is = reportTemplateService.generateReport("xxxxxx", params);
		try {
			diskInfoService.doneCreateDisk("月度个人考勤补充统计.xls", is.available(), "", new InputStreamDataSource(is), quartzJob.getOwner());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void vacation(){
		QuartzJob quartzJob = getQuartzJob();
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String KSSJ = StringUtil.parseDateTime(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		String JSSJ = StringUtil.parseDateTime(cal.getTime());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("KSSJ", KSSJ);
		params.put("JSSJ", JSSJ);
		params.put("CURRENT_USER_ID", quartzJob.getOwner().getId());
		
		InputStream is = reportTemplateService.generateReport("xxxxxx", params);
		try {
			diskInfoService.doneCreateDisk("月度个人请假统计.xls", is.available(), "", new InputStreamDataSource(is), quartzJob.getOwner());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

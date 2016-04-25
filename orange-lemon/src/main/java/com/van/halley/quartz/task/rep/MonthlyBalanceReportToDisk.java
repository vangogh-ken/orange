package com.van.halley.quartz.task.rep;

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

public class MonthlyBalanceReportToDisk extends AbstractQuartzTarget{
	@Autowired
	private ReportTemplateService reportTemplateService;
	@Autowired
	private DiskInfoService diskInfoService;
	
	
	public void balance(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String KSSJ = StringUtil.parseDateTime(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		String JSSJ = StringUtil.parseDateTime(cal.getTime());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("KSSJ", KSSJ);
		params.put("JSSJ", JSSJ);
		
		QuartzJob quartzJob = getQuartzJob();
		InputStream is = reportTemplateService.generateReport("xxxxxx", params);
		try {
			diskInfoService.doneCreateDisk("月度收入支出表.xls", is.available(), "", new InputStreamDataSource(is), quartzJob.getOwner());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

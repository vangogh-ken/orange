package com.van.halley.job.common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.DiskInfo;
import com.van.service.DiskInfoService;

/**
 * 网盘数据自动清理。周期14天
 * @author anxinxx
 *
 */
@Component
public class DiskClearTask {
	@Autowired
	private DiskInfoService diskInfoService;
	
	@Scheduled(cron="0 0 8 * * ?")
	public void execute(){
		DiskInfo filter = new DiskInfo();
		filter.setStatus(DiskInfo.Status.TRASH);
		List<DiskInfo> list = diskInfoService.queryForList(filter);
		for(DiskInfo item : list){
			if(item.getModifyTime().getTime() + 3600*24*14 < new Date().getTime()){
				diskInfoService.doneClearDisk(new String[]{item.getId()});
			}
		}
	}
}

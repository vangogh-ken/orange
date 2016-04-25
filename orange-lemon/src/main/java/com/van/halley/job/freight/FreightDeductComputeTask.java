package com.van.halley.job.freight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.service.FreightDeductService;

/**
 * 该任务用于计算财务提成
 * @author Think
 *
 */
@Component
public class FreightDeductComputeTask {
	@Autowired
	private FreightDeductService freightDeductService;
	
	
	@Scheduled(cron="0 0 7,8,20,21 * * ?")//每天7，8，20，21点执行小时执行
	//@Scheduled(cron="0 1 * * * ?")//每小时执行
	//@Scheduled(cron="5 * * * * ?")//每分钟执行
	public void deduct(){
		freightDeductService.doneOrderDeduct();
		//freightDeductService.doneBoxDeduct();取消标箱提成
		freightDeductService.settleDeductTime();
	}
}

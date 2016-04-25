package com.van.halley.job.freight;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.FreightAuditRecord;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.service.FreightAuditRecordService;
import com.van.service.FreightOrderService;
	/**
	 * 费用审核超时处理。超时48小时，当前订单锁定，操作和业务都不能做任何操作。
	 * @author Think
	 */
	@Component
	public class FreightAuditExpiredTask {
		private static Logger LOG = LoggerFactory.getLogger(FreightAuditExpiredTask.class);
		@Autowired
		private FreightOrderService freightOrderService;
		@Autowired
		private FreightAuditRecordService freightAuditRecordService;

		//@Scheduled(cron="59 * * * * ?")//每分钟执行
		@Scheduled(cron="0 0 8,12,16 * * ?")//每天8点、12点、16点执行小时执行
		public void execute() {
			FreightOrder filter = new FreightOrder();
			filter.setOrderStatus("审核中");
			List<FreightOrder> freightOrders = freightOrderService.queryForList(filter);
			if(freightOrders != null && !freightOrders.isEmpty()){
				for(FreightOrder freightOrder : freightOrders){
					FreightAuditRecord freightAuditRecord = freightAuditRecordService
							.getFreightAuditRecordProximate(freightOrder.getId());
					if(freightAuditRecord != null && (new Date().getTime() - freightAuditRecord.getCreateTime().getTime() > 1000*60*60*24*2)){
						freightOrder.setOrderStatus("锁定中");
						freightOrderService.modify(freightOrder);
						LOG.info("订单审核超时，订单将被锁定，订单号: {}", freightOrder.getOrderNumber());
					}
				}
			}
		}
	}


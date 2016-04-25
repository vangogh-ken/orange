package com.van.halley.job.freight;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightPrice;
import com.van.halley.util.StringUtil;
import com.van.service.FreightExpenseService;
import com.van.service.FreightPriceService;

/**
 * 该调度任务主要处理之前成本加载与成本关联的问题。
 * @author Think
 *
 */
@Component
public class FreightPriceErrorTask {
	private static Logger LOG = LoggerFactory.getLogger(FreightPriceErrorTask.class);
	@Autowired
	private FreightPriceService freightPriceService;
	@Autowired
	private FreightExpenseService freightExpenseService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 恢复原始成本信息并设置个状态，如果有关联到了费用，则取消关联，新生成一个再与成本相关联
	 */
	//@Scheduled(cron="59 * * * * ?")//每分钟执行
	//@Scheduled(cron="0 0 8,12,16 * * ?")//每天8点、12点、16点执行小时执行
	public void initOriginalPrice(){
		LOG.info("恢复初始成本任务开始执行...");
		List<FreightPrice> originalPrices = freightPriceService.getByOriginalTime();
		for(FreightPrice originalPrice : originalPrices){
			if("O".equals(originalPrice.getStatus())){
				continue;
			}
			FreightExpense filter = new FreightExpense();
			filter.setFreightPrice(originalPrice);
			List<FreightExpense> freightExpenses = freightExpenseService.queryForList(filter);
			for(FreightExpense freightExpense : freightExpenses){
				FreightPrice relatedPrice = freightExpense.getFreightPrice();
				relatedPrice.setId(StringUtil.getUUID());
				relatedPrice.setStatus("T");
				relatedPrice.setCreateTime(new Date());
				relatedPrice.setModifyTime(new Date());
				freightPriceService.add(relatedPrice);
				freightExpense.setFreightPrice(relatedPrice);
				freightExpenseService.modify(freightExpense);
			}
			if(!StringUtil.isNullOrEmpty(originalPrice.getActual())){
				originalPrice.setActual("N");//表示取消特殊，其他状态T/F表示已经做特殊费用和特殊费用专门处理过。
				originalPrice.setActualCount(0);
			}
			originalPrice.setStatus("O");
			freightPriceService.modify(originalPrice);
		}
		LOG.info("恢复初始成本任务执行完毕...");
	}
	
	/**
	 * 处理多个费用关联统一成本的问题。将重新复制一个新的成本再与费用相关联
	 */
	//@Scheduled(cron="59 * * * * ?")//每分钟执行
	public void initDuplicationPrice(){
		LOG.info("费用成本重复关联任务开始执行...");
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID FROM FRE_EXPENSE ");
		sql.append("WHERE EXISTS ");
		sql.append("(SELECT 1 FROM "
				+ "(SELECT FRE_PRICE_ID AS PID FROM FRE_EXPENSE  GROUP BY FRE_PRICE_ID HAVING COUNT(1) > 1 "
				+ "AND FRE_PRICE_ID IS NOT NULL) AS T WHERE T.PID=FRE_PRICE_ID)");
		List<String> ids = jdbcTemplate.queryForList(sql.toString(), String.class);
		if(ids != null && !ids.isEmpty()){
			for(String id : ids){
				FreightExpense freightExpense = freightExpenseService.getById(id);
				FreightPrice freightPrice = freightExpense.getFreightPrice();
				if(freightPrice == null){
					continue;
				}
				freightPrice.setId(StringUtil.getUUID());
				freightPrice.setStatus("T");
				freightPrice.setCreateTime(new Date());
				freightPrice.setModifyTime(new Date());
				freightPriceService.add(freightPrice);
				freightExpense.setFreightPrice(freightPrice);
				freightExpenseService.modify(freightExpense);
			}
		}
		LOG.info("费用成本重复关联任务执行完毕...");
	}
}

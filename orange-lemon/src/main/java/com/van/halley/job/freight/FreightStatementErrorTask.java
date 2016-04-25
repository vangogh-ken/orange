package com.van.halley.job.freight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightStatementDao;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightStatement;

/**
 * 本任务是处理账单金额与费用金额不一致的问题
 * @author Think
 *
 */
@Component
public class FreightStatementErrorTask {
	private static Logger LOG = LoggerFactory.getLogger(FreightStatementErrorTask.class);
	@Autowired
	private FreightStatementDao freightStatementDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 处理重复错误的单位名称
	 */
	//@Scheduled(cron="0 1 * * * ?")//每小时执行
	//@Scheduled(cron="59 * * * * ?")//每分钟执行
	@Scheduled(cron="0 0 8,12,16 * * ?")//每天8点、12点、16点执行小时执行
	public void executePart(){
		Map<FreightStatement, Object> errorResult = new HashMap<FreightStatement, Object>();
		String sql = "SELECT ID FROM FRE_STATEMENT";
		List<String> ids = jdbcTemplate.queryForList(sql, String.class);
		for(String freightStatementId : ids){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightStatementId);
			
			double moneyCountRmb = 0;
			double moneyCountDollar = 0;
			for(FreightExpense freightExpense : freightExpenses){
				if(freightExpense.getCurrency().equals("人民币")){
					if(freightExpense.getCountUnit().equals("票")){
						if(freightExpense.getFreightPrice() != null 
								&& ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
							moneyCountRmb += freightExpense.getFreightPrice().getActualCount();
						}else{
							moneyCountRmb += freightExpense.getActualAmount();
						}
					}else if(freightExpense.getCountUnit().equals("箱")){
						if(freightExpense.getFreightPrice() != null 
								&& ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
							moneyCountRmb += freightExpense.getFreightPrice().getActualCount() * freightExpense.getFreightOrderBoxs().size();
						}else{
							moneyCountRmb += freightExpense.getActualAmount();
						}
					}
				}else if(freightExpense.getCurrency().equals("美元")){
					if(freightExpense.getCountUnit().equals("票")){
						if(freightExpense.getFreightPrice() != null 
								&& ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
							moneyCountDollar += freightExpense.getFreightPrice().getActualCount();
						}else{
							moneyCountDollar += freightExpense.getActualAmount();
						}
					}else if(freightExpense.getCountUnit().equals("箱")){
						if(freightExpense.getFreightPrice() != null 
								&& ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
							moneyCountDollar += freightExpense.getFreightPrice().getActualCount() * freightExpense.getFreightOrderBoxs().size();
						}else{
							moneyCountDollar += freightExpense.getActualAmount();
						}
					}
				}
			}
			
			if(Math.abs(moneyCountRmb - freightStatement.getMoneyCountRmb()) > 1 || 
					Math.abs(moneyCountDollar - freightStatement.getMoneyCountDollar()) > 1){
				errorResult.put(freightStatement, new double[]{moneyCountRmb, moneyCountDollar});
			}
		}
		
		for(FreightStatement freightStatement : errorResult.keySet()){
			LOG.info("账单号：{}， 创建人： {}， 人民币：{}， 美元：{}", 
					freightStatement.getStatementNumber(), 
					freightStatement.getCreator().getDisplayName(),
					((double[])errorResult.get(freightStatement))[0],
					((double[])errorResult.get(freightStatement))[1]);
		}
	}
	
}

package com.van.halley.job.crm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.CrmCustomerFollowDao;
import com.van.halley.db.persistence.entity.CrmCustomerFollow;
@Component
public class CrmCustomerFollowTask {
	private static Logger LOG = LoggerFactory.getLogger(CrmCustomerFollowTask.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CrmCustomerFollowDao crmCustomerFollowDao;
	
	
	//@Scheduled(cron="0 1 * * * ?")//每小时执行
	@Scheduled(cron="0 0 8,12,16 * * ?")//每天8点、12点、16点执行小时执行
	public void execute(){
		String sql = "SELECT ID FROM CRM_CUSTOMER_FOLLOW WHERE STATUS IN ('上级建议', '领导建议') AND TO_DAYS(SYSDATE()) - TO_DAYS(CURRENT_FOLLOW_TIME) > 7";
		List<String> ids = jdbcTemplate.queryForList(sql, String.class);
		if(ids != null && !ids.isEmpty()){
			for(String id : ids){
				CrmCustomerFollow crmCustomerFollow = crmCustomerFollowDao.getById(id);
				crmCustomerFollow.setStatus("已归档");
				crmCustomerFollowDao.modify(crmCustomerFollow);
			}
		}
		LOG.info("完成本次客户联系自动归档任务");
	}
}

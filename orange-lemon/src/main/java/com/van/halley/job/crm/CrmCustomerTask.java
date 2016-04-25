package com.van.halley.job.crm;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.CrmCustomerDao;
import com.van.halley.db.persistence.entity.CrmCustomer;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.User;

/**
 * 客户信息自动处理任务
 * @author anxin
 *
 */
@Component
public class CrmCustomerTask {
	private static Logger LOG = LoggerFactory.getLogger(CrmCustomerTask.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CrmCustomerDao crmCustomerDao;
	
	//@Scheduled(cron="0 1 * * * ?")//每小时执行
	@Scheduled(cron="30 10 1 * * ?")//每天1点10分30秒触发任务
	public void execute(){
		//将订单信息中的委托单位，若不没有在客户信息里面存在，则新增到客户信息里
		String sql = "SELECT DISTINCT DELEGATE_PART FROM FRE_ORDER AS O WHERE NOT EXISTS (SELECT 1 FROM (SELECT CUSTOMER_NAME FROM CRM_CUSTOMER) AS T WHERE T.CUSTOMER_NAME = O.DELEGATE_PART)";
		List<String> customerNames = jdbcTemplate.queryForList(sql, String.class);
		if(customerNames != null && !customerNames.isEmpty()){
			for(String customerName : customerNames){
				CrmCustomer crmCustomer = new CrmCustomer();
				crmCustomer.setCustomerName(customerName);
				crmCustomer.setCustomerType("公海");
				crmCustomer.setStatus("未跟进");
				crmCustomerDao.add(crmCustomer);
			}
		}
		LOG.info("完成本次客户自动添加任务");
	}
	
	@Scheduled(cron="30 10 1 * * ?")//每天1点10分30秒触发任务
	public void execute1(){
		//跟进中的客户若已经是半年内的订单的货主，则客户类型标记为已合作
		String sql = "SELECT ID FROM CRM_CUSTOMER AS C WHERE C.CUSTOMER_Type != '合作' AND EXISTS(SELECT 1 FROM (SELECT CARGO_OWNER FROM FRE_ORDER GROUP BY CARGO_OWNER HAVING TO_DAYS(SYSDATE()) - TO_DAYS(MAX(PLACE_TIME)) < 180) AS T WHERE T.CARGO_OWNER = C.CUSTOMER_NAME)";
		List<String> ids = jdbcTemplate.queryForList(sql, String.class);
		if(ids != null && !ids.isEmpty()){
			for(String id : ids){
				CrmCustomer crmCustomer = crmCustomerDao.getById(id);
				crmCustomer.setCustomerType("合作");
				crmCustomerDao.modify(crmCustomer);
			}
		}
		LOG.info("完成本次货主状态自动任务");
	}
	
	@Scheduled(cron="30 10 1 * * ?")//每天1点10分30秒触发任务
	public void execute2(){
		//最后一次下单时间超过半年的
		String sql = "SELECT ID FROM CRM_CUSTOMER AS C WHERE EXISTS (SELECT 1 FROM (SELECT DELEGATE_PART FROM FRE_ORDER GROUP BY DELEGATE_PART HAVING TO_DAYS(SYSDATE()) - TO_DAYS(MAX(PLACE_TIME)) > 180 ) AS T WHERE T.DELEGATE_PART = C.CUSTOMER_NAME )";
		List<String> ids = jdbcTemplate.queryForList(sql, String.class);
		if(ids != null && !ids.isEmpty()){
			for(String id : ids){
				CrmCustomer crmCustomer = crmCustomerDao.getById(id);
				
				User follower = new User();
				follower.setId("A");
				crmCustomer.setFollower(follower);
				OrgEntity orgEntity = new OrgEntity();
				orgEntity.setId("A");
				crmCustomer.setOrgEntity(orgEntity);
				
				crmCustomer.setCustomerType("公海");
				crmCustomer.setStatus("未跟进");
				crmCustomerDao.modify(crmCustomer);
			}
		}
	}
	
	@Scheduled(cron="30 10 1 * * ?")//每天1点10分30秒触发任务
	public void execute3(){
		//3个月未能转换为合作客户的，自动取消跟进
		String sql = "SELECT ID FROM CRM_CUSTOMER AS C WHERE C.STATUS='已跟进' AND C.CUSTOMER_TYPE='跟进' AND EXISTS (SELECT 1 FROM (SELECT CRM_CUSTOMER_ID FROM CRM_CUSTOMER_FOLLOW GROUP BY CRM_CUSTOMER_ID HAVING TO_DAYS(SYSDATE()) - MIN(CURRENT_FOLLOW_TIME) > 90) AS T WHERE T.CRM_CUSTOMER_ID=C.ID)";
		List<String> ids = jdbcTemplate.queryForList(sql, String.class);
		if(ids != null && !ids.isEmpty()){
			for(String id : ids){
				CrmCustomer crmCustomer = crmCustomerDao.getById(id);
				
				User follower = new User();
				follower.setId("A");
				crmCustomer.setFollower(follower);
				OrgEntity orgEntity = new OrgEntity();
				orgEntity.setId("A");
				crmCustomer.setOrgEntity(orgEntity);
				
				crmCustomer.setCustomerType("公海");
				crmCustomer.setStatus("未跟进");
				crmCustomerDao.modify(crmCustomer);
			}
		}
	}
	
	@Scheduled(cron="30 10 1 * * ?")//每天1点10分30秒触发任务
	public void execute4(){
		//已跟进且有订单，但非合作客户自动转为合作客户
		String sql = "SELECT ID FROM CRM_CUSTOMER AS C WHERE C.STATUS='已跟进' AND C.CUSTOMER_TYPE!='合作' AND EXISTS (SELECT 1 FROM (SELECT DELEGATE_PART FROM FRE_ORDER GROUP BY DELEGATE_PART HAVING TO_DAYS(SYSDATE()) - TO_DAYS(MAX(PLACE_TIME)) < 180 ) AS T WHERE T.DELEGATE_PART = C.CUSTOMER_NAME )";
		List<String> ids = jdbcTemplate.queryForList(sql, String.class);
		if(ids != null && !ids.isEmpty()){
			for(String id : ids){
				CrmCustomer crmCustomer = crmCustomerDao.getById(id);
				crmCustomer.setCustomerType("合作");
				crmCustomerDao.modify(crmCustomer);
			}
		}
	}
}

package com.van.halley.job.freight;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.core.spring.ApplicationContextHelper;
import com.van.core.spring.ApplicationPropertiesFactoryBean;

/**
 * 本任务是处理类似的单位与费用类型的问题。
 * @author Think
 *
 */
@Component
public class FreightDuplicationErrorTask {
	private static Logger LOG = LoggerFactory.getLogger(FreightDuplicationErrorTask.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static boolean canExecute = "true".equals((String)((ApplicationPropertiesFactoryBean)ApplicationContextHelper.getBean(ApplicationPropertiesFactoryBean.class)).getProperties().getProperty("scheduler.fre.duppication")) ? true : false;
	/**
	 * 处理重复错误的单位名称
	 */
	//@Scheduled(cron="59 * * * * ?")
	//@Scheduled(cron="0 1 * * * ?")//每小时执行
	@Scheduled(cron="0 0 8,12,16 * * ?")//每天8点、12点、16点执行小时执行
	public void executePart(){
		if(!canExecute){
			return;
		}
		//需要更新的表名
		String[] targetTables = {"FAS_ACCOUNT","FAS_DUE","FAS_PAY","FRE_DELEGATE", "FRE_EXPENSE", "FRE_INVOICE", "FRE_NET_DAY", "FRE_PRICE", "FRE_STATEMENT"};
		//key 错误的 单位名称   value正确的单位名称
		Map<String, String> map = new HashMap<String, String>();
		map.put("T.S. LINES COMPANY LIMITED", "T.S. LINES LIMITED");
		map.put("川崎汽船(中国)有限公司南京分公司", "川崎汽船（中国）有限公司南京分公司");
		map.put("韩进海运(中国)有限公司", "韩进海运（中国）有限公司");
		map.put("赫伯罗特船务(中国)有限公司", "赫伯罗特船务（中国）有限公司");
		map.put("捷达国际货物运输公司成都分公司", "捷达国际运输公司成都分公司");
		map.put("四川安邦货运有限公司", "四川安邦国际货运有限公司");
		map.put("太平船务(中国)有限公司重庆分公司", "太平船务（中国）有限公司重庆分公司");
		
		for(String key : map.keySet()){
			for(String targetTable : targetTables){
				StringBuilder sql = new StringBuilder();
				sql.append("UPDATE ");
				sql.append(targetTable);
				if("FAS_DUE".equals(targetTable)){
					sql.append(" SET FRE_PART_ID_PAY=(SELECT ID FROM FRE_PART WHERE PART_NAME=?) WHERE FRE_PART_ID_PAY IN(SELECT ID FROM FRE_PART WHERE PART_NAME=? AND STATUS='T')");
				}else if("FRE_EXPENSE".equals(targetTable)){
					sql.append(" SET FRE_PART_ID_B=(SELECT ID FROM FRE_PART WHERE PART_NAME=?) WHERE FRE_PART_ID_B IN(SELECT ID FROM FRE_PART WHERE PART_NAME=? AND STATUS='T')");
				}else if("FRE_STATEMENT".equals(targetTable)){
					sql.append(" SET FRE_PART_ID_B=(SELECT ID FROM FRE_PART WHERE PART_NAME=?) WHERE FRE_PART_ID_B IN(SELECT ID FROM FRE_PART WHERE PART_NAME=? AND STATUS='T')");
				}else{
					sql.append(" SET FRE_PART_ID=(SELECT ID FROM FRE_PART WHERE PART_NAME=?) WHERE FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME=? AND STATUS='T')");
				}
				
				int count = jdbcTemplate.update(sql.toString(), map.get(key), key);
				LOG.info("执行单位重复错误纠正任务， sql {}， 正确的单位: {}， 错误的单位: {}， 表名: {}， 更新数: {}", 
						sql.toString(), map.get(key), key, targetTable, count);
			}
			//处理所有的状态
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE FRE_PART SET STATUS='T' WHERE STATUS IS NULL");
			jdbcTemplate.update(sql.toString());
			//标记错误的状态
			sql = new StringBuilder();
			sql.append("UPDATE FRE_PART SET STATUS='F' WHERE PART_NAME=?");
			int count = jdbcTemplate.update(sql.toString(), key);
			LOG.info("禁用重复错误的单位: {}， 数量:  {}", key, count);
		}
	}
	
	/**
	 * 处理重复错误的费用类型
	 */
	//@Scheduled(cron="59 * * * * ?")
	//@Scheduled(cron="0 1 * * * ?")//每小时执行
	@Scheduled(cron="0 0 8,12,16 * * ?")//每天8点、12点、16点执行小时执行
	public void executeExpense(){
		if(!canExecute){
			return;
		}
		//需要更新的表名
		String[] targetTables = {"FRE_EXPENSE", "FRE_PRICE"};
		//key 错误的费用类型   value正确的费用类型
		Map<String, String> map = new HashMap<String, String>();
		map.put("集港费-杨浦--外港", "集港费-杨浦-外港");
		map.put("集港费-杨浦--洋山", "集港费-杨浦-洋山");
		
		map.put("YAS/EBS", "YAS");
		map.put("仓储费-海关暂扣入监管库收取此费用", "仓储费");
		map.put("超重费-江运物流基地", "超重费");
		map.put("超重费-铁路物流基地", "超重费");
		map.put("吊重箱费-江运物流基地", "吊重箱费");
		map.put("吊重箱费-铁路物流基地", "吊重箱费");
		map.put("吊重箱费（现代物流堆场）", "吊重箱费");
		map.put("加固费1", "加固费");
		map.put("加固费2", "加固费");
		map.put("加固费（砂岩）", "加固费");
		map.put("口岸提空箱", "口岸提空箱费");
		map.put("口岸提空箱-外港", "口岸提空箱费");
		map.put("口岸提空箱-洋山", "口岸提空箱费");
		map.put("上下车费-外港", "上下车费");
		map.put("上下车费-洋山", "上下车费");
		map.put("铁保费", "铁路保价");
		map.put("退关注销", "退关注销费");
		
		for(String key : map.keySet()){
			for(String targetTable : targetTables){
				StringBuilder sql = new StringBuilder();
				sql.append("UPDATE ");
				sql.append(targetTable);
				sql.append(" SET FRE_EXPENSE_TYPE_ID=(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME=?) WHERE FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME=? AND STATUS='T')");
				
				int count = jdbcTemplate.update(sql.toString(), map.get(key), key);
				LOG.info("执行费用类型重复错误纠正任务， sql {}， 正确的类型: {}， 错误的类型: {}， 表名: {}， 更新数: {}", 
						sql.toString(), map.get(key), key, targetTable, count);
			}
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE FRE_EXPENSE_TYPE SET STATUS='T' WHERE STATUS IS NULL");
			jdbcTemplate.update(sql.toString());
			
			sql = new StringBuilder();
			sql.append("UPDATE FRE_EXPENSE_TYPE SET STATUS='F' WHERE TYPE_NAME=?");
			int count = jdbcTemplate.update(sql.toString(), key);
			LOG.info("禁用重复错误的费用类型: {}， 数量:  {}", key, count);
		}
	}
}

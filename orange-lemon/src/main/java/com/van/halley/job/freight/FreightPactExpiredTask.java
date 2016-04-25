package com.van.halley.job.freight;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.service.OutMsgInfoService;

/**
 * 合同逾期提醒
 * 
 * @author Think
 */
@Component
public class FreightPactExpiredTask {
	private static Logger LOG = LoggerFactory.getLogger(FreightPactExpiredTask.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private OutMsgInfoService outMsgInfoService;
	@Autowired
	private UserDao userDao;

	// @Scheduled(cron="59 * * * * ?")//每分钟执行
	@Scheduled(cron = "1 1 9 * * ?") // 每天9点1分1秒执行
	public void execute() {
		String sql = "SELECT * FROM FRE_PACT WHERE CUT_OFF_DATE > DATE_ADD(SYSDATE(),INTERVAL -1 MONTH)";
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : results) {
			OutMsgInfo msg = new OutMsgInfo();
			msg.setTitle(map.get("") + "将逾期，请尽快处理。");
			msg.setMsgType("系统自提醒");
			msg.setContent(map.get("") + "将逾期，请尽快处理。");
			msg.setSender(userDao.getByDisplayName("管理员"));
			msg.setReceiver(userDao.getByDisplayName((String) map.get("TRANSACTOR")));
			outMsgInfoService.add(msg);
			msg.setReceiver(userDao.getByDisplayName("赵敏"));
			outMsgInfoService.add(msg);
			msg.setReceiver(userDao.getByDisplayName("梁优悠"));
			outMsgInfoService.add(msg);
		}

		LOG.info("合同到期自动提醒任务执行完毕！");
	}
}

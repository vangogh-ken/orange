package com.van.halley.bpm.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Vangogh Activiti将按照自定义的 customGroupEntityManager对用户进行选取
 * 本项目约定所有流程的签收，委托等处理都使用将userId
 * 
 */
public class CustomGroupEntityManager extends GroupEntityManager {
	private static Logger logger = LoggerFactory
			.getLogger(CustomGroupEntityManager.class);
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Group> findGroupsByUser(String userId) {
		String sql = "SELECT * FROM SYS_AUTH_ROLE WHERE ID IN (SELECT ROLE_ID FROM SYS_AUTH_POSITION_ROLE WHERE POSITION_ID=(SELECT POSITION_ID FROM SYS_AUTH_USER WHERE ID=?))";
		logger.debug("findGroupsByUser userId : {}", userId);
		logger.debug("findGroupsByUser sql: {}", sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, userId);
		List<Group> groups = new ArrayList<Group>();

		for (Map<String, Object> map : list) {
			String name = (String) map.get("name");
			GroupEntity groupEntity = new GroupEntity(name);
			groups.add(groupEntity);
		}

		return groups;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

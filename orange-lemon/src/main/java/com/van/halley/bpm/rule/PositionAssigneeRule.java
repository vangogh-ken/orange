package com.van.halley.bpm.rule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 获得部门最接近的对应的岗位的人的信息.
 */
public class PositionAssigneeRule extends AbstractAssigneeRule {
    private static Logger logger = LoggerFactory.getLogger(PositionAssigneeRule.class);
  	private JdbcTemplate jdbcTemplate;

    public List<String> process(String value, String initiator) {
        return getPositionUsers(value);
    }
    
    public List<String> getPositionUsers(String positionName){
    	String positionSql = "SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE POSITION_ID=(SELECT ID FROM SYS_AUTH_POSITION WHERE POSITION_NAME=?)";
    	
    	if(jdbcTemplate == null){
    		jdbcTemplate = getJdbcTemplate();
    	}
    	List<String> positionUsers = jdbcTemplate.queryForList(positionSql, String.class, positionName);
    	if(positionUsers == null){
    		logger.debug("Could not find position for: {}", positionName);
    		throw new NullPointerException("Could not find users under this position");
    	}
    	return positionUsers;
    }
}

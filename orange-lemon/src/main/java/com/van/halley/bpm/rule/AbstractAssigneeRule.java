package com.van.halley.bpm.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.core.spring.ApplicationContextHelper;
import com.van.service.UserService;

/**
 * 通用的抽象工具基类.
 * @todo 这里很多的逻辑都应该移动到orgConnector里
 */
public abstract class AbstractAssigneeRule implements AssigneeRule {
    private static Logger logger = LoggerFactory.getLogger(AbstractAssigneeRule.class);
    
    private JdbcTemplate jdbcTemplate;
    private UserService userService;
    
    public JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate != null) {
            return jdbcTemplate;
        }

        jdbcTemplate = ApplicationContextHelper.getBean(JdbcTemplate.class);

        return jdbcTemplate;
    }
    
    public UserService getUserService() {
        if (userService != null) {
            return userService;
        }

        userService = ApplicationContextHelper.getBean(UserService.class);

        return userService;
    }
    
}

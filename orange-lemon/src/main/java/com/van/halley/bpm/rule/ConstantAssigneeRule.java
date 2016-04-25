package com.van.halley.bpm.rule;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.db.persistence.entity.User;
import com.van.service.UserService;

/**
 * 已弃用
 * 一个组织机构内，职位等级只能有两个
 * 组织机构内，直接上级为同组织机构内职位等级最高的人员；直接领导为上上级，如果找不到在同组织机构下的职位等级最高的，则应当找其直接上级的上级；
 * 一些常用词规则: 直接上级, 组长, 部门领导, 总监等
 */
public class ConstantAssigneeRule extends AbstractAssigneeRule {
    private static Logger logger = LoggerFactory.getLogger(ConstantAssigneeRule.class);
    
    private UserService userService;
  	private JdbcTemplate jdbcTemplate;

    public List<String> process(String value, String initiator) {
    	if(value.equals("直接上级")){
    		 return this.getDirectSuperior(initiator);
    	}else if(value.equals("直接领导")){
   		 	return this.getDirectShepherd(initiator);
    	}
    	
    	return null;
    }
    
    /**
     * 此处的userId为用户的ID
     * @param userId
     * @return
     */
    public List<String> getDirectSuperior(String userId){
    	if(userService == null){
    		userService = getUserService();
    	}
    	List<User> directSuperiors = userService.getDirectSuperior(userId);//FIXME 应当使用ID
    	//List<User> directSuperiors = userService.getDirectSuperior(userService.getByDisplayName(userId).getId());
    	if(directSuperiors != null && !directSuperiors.isEmpty()){
    		List<String> list = new ArrayList<String>();
    		for(User directSuperior : directSuperiors){
    			list.add(directSuperior.getDisplayName());
    		}
    		
    		return list;
    	}else {
    		return null;
    	}
    }
    
    
    /**
     * 此处的userId为用户的ID
     * @param userId
     * @return
     */
    public List<String> getDirectShepherd(String userId){
    	if(userService == null){
    		userService = getUserService();
    	}
    	
    	List<User> directShepherds = userService.getDirectShepherd(userId);//FIXME 应当使用ID
    	//List<User> directShepherds = userService.getDirectShepherd(userService.getByDisplayName(userId).getId());
    	if(directShepherds != null && !directShepherds.isEmpty()){
    		List<String> list = new ArrayList<String>();
    		for(User directShepherd : directShepherds){
    			list.add(directShepherd.getDisplayName());
    		}
    		
    		return list;
    	}else {
    		return null;
    	}
    }

    /**
     * 直接上级
     */
   /* public List<String> getDirectSuperior(String userId){
    	String directSuperiorSql = "SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE ID IN"
    			+ "(SELECT USER_ID FROM SYS_AUTH_ORG_ENTITY_IDENTITY WHERE ID="
    			+ "(SELECT ORG_ENTITY FROM SYS_AUTH_POSITION WHERE ID="
    			+ "(SELECT POSITION_ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME=?)))";
    	if(jdbcTemplate == null){
    		jdbcTemplate = getJdbcTemplate();
    	}
    	List<String> directSuperior = jdbcTemplate.queryForList(directSuperiorSql, String.class, userId);
    	if(directSuperior == null){
    		logger.debug("Could not find direct superior for: {}", userId);
    		throw new NullPointerException("Could not find direct superior");
    	}
    	return directSuperior;
    }*/
    
    /**
     * 直接领导
     */
    /*public String getDirectShepherd(String userId){
    	String directShepherdSql = "SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE ID="
		    	+ "(SELECT DUTY_USER_ID FROM SYS_AUTH_ORG_ENTITY WHERE ID="
		    	+ "(SELECT UNDER_ORG_ID FROM SYS_AUTH_POSITION WHERE ID="
    			+ "(SELECT POSITION_ID FROM SYS_AUTH_USER WHERE ID="
    			+ "(SELECT DUTY_USER_ID FROM SYS_AUTH_ORG_ENTITY WHERE ID="
    			+ "(SELECT UNDER_ORG_ID FROM SYS_AUTH_POSITION WHERE ID="
    			+ "(SELECT POSITION_ID FROM SYS_AUTH_USER WHERE DISPLAY_NAME=?))))))";
    	if(jdbcTemplate == null){
    		jdbcTemplate = getJdbcTemplate();
    	}
    	String directFurtherSuperior = jdbcTemplate.queryForObject(directShepherdSql, String.class, userId);
    	if(directFurtherSuperior == null){
    		logger.debug("Could not find direct superior for: {}", userId);
    		throw new NullPointerException("Could not find direct superior");
    	}
    	return directFurtherSuperior;
    }*/
}

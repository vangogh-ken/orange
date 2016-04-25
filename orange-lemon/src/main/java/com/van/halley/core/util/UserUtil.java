package com.van.halley.core.util;

import java.util.List;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.UserService;

/**
 * 提供用户各种关系的查询
 * @author anxin
 *
 */
public class UserUtil {
	private static UserService userService = ApplicationContextHelper.getBean("userService");
	/**
	 * 获取用户名
	 * @param userId
	 * @return
	 */
	public static String getAnyDisplayName(String userId){
		User user = userService.getById(userId);
		return user == null ? "" : user.getDisplayName();
	}
	
	/**
	 * 判断是否为直接上级
	 * @param superiorId
	 * @param userId
	 * @return
	 */
	public static boolean isSuperior(String superiorId, String userId){
		boolean flag = false;
		if(StringUtil.isNullOrEmpty(superiorId) || StringUtil.isNullOrEmpty(userId)){
			return flag;
		}
		List<User> superiors = userService.getDirectSuperior(userId);
		if(superiors == null || superiors.isEmpty()){
			return flag;
		}
		for(User superior : superiors){
			if(superiorId.equals(superior.getId())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否为直接领导
	 * @param shepherdId
	 * @param userId
	 * @return
	 */
	public static boolean isShepherd(String shepherdId, String userId){
		boolean flag = false;
		if(StringUtil.isNullOrEmpty(shepherdId) || StringUtil.isNullOrEmpty(userId)){
			return flag;
		}
		List<User> shepherds = userService.getDirectShepherd(userId);
		if(shepherds == null || shepherds.isEmpty()){
			return flag;
		}
		for(User shepherd : shepherds){
			if(shepherdId.equals(shepherd.getId())){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 判断是否为上级或者上上级
	 * @param currentUserId
	 * @param userId
	 * @return
	 */
	public static boolean isSuperiorOrShepherd(String currentUserId, String userId){
		boolean flag = false;
		if(StringUtil.isNullOrEmpty(currentUserId) || StringUtil.isNullOrEmpty(userId)){
			return flag;
		}
		List<User> superiors = userService.getDirectSuperior(userId);
		if(superiors == null || superiors.isEmpty()){
			return flag;
		}
		for(User superior : superiors){
			if(currentUserId.equals(superior.getId())){
				flag = true;
				break;
			}
		}
		if(!flag){
			List<User> shepherds = userService.getDirectShepherd(userId);
			if(shepherds == null || shepherds.isEmpty()){
				return flag;
			}
			for(User shepherd : shepherds){
				if(currentUserId.equals(shepherd.getId())){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}

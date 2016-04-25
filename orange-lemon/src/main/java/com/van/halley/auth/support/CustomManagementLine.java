package com.van.halley.auth.support;

import java.util.Arrays;
import java.util.List;


import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.User;

/**
 * 自定义上下级管理
 * @author anxin
 *
 */
public class CustomManagementLine {
	private static UserDao userDao = null;;
	
	static{
		userDao = ApplicationContextHelper.getBean("userDao");
	}
	
	/**
	 * 获取直接上级
	 * @param user
	 * @return
	 */
	public static List<User> getDirectSuperior(User user){
		String orgEntityName = user.getPosition().getOrgEntity().getOrgEntityName();
		int grade = user.getPosition().getGrade();
		switch(orgEntityName){
			case "财务部":
				if(grade < 3){
					return Arrays.asList(userDao.getByDisplayName("杜容"));
				}else if(grade == 3){
					return Arrays.asList(userDao.getByDisplayName("熊森"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "重庆分公司":
				if(grade < 4){
					return Arrays.asList(userDao.getByDisplayName("李娅梅"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "场站部":
				if(grade < 3){
					return Arrays.asList(userDao.getByDisplayName("叶荣科"), userDao.getByDisplayName("艾强"), userDao.getByDisplayName("陈京军"));
				}else if(grade == 3){
					return Arrays.asList(userDao.getByDisplayName("曾勇刚"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "公路运输部":
				if(grade < 4){
					return Arrays.asList(userDao.getByDisplayName("邹锦秀"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "自有车队":
				if(grade == 1){
					return Arrays.asList(userDao.getByDisplayName("曾小兵"));
				}else if(grade == 2){
					return Arrays.asList(userDao.getByDisplayName("郑红兵"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "下单部":
				if("孙愉然".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("卢沙沙"));
				}else if("卢沙沙".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("夏莺"));
				}
			break;
			
			case "结算部":
				if("张杨".equals(user.getDisplayName()) || "伍君".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("张巧"));
				}else if("张巧".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("夏莺"));
				}
			break;
			
			case "市场二部":
				if("陈丽".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("杨华军"));
				}else if("杨华军".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("范磊"));
				}
			break;
			
			case "客户服务一部":
				if("崔媛".equals(user.getDisplayName()) || "胡佳乐".equals(user.getDisplayName()) ||
						"刘坚".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("肖丰"));
				}else if("肖丰".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("范磊"));
				}
			break;
		}
		return null;
	}
	
	/**
	 * 获取直接领导
	 * @param user
	 * @return
	 */
	public static List<User> getDirectShepherd(User user){
		String orgEntityName = user.getPosition().getOrgEntity().getOrgEntityName();
		int grade = user.getPosition().getGrade();
		switch(orgEntityName){
			case "财务部":
				if(grade < 3){
					return Arrays.asList(userDao.getByDisplayName("熊森"));
				}else if(grade == 3){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "重庆分公司":
				if(grade < 4){
					return Arrays.asList(userDao.getByDisplayName("李娅梅"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "场站部":
				if(grade < 3){
					return Arrays.asList(userDao.getByDisplayName("曾勇刚"));
				}else if(grade == 3){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "公路运输部":
				if(grade < 4){
					return Arrays.asList(userDao.getByDisplayName("邹锦秀"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "自有车队":
				if(grade == 1){
					return Arrays.asList(userDao.getByDisplayName("郑红兵"));
				}else if(grade == 2){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}else if(grade == 4){
					return Arrays.asList(userDao.getByDisplayName("刘渝源"));
				}
			break;
			
			case "下单部":
				if("孙愉然".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("夏莺"));
				}else if("卢沙沙".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("夏莺"));
				}
			break;
			
			case "结算部":
				if("张杨".equals(user.getDisplayName()) || "伍君".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("夏莺"));
				}else if("张巧".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("夏莺"));
				}
			break;
			
			case "市场二部":
				if("陈丽".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("范磊"));
				}else if("杨华军".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("范磊"));
				}
			break;
			
			case "客户服务一部":
				if("崔媛".equals(user.getDisplayName()) || "胡佳乐".equals(user.getDisplayName()) ||
						"刘坚".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("范磊"));
				}else if("肖丰".equals(user.getDisplayName())){
					return Arrays.asList(userDao.getByDisplayName("范磊"));
				}
			break;
		}
		
		return null;
	}
}

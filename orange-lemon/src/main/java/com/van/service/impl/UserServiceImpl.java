package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.OrgEntityIdentityDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.OrgEntityIdentity;
import com.van.halley.db.persistence.entity.Role;
import com.van.halley.db.persistence.entity.User;
import com.van.service.UserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {
	private static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrgEntityIdentityDao orgEntityIdentityDao;
	@Autowired
	private WxCpService wxCpService;

	public PageView<User> query(PageView<User> pageView, User user) {
		List<User> list = userDao.query(pageView, user);
		pageView.setResults(list);
		return pageView;
	}

	public void add(User user) {
		userDao.add(user);

	}

	public void delete(String id) {
		userDao.delete(id);

	}

	public User getById(String id) {
		return userDao.getById(id);
	}

	public void modify(User user) {
		userDao.modify(user);
	}

	public int countUser(String userName, String userPassword) {
		return userDao.countUser(userName, userPassword);
	}

	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public Role findbyUserRole(String userId) {
		return userDao.findbyUserRole(userId);
	}

	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User getByDisplayName(String dispalyName) {
		return userDao.getByDisplayName(dispalyName);
	}

	@Override
	public boolean isExistsUserName(String userName) {
		if (userDao.countUserByUserName(userName) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isExistsDisplayName(String displayName) {
		if (userDao.countUserByDisplayName(displayName) > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<User> queryForList(User user) {
		return userDao.queryForList(user);
	}

	@Override
	public List<User> getDirectSuperior(String userId) {
		User current = userDao.getById(userId);
		if(current.getPosition() != null){
			List<User> users = userDao.getTopByOrgEntityId(current.getOrgEntity().getId());
			boolean containsSelf = false;
			for(User user : users){
				if(userId.equals(user.getId())){
					containsSelf = true;
					break;
				}
			}
			
			if(!containsSelf){
				return users;
			}else{
				LOG.info("该用户已是该所在组织机构的最高职级，则取其分管领导，姓名：{}", current.getDisplayName());
				return userDao.getGafferByOrgEntityId(current.getOrgEntity().getId());
			}
		}else{
			LOG.info("该用户尚未分配任何职位，找不到其上级，姓名：{}", current.getDisplayName());
			return null;
		}
	}

	@Override
	public List<User> getDirectShepherd(String userId) {
		User current = userDao.getById(userId);
		List<User> directSuperiors = getDirectSuperior(userId);
		List<User> directShepherds = new ArrayList<User>();
		if(directSuperiors != null && !directSuperiors.isEmpty()){
			for(User user : directSuperiors){
				List<User> superiors = getDirectSuperior(user.getId());
				if(superiors != null && !superiors.isEmpty()){
					for(User superior :superiors){
						boolean isContains = false;
						for(User directShepherd : directShepherds){
							if(directShepherd.getId().equals(superior.getId())){
								isContains = true;
								break;
							}
						}
						
						if(!isContains){
							directShepherds.add(superior);
						}
					}
				}
			}
			
			return directShepherds;
		}else{
			LOG.info("找不到该用户的领导，姓名：{}", current.getDisplayName());
			return null;
		}
	}

	@Override
	public List<User> getCoworker(String userId) {
		User current = userDao.getById(userId);
		if(current.getPosition() == null || current.getPosition().getOrgEntity() == null){
			LOG.info("找不到该用户的职位或部门，姓名：{}", current.getDisplayName());
			return null;
		}
		List<User> users = userDao.getByOrgEntityId(current.getPosition().getOrgEntity().getId());
		if(users != null && !users.isEmpty()){
			for(int i=0, size=users.size(); i<size; i++){
				if(userId.equals(users.get(i).getId())){
					users.remove(i);
					i--;
					size--;
				}
			}
			return users;
		}else{
			LOG.info("找不到该用户的部门的人员，姓名：{}", current.getDisplayName());
			return null;
		}
	}

	@Override
	public List<User> getDirectUnderling(String userId) {
		List<User> results = new ArrayList<User>();
		User current = userDao.getById(userId);
		//同部门，职级小于自己的
		List<User> coworkers = userDao.getByOrgEntityId(current.getPosition().getOrgEntity().getId());
		if(coworkers != null && !coworkers.isEmpty()){
			for(User user : coworkers){
				if(user.getPosition().getGrade() < current.getPosition().getGrade()){
					results.add(user);
				}
			}
		}
		//取自己分管的部门，目标部门内职级最高的人
		OrgEntityIdentity filter = new OrgEntityIdentity();
		filter.setUserId(userId);
		List<OrgEntityIdentity> identities = orgEntityIdentityDao.queryForList(filter);
		if(identities != null && !identities.isEmpty()){
			for(OrgEntityIdentity identity : identities){
				List<User> underlings = userDao.getTopByOrgEntityId(identity.getOrgEntityId());
				for(User user : underlings){
					results.add(user);
				}
			}
		}
		
		return results;
	}

	@Override
	public List<User> getDirectSubordinate(String userId) {
		List<User> results = new ArrayList<User>();
		List<User> underling = getDirectUnderling(userId);
		if(underling != null && !underling.isEmpty()){
			for(User user : underling){
				List<User> subordinate = getDirectUnderling(user.getId());
				if(subordinate != null && !subordinate.isEmpty()){
					results.addAll(subordinate);
				}
			}
		}
		return results;
	}

	@Override
	public boolean userInviteWeixin(String userId) {
		boolean flag = false;
		User user = userDao.getById(userId);
		if(user != null && user.getUserBase() != null && user.getUserBase().getWeixinName() != null){
			try{
				boolean exists = false;
				List<WxCpUser> wxCpUsers = wxCpService.userList(2, true, 0);
				for(WxCpUser u : wxCpUsers){
					if(u.getName().equals(user.getDisplayName())){
						exists = true;
						break;
					}
				}
				
				if(!exists){
					WxCpUser wxCpUser = new WxCpUser();
					wxCpUser.setName(user.getDisplayName());
					wxCpUser.setUserId(user.getUserBase().getWeixinName());
					wxCpUser.setWeiXinId(user.getUserBase().getWeixinName());
					wxCpUser.setEmail(user.getUserBase().getMailAddress());
					wxCpUser.setMobile(user.getUserBase().getMobile());
					wxCpUser.setDepartIds(new Integer[]{2});//~ 默认部门
					wxCpUser.setEnable(1);
					wxCpService.userCreate(wxCpUser);
				}
				
				int type = wxCpService.invite(user.getUserBase().getWeixinName(), "邀请您关注企业信息号");
				if(type == 1){
					flag = true;
					LOG.info("通过微信邀请关注成功，用户：{}， 微信：{}", user.getDisplayName(), user.getUserBase().getWeixinName());
				}else if(type == 2){
					flag = true;
					LOG.info("通过邮件邀请关注成功，用户：{}， 微信：{}", user.getDisplayName(), user.getUserBase().getWeixinName());
				}else{
					flag = false;
					LOG.info("邀请关注失败，用户：{}， 微信：{}", user.getDisplayName(), user.getUserBase().getWeixinName());
				}
				
			}catch(WxErrorException e){
				LOG.error("邀请微信关注获取到错误 用户：{}， 微信：{} 错误： {} ", user.getDisplayName(), user.getUserBase().getWeixinName(), e);
			}
		}
		return flag;
	}
}

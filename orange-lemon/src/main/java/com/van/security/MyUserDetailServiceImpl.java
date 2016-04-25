package com.van.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.van.halley.db.persistence.ResourceDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.Resource;
import com.van.halley.db.persistence.entity.Role;

/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 获得对象的方式： WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 或在JSP中： <sec:authentication property="principal.username"/>
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可 权限验证类
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ResourceDao resourceDao;
	/**
	 * 缓存用户与资源，使用用户ID为KEY
	 */
	//public static Map<String, List<Resource>> cachedUserResource = new HashMap<String, List<Resource>>();
	/**
	 * 缓存用户与权限，使用用户ID为KEY
	 */
	//public static Map<String, Set<GrantedAuthority>> cachedUserAuthority = new HashMap<String, Set<GrantedAuthority>>();

	// 登录验证
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		com.van.halley.db.persistence.entity.User user = userDao.getByUserName(username);
		if (user == null || user.getPosition() == null){
			throw new UsernameNotFoundException(username + " not exist or not set right position!");
		}
		
		//Collection<GrantedAuthority> grantedAuths = cachedUserAuthority.get(user.getId());//从缓存中取
		//if(grantedAuths == null){
			//grantedAuths = obtainGrantedAuthorities(user);// 取得用户的权限
		//}
		Collection<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);
		User userdetail = new User(user.getUserName(), user.getPassword(), true, true, true, true, grantedAuths);// 封装成spring security的user
		return userdetail;
	}

	/**
	 * 取得用户的权限
	 * @param user
	 * @return
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(com.van.halley.db.persistence.entity.User user) {
		//List<Resource> resources = cachedUserResource.get(user.getUserName());
		//if(resources == null){
			//resources = resourceDao.getResourceByPositionId(user.getPosition().getId());
			//cachedUserResource.put(user.getId(), resources);取消
		//}
		List<Resource> resources = resourceDao.getResourceByPositionId(user.getPosition().getId());
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Resource resource : resources) {//TODO:ZZQ 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头// 关联代码：applicationContext-security.xml// 关联代码：com.van.core.security.MySecurityMetadataSource#loadResourceDefine
			authorities.add(new SimpleGrantedAuthority("ROLE_" + resource.getResourceKey()));
		}
		
		for(Role role : user.getRoles()){//添加角色信息
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleKey()));
		}
		
		//cachedUserAuthority.put(user.getId(), authorities);取消
		return authorities;
	}
}
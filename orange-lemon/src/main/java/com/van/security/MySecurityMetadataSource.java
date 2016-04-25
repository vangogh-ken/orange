package com.van.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.van.halley.db.persistence.ResourceDao;
import com.van.halley.db.persistence.RoleDao;
import com.van.halley.db.persistence.entity.Resource;
import com.van.halley.db.persistence.entity.Role;

/**
 * 加载资源与权限的对应关系
 * */
@Service
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private RoleDao roleDao;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	//加载所有资源与权限的关系
	@PostConstruct
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resource> resources = this.resourceDao.getAll();
			for (Resource resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				// 关联代码：spring-security.xml //通过资源名称来表示具体的权限 注意：必须"ROLE_"开头
				// 关联代码：com.van.security.MyUserDetailServiceImpl#obtionGrantedAuthorities
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + resource.getResourceKey());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getResourceUrl(), configAttributes);
			}
			//也将角色信息加入
			List<Role> roles = roleDao.getAll();
			for(Role role : roles){
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + role.getRoleKey());
				configAttributes.add(configAttribute);
				resourceMap.put(role.getRoleKey(), configAttributes);
			}
		}
	}
	
	public void forceLoadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Resource> resources = this.resourceDao.getAll();
		for (Resource resource : resources) {
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			ConfigAttribute configAttribute = new SecurityConfig("ROLE_"
					+ resource.getResourceKey());
			configAttributes.add(configAttribute);
			resourceMap.put(resource.getResourceUrl(), configAttributes);
		}
		
		List<Role> roles = roleDao.getAll();
		for(Role role : roles){
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + role.getRoleKey());
			configAttributes.add(configAttribute);
			resourceMap.put(role.getRoleKey(), configAttributes);
		}
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if (resourceMap == null) {
			loadResourceDefine();
		}
		if (requestUrl.indexOf("?") > -1) {
			
			if(requestUrl.contains("/common/common-business-list.do")){
				requestUrl = requestUrl.substring(0, requestUrl.indexOf("controllerId=") + 49);
			}else{
				requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
			}
		}
		Collection<ConfigAttribute> configAttributes = resourceMap.get(requestUrl);
		// if(configAttributes == null){
		// Collection<ConfigAttribute> returnCollection = new ArrayList<ConfigAttribute>();
		// returnCollection.add(new SecurityConfig("ROLE_NO_USER"));
		// return returnCollection;
		// }
		return configAttributes;
	}

}
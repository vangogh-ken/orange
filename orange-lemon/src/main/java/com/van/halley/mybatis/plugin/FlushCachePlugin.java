package com.van.halley.mybatis.plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
/***
 * 思路：捕获相应的sqlId,再扫描到全部的涉及到本命名空间的其他命名空间，并刷新其缓存。
 * 此方法只针对一对一，一对多等相互有应用的情况；如果通过第三方表存储关联关系，则需要手动的更新一下数据，然后自动刷新缓存。
 * 注意：目前只对直接关联的缓存进行删除；因此在编写代码过程中如果需要几级获取数据的，那么最后一级应当从数据库中获取，而不是直接使用；否则将出现BUG；
 * 
 * 首先拦截Executor class的query方法来得到以下三个参数MappedStatement.class, 
 * Object.class, RowBounds.class， 这个三个参数是为了计算存放到cahce中的key，
 * 然后再由Executor.createCacheKey(mappedStatement, parameter, rowBounds)方法计算出cacheKey, 
 * 这样就可以得到每个select语句被缓存到cahce中时所对应的key, 顺带说一下这个cacheKey的计算是由几个要素来计算的，
 * 1.select标签的id, 可通过MappedStatement.getId得到 
 * 2. RowBounds 的getOffset()和getLimit() 
 * 3. select的sql语句  
 * 4. 最重要的一点，也是决定这key是否相同的一点， sql的参数，由上面三个参数中的第二个提供，
 *  当然cahceKey的不同也可能会由RowBounds的不同而不同。 
	得到cahceKey之后把它保存到一个Map<String, Set<CacheKey>>类型的map里，String对应一个sqlid, 
	比如上面提到的sql语句 getInfo, 不过还要加上namesapace那就是 A.getInfo,  Set<CacheKey> 保存某个SQL所对应的不同查询参数的不同结果。
	当我们得到想要flush的select 的cachekey之后，就可以拦腰Executor class的update方法（包括insert,update,delete）, 至于过程很简单，上源码。 
 * @author ken
 *
 */

@Intercepts( {
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }),
		//@Signature(type = Executor.class, method = "insert", args = {MappedStatement.class, Object.class }),//拦截器没有这个方法。
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, 
			RowBounds.class, ResultHandler.class }) })
public class FlushCachePlugin implements Interceptor {
	private Properties properties;
	//namespace与相关的缓存
	private static Map<String, Set<CacheKey>> cacheKeyMap = new HashMap<String, Set<CacheKey>>();
	//namespace与 涉及的缓存的namespace,String 被更新的，Set 关联的
	private static Map<String, Set<String>> relatedCacheMap = new HashMap<String, Set<String>>();
	//通过echcache缓存的namespace
	private static Collection<String> cacheNames = new HashSet<String>();
	//private Set<String> removedCacheNames = new HashSet<String>(); 
	
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		//第一次拦截时，进行关联
		if(relatedCacheMap.isEmpty()){
			Configuration configuration = mappedStatement.getConfiguration();
			Collection<String> mappedStatementNames = configuration.getMappedStatementNames();
			cacheNames = configuration.getCacheNames();
			for(String mappedStatementName : mappedStatementNames){
				if(!mappedStatementName.contains(".")){//排除自身不带namespace的
					continue;
				}
				if(configuration.getMappedStatement(mappedStatementName).isUseCache()){
					List<ResultMap> resultMaps = configuration.getMappedStatement(mappedStatementName).getResultMaps();
					if(resultMaps != null && !resultMaps.isEmpty()){
						for(ResultMap resultMap : resultMaps){
							if(resultMap.hasNestedQueries()){
								List<ResultMapping> resultMappings = resultMap.getResultMappings();
								for(ResultMapping resultMapping : resultMappings){
									String nestedQueryId = resultMapping.getNestedQueryId();
									if(nestedQueryId != null){
										String nestedQueryNameSpace = nestedQueryId.substring(0, nestedQueryId.indexOf('.'));
										String relatedNameSpace = mappedStatementName.substring(0, mappedStatementName.indexOf('.'));
										if(cacheNames.contains(relatedNameSpace)){//如果是被缓存的才关联起来；
											if(relatedCacheMap.get(nestedQueryNameSpace) == null){
												Set<String> sets = new HashSet<String>();
												sets.add(relatedNameSpace);
												relatedCacheMap.put(nestedQueryNameSpace, sets);
											}else{
												relatedCacheMap.get(nestedQueryNameSpace).add(relatedNameSpace);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		//如果非缓存直接返回
		if (!mappedStatement.getConfiguration().isCacheEnabled()){
			return invocation.proceed();
		}
		String sqlId = mappedStatement.getId();//sqlId
		String nameSpace = sqlId.substring(0, sqlId.indexOf('.'));//命名空间
		Executor exe = (Executor) invocation.getTarget();
		String methodName = invocation.getMethod().getName();
		Configuration configuration = mappedStatement.getConfiguration();
		//移除缓存
		if (methodName.equals("update")) {
			removeRelatedCache(nameSpace, configuration);
			//removedCacheNames = new HashSet<String>(); //重置
			/**
			Set<String> sets = relatedCacheMap.get(nameSpace);
			if(sets != null && !sets.isEmpty()){
				for(String relatedNameSpace : sets){
					Cache cache = mappedStatement.getConfiguration().getCache(relatedNameSpace);
					Set<CacheKey> ketSet = cacheKeyMap.get(relatedNameSpace);
					try {
						if(ketSet != null && !ketSet.isEmpty()){
							for (Iterator<CacheKey> it = ketSet.iterator(); it.hasNext();) {
								cache.removeObject(it.next());
							}
						}
						
					} finally {
						cacheKeyMap.remove(relatedNameSpace);
					}	
				}
			}
			**/
		//获取缓存
		}else if (methodName.equals("query")) {
			if(cacheNames.contains(nameSpace) && mappedStatement.isUseCache()){
				Object parameter = invocation.getArgs()[1];
				RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
				//Cache cache = mappedStatement.getConfiguration().getCache(nameSpace);
				//cache.getReadWriteLock().readLock().lock();
				CacheKey cacheKey = exe.createCacheKey(mappedStatement, parameter, rowBounds, mappedStatement.getBoundSql(parameter));
				try {
					//if (cache.getObject(cacheKey) == null) {
						if (cacheKeyMap.get(nameSpace) == null) {
							Set<CacheKey> cacheSet = new HashSet<CacheKey>();
							cacheSet.add(cacheKey);
							cacheKeyMap.put(nameSpace, cacheSet);
						} else {
							cacheKeyMap.get(nameSpace).add(cacheKey);
						}
					//}
				} finally {
					//cache.getReadWriteLock().readLock().unlock();
				}
			}
		}
		return invocation.proceed();
	}
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	/**
	 * 目前仅仅删除直接关联
	 * 删除所有与之关联的缓存，直接关联或者间接关联；
	 * @param nameSpace
	 * @param configuration
	 */
	public void removeRelatedCache(String nameSpace, Configuration configuration){
		/*if(removedCacheNames.contains(nameSpace)){
			return;
		}else{
			removedCacheNames.add(nameSpace);
		}*/
		
		/**
		 * 20150109 弃用
		Set<String> sets = relatedCacheMap.get(nameSpace);
		if(sets != null && !sets.isEmpty()){
			for(String relatedNameSpace : sets){
				Cache cache = configuration.getCache(relatedNameSpace);
				if(cache == null){
					continue;
				}
				Set<CacheKey> ketSet = cacheKeyMap.get(relatedNameSpace);
				try {
					if(ketSet != null && !ketSet.isEmpty()){
						for (Iterator<CacheKey> it = ketSet.iterator(); it.hasNext();) {
							cache.removeObject(it.next());
						}
					}
					
				} finally {
					cacheKeyMap.remove(relatedNameSpace);
					//removeRelatedCache(relatedNameSpace, configuration);
				}	
			}
		}
		**/
		
		Set<String> sets = relatedCacheMap.get(nameSpace);
		if(sets != null && !sets.isEmpty()){
			for(String relatedNameSpace : sets){
				Cache cache = configuration.getCache(relatedNameSpace);
				if(cache == null){
					continue;
				}else{
					cache.clear();//直接清空缓存；
					cacheKeyMap.remove(relatedNameSpace);
				}
			}
		}
	}
}

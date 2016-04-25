package com.van.halley.mybatis.plugin;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * 
 * @author ken
 *
 */

@Intercepts( {
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, 
			RowBounds.class, ResultHandler.class }) })
public class FlushCachePlugin2 implements Interceptor {
	private String property;
	private Properties properties;
	private Map<String, Set<CacheKey>> keyMap = new HashMap<String, Set<CacheKey>>();
	//namespance与 涉及的缓存的namespace,String 被更新的，Set 关联的
	private static Map<String, Set<String>> relatedCache = new HashMap<String, Set<String>>();
	
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		//第一次拦截时，进行关联
		if(relatedCache.isEmpty()){
			Configuration configuration = mappedStatement.getConfiguration();
			Collection<String> mappedStatementNames = configuration.getMappedStatementNames();
			//Collection<String> cacheNames = configuration.getCacheNames();
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
										
										if(relatedCache.get(nestedQueryNameSpace) == null){
											Set<String> sets = new HashSet<String>();
											sets.add(relatedNameSpace);
											relatedCache.put(nestedQueryNameSpace, sets);
										}else{
											relatedCache.get(nestedQueryNameSpace).add(relatedNameSpace);
										}
									}
									
								}
							}
						}
					}
				}
			}
		}
		
		
		/**
		 * Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
		 * for(Iterator it = mappedStatements.iterator(); it.hasNext();){
			Object mappedStatementOther = it.next();
			
			List<ResultMap> resultMaps = mappedStatementOther.getResultMaps();
			if(resultMaps.isEmpty()){
				continue;
			}else{
				ResultMap resultMap = resultMaps.get(0);
				if(resultMap.hasNestedQueries()){
					for(ResultMapping resultMapping : resultMap.getIdResultMappings()){
						System.out.println(resultMapping.getNestedQueryId());
					}
				}
			}
		}
		 * 
		 * **/
		//如果非缓存直接返回
		if (!mappedStatement.getConfiguration().isCacheEnabled()){
			return invocation.proceed();
		}
		String sqlId = mappedStatement.getId();//sqlId
		String nameSpace = sqlId.substring(0, sqlId.indexOf('.'));//命名空间
		Executor exe = (Executor) invocation.getTarget();
		String methodName = invocation.getMethod().getName();
		if (methodName.equals("query")) {
			for (Object key : properties.keySet()) {
				if (key.equals(sqlId)) {
					Object parameter = invocation.getArgs()[1];
					RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
					//BoundSql boundSql = (BoundSql) invocation.getArgs()[3];
					Cache cache = mappedStatement.getConfiguration().getCache(nameSpace);
					cache.getReadWriteLock().readLock().lock();
					//CacheKey cacheKey = exe.createCacheKey(mappedStatement, parameter, rowBounds);
					CacheKey cacheKey = exe.createCacheKey(mappedStatement, parameter, rowBounds, mappedStatement.getBoundSql(parameter));
					try {
						if (cache.getObject(cacheKey) == null) {
							if (keyMap.get(sqlId) == null) {
								Set<CacheKey> cacheSet = new HashSet<CacheKey>();
								cacheSet.add(cacheKey);
								keyMap.put(sqlId, cacheSet);
							} else {
								keyMap.get(sqlId).add(cacheKey);
							}
						}
					} finally {
						cache.getReadWriteLock().readLock().unlock();
					}
					break;
				}
			}
		} else if (methodName.equals("update")) {
			for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
				String cacheSqlId = (String) e.nextElement();
				String updateNameSpace = properties.getProperty(cacheSqlId);
				if (updateNameSpace.equals(nameSpace)) {
					String cacheNamespace = cacheSqlId.substring(0, cacheSqlId.indexOf('.'));
					Cache cache = mappedStatement.getConfiguration().getCache(cacheNamespace);
					Set<CacheKey> cacheSet = keyMap.get(cacheSqlId);
					cache.getReadWriteLock().writeLock().lock();
					try {					
						for (Iterator it = cacheSet.iterator(); it.hasNext();) {
							cache.removeObject(it.next());
						}
					} finally {
							cache.getReadWriteLock().writeLock().unlock();
							keyMap.remove(cacheSqlId);
					}	
					
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
}

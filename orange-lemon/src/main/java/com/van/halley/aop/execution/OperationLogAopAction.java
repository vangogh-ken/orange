package com.van.halley.aop.execution;

import java.net.InetAddress;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.core.spring.ApplicationContextHelper;
import com.van.core.spring.ApplicationPropertiesFactoryBean;
import com.van.halley.db.persistence.OperationLogDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.OperationLog;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.Object2JsonUtil;
import com.van.halley.util.ReflectUtils;
import com.van.halley.util.SpringSecurityUtil;

/**
 * AOP注解方法实现日志管理 利用spring AOP 切面技术记录日志 定义切面类（这个是切面类和切入点整天合在一起的),这种情况是共享切入点情况;
 * 
 */
@Component
@Aspect
public class OperationLogAopAction {
	private static Logger logger = LoggerFactory.getLogger(OperationLogAopAction.class);
	@Autowired
	private OperationLogDao operationLogDao;
	@Autowired
	private UserDao userDao;
	//系统只读限制
	public static boolean readLimit = "true".equals((String)((ApplicationPropertiesFactoryBean)ApplicationContextHelper.getBean(ApplicationPropertiesFactoryBean.class)).getProperties().getProperty("sys.read.limit")) ? true : false;

	@Around("execution(public * com.van.service.impl.*Impl.*(..))")
	public Object logAll(ProceedingJoinPoint point) {
		// 执行方法名
		String methodName = point.getSignature().getName();
		String className = point.getTarget().getClass().getSimpleName();

		Object result = null;
		Long start = 0L;
		Long end = 0L;

		User user = null;
		String ipAddress = null;
		String type = methodName;
		String module = className;
		// 此处必须要排除查询相关的方法,否则因为查询时的连接是readonly的,在插入日志信息的时候就会报错。
		// 执行方法所消耗的时间
		boolean isRollback = false;
		try {
			start = System.currentTimeMillis();
			result = point.proceed();
			end = System.currentTimeMillis();
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (Throwable e) {
			logger.error("AOP拦截到系统执行过程错误：{}", e);
			isRollback = true;
		}
		//只读限制时，数据回滚
		if(readLimit){
			isRollback = true;
		}
		
		if(!type.contains("get") && !type.contains("query") && !type.contains("find")
				&& !type.contains("toImport") && !type.contains("toExport")){
			Object[] args = point.getArgs();
			if ((className.indexOf("OperationLog") == -1 && className.indexOf("LoginLog") == -1)) {
				//如果拦截报错，且是对数据库进行了操作，则回滚。
				if(isRollback){
					logger.error("因拦截到错误，数据将回滚，方法：{}", type);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}
				OperationLog operationLog = new OperationLog();
				//如果为通用实体
				if(className.contains("NormalEntityService") && (type.contains("delete") || 
						type.contains("modify") || type.contains("add"))){
					if (type.contains("delete")) {
						operationLog.setPrimaryKey(args[0].toString());
						operationLog.setContent("ClsId: " + args[1].toString());
					}else if(type.contains("modify")){
						operationLog.setPrimaryKey("ClsId: " + args[0].toString());
						operationLog.setContent(ReflectUtils.getToStringOfMapOrRequest(args[1]));
					}else if(type.contains("add")){
						operationLog.setPrimaryKey("ClsId: " + args[0].toString());
						operationLog.setContent(ReflectUtils.getToStringOfMapOrRequest(args[1]));
					}
				}else{
					StringBuilder content = new StringBuilder();
					for(Object t : args){
						content.append(Object2JsonUtil.toJsonString(t) + ";");
					}
					if(content.lastIndexOf(";") > 0){
						content.deleteCharAt(content.lastIndexOf(";"));
					}
					operationLog.setContent(content.toString());
				}

				Long time = end - start;
				// 放到此处避免在登出操作时也会出发AOP，但是获取userName为NULL。
				String userName = SpringSecurityUtil.getCurrentUserName();
				if(userName == null){
					user = userDao.getByUserName("admin");
				}else{
					user = userDao.getByUserName(userName);
				}
				operationLog.setModule(module);
				operationLog.setType(type);
				operationLog.setUser(user);
				operationLog.setActionTime(time.toString());
				operationLog.setIpAddress(ipAddress);
				operationLog.setCreateTime(new Date());
				operationLogDao.add(operationLog);
			}
		}
		return result;
	}
}

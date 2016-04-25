package com.van.core.jackson.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class IgnorePropertyAspect {
	private static Logger logger = LoggerFactory.getLogger(IgnorePropertyAspect.class);
	
	@Pointcut("execution(* com.van.controller.*.*(..))")
	private void anyMethod(){
		
	}
	
	@Around("anyMethod()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		Object returnValue = jp.proceed();
		/**
		 * 暂不使用，此方式只针对简单Object类型，对于复杂组合式的类型无法进行处理
		try{
			FilterPropertyHandler filterPropertyHandler = new JavassistFilterPropertyHandler(true);
			Method method = ((MethodSignature)jp.getSignature()).getMethod();
			returnValue = filterPropertyHandler.filterProperties(method, returnValue);
		}catch(Exception e){
			logger.error("转换时出错", e);
		}
		**/
		return returnValue;
	}
	
	@AfterThrowing(pointcut="anyMethod()", throwing="e")
	public void doAfterThrowing(Exception e){
		
	}
}

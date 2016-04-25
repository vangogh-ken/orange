package com.van.halley.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 暂未使用
 * @author Administrator
 *
 */
//@Aspect
public class AutoInjectDependecyAspect implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoInjectDependecyAspect.class);

	private ApplicationContext applicationContext = null;

	@Pointcut("execution((@org.springframework.beans.factory.annotation.Configurable *).new())")
	public void constructor() {
	}

	@Before("constructor()")
	public void injectAutoWiredFields(JoinPoint aPoint) {
		Class<?> theClass = aPoint.getTarget().getClass();
		try {
			Field[] theFields = theClass.getDeclaredFields();
			for (Field thefield : theFields) {
				for (Annotation theAnnotation : thefield.getAnnotations()) {
					if (theAnnotation instanceof Autowired) {
						// found a field annotated with 'AutoWired'
						if (!thefield.isAccessible()) {
							thefield.setAccessible(true);
						}

						Object theBean = applicationContext.getBean(thefield.getType());
						if (theBean != null) {
							thefield.set(aPoint.getTarget(), theBean);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(
					"An error occured while trying to inject bean on mapper '" + aPoint.getTarget().getClass() + "'",
					e);
		}

	}

	@Override
	public void setApplicationContext(ApplicationContext aApplicationContext) throws BeansException {
		applicationContext = aApplicationContext;
	}

}

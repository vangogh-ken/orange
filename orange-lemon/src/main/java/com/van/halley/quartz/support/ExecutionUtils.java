package com.van.halley.quartz.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.db.persistence.entity.QuartzJob;

public class ExecutionUtils {
	private static Logger LOG = LoggerFactory.getLogger(ExecutionUtils.class);

	/**
	 * 通过反射调用方法
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void invokMethod(QuartzJob quartzJob) {
		Object object = null;
		Class<?> clazz = null;
		if (StringUtils.isNotBlank(quartzJob.getQuartzTask().getSpringId())) {
			object = ApplicationContextHelper.getBean(quartzJob.getQuartzTask().getSpringId());
		} else if (StringUtils.isNotBlank(quartzJob.getQuartzTask().getBeanClass())) {
			try {
				clazz = Class.forName(quartzJob.getQuartzTask().getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (object == null) {
			LOG.error("任务名称 = [" + quartzJob.getQuartzTask().getTaskName() + "]---------------调用失败，请检查是否配置正确！！！");
			return;
		}
		if(!(object instanceof AbstractQuartzTarget)){
			LOG.error("任务名称 = [" + quartzJob.getQuartzTask().getTaskName() + "]---------------调用失败，请检查是否实现正确！！！");
			return;
		}else{//设置quartzJob
			AbstractQuartzTarget target = (AbstractQuartzTarget)object;
			target.setQuartzJob(quartzJob);
			
			autowired((Class< ? extends AbstractQuartzTarget>)clazz, target);
		}
		
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(quartzJob.getQuartzTask().getMethodName());
		} catch (NoSuchMethodException e) {
			LOG.error("任务名称 = [" + quartzJob.getQuartzTask().getTaskName() + "]---------------调用失败，方法名设置错误！！！");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (method != null) {
			try {
				method.invoke(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		LOG.info("任务名称 = [" + quartzJob.getQuartzTask().getTaskName() + "]----------调用成功");
	}
	
	/**
	 * 注入Bean属性
	 * @param clazz
	 * @param task
	 */
	public static void autowired(Class<? extends AbstractQuartzTarget> clazz, AbstractQuartzTarget task){
		Field[] theFields = clazz.getDeclaredFields();
		for (Field thefield : theFields) {
			for (Annotation theAnnotation : thefield.getAnnotations()) {
				if (theAnnotation instanceof Autowired) {
					// found a field annotated with 'AutoWired'
					boolean isAccessible = true;
					if (!thefield.isAccessible()) {
						thefield.setAccessible(true);
						isAccessible = false;
					}

					Object theBean = ApplicationContextHelper.getBean(thefield.getType());
					if (theBean != null) {
						try {
							thefield.set(task, theBean);
							if (!isAccessible) {//reback
								thefield.setAccessible(false);
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}

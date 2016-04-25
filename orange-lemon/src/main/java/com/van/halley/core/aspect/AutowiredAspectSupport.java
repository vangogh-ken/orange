package com.van.halley.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;

import com.van.core.spring.ApplicationContextHelper;

/**
 * @author anxinxx
 *
 */
public class AutowiredAspectSupport {
	public static void autowired(Class<?> clazz, Object target){
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
							thefield.set(target, theBean);
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

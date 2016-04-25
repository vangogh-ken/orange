package com.van.halley.aop.execution;

import javax.annotation.PostConstruct;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class OperationAop {
	@PostConstruct
	public void init(){
		 //System.out.println("===========INIT=================");
	}
	
	@Before("execution(public * com.van.service.impl.*Impl.*(..))")
    public void before() {
        //System.out.println("======================begin==============================");
    }
	
	@After("execution(public * com.van.service.impl.*Impl.*(..))")
    public void after() {
        //System.out.println("=======================end===============================");
    }
	
}

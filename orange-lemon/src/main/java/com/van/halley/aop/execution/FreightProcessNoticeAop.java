package com.van.halley.aop.execution;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.van.halley.core.message.MessageAdapter;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.UserDao;

@Component
@Aspect
public class FreightProcessNoticeAop extends MessageAdapter{
	private static Logger LOG = LoggerFactory.getLogger(OperationLogAopAction.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;

	@PostConstruct
	public void init() {
		// System.out.println("===========INIT=================");
	}

	@Before("execution(public * com.van.service.impl.*Impl.*(..))")
	public void before() {
		// System.out.println("======================begin==============================");
	}

	@After("execution(public * com.van.service.impl.*Impl.*(..))")
	public void after(JoinPoint point) {
		// 执行方法名
		String className = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		Object[] args = point.getArgs();
		if("FreightInvoiceServiceImpl".equals(className)){
			if("doneAuditInvoicePay".equals(methodName)){
				String[] ids = (String[]) args[0];
				for(String id : ids){
					super.processMessage(null, userDao.getByDisplayName("熊森"), "付款审核", "你有新的付款复审任务需要处理。");
				}
			}else if("doneAuditInvoicePay".equals(methodName)){
				String[] ids = (String[]) args[0];
				for(String id : ids){
					super.processMessage(null, userDao.getByDisplayName("刘渝源"), "付款审核","你有新的付款终审任务需要处理。");
				}
			}else if("backAuditInvoicePay".equals(methodName)){
				String[] ids = (String[]) args[0];
				for(String id : ids){
					super.processMessage(null, freightInvoiceDao.getById(id).getFreightStatement().getCreator(), "付款初审退回","你有新的付款初审被退回，请注意处理。");
				}
			}else if("backRehearInvoicePay".equals(methodName)){
				String[] ids = (String[]) args[0];
				for(String id : ids){
					super.processMessage(null, userDao.getByDisplayName("杜容"), "付款复审退回","你有新的付款复审被退回，请注意处理。");
				}
			}else if("backEventideInvoicePay".equals(methodName)){
				String[] ids = (String[]) args[0];
				for(String id : ids){
					super.processMessage(null, userDao.getByDisplayName("熊森"), "付款终审退回","你有新的付款终审被退回，请注意处理。");
				}
			}
		}else if("FreightOrderServiceImpl".equals(className)){
			if("doneRehearOrder".equals(methodName)){
				super.processMessage(null, userDao.getByDisplayName("刘渝源"), "订单审核","你有新的订单终审任务需要处理。");
			}
		}else if("FreightStatementServiceImpl".equals(className)){
			if("toStatementAudit".equals(methodName)){
				super.processMessage(null, userDao.getByDisplayName("吴晨曦"), "账单审核","你有新的账单复核任务需要处理。");
			}else if("toPayStatement".equals(methodName)){
				super.processMessage(null, userDao.getByDisplayName("杜容"), "付款审核","你有新的付款初审任务需要处理。");
			}
		}
	}
}

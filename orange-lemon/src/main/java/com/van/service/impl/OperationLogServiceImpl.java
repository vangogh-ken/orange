package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.OperationLogDao;
import com.van.halley.db.persistence.entity.OperationLog;
import com.van.service.OperationLogService;

@Transactional
@Service("OperationLogService")
public class OperationLogServiceImpl implements OperationLogService {
	@Autowired
	private OperationLogDao operationLogDao;

	public PageView query(PageView pageView, OperationLog log) {
		List<OperationLog> list = operationLogDao.query(pageView, log);
		pageView.setResults(list);
		return pageView;
	}

	public void add(OperationLog log) {
		operationLogDao.add(log);
	}

	public void delete(String id) {
		operationLogDao.delete(id);
	}

	public OperationLog getById(String id) {
		return operationLogDao.getById(id);
	}

	public void modify(OperationLog log) {
		operationLogDao.modify(log);
	}

	public List<OperationLog> getAll() {
		return operationLogDao.getAll();
	}

}

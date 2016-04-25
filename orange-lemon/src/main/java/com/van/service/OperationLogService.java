package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.OperationLog;

public interface OperationLogService {
	public PageView query(PageView pageView, OperationLog log);

	public void add(OperationLog operationLog);

	public void delete(String id);

	public void modify(OperationLog operationLog);

	public OperationLog getById(String id);

	public List<OperationLog> getAll();

}

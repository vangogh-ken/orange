package com.van.service;

import java.util.List;

import com.van.ext.data.ExportDataModel;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.WorkCalInfo;

public interface WorkCalInfoService {
	public List<WorkCalInfo> getAll();

	public List<WorkCalInfo> queryForList(WorkCalInfo workCalInfo);

	public PageView query(PageView pageView, WorkCalInfo workCalInfo);

	public void add(WorkCalInfo workCalInfo);

	public void delete(String id);

	public void modify(WorkCalInfo workCalInfo);

	public WorkCalInfo getById(String id);
	
	public List<WorkCalInfo> getByOrgId(String orgId, String year, String month, String type);
	
	public List<WorkCalInfo> getByUserId(String userId, String year, String month, String type);
	
	/**
	 * 按个人导出数据
	 */
	public ExportDataModel exportByUserId(String userId, String type, String month, String year);
	/**
	 * 按组织机构导出数据
	 */
	public ExportDataModel exportByOrgId(String orgId, String type, String month, String year);
}

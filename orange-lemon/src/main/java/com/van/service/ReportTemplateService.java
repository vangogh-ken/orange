package com.van.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ReportDataSource;
import com.van.halley.db.persistence.entity.ReportTemplate;

public interface ReportTemplateService {
	public List<ReportTemplate> getAll();

	public List<ReportTemplate> queryForList(ReportTemplate reportTemplate);

	public ReportTemplate queryForOne(ReportTemplate reportTemplate);

	public PageView<ReportTemplate> query(PageView<ReportTemplate> pageView, ReportTemplate reportTemplate);

	public void add(ReportTemplate reportTemplate);

	public void delete(String id);

	public void modify(ReportTemplate reportTemplate);

	public ReportTemplate getById(String id);

	/**
	 * 新增或删除，同时对模板流进行处理，从templateFile中获取并更新数据库
	 * @param reportTemplate
	 */
	public void addOrModify(ReportTemplate reportTemplate);

	/**
	 * 删除模板，并删除相应的is
	 * @param reportTemplateIds
	 */
	public void deleteTemplate(String[] reportTemplateIds);
	/**
	 * 获取模板文件数据流
	 * @param reportTemplateId
	 * @return
	 */
	public InputStream getInputStream(String reportTemplateId);
	/**
	 * 下载模板
	 * @param reportTemplateId
	 * @param response
	 * @throws IOException 
	 */
	public void downloadTemplate(String reportTemplateId, HttpServletResponse response);
	/**
	 * 解析数据源，数据源不能自身引用以及相互引用，引用关系应当是单向进行的。
	 * @param reportTemplateId
	 */
	public List<ReportDataSource> mergeSqlText(String reportTemplateId);
	/**
	 * 生成报表
	 * @param reportTemplateId
	 * 请使用generateReport
	 */
	@Deprecated
	public InputStream releaseReport(String reportTemplateId);
	/**
	 * 生成报表，直接传入参数
	 * @param reportTemplateId
	 * @param params
	 * @return
	 */
	public InputStream generateReport(String reportTemplateId, Map<String, Object> params);
	/**
	 * 根据填报参数，转为MAP
	 * @param reportTemplateId
	 * @param params
	 * @return
	 */
	public Map<String, Object> parseParams(String reportTemplateId);
	/**
	 * 获取数据源
	 * @param reportTemplateId
	 * @param params
	 * @return
	 */
	public Map<String, Object> parseDataSource(String reportTemplateId, Map<String, Object> params);
	/**
	 * 方法调用数据源
	 * @param beanClass
	 * @param methodName
	 * @param params
	 * @return
	 */
	public Map<String, Object> parseMethodDataSource(String beanClass, String methodName, Map<String, Object> params);
	/**
	 * SQL语句数据源
	 * @param reportTemplateId
	 * @param params
	 * @return
	 */
	public Map<String, Object> parseSqlTextDataSource(List<ReportDataSource> dataSources, Map<String, Object> params);
	/**
	 * 重新计算收支差数据，增加不能
	 * @param dataSource
	 * @return
	 */
	//public Map<String, Object> recountDataSource(Map<String, Object> dataSource);

	/**
	 * 根据职位获取报表
	 * @param positionId
	 * @return
	 */
	public List<ReportTemplate> getByPositionId(String positionId);

	/**
	 * 禁用
	 * @param reportTemplateIds
	 * @return
	 */
	public boolean doneInvalidReport(String[] reportTemplateIds);
	/**
	 * 启用
	 * @param reportTemplateIds
	 * @return
	 */
	public boolean doneReuseReport(String[] reportTemplateIds);

	/**
	 * 添加数据源
	 * @param reportTemplateId
	 * @return
	 */
	public Map<String, Object> toAddDataSource(String reportTemplateId);

	
	
}

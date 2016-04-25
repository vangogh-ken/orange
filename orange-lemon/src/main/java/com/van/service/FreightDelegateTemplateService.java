package com.van.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightDelegateTemplate;

public interface FreightDelegateTemplateService {
	public List<FreightDelegateTemplate> getAll();

	public List<FreightDelegateTemplate> queryForList(
			FreightDelegateTemplate freightDelegateTemplate);

	public PageView query(PageView pageView,
			FreightDelegateTemplate freightDelegateTemplate);

	public void add(FreightDelegateTemplate freightDelegateTemplate);

	public void delete(String id);

	public void modify(FreightDelegateTemplate freightDelegateTemplate);

	public FreightDelegateTemplate getById(String id);

	/**
	 * 添加或保存委托模板，将模板文件保存至数据库中
	 * @param freightDelegateTemplate
	 */
	public void addOrModify(FreightDelegateTemplate freightDelegateTemplate);

	/**
	 * 删除模板，以及数据库中的文件
	 * @param selectedItem
	 */
	public void deleteTemplate(String[] freightDelegateTemplateIds);

	/**
	 * 下载模板文件
	 * @param freightDelegateTemplateId
	 * @param response
	 * @throws IOException 
	 */
	public void downloadTemplate(String freightDelegateTemplateId, HttpServletResponse response) throws IOException;
}

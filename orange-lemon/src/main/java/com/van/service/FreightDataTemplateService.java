package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightDataTemplate;
import com.van.halley.db.persistence.entity.User;

public interface FreightDataTemplateService {
	public List<FreightDataTemplate> getAll();

	public List<FreightDataTemplate> queryForList(
			FreightDataTemplate freightDataTemplate);

	public PageView query(PageView pageView,
			FreightDataTemplate freightDataTemplate);

	public void add(FreightDataTemplate freightDataTemplate);

	public void delete(String id);

	public void modify(FreightDataTemplate freightDataTemplate);

	public FreightDataTemplate getById(String id);
	/**
	 * 存为模板
	 * @param freightActionId
	 * @return
	 */
	public Map<String, Object> toAddTemplate(String freightActionId);
	/**
	 * 保存模板
	 * @param templateName
	 * @param freightActionValueIds
	 */
	public void doneAddTemplate(String templateName, User owner, String[] freightActionValueIds);
	/**
	 * 获取数据模板，准备复制
	 * freightActionId 复制的目标动作，通过此动作的类型对模板进行过滤
	 * @param owner
	 * @return
	 */
	public Map<String, Object> toCopyTempate(String freightActionId, String userId);
	/**
	 * 复制目标信息
	 * @param freightActionId
	 * @param freightDataTemplateId
	 * @param sheathe 是否覆盖已有的值
	 * @return
	 */
	public boolean doneCopyTemplate(String freightActionId, String freightDataTemplateId, String sheathe);
}

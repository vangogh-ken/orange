package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.ReportTemplate;

public interface ReportTemplateDao extends BaseDao<ReportTemplate> {
	/**
	 * 获取职位拥有的报表权限
	 * @param positionId
	 * @return
	 */
	public List<ReportTemplate> getByPositionId(String positionId);
	/**
	 * 获取角色相关模板权限
	 * @param roleId
	 * @return
	 */
	public List<ReportTemplate> getByRoleId(String roleId);
	/**
	 * 添加角色模板关联
	 * @param roleId
	 * @param reportTemplateId
	 */
	public void addRoleReport(String roleId, String reportTemplateId);
	/**
	 * 删除相关关联
	 * @param roleId
	 */
	public void deleteRoleReport(String roleId);
	/**
	 * 获取报表模板
	 * @return
	 */
	public List<ReportTemplate> getAllStatistics();
}
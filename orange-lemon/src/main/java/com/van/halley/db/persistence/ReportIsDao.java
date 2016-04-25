package com.van.halley.db.persistence;


import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.ReportIs;

public interface ReportIsDao extends BaseDao<ReportIs> {
	/**
	 * 删除与模板关联的流
	 * @param templateId
	 */
	public void deleteByReportTemplateId(String reportTemplateId);
	/**
	 * 通过模板ID获取具体的文件流
	 * @param reportTemplateId
	 * @return
	 */
	public ReportIs getByReportTemplateId(String reportTemplateId);
}
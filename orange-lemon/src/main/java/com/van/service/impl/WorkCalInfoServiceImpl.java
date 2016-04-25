package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.ext.data.ExportDataModel;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.WorkCalInfoDao;
import com.van.halley.db.persistence.entity.WorkCalInfo;
import com.van.halley.util.StringUtil;
import com.van.service.WorkCalInfoService;

@Transactional
@Service("workCalInfoService")
public class WorkCalInfoServiceImpl implements WorkCalInfoService {
	@Autowired
	private WorkCalInfoDao workCalInfoDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<WorkCalInfo> getAll() {
		return workCalInfoDao.getAll();
	}

	public List<WorkCalInfo> queryForList(WorkCalInfo workCalInfo) {
		return workCalInfoDao.queryForList(workCalInfo);
	}

	public PageView query(PageView pageView, WorkCalInfo workCalInfo) {
		List<WorkCalInfo> list = workCalInfoDao.query(pageView, workCalInfo);
		pageView.setResults(list);
		return pageView;
	}

	public void add(WorkCalInfo workCalInfo) {
		workCalInfoDao.add(workCalInfo);
	}

	public void delete(String id) {
		workCalInfoDao.delete(id);
	}

	public void modify(WorkCalInfo workCalInfo) {
		workCalInfoDao.modify(workCalInfo);
	}

	public WorkCalInfo getById(String id) {
		return workCalInfoDao.getById(id);
	}

	@Override
	public List<WorkCalInfo> getByOrgId(String orgId, String year, String month, String type) {
		return workCalInfoDao.getByOrgId(orgId, year, month, type);
	}

	@Override
	public List<WorkCalInfo> getByUserId(String userId, String year,
			String month, String type) {
		return workCalInfoDao.getByUserId(userId, year, month, type);
	}

	@Override
	public ExportDataModel exportByUserId(String userId, String type,
			String month, String year) {
		StringBuilder sql = new StringBuilder();
		List<String> params = new ArrayList<String>();
		sql.append("SELECT START_TIME, ");
		sql.append("(SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE ID=USER_ID) AS DISPLAYNAME, ");
		sql.append(" MAX(CASE PHASE WHEN 'AM' THEN CONTENT ELSE NULL END) AS AM, ");
		sql.append(" MAX(CASE PHASE WHEN 'PM' THEN CONTENT ELSE NULL END) AS PM, ");
		sql.append(" MAX(CASE PHASE WHEN 'EV' THEN CONTENT ELSE NULL END) AS EV, ");
		sql.append(" REPLACE(GROUP_CONCAT(DESCN), ',',';') AS DESCN ");
		sql.append(" FROM OUT_WORK_CAL_INFO WHERE USER_ID=? ");
		params.add(userId);
		if(!StringUtil.isNullOrEmpty(type)){
			sql.append(" AND TYPE=? ");
			params.add(type);
		}
		if(!StringUtil.isNullOrEmpty(year)){
			sql.append(" AND YEAR(START_TIME)=? ");
			params.add(year);
		}
		if(!StringUtil.isNullOrEmpty(month)){
			sql.append(" AND MONTH(START_TIME)=? ");
			params.add(month);
		}
		sql.append(" GROUP BY START_TIME");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), params.toArray());
		
		//拼接文件名
		String fileName = "工作日程数据-" + list.get(0).get("DISPLAYNAME") + "-";
		if(!StringUtil.isNullOrEmpty(type)){
			fileName += (type == "RC" ? "日程": type == "JH"? "计划" : "日志") + "-";
		}
		
		if(!StringUtil.isNullOrEmpty(year)){
			fileName += year + "年-";
		}
		
		if(!StringUtil.isNullOrEmpty(month)){
			fileName += month + "月-";
		}
		fileName = fileName.substring(0, fileName.length() -1);
		ExportDataModel exportDataModel = new ExportDataModel(fileName, list);
		exportDataModel.addHeaders("日期","姓名","上午","下午","晚上","总结");
		exportDataModel.addGetValueMethods("START_TIME","DISPLAYNAME","AM","PM","EV","DESCN");
		
		return exportDataModel;
	}

	@Override
	public ExportDataModel exportByOrgId(String orgId, String type,
			String month, String year) {
		StringBuilder sql = new StringBuilder();
		List<String> params = new ArrayList<String>();
		sql.append("SELECT START_TIME, ");
		sql.append("(SELECT DISPLAY_NAME FROM SYS_AUTH_USER WHERE ID=USER_ID) AS DISPLAYNAME, ");
		sql.append(" MAX(CASE PHASE WHEN 'AM' THEN CONTENT ELSE NULL END) AS AM, ");
		sql.append(" MAX(CASE PHASE WHEN 'PM' THEN CONTENT ELSE NULL END) AS PM, ");
		sql.append(" MAX(CASE PHASE WHEN 'EV' THEN CONTENT ELSE NULL END) AS EV, ");
		sql.append(" REPLACE(GROUP_CONCAT(DESCN), ',',';') AS DESCN ");
		sql.append(" FROM OUT_WORK_CAL_INFO ");
		
		sql.append(" WHERE USER_ID IN ");
		sql.append("(SELECT ID FROM SYS_AUTH_USER WHERE POSITION_ID IN ");
		sql.append("(SELECT ID FROM SYS_AUTH_POSITION WHERE UNDER_ORG_ID =?)) ");
		
		params.add(orgId);
		if(!StringUtil.isNullOrEmpty(type)){
			sql.append(" AND TYPE=? ");
			params.add(type);
		}
		if(!StringUtil.isNullOrEmpty(year)){
			sql.append(" AND YEAR(START_TIME)=? ");
			params.add(year);
		}
		if(!StringUtil.isNullOrEmpty(month)){
			sql.append(" AND MONTH(START_TIME)=? ");
			params.add(month);
		}
		sql.append(" GROUP BY START_TIME");
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), params.toArray());
		
		//拼接文件名
		String fileName = "工作日程数据-";
		if(!StringUtil.isNullOrEmpty(type)){
			fileName += (type == "RC" ? "日程": type == "JH"? "计划" : "日志") + "-";
		}
		
		if(!StringUtil.isNullOrEmpty(year)){
			fileName += year + "年-";
		}
		
		if(!StringUtil.isNullOrEmpty(month)){
			fileName += month + "月-";
		}
		fileName = fileName.substring(0, fileName.length() -1);
		ExportDataModel exportDataModel = new ExportDataModel(fileName, list);
		exportDataModel.addHeaders("日期","姓名","上午","下午","晚上","总结");
		exportDataModel.addGetValueMethods("START_TIME","DISPLAYNAME","AM","PM","EV","DESCN");
		
		return exportDataModel;
	}
}

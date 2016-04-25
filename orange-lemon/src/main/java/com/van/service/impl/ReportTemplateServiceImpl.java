package com.van.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.common.collect.Lists;
import com.van.halley.core.aspect.ReflectInvokeUtil;
import com.van.halley.core.page.PageView;
import com.van.halley.core.rep.BasisSqlHelper;
import com.van.halley.core.rep.JuelUtil;
import com.van.halley.core.rep.ReportUtil;
import com.van.halley.db.persistence.BasisAttributeDao;
import com.van.halley.db.persistence.ReportDataSourceDao;
import com.van.halley.db.persistence.ReportIsDao;
import com.van.halley.db.persistence.ReportParamDao;
import com.van.halley.db.persistence.ReportSetDao;
import com.van.halley.db.persistence.ReportTemplateDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.ReportDataSource;
import com.van.halley.db.persistence.entity.ReportIs;
import com.van.halley.db.persistence.entity.ReportParam;
import com.van.halley.db.persistence.entity.ReportSet;
import com.van.halley.db.persistence.entity.ReportTemplate;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.SpringSecurityUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.ReportTemplateService;

@Transactional
@Service("reportTemplateService")
public class ReportTemplateServiceImpl implements ReportTemplateService {
	private static Logger LOG = LoggerFactory.getLogger(ReportTemplateServiceImpl.class);
	@Autowired
	private ReportTemplateDao reportTemplateDao;
	@Autowired
	private ReportParamDao reportParamDao;
	@Autowired
	private ReportDataSourceDao reportDataSourceDao;
	@Autowired
	private ReportSetDao reportSetDao;
	@Autowired
	private ReportIsDao reportIsDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BasisAttributeDao basisAttributeDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ReportTemplate> getAll() {
		return reportTemplateDao.getAll();
	}

	public List<ReportTemplate> queryForList(ReportTemplate reportTemplate) {
		return reportTemplateDao.queryForList(reportTemplate);
	}

	public ReportTemplate queryForOne(ReportTemplate reportTemplate) {
		return reportTemplateDao.queryForOne(reportTemplate);
	}

	public PageView<ReportTemplate> query(PageView<ReportTemplate> pageView, ReportTemplate reportTemplate) {
		List<ReportTemplate> list = reportTemplateDao.query(pageView,
				reportTemplate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(ReportTemplate reportTemplate) {
		reportTemplateDao.add(reportTemplate);
	}

	public void delete(String id) {
		reportTemplateDao.delete(id);
	}

	public void modify(ReportTemplate reportTemplate) {
		reportTemplateDao.modify(reportTemplate);
	}

	public ReportTemplate getById(String id) {
		return reportTemplateDao.getById(id);
	}

	@Override
	public void addOrModify(ReportTemplate reportTemplate) {
		try {
			String reportTemplateId = reportTemplate.getId();
			if(reportTemplateId == null){
				reportTemplateId = StringUtil.getUUID();
				reportTemplate.setId(reportTemplateId);
				reportTemplateDao.add(reportTemplate);
			}else{
				reportTemplateDao.modify(reportTemplate);
			}
			reportIsDao.deleteByReportTemplateId(reportTemplateId);
			ReportIs reportIs = new ReportIs();
			reportIs.setTemplateId(reportTemplateId);
			reportIs.setTemplateStream(new FileInputStream(new File(FileUtil.attachmentPath, reportTemplate.getTemplateFile())));
			reportIsDao.add(reportIs);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("找不到模板文件。", e);
		} catch(Exception e){
			throw new RuntimeException("保存模板出错。", e);
		}
	}

	@Override
	public void deleteTemplate(String[] reportTemplateIds) {
		for(String reportTemplateId : reportTemplateIds){
			reportIsDao.deleteByReportTemplateId(reportTemplateId);
			reportTemplateDao.delete(reportTemplateId);
		}
	}
	
	@Override
	public List<ReportTemplate> getByPositionId(String positionId) {
		return reportTemplateDao.getByPositionId(positionId);
	}
	
	@Override
	public InputStream getInputStream(String reportTemplateId) {
		ReportIs reportIs = reportIsDao.getByReportTemplateId(reportTemplateId);
		byte[] blob = (byte[]) reportIs.getTemplateStream();
		return new ByteArrayInputStream(blob);
	}
	
	@Override
	public void downloadTemplate(String reportTemplateId, HttpServletResponse response){
		ReportTemplate reportTemplate = reportTemplateDao.getById(reportTemplateId);
		String templateFileName = reportTemplate.getTemplateFile();
		String fileName = "XXX.xls";//默认名称
		if(templateFileName.lastIndexOf(".") > 0){
			fileName = reportTemplate.getTemplateName() + "." + templateFileName.substring(templateFileName.lastIndexOf(".") + 1, templateFileName.length());
		}
		FileUtil.download(getInputStream(reportTemplateId), fileName, response);
	}

	@Override
	@Deprecated
	public InputStream releaseReport(String reportTemplateId) {
		ReportTemplate reportTemplate = reportTemplateDao.getById(reportTemplateId);
		List<ReportParam> reportParams = reportParamDao.getByReportTemplateId(reportTemplateId);
		//List<ReportDataSource> reportDataSources = reportDataSourceDao.getByReportTemplateId(reportTemplateId);
		List<ReportDataSource> reportDataSources = mergeSqlText(reportTemplateId);//通过解析数据获取数据源
		List<ReportSet> reportSets = reportSetDao.getByReportTemplateId(reportTemplateId);
		Map<String, Object> dataSource = new HashMap<String, Object>();
		
		boolean isBasisTemplate = 
				(reportTemplate.getDescn() != null && reportTemplate.getDescn().length() == 36) ? true : false;
		if(!isBasisTemplate && (reportDataSources == null || reportDataSources.isEmpty())){
			LOG.error("找不到此模板对应的数据源，模板ID: {}", reportTemplateId);
			return null;
		}else{
			if(isBasisTemplate){//基础数据报表
				String basisSubstanceTypeId = reportTemplate.getDescn();//从备注说明中获取BASIS_SUBSTANCE_TYPE_ID
				if(basisSubstanceTypeId != null && basisSubstanceTypeId.length() == 36){
					StringBuilder filterText = new StringBuilder();
					String sqlText = null;
					if(reportDataSources != null && !reportDataSources.isEmpty()){
						for(ReportDataSource ds : reportDataSources){
							filterText.append(ds.getSqlText() + " AND ");
						}
						filterText.delete(filterText.lastIndexOf(" AND "), filterText.lastIndexOf(" AND ") + 5);
						sqlText = BasisSqlHelper.getSqlOfBasisSubstance(20, basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId), filterText.toString());
						
					}else {
						sqlText = BasisSqlHelper.getSqlOfBasisSubstance(20, basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId), null);
					}
					reportDataSources = Lists.newArrayList();//清空
					
					ReportDataSource ds = new ReportDataSource();
					ds.setSqlText(sqlText);
					ds.setStatus("T");
					ds.setDataSourceKey("BASIS");
					reportDataSources.add(ds);
				}
			}
		}
		
		//如果是流程通用数据
		if(reportParams != null && !reportParams.isEmpty()){
			Map<String, Object> reportParamMap = new HashMap<String, Object>();
			for(ReportParam reportParam : reportParams){
				//先将值保存至map;将参数值也放入到数据源中
				if("VARCHAR".equals(reportParam.getParamType())){
					reportParamMap.put(reportParam.getParamColumn(), reportParam.getStringValue());
					dataSource.put(reportParam.getParamColumn(), reportParam.getStringValue());
				}else if("INT".equals(reportParam.getParamType())){
					reportParamMap.put(reportParam.getParamColumn(), reportParam.getIntValue());
					dataSource.put(reportParam.getParamColumn(), reportParam.getIntValue());
				}else if("DOUBLE".equals(reportParam.getParamType())){
					reportParamMap.put(reportParam.getParamColumn(), reportParam.getDoubleValue());
					dataSource.put(reportParam.getParamColumn(), reportParam.getDoubleValue());
				}else if("DATETIME".equals(reportParam.getParamType())){
					reportParamMap.put(reportParam.getParamColumn(), reportParam.getDateValue());
					dataSource.put(reportParam.getParamColumn(), reportParam.getDateValue());
				}
			}
			
			for(ReportDataSource reportDataSource : reportDataSources){
				if(!"T".equals(reportDataSource.getStatus())){
					continue;
				}
				String sqlText = reportDataSource.getSqlText();
				String[] ts = sqlText.split("\\s+");
				List<String> params = new ArrayList<String>();//值
				for(int i=0, len = ts.length; i<len; i++){
					if(ts[i].contains(":")){
						String param = ts[i].split(":")[1];
						params.add(param);
					}
				}
				
				List<Object> values = new ArrayList<Object>();//值
				for(int i=0, len = params.size(); i<len; i++){
					sqlText = sqlText.replace(":" + params.get(i), "?");
					if("CURRENT_USER_ID".equals(params.get(i))){
						values.add(reportParamMap.get(params.get(i)) == null ? 
								userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getId() : reportParamMap.get(params.get(i)));
					}else if("CURRENT_DISPLAY_NAME".equals(params.get(i))){
						values.add(reportParamMap.get(params.get(i)) == null ? 
								userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getDisplayName() : reportParamMap.get(params.get(i)));
					}else if("CURRENT_USER_NAME".equals(params.get(i))){
						values.add(reportParamMap.get(params.get(i)) == null ? 
								userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getUserName() : reportParamMap.get(params.get(i)));
					}else{
						values.add(reportParamMap.get(params.get(i)));
					}
				}
				LOG.info("Executing sql:{} params:{}", sqlText, values.toArray());
				dataSource.put(reportDataSource.getDataSourceKey(), jdbcTemplate.queryForList(sqlText, values.toArray()));
			}
		}else{
			for(ReportDataSource reportDataSource : reportDataSources){
				String sqlText = reportDataSource.getSqlText();
				//如果没有参数，也要检测是否有当前用户信息
				String[] ts = sqlText.split("\\s+");
				List<String> params = new ArrayList<String>();//值
				for(int i=0, len = ts.length; i<len; i++){
					if(ts[i].contains(":")){
						String param = ts[i].split(":")[1];
						params.add(param);
					}
				}
				
				List<Object> values = new ArrayList<Object>();//值
				for(int i=0, len = params.size(); i<len; i++){
					sqlText = sqlText.replace(":" + params.get(i), "?");
					if("CURRENT_USER_ID".equals(params.get(i))){
						values.add(userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getId());
					}else if("CURRENT_DISPLAY_NAME".equals(params.get(i))){
						values.add(userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getDisplayName());
					}else if("CURRENT_USER_NAME".equals(params.get(i))){
						values.add(userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getUserName());
					}
				}
				
				if(values.isEmpty()){
					LOG.info("Executing sql:{}", sqlText);
					dataSource.put(reportDataSource.getDataSourceKey(), jdbcTemplate.queryForList(sqlText));
				}else{
					LOG.info("Executing sql:{} params:{}", sqlText, values.toArray());
					dataSource.put(reportDataSource.getDataSourceKey(), jdbcTemplate.queryForList(sqlText, values.toArray()));
				}
			}
		}
			
		File templateFile = new File(FileUtil.attachmentPath, reportTemplate.getTemplateFile());
		if(!templateFile.exists()){
			try {
				ReportIs reportIs = reportIsDao.getByReportTemplateId(reportTemplateId);
				byte[] blob = (byte[]) reportIs.getTemplateStream();
				InputStream is = new ByteArrayInputStream(blob);
				if(is == null || is.available() == 0){
					LOG.error("从数据库中获取不到相应的模板文件，模板ID:{}", reportTemplateId);
					return null;
				}else{
					FileUtils.copyInputStreamToFile(is, templateFile);
				}
			
				return ReportUtil.createNormalReport(dataSource, templateFile, reportSets);
			} catch (IOException e) {
				LOG.error("读取模板文件出错", e);
				return null;
			}
		}else{
			return ReportUtil.createNormalReport(dataSource, templateFile, reportSets);
		}
	}

	/*@Override
	public Map<String, Object> recountDataSource(Map<String, Object> dataSource) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public boolean doneInvalidReport(String[] reportTemplateIds) {
		boolean flag = true;
		for(String reportTemplateId : reportTemplateIds){
			ReportTemplate reportTemplate = reportTemplateDao.getById(reportTemplateId);
			if("T".equals(reportTemplate.getStatus())){
				reportTemplate.setStatus("F");
				reportTemplateDao.modify(reportTemplate);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}
	
	@Override
	public boolean doneReuseReport(String[] reportTemplateIds) {
		boolean flag = true;
		for(String reportTemplateId : reportTemplateIds){
			ReportTemplate reportTemplate = reportTemplateDao.getById(reportTemplateId);
			if("F".equals(reportTemplate.getStatus())){
				reportTemplate.setStatus("T");
				reportTemplateDao.modify(reportTemplate);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}

	@Override
	public Map<String, Object> toAddDataSource(String reportTemplateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", reportDataSourceDao.getByReportTemplateId(reportTemplateId));
		return map;
	}

	@Override
	public List<ReportDataSource> mergeSqlText(String reportTemplateId) {
		List<ReportDataSource> dataSources = reportDataSourceDao.getByReportTemplateId(reportTemplateId);//报表内部的数据源
		List<ReportDataSource> participates = reportDataSourceDao.getParticipate();//共享的数据源
		Map<String, String> variables = new HashMap<String, String>();
		boolean needParse = false;
		if(dataSources != null && !dataSources.isEmpty()){
			Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
			
			for(ReportDataSource dataSource : dataSources){
				if("T".equals(dataSource.getStatus())){
					variables.put(dataSource.getDataSourceKey(), dataSource.getSqlText());
					Matcher matcher = pattern.matcher(dataSource.getSqlText());
					if(matcher.find()){
						needParse = true;
					}
				}
			}
		}
		//如果不需要进行解析则直接返回数据
		if(!needParse){
			return dataSources;
		}
		//Map<String, String> participateVariables = new HashMap<String, String>();
		if(participates != null && !participates.isEmpty()){
			for(ReportDataSource participate : participates){
				if("T".equals(participate.getStatus())){
					variables.put(participate.getDataSourceKey(), participate.getSqlText());
				}
			}
		}
		
		//循环两次进行解析，避免报表内部之间的引用有遗留
		for(ReportDataSource dataSource : dataSources){
			if("T".equals(dataSource.getStatus())){
				Pattern pattern = Pattern.compile("\\$\\{\\s" + dataSource.getDataSourceKey() + "\\s\\}", Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(dataSource.getSqlText());
				if(matcher.find()){
					LOG.error("数据源sql语句中包含的自身的错误引用，应跳过此数据源。数据源名称: {}", dataSource.getDataSourceName());
				}else{
					dataSource.setSqlText(JuelUtil.parseSimpleText(dataSource.getSqlText(), variables));
				}
			}
		}
		for(ReportDataSource dataSource : dataSources){
			if("T".equals(dataSource.getStatus())){
				dataSource.setSqlText(JuelUtil.parseSimpleText(dataSource.getSqlText(), variables));
			}
		}
		
		return dataSources;
	}
	
	@Override
	public Map<String, Object> parseParams(String reportTemplateId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ReportParam> reportParams = reportParamDao.getByReportTemplateId(reportTemplateId);
		if(reportParams != null && !reportParams.isEmpty()){
			for(ReportParam reportParam : reportParams){
				//先将值保存至map;将参数值也放入到数据源中
				if("VARCHAR".equals(reportParam.getParamType())){
					paramMap.put(reportParam.getParamColumn(), reportParam.getStringValue());
				}else if("INT".equals(reportParam.getParamType())){
					paramMap.put(reportParam.getParamColumn(), String.valueOf(reportParam.getIntValue()));
				}else if("DOUBLE".equals(reportParam.getParamType())){
					paramMap.put(reportParam.getParamColumn(), String.valueOf(reportParam.getDoubleValue()));
				}else if("DATETIME".equals(reportParam.getParamType())){
					paramMap.put(reportParam.getParamColumn(), StringUtil.parseDateTime(reportParam.getDateValue()));
				}
			}
		}
		
		String userName = SpringSecurityUtil.getCurrentUserName();
		if(StringUtils.isNotBlank(userName)){
			User currentUser = userDao.getByUserName(userName);
			paramMap.put("CURRENT_USER_ID", currentUser.getId());
			paramMap.put("CURRENT_USER_NAME", currentUser.getUserName());
			paramMap.put("CURRENT_DISPLAY_NAME", currentUser.getDisplayName());
			
			paramMap.put("CURRENT_ORG_ENTITY_NAME", currentUser.getOrgEntity().getOrgEntityName());
			paramMap.put("CURRENT_ORG_ENTITY_ID", currentUser.getOrgEntity().getId());
		}
		return paramMap;
	}

	@Override
	public Map<String, Object> parseDataSource(String reportTemplateId, Map<String, Object> params) {
		ReportTemplate reportTemplate = reportTemplateDao.getById(reportTemplateId);
		List<ReportDataSource> dataSources = mergeSqlText(reportTemplateId);
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(!StringUtil.isNullOrEmpty(reportTemplate.getBeanClass()) && 
				!StringUtil.isNullOrEmpty(reportTemplate.getMethodName())){
			result.putAll(parseMethodDataSource(reportTemplate.getBeanClass(), reportTemplate.getMethodName(), params));
		}
		
		if(dataSources != null && !dataSources.isEmpty()){
			result.putAll(parseSqlTextDataSource(dataSources, params));
		}
		
		result.putAll(params);//把参数添加
		return result;
	}

	@Override
	public Map<String, Object> parseMethodDataSource(String beanClass, String methodName, Map<String, Object> params) {
		return ReflectInvokeUtil.invokeReturnMap(beanClass, methodName, params);
	}

	@Override
	public Map<String, Object> parseSqlTextDataSource(List<ReportDataSource> dataSources, Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		for(ReportDataSource dataSource : dataSources){
			if("T".equals(dataSource.getStatus())){
				String sqlText = dataSource.getSqlText();
				String[] ts = sqlText.split("\\s+");
				List<String> statementParams = new ArrayList<String>();//KEY
				for(int i=0, len = ts.length; i<len; i++){
					if(ts[i].contains(":")){
						String param = ts[i].split(":")[1];
						statementParams.add(param);
					}
				}
				
				List<Object> values = new ArrayList<Object>();//VALUE
				for(int i=0, len = statementParams.size(); i<len; i++){
					sqlText = sqlText.replace(":" + statementParams.get(i), "?");
					if("CURRENT_USER_ID".equals(statementParams.get(i))){
						values.add(params.get(statementParams.get(i)) == null ? 
								userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getId() : params.get(statementParams.get(i)));
					}else if("CURRENT_DISPLAY_NAME".equals(statementParams.get(i))){
						values.add(params.get(statementParams.get(i)) == null ? 
								userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getDisplayName() : params.get(statementParams.get(i)));
					}else if("CURRENT_USER_NAME".equals(statementParams.get(i))){
						values.add(params.get(statementParams.get(i)) == null ? 
								userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getUserName() : params.get(statementParams.get(i)));
					}else{
						values.add(params.get(statementParams.get(i)));
					}
				}
				if(statementParams.size() == 0){
					LOG.info("Executing sql:{}", sqlText);
					result.put(dataSource.getDataSourceKey(), jdbcTemplate.queryForList(sqlText));
				}else{
					LOG.info("Executing sql:{} params:{}", sqlText, values.toArray());
					result.put(dataSource.getDataSourceKey(), jdbcTemplate.queryForList(sqlText, values.toArray()));
				}
			}
		}
		return result;
	}
	
	@Override
	public InputStream generateReport(String reportTemplateId, Map<String, Object> params) {
		return ReportUtil.createSimpleReportToStream(parseDataSource(reportTemplateId, params), getInputStream(reportTemplateId));
	}

	
}

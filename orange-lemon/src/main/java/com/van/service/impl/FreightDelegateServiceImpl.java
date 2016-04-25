package com.van.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionDao;
import com.van.halley.db.persistence.FreightActionFieldDao;
import com.van.halley.db.persistence.FreightActionTypeIdentityDao;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.FreightDelegateDao;
import com.van.halley.db.persistence.FreightDelegateTemplateDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.OrgEntityDao;
import com.van.halley.db.persistence.ReportIsDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightActionField;
import com.van.halley.db.persistence.entity.FreightActionTypeIdentity;
import com.van.halley.db.persistence.entity.FreightBox;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightDelegate;
import com.van.halley.db.persistence.entity.FreightDelegateTemplate;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.db.persistence.entity.FreightSeal;
import com.van.halley.db.persistence.entity.ReportIs;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.fre.util.FreightDelegateUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.FreightDelegateService;

@Transactional
@Service("freightDelegateService")
public class FreightDelegateServiceImpl implements FreightDelegateService {
	private static Logger logger = LoggerFactory.getLogger(FreightDelegateServiceImpl.class);
	@Autowired
	private FreightDelegateDao freightDelegateDao;
	@Autowired
	private FreightActionDao freightActionDao;
	@Autowired
	private FreightPartDao freightPartDao;
	@Autowired
	private OrgEntityDao orgEntityDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FreightActionTypeIdentityDao freightActionTypeIdentityDao;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightBoxRequireDao freightBoxRequireDao;
	@Autowired
	private FreightOrderBoxDao freightOrderBoxDao;
	@Autowired
	private FreightDelegateTemplateDao freightDelegateTemplateDao;
	@Autowired
	private FreightActionFieldDao freightActionFieldDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private ReportIsDao reportIsDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightDelegate> getAll() {
		return freightDelegateDao.getAll();
	}

	public List<FreightDelegate> queryForList(FreightDelegate freightDelegate) {
		return freightDelegateDao.queryForList(freightDelegate);
	}

	public PageView query(PageView pageView, FreightDelegate freightDelegate) {
		List<FreightDelegate> list = freightDelegateDao.query(pageView,
				freightDelegate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightDelegate freightDelegate) {
		freightDelegateDao.add(freightDelegate);
	}

	public void delete(String id) {
		freightDelegateDao.delete(id);
	}

	public void modify(FreightDelegate freightDelegate) {
		freightDelegateDao.modify(freightDelegate);
	}

	public FreightDelegate getById(String id) {
		return freightDelegateDao.getById(id);
	}
	
	@Override
	public Map<String, Object> getDataSource(String freightActionId) {
		//数据源:订单信息,动作信息,箱需和箱容
		Map<String, Object> dataSource = new HashMap<String, Object>();
		//动作
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		
		//一般字段
		List<FreightActionField> fieldNormal = freightActionFieldDao
				.getNormalByFreightActionTypeId(freightAction.getFreightActionType().getId());
		//订单信息
		dataSource.put("ORDER", freightOrderDao.getById(freightAction.getFreightMaintain().getFreightOrder().getId()));
		//动作字段的所有信息信息
		String actionValueSql = "SELECT * FROM FRE_ACTION_VALUE WHERE FRE_ACTION_ID=?";
		List<Map<String, Object>> actionFieldValueList = jdbcTemplate.queryForList(actionValueSql, freightAction.getId());
		Map<String, Object> actionVaLueMap = new HashMap<String, Object>();
		for(FreightActionField actionField : fieldNormal){
			for(Map<String, Object> map : actionFieldValueList){
				if(map.get("FRE_ACTION_FIELD_ID").equals(actionField.getId())){
					String fieldType = actionField.getFieldType();
					if(fieldType.equals("VARCHAR")){
						actionVaLueMap.put(actionField.getFieldColumn(), map.get("STRING_VALUE"));
					}else if(fieldType.equals("TEXT")){
						actionVaLueMap.put(actionField.getFieldColumn(), map.get("TEXT_VALUE"));
					}else if(fieldType.equals("DOUBLE")){
						actionVaLueMap.put(actionField.getFieldColumn(), map.get("DOUBLE_VALUE"));
					}else if(fieldType.equals("INT")){
						actionVaLueMap.put(actionField.getFieldColumn(), map.get("INT_VALUE"));
					}else if(fieldType.equals("DATETIME") || fieldType.equals("TIMESTAMP")){
						actionVaLueMap.put(actionField.getFieldColumn(), map.get("DATE_VALUE"));
					}
				}
			}
		}
		dataSource.put("ACTION", actionVaLueMap);
		//此处只需要用箱封信息即可；
		List<FreightBoxRequire> freightBoxRequires = new ArrayList<FreightBoxRequire>();
		List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightActionId(freightActionId);
		
		if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
			Map<String, Integer> requireMap = new HashMap<String, Integer>();
			List<FreightActionField> fieldForBox = freightActionFieldDao
					.getForBoxByFreightActionTypeId(freightAction.getFreightActionType().getId());
			
			List<Map<String, Object>> boxList = new ArrayList<Map<String, Object>>();
			for(FreightOrderBox freightOrderBox : freightOrderBoxs){
				//获取箱需
				FreightBoxRequire freightBoxRequire = freightOrderBox.getFreightBoxRequire();
				if(requireMap.keySet().contains(freightBoxRequire.getId())){
					requireMap.put(freightBoxRequire.getId(), requireMap.get(freightBoxRequire.getId()) + 1);
				}else{
					freightBoxRequires.add(freightBoxRequire);
					requireMap.put(freightBoxRequire.getId(), 1);
				}
				//获取数据
				Map<String, Object> valueMap = new HashMap<String, Object>();
				for(FreightActionField freightActionField : fieldForBox){
					for(Map<String, Object> map : actionFieldValueList){
						if(freightActionField.getId().equals(map.get("FRE_ACTION_FIELD_ID")) 
								&& freightOrderBox.getId().equals(map.get("FRE_ORDER_BOX_ID"))){
							String fieldType = freightActionField.getFieldType();
							if(fieldType.equals("VARCHAR")){
								valueMap.put(freightActionField.getFieldColumn(), map.get("STRING_VALUE"));
							}else if(fieldType.equals("TEXT")){
								valueMap.put(freightActionField.getFieldColumn(), map.get("TEXT_VALUE"));
							}else if(fieldType.equals("DOUBLE")){
								valueMap.put(freightActionField.getFieldColumn(), map.get("DOUBLE_VALUE"));
							}else if(fieldType.equals("INT")){
								valueMap.put(freightActionField.getFieldColumn(), map.get("INT_VALUE"));
							}else if(fieldType.equals("DATETIME") || fieldType.equals("TIMESTAMP")){
								valueMap.put(freightActionField.getFieldColumn(), map.get("DATE_VALUE"));
							}
						}
					}
				}
				
				valueMap.put("freightBox", freightOrderBox.getFreightBox());
				valueMap.put("freightSeal", freightOrderBox.getFreightSeal());
				valueMap.put("freightBoxRequire", freightOrderBox.getFreightBoxRequire());
				boxList.add(valueMap);
			}
			//重新设置箱子数量
			for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
				freightBoxRequire.setBoxCount(requireMap.get(freightBoxRequire.getId()));
			}
			dataSource.put("BOX", boxList);
			dataSource.put("REQUIRE", freightBoxRequires);
			
			//拼接BRQ信息
			StringBuilder text = new StringBuilder();
			List<Map<String, Object>> freightOrderBoxList = (List<Map<String, Object>>)dataSource.get("BOX");
			if(freightOrderBoxList != null && !freightOrderBoxList.isEmpty()){
				for(Map<String, Object> map : freightOrderBoxList){
					text.append((map.get("freightBox") == null ? "" : ((FreightBox)map.get("freightBox")).getBoxNumber()) + "/");
				}
				if(text.lastIndexOf("/") > 0){
					text.deleteCharAt(text.lastIndexOf("/"));
				}
				text.append("(");
				//同时包含箱需
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					text.append(freightBoxRequire.getBoxCount() + "*" + freightBoxRequire.getBoxType() + ";");
				}
				if(text.lastIndexOf(";") > 0){
					text.deleteCharAt(text.lastIndexOf(";"));
				}
				text.append(")");
			}else{
				text.append("无数据");
			}
			dataSource.put("BEQ", text.toString());
		}else{//如果动作没有关联任何箱封，则默认为所有箱封；也不需要获取含箱数据；箱封详情REQ则直接取全部箱需
			freightOrderBoxs = freightOrderBoxDao.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
			freightBoxRequires = freightBoxRequireDao.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
			dataSource.put("BOX", freightOrderBoxs);
			dataSource.put("REQUIRE", freightBoxRequires);
			//拼接BRQ信息
			StringBuilder text = new StringBuilder();
			List<FreightOrderBox> freightOrderBoxList = (List<FreightOrderBox>)dataSource.get("BOX");
			if(freightOrderBoxList != null && !freightOrderBoxList.isEmpty()){
				for(FreightOrderBox freightOrderBox : freightOrderBoxList){
					text.append((freightOrderBox.getFreightBox() == null ? "" : freightOrderBox.getFreightBox().getBoxNumber()) + "/");
				}
				if(text.lastIndexOf("/") > 0){
					text.deleteCharAt(text.lastIndexOf("/"));
				}
				text.append("(");
				//同时包含箱需
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					text.append(freightBoxRequire.getBoxCount() + "*" + freightBoxRequire.getBoxType() + ";");
				}
				if(text.lastIndexOf(";") > 0){
					text.deleteCharAt(text.lastIndexOf(";"));
				}
				text.append(")");
			}else{
				text.append("无数据");
			}
			dataSource.put("BEQ", text.toString());
		}
		
		//总箱需信息
		StringBuilder text = new StringBuilder();
		List<FreightBoxRequire> freightBoxRequireList = (List<FreightBoxRequire>)dataSource.get("REQUIRE");
		if(freightBoxRequireList != null && !freightBoxRequireList.isEmpty()){
			for(FreightBoxRequire freightBoxRequire : freightBoxRequireList){
				text.append(freightBoxRequire.getBoxType() + "*" + freightBoxRequire.getBoxCount() + " " + freightBoxRequire.getBoxBelong() + ";");
			}
			if(text.lastIndexOf(";") > 0){
				text.deleteCharAt(text.lastIndexOf(";"));
			}
		}else{
			text.append("无数据");
		}
		dataSource.put("REQ", text.toString());
		
		//拼接箱封信息
		if(dataSource.get("BOX") != null){
			StringBuilder BSQ = new StringBuilder();
			for(Object obj : (List<Object>)dataSource.get("BOX")){
				if(obj instanceof FreightOrderBox){
					FreightOrderBox map = (FreightOrderBox)obj;
					FreightBox freightBox = map.getFreightBox();
					FreightSeal freightSeal = map.getFreightSeal();
					BSQ.append((freightBox == null ? "" : freightBox.getBoxNumber()) + "-" + (freightSeal == null ? "" : freightSeal.getSealNumber()) + "\r\n");
				}else{
					Map<String, Object> map  = (Map<String, Object>)obj;
					FreightBox freightBox = (FreightBox)map.get("freightBox");
					FreightSeal freightSeal = (FreightSeal)map.get("freightSeal");
					BSQ.append((freightBox == null ? "" : freightBox.getBoxNumber()) + "-" + (freightSeal == null ? "" : freightSeal.getSealNumber()) + "\r\n");
				}
			}
			
			dataSource.put("BSQ", BSQ.toString());
		}
		
		dataSource.put("WEEK", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));//一年中的周数
		dataSource.put("DAY", Calendar.getInstance().get(Calendar.DAY_OF_WEEK));//星期数
		return dataSource;
	}
	
	@Override
	public InputStream initFile(String freightActionId, boolean hasRegionParam, String regionParam){
		//此处重新获取模板信息是为了避免缓存BUG；
		File templateFile = new File(FileUtil.attachmentPath, freightDelegateTemplateDao.getById(freightActionDao.getById(freightActionId)
				.getFreightActionType().getFreightDelegateTemplate().getId()).getTemplateFile());
		if(templateFile.exists()){
			if(templateFile.getName().endsWith("xls")){
				return FreightDelegateUtil.xlsTemplate(getDataSource(freightActionId), templateFile, hasRegionParam, regionParam);
			}else if(templateFile.getName().endsWith("docx")){
				return FreightDelegateUtil.docxTemplate(getDataSource(freightActionId), templateFile);
			}else{
				logger.error("读取模板文件出错, 错误的模板文件");
				return null;
			}
		}else{
			try {
				ReportIs reportIs = reportIsDao.getByReportTemplateId(freightActionDao.getById(freightActionId).getFreightActionType().getFreightDelegateTemplate().getId());
				byte[] blob = (byte[]) reportIs.getTemplateStream();
				InputStream is = new ByteArrayInputStream(blob);
				if(is == null || is.available() == 0){
					logger.error("从数据库中获取不到相应的模板文件，模板ID:{}", freightActionDao.getById(freightActionId).getFreightActionType().getFreightDelegateTemplate().getId());
					return null;
				}else{
					FileUtils.copyInputStreamToFile(is, templateFile);
				}
				
				if(templateFile.getName().endsWith("xls")){
					return FreightDelegateUtil.xlsTemplate(getDataSource(freightActionId), templateFile, hasRegionParam, regionParam);
				}else if(templateFile.getName().endsWith("docx")){
					return FreightDelegateUtil.docxTemplate(getDataSource(freightActionId), templateFile);
				}else{
					logger.error("读取模板文件出错, 错误的模板文件");
					return null;
				}
			} catch (IOException e) {
				logger.error("读取模板文件出错", e);
				return null;
			}
		}
		
	}
	
	@Override
	public FreightDelegate initDelegate(String freightActionId) {
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		if(!"T".equals(freightAction.getDelegate()) || freightAction.getFreightActionType().getFreightDelegateTemplate() == null){
			return null;
		}else{
			FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
			try {
				if(freightDelegate == null){
					freightDelegate = new FreightDelegate();
					freightDelegate.setFreightAction(freightAction);
					FreightActionTypeIdentity freightActionTypeIdentity = 
							freightActionTypeIdentityDao.getByFreightActionTypeId(freightAction.getFreightActionType().getId());
					if(freightActionTypeIdentity != null){
						freightDelegate.setOwner(userDao.getById(freightActionTypeIdentity.getAssigneeUserId()));
					}else{//如果没有设置有专人处理，则委派给操作员自己
						String manipulator = freightAction.getFreightMaintain().getFreightOrder().getManipulator();
						freightDelegate.setOwner(userDao.getByDisplayName(manipulator));
					}
					freightDelegate.setDelegateNumber(getNextDelegateNumber(freightAction.getFreightMaintain().getFreightOrder().getId()));
					freightDelegate.setStatus("草稿");
					String templateFile = freightAction.getFreightActionType().getFreightDelegateTemplate().getTemplateFile();//模板文件
					String delegateFile = StringUtil.getUUID() + "." + templateFile.substring(templateFile.lastIndexOf(".") + 1, templateFile.length());
					freightDelegate.setDelegateFile(delegateFile);
					freightDelegate.setFreightPart(initAcceptPart(freightActionId));
					freightDelegateDao.add(freightDelegate);
					FileUtils.copyInputStreamToFile(initStream(freightActionId), new File(FileUtil.attachmentPath, delegateFile));
					logger.info("该动作: {}, 新生成委托{}", freightActionId, freightDelegate.getDelegateNumber());
				}else{//收托单位暂不不再重新设置，这里只将委托文件重新更新
					String delegateFile = StringUtil.getUUID() + "." + freightDelegate.getDelegateFile().substring(freightDelegate.getDelegateFile().lastIndexOf(".") + 1, freightDelegate.getDelegateFile().length());
					if("已发送".equals(freightAction.getStatus()) || "已执行".equals(freightAction.getStatus())){
						if(!new File(FileUtil.attachmentPath, freightDelegate.getDelegateFile()).exists()){//如果已发送，已执行的文件不存在
							FileUtils.deleteQuietly(new File(FileUtil.attachmentPath, freightDelegate.getDelegateFile()));
							FileUtils.copyInputStreamToFile(initStream(freightActionId), new File(FileUtil.attachmentPath, delegateFile));
							logger.info("该动作: {}, 已生成委托，但找不到对应委托文件，重新生成{}", freightActionId, freightDelegate.getDelegateNumber());
							freightDelegate.setDelegateFile(delegateFile);
							freightDelegateDao.modify(freightDelegate);
						}
					}else{
						FileUtils.deleteQuietly(new File(FileUtil.attachmentPath, freightDelegate.getDelegateFile()));
						FileUtils.copyInputStreamToFile(initStream(freightActionId), new File(FileUtil.attachmentPath, delegateFile));
						logger.info("该动作: {}, 已生成委托，但非已发送或已执行状态，重新生成{}", freightActionId, freightDelegate.getDelegateNumber());
						freightDelegate.setDelegateFile(delegateFile);
						freightDelegateDao.modify(freightDelegate);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return freightDelegate;
		}
	}
	
	@Override
	public InputStream initStream(String freightActionId) {
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		InputStream inputStream = null;
		String regionParam = freightAction.getFreightActionType().getFreightDelegateTemplate().getRegionParam();
		if(StringUtil.isNullOrEmpty(regionParam)){
			inputStream = initFile(freightActionId, false, null);
		}else{
			inputStream = initFile(freightActionId, true, regionParam);
		}
		return inputStream;
	}
	
	@Override
	public FreightPart initAcceptPart(String freightActionId) {
		String sql = "SELECT MAX(STRING_VALUE) FROM FRE_ACTION_VALUE WHERE FRE_ACTION_ID='" + freightActionId +"' "
			+ " AND FRE_ACTION_FIELD_ID IN(SELECT ID FROM FRE_ACTION_FIELD WHERE "
			+ " FRE_ACTION_TYPE_ID=(SELECT FRE_ACTION_TYPE_ID FROM FRE_ACTION WHERE ID='" + freightActionId +"') AND FIELD_COLUMN IN('STDW', 'STBM')) LIMIT 1";
		String partName = (String)jdbcTemplate.queryForObject(sql, String.class);
		if(!StringUtil.isNullOrEmpty(partName)){
			return freightPartDao.getByPartName(partName);
		}else{
			return null;
		}
	}

	@Override
	public String getNextDelegateNumber(String freightOrderId) {
		String sql = "SELECT CONCAT(LEFT(DELEGATE_NUMBER,LENGTH(DELEGATE_NUMBER) - 4), REPEAT('0',4 - LENGTH(MAX(RIGHT(DELEGATE_NUMBER,4)) + 1)), MAX(RIGHT(DELEGATE_NUMBER,4)) + 1) "
					+ " FROM FRE_DELEGATE " 
					+ "WHERE FRE_ACTION_ID in "
					+ "(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN "
					+ "(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?))";
		String delegateNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
		if(StringUtil.isNullOrEmpty(delegateNumber)){
			sql = "SELECT CONCAT(ORDER_NUMBER, '-WT-0001') FROM FRE_ORDER WHERE ID=?";
			delegateNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
		}
		return delegateNumber;
	}

	@Override
	public FreightDelegate getByFreightActionId(String freightActionId) {
		FreightDelegate filter = new FreightDelegate();
		filter.setFreightAction(freightActionDao.getById(freightActionId));
		return freightDelegateDao.queryForOne(filter);
	}

	@Override
	public InputStream toExportDelegateRail(String freightActionTypeId, Date turnTime, String[] freightDelegateIds){
		FreightDelegateTemplate freightDelegateTemplate = null;
		List<Map<String, Object>> boxList = new ArrayList<Map<String, Object>>();
		for(String freightDelegateId : freightDelegateIds){
			FreightAction freightAction = freightDelegateDao.getById(freightDelegateId).getFreightAction();
			if(!freightActionTypeId.equals(freightAction.getFreightActionType().getId())){//如果动作类型不一致
				continue;
			}
			if(freightDelegateTemplate == null){
				freightDelegateTemplate = freightAction.getFreightActionType().getFreightDelegateTemplate();
			}
			Map<String, Object> dataSource = getDataSource(freightAction.getId());
			List<Map<String, Object>> list = (List<Map<String, Object>>) dataSource.get("BOX");
			if(list == null || list.isEmpty()){
				continue;
			}
			for(Map<String, Object> map : list){
				if(turnTime.equals((Date) map.get("BLSJ"))){
					boxList.add(map);
				}
			}
		}
		
		Map<String, Object> dataSource = new HashMap<String, Object>();
		dataSource.put("BOX", boxList);
		
		File templateFile = new File(FileUtil.attachmentPath, freightDelegateTemplate.getTemplateFile());
		if(!templateFile.exists()){
			try {
				ReportIs reportIs = reportIsDao.getByReportTemplateId(freightDelegateTemplate.getId());
				byte[] blob = (byte[]) reportIs.getTemplateStream();
				InputStream is = new ByteArrayInputStream(blob);
				if(is == null || is.available() == 0){
					logger.error("从数据库中获取不到相应的模板文件，模板ID:{}", freightDelegateTemplate.getId());
				}else{
					FileUtils.copyInputStreamToFile(is, templateFile);
				}
			} catch (IOException e) {
				logger.error("读取模板文件出错", e);
				return null;
			}
		}
		if(StringUtil.isNullOrEmpty(freightDelegateTemplate.getRegionParam())){
			return FreightDelegateUtil.xlsTemplate(dataSource, templateFile, false, null);
		}else{
			return FreightDelegateUtil.xlsTemplate(dataSource, templateFile, true, freightDelegateTemplate.getRegionParam());
		}
	}

	@Override
	public boolean toRecoverDelegate(String[] freightDelegateIds) {
		boolean flag = true;
		for(String freightDelegateId : freightDelegateIds){
			FreightDelegate freightDelegate = freightDelegateDao.getById(freightDelegateId);
			//判断是否有已对账的费用，如果已经有已对账的费用禁止撤销
			String sql = "SELECT COUNT(1) FROM FRE_EXPENSE WHERE FRE_ACTION_ID=? AND FRE_ORDER_ID IS NULL AND STATUS='已对账'";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, freightDelegate.getFreightAction().getId());
			if(count > 0){
				flag = false;
				break;
			}
			
			if("已执行".equals(freightDelegate.getStatus()) || "待执行".equals(freightDelegate.getStatus())
					|| "预备执行".equals(freightDelegate.getStatus())){
				FreightAction freightAction = freightActionDao.getById(freightDelegate.getFreightAction().getId());
				if("T".equals(freightAction.getInternal())){
					//如果是对内部部门的，则需要与内部的其他部门确认
					freightDelegate.setStatus("确认撤销");
					freightDelegateDao.modify(freightDelegate);
				}else{//如果是对外单位的就直接撤销，恢复到原状态
					freightDelegate.setStatus("草稿");
					freightDelegateDao.modify(freightDelegate);
					freightAction.setStatus("未执行");
					freightActionDao.modify(freightAction);
				}
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
	public boolean doneRecoverDelegate(String[] freightDelegateIds) {
		boolean flag = true;
		for(String freightDelegateId : freightDelegateIds){
			FreightDelegate freightDelegate = freightDelegateDao.getById(freightDelegateId);
			//判断是否有已对账的费用，如果已经有已对账的费用禁止撤销
			String sql = "SELECT COUNT(1) FROM FRE_EXPENSE WHERE FRE_ACTION_ID=? AND FRE_ORDER_ID IS NULL AND STATUS='已对账'";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, freightDelegate.getFreightAction().getId());
			if(count > 0){
				flag = false;
				break;
			}
			
			if("确认撤销".equals(freightDelegate.getStatus())){
				freightDelegate.setStatus("草稿");
				freightDelegateDao.modify(freightDelegate);
				
				FreightAction freightAction = freightActionDao.getById(freightDelegate.getFreightAction().getId());
				freightAction.setStatus("未执行");
				freightActionDao.modify(freightAction);
				
				if("T".equals(freightAction.getInternal())){
					freightExpenseDao.deleteInternalByFreightActionId(freightAction.getId());//删除相关内部费用
				}
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
	public boolean doneTransferDelegate(String receiverUserId, String[] freightDelegateIds) {
		boolean flag = true;
		for(String freightDelegateId : freightDelegateIds){
			User owner = userDao.getById(receiverUserId);
			FreightDelegate freightDelegate = freightDelegateDao.getById(freightDelegateId);
			if("待执行".equals(freightDelegate.getStatus()) || "预备执行".equals(freightDelegate.getStatus())){
				freightDelegate.setOwner(owner);
				freightDelegate.setOrgEntity(owner.getOrgEntity());
				freightDelegateDao.modify(freightDelegate);
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
	public boolean doneMarkDelegate(String[] freightDelegateIds) {
		boolean flag = true;
		for(String freightDelegateId : freightDelegateIds){
			FreightDelegate freightDelegate = freightDelegateDao.getById(freightDelegateId);
			
			if(freightDelegate.getExecuteStatus() == null || "F".equals(freightDelegate.getExecuteStatus())){
				freightDelegate.setExecuteStatus("T");
				freightDelegateDao.modify(freightDelegate);
			}else{
				freightDelegate.setExecuteStatus("F");
				freightDelegateDao.modify(freightDelegate);
			}
		}
		return flag;
	}

	@Override
	public List<List<String>> doneBatchExport(String[] freightDelegateIds) {
		PageView pageView = new PageView(freightDelegateIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String freightDelegateId : freightDelegateIds){
			filterText.append("'" +freightDelegateId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<FreightDelegate> freightDelegates = freightDelegateDao.query(pageView, new FreightDelegate());
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightDelegate freightDelegate : freightDelegates){
			List<String> singleValue = new ArrayList<String>();
			
			singleValue.add(freightDelegate.getDelegateNumber());
			singleValue.add(StringUtil.parseDateTime(freightDelegate.getPlaceTime()));
			singleValue.add(freightDelegate.getFreightAction().getFreightActionType().getTypeName());
			singleValue.add("成都创源");
			singleValue.add(freightDelegate.getFreightAction().getFreightMaintain().getFreightOrder().getCargoName());
			singleValue.add(String.valueOf(freightDelegate.getFreightAction().getIncomeAmount()));
			singleValue.add(String.valueOf(freightDelegate.getFreightAction().getPaymentAmount()));
			singleValue.add(String.valueOf(freightDelegate.getFreightAction().getAmountsTaxation()));
			singleValue.add(String.valueOf(freightDelegate.getFreightAction().getBalance()));
			
			values.add(singleValue);
		}
		
		return values;
	}
}

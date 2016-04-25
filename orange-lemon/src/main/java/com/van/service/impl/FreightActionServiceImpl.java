package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.van.halley.db.persistence.FreightActionTypeDao;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.FreightDelegateDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightMaintainDao;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.OutMsgInfoDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightActionField;
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.halley.db.persistence.entity.FreightActionValue;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightDelegate;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.halley.util.SpringSecurityUtil;
import com.van.halley.util.StringUtil;
import com.van.service.FreightActionService;

@Transactional
@Service("freightActionService")
public class FreightActionServiceImpl implements FreightActionService {
	private static Logger logger = LoggerFactory.getLogger(FreightActionServiceImpl.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FreightActionDao freightActionDao;
	@Autowired
	private FreightDelegateDao freightDelegateDao;
	@Autowired
	private FreightActionFieldDao freightActionFieldDao;
	@Autowired
	private FreightActionValueDao freightActionValueDao;
	@Autowired
	private FreightActionTypeDao freightActionTypeDao;
	@Autowired
	private FreightMaintainDao freightMaintainDao;
	@Autowired
	private FreightOrderBoxDao freightOrderBoxDao;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightPartDao freightPartDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightBoxRequireDao freightBoxRequireDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OutMsgInfoDao outMsgInfoDao;

	public List<FreightAction> getAll() {
		return freightActionDao.getAll();
	}

	public List<FreightAction> queryForList(FreightAction freightAction) {
		return freightActionDao.queryForList(freightAction);
	}

	public PageView<FreightAction> query(PageView<FreightAction> pageView, FreightAction freightAction) {
		List<FreightAction> list = freightActionDao.query(pageView,
				freightAction);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightAction freightAction) {
		freightActionDao.add(freightAction);
	}

	public void delete(String id) {
		freightActionDao.delete(id);
		freightActionValueDao.deleteByFreightActionId(id);
	}

	public void modify(FreightAction freightAction) {
		freightActionDao.modify(freightAction);
	}

	public FreightAction getById(String id) {
		return freightActionDao.getById(id);
	}


	@Override
	public Map<String, Object> initPrime(String freightActionId) {
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		boolean isInit = false;//是否已经初始化
		FreightActionValue filter = new FreightActionValue();
		filter.setFreightAction(freightAction);
		if(freightActionValueDao.count(filter) > 0){
			isInit = true;
		}
		
		List<FreightActionValue> freightActionValueNormal = null;
		List<FreightActionValue> freightActionValueForBox = null;
		//先取出共享字段数据
		List<FreightActionValue> participateValues = freightActionValueDao.getAllParticipateValue(freightAction.getFreightMaintain().getFreightOrder().getId());
		Map<String, FreightActionValue> participateValueMap = new HashMap<String, FreightActionValue>();
		if(participateValues != null && !participateValues.isEmpty()){
			for(FreightActionValue item : participateValues){
				if(participateValueMap.get(item.getFreightActionField().getFieldColumn()) == null){
					participateValueMap.put(item.getFreightActionField().getFieldColumn(), item);
				}
			}
		}
		
		
		if(!isInit){//如果未初始化
			//获取一般字段
			List<FreightActionField> freightActionFiledNormal = freightActionFieldDao
					.getNormalByFreightActionTypeId(freightAction.getFreightActionType().getId());
			//初始化值
			for(FreightActionField freightActionField : freightActionFiledNormal){
				boolean canCreate  = true;
				if("T".equals(freightActionField.getParticipate())){//如果是共享字段
					if("TDH".equals(freightActionField.getFieldColumn())){//对于提单号进行特殊处理
						List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao
								.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
						StringBuilder text = new StringBuilder();
						for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
							if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
								if(text.indexOf(freightBoxRequire.getBlNo()) < 0){
									text.append(freightBoxRequire.getBlNo() + ",");
								}
							}
						}
						if(text.lastIndexOf(",") > 0){
							text.deleteCharAt(text.lastIndexOf(","));
							
							FreightActionValue freightActionValue = new FreightActionValue();
							freightActionValue.setFreightAction(freightAction);
							freightActionValue.setFreightActionField(freightActionField);
							freightActionValue.setStringValue(text.toString());
							freightActionValue.setCreateTime(new Date());
							freightActionValue.setModifyTime(new Date());
							freightActionValue.setStatus("T");
							freightActionValueDao.add(freightActionValue);
							canCreate = false;
						}else{
							//FreightActionValue participateValue = freightActionValueDao
									//.getParticipateValue(freightActionField.getFieldColumn(), freightActionId);
							
							FreightActionValue participateValue = participateValueMap.get(freightActionField.getFieldColumn());
							if(participateValue != null){
								participateValue.setId(StringUtil.getUUID());
								participateValue.setFreightAction(freightAction);
								participateValue.setFreightActionField(freightActionField);
								participateValue.setFreightOrderBox(new FreightOrderBox());
								participateValue.setCreateTime(new Date());
								participateValue.setModifyTime(new Date());
								participateValue.setStatus("T");
								freightActionValueDao.add(participateValue);
								canCreate = false;
							}
						}
					}else{
						//FreightActionValue participateValue = freightActionValueDao
								//.getParticipateValue(freightActionField.getFieldColumn(), freightActionId);
						FreightActionValue participateValue = participateValueMap.get(freightActionField.getFieldColumn());
						if(participateValue != null){
							participateValue.setId(StringUtil.getUUID());
							participateValue.setFreightAction(freightAction);
							participateValue.setFreightActionField(freightActionField);
							participateValue.setFreightOrderBox(new FreightOrderBox());
							participateValue.setCreateTime(new Date());
							participateValue.setModifyTime(new Date());
							participateValue.setStatus("T");
							freightActionValueDao.add(participateValue);
							canCreate = false;
						}
					}
				}
				if(canCreate){
					FreightActionValue freightActionValue = new FreightActionValue();
					freightActionValue.setFreightAction(freightAction);
					freightActionValue.setFreightActionField(freightActionField);
					freightActionValue.setStatus("F");
					freightActionValueDao.add(freightActionValue);
				}
			}
			
			//同时将含箱字段也初始化
			List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao
					.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
			if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
				List<String> freightOrderBoxIds = new ArrayList<String>(0);
				for(FreightOrderBox freightOrderBox : freightOrderBoxs){
					freightOrderBoxIds.add(freightOrderBox.getId());
				}
				
				toPrimeBox(freightActionId, freightOrderBoxIds.toArray(new String[freightOrderBoxIds.size()]));
			}
		}else{
			freightActionValueNormal = freightActionValueDao.getNormalByFreightActionId(freightActionId);
			if(freightActionValueNormal != null && !freightActionValueNormal.isEmpty()){
				for(FreightActionValue freightActionValue : freightActionValueNormal){
					if(!"T".equals(freightActionValue.getStatus()) && "T".equals(freightActionValue.getFreightActionField().getParticipate())){
						if("TDH".equals(freightActionValue.getFreightActionField().getFieldColumn())){//对于提单号进行特殊处理
							List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao
									.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
							StringBuilder text = new StringBuilder();
							for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
								if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
									if(text.indexOf(freightBoxRequire.getBlNo()) < 0){
										text.append(freightBoxRequire.getBlNo() + ",");
									}
								}
							}
							if(text.lastIndexOf(",") > 0){
								text.deleteCharAt(text.lastIndexOf(","));
								freightActionValue.setStringValue(text.toString());
								freightActionValue.setStatus("T");
								freightActionValueDao.modify(freightActionValue);
							}else{
								//FreightActionValue participateValue = freightActionValueDao
										//.getParticipateValue(freightActionValue.getFreightActionField().getFieldColumn(), freightActionId);
								FreightActionValue participateValue = participateValueMap.get(freightActionValue.getFreightActionField().getFieldColumn());
								if(participateValue != null){
									freightActionValue.setStringValue(participateValue.getStringValue());
									freightActionValue.setStatus("T");
									freightActionValueDao.modify(freightActionValue);
								}
							}
						}else{
							//FreightActionValue participateValue = freightActionValueDao
									//.getParticipateValue(freightActionValue.getFreightActionField().getFieldColumn(), freightActionId);
							FreightActionValue participateValue = participateValueMap.get(freightActionValue.getFreightActionField().getFieldColumn());
							if(participateValue != null){
								freightActionValue.setStringValue(participateValue.getStringValue());
								freightActionValue.setTextValue(participateValue.getTextValue());
								freightActionValue.setDateValue(participateValue.getDateValue());
								freightActionValue.setIntValue(participateValue.getIntValue());
								freightActionValue.setDoubleValue(participateValue.getDoubleValue());
								freightActionValue.setStatus("T");
								freightActionValueDao.modify(freightActionValue);
							}
						}
					}
				}
			}
			
			freightActionValueForBox = freightActionValueDao.getForBoxByFreightActionId(freightActionId);
			if(freightActionValueForBox != null && !freightActionValueForBox.isEmpty()){
				for(FreightActionValue freightActionValue : freightActionValueForBox){
					if(!"T".equals(freightActionValue.getStatus()) && "T".equals(freightActionValue.getFreightActionField().getParticipate())){
						if("TDH".equals(freightActionValue.getFreightActionField().getFieldColumn())){//对于提单号进行特殊处理
							List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao
									.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
							StringBuilder text = new StringBuilder();
							for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
								if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
									if(text.indexOf(freightBoxRequire.getBlNo()) < 0){
										text.append(freightBoxRequire.getBlNo() + ",");
									}
								}
							}
							if(text.lastIndexOf(",") > 0){
								text.deleteCharAt(text.lastIndexOf(","));
								freightActionValue.setStringValue(text.toString());
								freightActionValue.setStatus("T");
								freightActionValueDao.modify(freightActionValue);
							}else{
								//FreightActionValue participateValue = freightActionValueDao
										//.getParticipateValue(freightActionValue.getFreightActionField().getFieldColumn(), freightActionId);
								FreightActionValue participateValue = participateValueMap.get(freightActionValue.getFreightActionField().getFieldColumn());
								if(participateValue != null){
									freightActionValue.setStringValue(participateValue.getStringValue());
									freightActionValue.setStatus("T");
									freightActionValueDao.modify(freightActionValue);
								}
							}
						}else{
							//FreightActionValue participateValue = freightActionValueDao
									//.getParticipateValue(freightActionValue.getFreightActionField().getFieldColumn(), freightActionId);
							FreightActionValue participateValue = participateValueMap.get(freightActionValue.getFreightActionField().getFieldColumn());
							if(participateValue != null){
								freightActionValue.setStringValue(participateValue.getStringValue());
								freightActionValue.setTextValue(participateValue.getTextValue());
								freightActionValue.setDateValue(participateValue.getDateValue());
								freightActionValue.setIntValue(participateValue.getIntValue());
								freightActionValue.setDoubleValue(participateValue.getDoubleValue());
								freightActionValue.setStatus("T");
								freightActionValueDao.modify(freightActionValue);
							}
						}
					}
				}
			}
			
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freightActionFiledNormal", freightActionFieldDao.getNormalByFreightActionTypeId(freightAction.getFreightActionType().getId()));
		map.put("freightActionFiledForBox", freightActionFieldDao.getForBoxByFreightActionTypeId(freightAction.getFreightActionType().getId()));
		
		map.put("freightActionValueNormal", freightActionValueNormal == null ? freightActionValueDao.getNormalByFreightActionId(freightActionId) : freightActionValueNormal);
		map.put("freightActionValueForBox", freightActionValueForBox == null ? freightActionValueDao.getForBoxByFreightActionId(freightActionId) : freightActionValueForBox);
		return map;
		
	}

	@Override
	public List<FreightAction> getByOrderId(String freightOrderId) {
		return freightActionDao.getByFreightOrderId(freightOrderId);
	}

	@Override
	public boolean doneSendAction(String freightActionId) {
		boolean flag = true;
		if(!hasPrimeAction(freightActionId)){//首先判断数据是否填报完毕
			flag =  false;
		}else{
			FreightAction freightAction = freightActionDao.getById(freightActionId);
			freightAction.setStatus("已发送");
			freightAction.setModifyTime(new Date());
			freightActionDao.modify(freightAction);
			
			if(freightAction.getDelegate().equals("T")){//如果有委托
				//FreightDelegate filter = new FreightDelegate();
				//filter.setFreightAction(freightAction);
				//FreightDelegate freightDelegate = freightDelegateDao.queryForOne(filter);
				FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
				if(freightDelegate != null){
					freightDelegate.setStatus("待执行");
					freightDelegate.setPlaceTime(new Date());
					freightDelegate.setModifyTime(new Date());
					freightDelegateDao.modify(freightDelegate);
					
					List<FreightExpense> freightExpenses = freightExpenseDao.getNormalByFreightActionId(freightActionId);
					if("T".equals(freightAction.getInternal()) && freightExpenses != null && !freightExpenses.isEmpty()){
						for(FreightExpense normal : freightExpenses){
							if(!"异常费用".equals(normal.getStatus()) && normal.getFreightPartB().getOrgEntity() != null 
									&& normal.getFreightPartB().getOrgEntity().getId()
									.equals(freightDelegate.getOwner().getOrgEntity().getId())){
								
								normal.setId(StringUtil.getUUID());
								normal.setIncomeOrExpense("付".equals(normal.getIncomeOrExpense()) ? "收" : "付");
								normal.setExpenseNumber(normal.getExpenseNumber().replace("-FY-", "-NB-"));
								normal.setFreightPartB(normal.getFreightPartA());
								normal.setFreightPartA(freightPartDao.getByOrgEntityId(freightDelegate.getOwner().getOrgEntity().getId()));
								normal.setCreator(freightDelegate.getOwner());
								normal.setOrgEntity(freightDelegate.getOwner().getOrgEntity());
								normal.setModifyTime(new Date());
								normal.setCreateTime(new Date());
								normal.setStatus("审核中");
								normal.setFreightOrder(null);
								normal.setFreightPrice(null);
								normal.setFreightStatement(null);

								if("箱".equals(normal.getCountUnit())){
									List<FreightOrderBox> freightOrderBoxs = normal.getFreightOrderBoxs();
									if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
										freightOrderBoxDao.deleteExpenseBox(normal.getId());
										for(FreightOrderBox freightOrderBox : freightOrderBoxs){
											freightOrderBoxDao.addExpenseBox(normal.getId(), freightOrderBox.getId());
										}
										
										freightExpenseDao.add(normal);
									}else{
										logger.info("费用关联的箱封信息为0，费用编号 {}", normal.getExpenseNumber());
									}
								}else{
									freightExpenseDao.add(normal);
								}
							}else{
								logger.info("费用不符合自动加载内部费用的条件，费用编号 {}", normal.getExpenseNumber());
							}
						}
					}else{
						logger.info("找不到该动作关联的普通费用， freightActionId : {}", freightActionId);
					}
					//设置提醒
					OutMsgInfo outMsgInfo = new OutMsgInfo();
					outMsgInfo.setSender(userDao.getByDisplayName(freightAction.getFreightMaintain().getFreightOrder().getManipulator()));//操作
					outMsgInfo.setReceiver(freightDelegate.getOwner());//分配人员
					outMsgInfo.setMsgType("订单任务");
					outMsgInfo.setContent("委托" + freightDelegate.getDelegateNumber() + " 已送达！请及时处理！");
					outMsgInfo.setStatus("未读");
					outMsgInfo.setHandled("F");
					outMsgInfoDao.add(outMsgInfo);
				}else{
					flag =  false;
					logger.error("该动作未生成委托 freightActionId: " + freightAction.getId());
				}
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}
	
	@Override
	public boolean doneExecuteBatch(String[] freightActionIds) {
		boolean flag = true;
		for(String freightActionId : freightActionIds){
			FreightAction freightAction = freightActionDao.getById(freightActionId);
			//填报完毕，对外动作，已发送状态下的才能批量执行
			if(hasPrimeAction(freightActionId) && "F".equals(freightAction.getInternal()) && "已发送".equals(freightAction.getStatus())){
				freightAction.setStatus("已执行");
				freightAction.setModifyTime(new Date());
				freightActionDao.modify(freightAction);
				
				if(freightAction.getDelegate().equals("T")){//如果有委托
					FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
					if(freightDelegate != null ){//当前用户必须与关联用户一致才能执行委托
						if(freightDelegate.getOwner().getId().equals(userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getId())){
							freightDelegate.setStatus("已执行");
							freightDelegate.setModifyTime(new Date());
							freightDelegateDao.modify(freightDelegate);
							//设置提醒
							OutMsgInfo outMsgInfo = new OutMsgInfo();
							outMsgInfo.setSender(freightDelegate.getOwner());//分配人员
							outMsgInfo.setReceiver(userDao.getByDisplayName(freightAction.getFreightMaintain().getFreightOrder().getManipulator()));//操作
							outMsgInfo.setMsgType("订单任务");
							outMsgInfo.setContent("委托" + freightDelegate.getDelegateNumber() + " 已执行！请知悉！");
							outMsgInfo.setStatus("未读");
							outMsgInfo.setHandled("F");
							outMsgInfoDao.add(outMsgInfo);
						}else{
							logger.error("该动作分配处理人与当前处理人不一致， 分配用户 ： {}， 执行用户：{}", freightDelegate.getOwner().getUserName(), SpringSecurityUtil.getCurrentUserName());
							flag = false;
							break;
						}
					}else{
						logger.error("该动作未生成委托， 动作ID: {} ", freightAction.getId());
						flag = false;
						break;
					}
				}
				
				if(!flag){
					//判断订单状态是否为已完成。订单关联的所有动作已执行， 所有费用已审核；且订单状态为已审核，费用状态为添加完毕。
					FreightOrder freightOrder = freightAction.getFreightMaintain().getFreightOrder();
					if("已审核".equals(freightOrder.getOrderStatus()) && "添加完毕".equals(freightOrder.getExpenseStatus())){
						boolean isComplete = true;
						List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrder.getId());
						for(FreightAction singleAction : freightActions){
							if(!"已执行".equals(singleAction.getStatus())){
								isComplete = false;
								break;
							}
						}
						
						if(isComplete){
							List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrder.getId());
							for(FreightExpense freightExpense : freightExpenses){
								if(!"已审核".equals(freightExpense.getStatus()) && !"已对账".equals(freightExpense.getStatus())){
									isComplete = false;
									break;
								}
							}
						}
						
						if(isComplete){
							freightOrder.setOrderStatus("已完成");
							freightOrderDao.modify(freightOrder);
							logger.info("订单已完成，订单号: {}", freightOrder.getOrderNumber());
						}
					}
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
	public boolean doneExecuteAction(String freightActionId) {
		if(!hasPrimeAction(freightActionId)){//首先判断数据是否填报完毕
			return false;
		}
		
		boolean flag = true;
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		freightAction.setStatus("已执行");
		freightAction.setModifyTime(new Date());
		freightActionDao.modify(freightAction);
		
		if(freightAction.getDelegate().equals("T")){//如果有委托
			FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
			//当前用户必须与关联用户一致才能执行委托
			if(freightDelegate != null ){
				if(freightDelegate.getOwner().getId().equals(userDao.getByUserName(SpringSecurityUtil.getCurrentUserName()).getId())){
					freightDelegate.setStatus("已执行");
					freightDelegate.setModifyTime(new Date());
					freightDelegateDao.modify(freightDelegate);
					flag = true;
					
					//设置提醒
					OutMsgInfo outMsgInfo = new OutMsgInfo();
					outMsgInfo.setSender(freightDelegate.getOwner());//分配人员
					outMsgInfo.setReceiver(userDao.getByDisplayName(freightAction.getFreightMaintain().getFreightOrder().getManipulator()));//操作
					outMsgInfo.setMsgType("订单任务");
					outMsgInfo.setContent("委托" + freightDelegate.getDelegateNumber() + " 已执行！请知悉！");
					outMsgInfo.setStatus("未读");
					outMsgInfo.setHandled("F");
					outMsgInfoDao.add(outMsgInfo);
				}else{
					logger.error("该动作分配处理人与当前处理人不一致， 分配用户 ： {}， 执行用户：{}", freightDelegate.getOwner().getUserName(), SpringSecurityUtil.getCurrentUserName());
					flag = false;
				}
			}else{
				logger.error("该动作未生成委托， 动作ID: {} ", freightAction.getId());
				flag = false;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}else{//判断订单状态是否为已完成。订单关联的所有动作已执行， 所有费用已审核；且订单状态为已审核，费用状态为添加完毕。
			FreightOrder freightOrder = freightAction.getFreightMaintain().getFreightOrder();
			if("已审核".equals(freightOrder.getOrderStatus()) && "添加完毕".equals(freightOrder.getExpenseStatus())){
				boolean isComplete = true;
				List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrder.getId());
				for(FreightAction singleAction : freightActions){
					if(!"已执行".equals(singleAction.getStatus())){
						isComplete = false;
						break;
					}
				}
				
				if(isComplete){
					List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrder.getId());
					for(FreightExpense freightExpense : freightExpenses){
						if(!"已审核".equals(freightExpense.getStatus()) && !"已对账".equals(freightExpense.getStatus())){
							isComplete = false;
							break;
						}
					}
				}
				
				if(isComplete){
					freightOrder.setOrderStatus("已完成");
					freightOrderDao.modify(freightOrder);
					logger.info("订单已完成，订单号: {}", freightOrder.getOrderNumber());
				}
			}
		}
		return flag;
	}

	@Override
	public void doneAddAction(FreightAction freightAction, String freightMaintainId, String[] freightActionTypeIds) {
		for(String freightActionTypeId : freightActionTypeIds){
			FreightActionType freightActionType = freightActionTypeDao.getById(freightActionTypeId);
			String freightActionId = StringUtil.getUUID();
			freightAction.setId(freightActionId);
			freightAction.setFreightActionType(freightActionType);
			freightAction.setFreightMaintain(freightMaintainDao.getById(freightMaintainId));
			freightAction.setDelegate(freightActionType.getDelegate());
			freightAction.setPrime(freightActionType.getPrime());
			freightAction.setInternal(freightActionType.getInternal());
			freightAction.setStatus("未执行");
			freightActionDao.add(freightAction);
			
			freightAction.setDisplayIndex(freightAction.getDisplayIndex() + 1);//递增显示顺序
			
			initPrime(freightActionId);//新增时，直接初始化动作信息
		}
		
	}

	@Override
	public boolean doneRemoveAction(String freightActionId) {
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		if("未执行".equals(freightAction.getStatus()) || "预备执行".equals(freightAction.getStatus())){
			FreightAction filter = new FreightAction();
			filter.setFreightMaintain(freightAction.getFreightMaintain());
			List<FreightAction> list = freightActionDao.queryForList(filter);
			int sourceIndex = freightAction.getDisplayIndex();
			for(FreightAction item : list){
				int index = item.getDisplayIndex();
				if(index > sourceIndex){
					item.setDisplayIndex(index - 1);
					freightActionDao.modify(item);
				}
			}
			FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
			if(freightDelegate != null){
				freightDelegateDao.delete(freightDelegate.getId());
			}
			//删除值
			freightActionValueDao.deleteByFreightActionId(freightActionId);
			freightActionDao.delete(freightActionId);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean donePrepareAction(String freightActionId) {
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		//预备，是只针对内部部门，有委托，有填报
		if("预备执行".equals(freightAction.getStatus()) && "T".equals(freightAction.getInternal())
				&& "T".equals(freightAction.getDelegate()) && "T".equals(freightAction.getPrime())){
			freightAction.setStatus("预备完成");
			freightActionDao.modify(freightAction);
			
			FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
			freightDelegate.setStatus("预备完成");
			freightDelegateDao.modify(freightDelegate);
			
			//设置提醒
			OutMsgInfo outMsgInfo = new OutMsgInfo();
			outMsgInfo.setSender(freightDelegate.getOwner());//分配人员
			outMsgInfo.setReceiver(userDao.getByDisplayName(freightAction.getFreightMaintain().getFreightOrder().getManipulator()));//操作
			outMsgInfo.setMsgType("订单任务");
			outMsgInfo.setContent("委托" + freightDelegate.getDelegateNumber() + " 预备完成！请知悉！");
			outMsgInfo.setStatus("未读");
			outMsgInfo.setHandled("F");
			outMsgInfoDao.add(outMsgInfo);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Map<String, Object> toPrimeAction(String freightActionId) {
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		if("T".equals(freightAction.getPrime())){
			Map<String, Object> map = new HashMap<String, Object>();
			if("已发送".equals(freightAction.getStatus()) || "已执行".equals(freightAction.getStatus())){
				map.put("freightActionFiledNormal", freightActionFieldDao.getNormalByFreightActionTypeId(freightAction.getFreightActionType().getId()));
				map.put("freightActionFiledForBox", freightActionFieldDao.getForBoxByFreightActionTypeId(freightAction.getFreightActionType().getId()));
				map.put("freightActionValueNormal", freightActionValueDao.getNormalByFreightActionId(freightActionId));
				map.put("freightActionValueForBox", freightActionValueDao.getForBoxByFreightActionId(freightActionId));
			}else{
				map.putAll(initPrime(freightActionId));//优先对动作进行初始化
			}
			FreightActionField filter = new FreightActionField();
			filter.setFreightActionType(freightAction.getFreightActionType());
			filter.setForBox("T");
			int count = freightActionFieldDao.count(filter);
			if(count > 0){//如果有含箱字段
				map.put("freightOrderBoxOfOrder", freightOrderBoxDao.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId()));
				map.put("freightOrderBoxOfAction", freightOrderBoxDao.getByFreightActionId(freightActionId));
			}
			map.put("freightAction", freightAction);
			
			//减少前台数据循环，提高效率,暂不使用
			/*List<FreightActionValue> freightActionValueNormal = (List<FreightActionValue>)map.get("freightActionValueNormal");
			Map<String, FreightActionValue> freightActionValueNormalMap = new HashMap<String, FreightActionValue>();
			for(FreightActionValue freightActionValue : freightActionValueNormal){
				freightActionValueNormalMap.put(freightActionValue.getFreightActionField().getId(), freightActionValue);
			}
			
			Map<String, FreightActionValue> freightActionValueForBoxMap = new HashMap<String, FreightActionValue>();
			List<FreightActionValue> freightActionValueForBox = (List<FreightActionValue>)map.get("freightActionValueForBox");
			for(FreightActionValue freightActionValue : freightActionValueForBox){
				freightActionValueForBoxMap.put(freightActionValue.getFreightActionField().getId() + freightActionValue.getFreightOrderBox().getId(), freightActionValue);
			}
			map.put("freightActionValueNormalMap", freightActionValueNormalMap);
			map.put("freightActionValueForBoxMap", freightActionValueForBoxMap);
			*/
			return map;
		}else{
			return null;
		}
	}

	@Override
	public boolean toPrimeBox(String freightActionId, String[] freightOrderBoxIds) {
		List<String> freightOrderBoxIdToAdd = new ArrayList<String>();
		List<String> freightOrderBoxIdHasAdd = new ArrayList<String>();
		for(String freightOrderBoxId : freightOrderBoxIds){
			freightOrderBoxIdToAdd.add(freightOrderBoxId);
		}
		//先取出之后再删除
		List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightActionId(freightActionId);
		//首先删除其关联
		freightOrderBoxDao.deleteActionBox(freightActionId);
		if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
			for(FreightOrderBox freightOrderBox : freightOrderBoxs){
				String freightOrderBoxId = freightOrderBox.getId();
				if(freightOrderBoxIdToAdd.contains(freightOrderBoxId)){//如果已有，则移除该箱封ID
					freightOrderBoxIdHasAdd.add(freightOrderBoxId);
					//freightOrderBoxDao.addActionBox(freightActionId, freightOrderBoxId);//重新添加关联，ActionValue不做任何操作
					continue;
				}else{//如果没有，则需要ActionValue
					freightActionValueDao.deleteForOrderBox(freightActionId, freightOrderBoxId);
				}
			}
		}
		//重新添加关联，无论是否已经添加过
		for(String freightOrderBoxId : freightOrderBoxIdToAdd){
			freightOrderBoxDao.addActionBox(freightActionId, freightOrderBoxId);
		}
		
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		List<FreightActionField> forBoxFields = freightActionFieldDao
				.getForBoxByFreightActionTypeId(freightAction.getFreightActionType().getId());
		//先取出共享字段数据
		List<FreightActionValue> participateValues = freightActionValueDao.getAllParticipateValue(freightAction.getFreightMaintain().getFreightOrder().getId());
		Map<String, FreightActionValue> participateValueMap = new HashMap<String, FreightActionValue>();
		if(participateValues != null && !participateValues.isEmpty()){
			for(FreightActionValue item : participateValues){
				if(participateValueMap.get(item.getFreightActionField().getFieldColumn()) == null){
					participateValueMap.put(item.getFreightActionField().getFieldColumn(), item);
				}
			}
		}
				
		for(String freightOrderBoxId : freightOrderBoxIdToAdd){
			if(!freightOrderBoxIdHasAdd.contains(freightOrderBoxId)){//已经添加过的就跳过
				for(FreightActionField freightActionField : forBoxFields){
					boolean canCreate = true;
					//优先从都是foxbox的值获取，如果获取不到，再从其他地方获取
					if("T".equals(freightActionField.getParticipate())){
						if("TDH".equals(freightActionField.getFieldColumn())){//对于提单号进行特殊处理
							List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao
									.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId());
							StringBuilder text = new StringBuilder();
							for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
								if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
									if(text.indexOf(freightBoxRequire.getBlNo()) < 0){
										text.append(freightBoxRequire.getBlNo() + ",");
									}
								}
							}
							
							if(text.lastIndexOf(",") > 0){//从箱需中找提单号
								text.deleteCharAt(text.lastIndexOf(","));
								FreightActionValue freightActionValue = new FreightActionValue();
								freightActionValue.setFreightAction(freightAction);
								freightActionValue.setFreightActionField(freightActionField);
								freightActionValue.setStringValue(text.toString());
								freightActionValue.setCreateTime(new Date());
								freightActionValue.setModifyTime(new Date());
								freightActionValue.setStatus("T");
								freightActionValue.setFreightOrderBox(freightOrderBoxDao.getById(freightOrderBoxId));
								freightActionValueDao.add(freightActionValue);
								canCreate = false;
							}else{
								//FreightActionValue participateValue = freightActionValueDao
										//.getParticipateValue(freightActionField.getFieldColumn(), freightActionId, freightOrderBoxId);
								FreightActionValue participateValue = participateValueMap.get(freightActionField.getFieldColumn());
								if(participateValue != null){
									participateValue.setId(StringUtil.getUUID());
									participateValue.setFreightAction(freightAction);
									participateValue.setFreightActionField(freightActionField);
									participateValue.setFreightOrderBox(freightOrderBoxDao.getById(freightOrderBoxId));
									participateValue.setStatus("T");
									participateValue.setCreateTime(new Date());
									participateValue.setModifyTime(new Date());
									freightActionValueDao.add(participateValue);
									canCreate = false;
								}
								
								/*else if((participateValue = freightActionValueDao
										.getParticipateValue(freightActionField.getFieldColumn(), freightActionId)) != null){
									participateValue.setId(StringUtil.getUUID());
									participateValue.setFreightAction(freightAction);
									participateValue.setFreightActionField(freightActionField);
									participateValue.setFreightOrderBox(freightOrderBoxDao.getById(freightOrderBoxId));
									participateValue.setStatus("T");
									participateValue.setCreateTime(new Date());
									participateValue.setModifyTime(new Date());
									freightActionValueDao.add(participateValue);
									canCreate = false;
								}*/
							}
						}else{
							//FreightActionValue participateValue = freightActionValueDao
									//.getParticipateValue(freightActionField.getFieldColumn(), freightActionId, freightOrderBoxId);
							FreightActionValue participateValue = participateValueMap.get(freightActionField.getFieldColumn());
							if(participateValue != null){
								participateValue.setId(StringUtil.getUUID());
								participateValue.setFreightAction(freightAction);
								participateValue.setFreightActionField(freightActionField);
								participateValue.setFreightOrderBox(freightOrderBoxDao.getById(freightOrderBoxId));
								participateValue.setStatus("T");
								participateValue.setCreateTime(new Date());
								participateValue.setModifyTime(new Date());
								freightActionValueDao.add(participateValue);
								canCreate = false;
							}
							
							/*else if((participateValue = freightActionValueDao
									.getParticipateValue(freightActionField.getFieldColumn(), freightActionId)) != null){
								participateValue.setId(StringUtil.getUUID());
								participateValue.setFreightAction(freightAction);
								participateValue.setFreightActionField(freightActionField);
								participateValue.setFreightOrderBox(freightOrderBoxDao.getById(freightOrderBoxId));
								participateValue.setStatus("T");
								participateValue.setCreateTime(new Date());
								participateValue.setModifyTime(new Date());
								freightActionValueDao.add(participateValue);
								canCreate = false;
							}*/
						}
					}
					if(canCreate){
						FreightActionValue freightActionValue = new FreightActionValue();
						freightActionValue.setFreightAction(freightAction);
						freightActionValue.setFreightActionField(freightActionField);
						freightActionValue.setFreightOrderBox(freightOrderBoxDao.getById(freightOrderBoxId));
						freightActionValue.setStatus("F");
						freightActionValueDao.add(freightActionValue);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean donePrimeAction(String freightActionId, HttpServletRequest request) {
		boolean flag = true;
		try{
			List<FreightActionValue> values = new ArrayList<FreightActionValue>();
			values.addAll(freightActionValueDao.getNormalByFreightActionId(freightActionId));
			values.addAll(freightActionValueDao.getForBoxByFreightActionId(freightActionId));
			if(values != null && !values.isEmpty()){
				for(FreightActionValue freightActionValue : values){
					String value = request.getParameter(freightActionValue.getId());
					if(!StringUtil.isNullOrEmpty(value)){
						String fieldType = freightActionValue.getFreightActionField().getFieldType();
						if("VARCHAR".equals(fieldType)){
							freightActionValue.setStringValue(value);
						}else if("TEXT".equals(fieldType)){
							freightActionValue.setTextValue(value);
						}else if("INT".equals(fieldType)){
							freightActionValue.setIntValue(Integer.parseInt(value));
						}else if("DOUBLE".equals(fieldType)){
							freightActionValue.setDoubleValue(Double.parseDouble(value));
						}else if("DATETIME".equals(fieldType) || "TIMESTAMP".equals(fieldType)){
							freightActionValue.setDateValue(StringUtil.convert(value));
						}
						
						freightActionValue.setStatus("T");//T表示已填报
						freightActionValueDao.modify(freightActionValue);
					}else if(value != null){
						String fieldType = freightActionValue.getFreightActionField().getFieldType();
						if("VARCHAR".equals(fieldType)){
							freightActionValue.setStringValue(null);
						}else if("TEXT".equals(fieldType)){
							freightActionValue.setTextValue(null);
						}else if("INT".equals(fieldType)){
							freightActionValue.setIntValue(0);
						}else if("DOUBLE".equals(fieldType)){
							freightActionValue.setDoubleValue(0);
						}else if("DATETIME".equals(fieldType) || "TIMESTAMP".equals(fieldType)){
							freightActionValue.setDateValue(null);
						}
						
						freightActionValue.setStatus("F");//T表示已填报
						freightActionValueDao.modify(freightActionValue);
					}
				}
			}
		}catch(Exception e){
			logger.info("保存数据错误，freightActionId:{}, 信息:{}", freightActionId, e);
			flag = false;
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}

	@Override
	public boolean hasPrimeAction(String freightActionId) {
		String sql = "SELECT COUNT(1) FROM FRE_ACTION_VALUE "
					+" WHERE FRE_ACTION_ID=? "
					+" AND FRE_ACTION_FIELD_ID IN(SELECT ID FROM FRE_ACTION_FIELD WHERE REQUIRED='T' AND FRE_ACTION_TYPE_ID=(SELECT FRE_ACTION_TYPE_ID FROM FRE_ACTION WHERE ID=?)) "
					+" AND STATUS IS NULL";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, freightActionId, freightActionId);
		return count == 0;
	}

	@Override
	public boolean toPrepareAction(String freightActionId) {
		boolean flag = true;
		FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightActionId);
		if(freightDelegate != null){
			if(!"草稿".equals(freightDelegate.getStatus())){
				flag = false;
			}else if("已执行".equals(freightDelegate.getStatus()) || "未执行".equals(freightDelegate.getStatus())){
				flag = false;
			}else{
				freightDelegate.setStatus("预备执行");
				freightDelegateDao.modify(freightDelegate);
				
				FreightAction freightAction  = freightActionDao.getById(freightActionId);
				freightAction.setStatus("预备执行");
				freightActionDao.modify(freightAction);
				
				//设置提醒
				OutMsgInfo outMsgInfo = new OutMsgInfo();
				outMsgInfo.setSender(userDao.getByDisplayName(freightAction.getFreightMaintain().getFreightOrder().getManipulator()));//操作
				outMsgInfo.setReceiver(freightDelegate.getOwner());//分配人员
				outMsgInfo.setMsgType("订单任务");
				outMsgInfo.setContent("委托" + freightDelegate.getDelegateNumber() + " 预备执行！请及时处理！");
				outMsgInfo.setStatus("未读");
				outMsgInfo.setHandled("F");
				outMsgInfoDao.add(outMsgInfo);
			}
		}else{
			flag = false;
		}
		return flag;
	}

	
}

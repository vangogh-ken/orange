package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasExchangeRateDao;
import com.van.halley.db.persistence.FreightActionDao;
import com.van.halley.db.persistence.FreightActionTypeDao;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.FreightDeductDao;
import com.van.halley.db.persistence.FreightDelegateDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightMaintainDao;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightPriceDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightActionType;
import com.van.halley.db.persistence.entity.FreightActionValue;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightDeduct;
import com.van.halley.db.persistence.entity.FreightDelegate;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightMaintain;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.FreightPrice;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.FreightActionService;
import com.van.service.FreightOrderService;

@Transactional
@Service("freightOrderService")
public class FreightOrderServiceImpl implements FreightOrderService {
	private static Logger logger = LoggerFactory.getLogger(FreightOrderServiceImpl.class);
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightMaintainDao freightMaintainDao;
	@Autowired
	private FreightActionDao freightActionDao;
	@Autowired
	private FreightBoxRequireDao freightBoxRequireDao;
	@Autowired
	private FreightActionValueDao freightActionValueDao;
	@Autowired
	private FreightPriceDao freightPriceDao;
	@Autowired
	private FreightActionTypeDao freightActionTypeDao;
	@Autowired
	private FreightOrderBoxDao freightOrderBoxDao;
	@Autowired
	private FreightDelegateDao freightDelegateDao;
	@Autowired
	private FreightActionService freightActionService;
	@Autowired
	private FasExchangeRateDao fasExchangeRateDao;
	@Autowired
	private FreightDeductDao freightDeductDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightOrder> getAll() {
		return freightOrderDao.getAll();
	}

	public List<FreightOrder> queryForList(FreightOrder freightOrder) {
		return freightOrderDao.queryForList(freightOrder);
	}

	public PageView<FreightOrder> query(PageView<FreightOrder> pageView, FreightOrder freightOrder) {
		List<FreightOrder> list = freightOrderDao.query(pageView, freightOrder);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightOrder freightOrder) {
		freightOrderDao.add(freightOrder);
	}

	public void delete(String id) {
		freightOrderDao.delete(id);
	}

	public void modify(FreightOrder freightOrder) {
		freightOrderDao.modify(freightOrder);
	}

	public FreightOrder getById(String id) {
		return freightOrderDao.getById(id);
	}

	@Override
	public boolean toOrderAudit(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			//为整箱业务必须要添加箱需才能提交审核
			if("整箱".equals(freightOrder.getThirdType())){
				FreightBoxRequire filter = new FreightBoxRequire();
				filter.setFreightOrder(freightOrder);
				if(freightBoxRequireDao.count(filter) == 0){
					logger.info("提交审核失败，未选择箱需， 订单号: {}", freightOrder.getOrderNumber());
					flag = false;
					break;
				}
			}
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("未提交") || orderStatus.equals("已退回") || orderStatus.equals("已追回")){
				freightOrder.setOrderStatus("审核中");
				freightOrderDao.modify(freightOrder);
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
	public boolean doneOrderAudit(String[] freightOrderIds){
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("审核中") || orderStatus.equals("锁定中")){
				freightOrder.setOrderStatus("已审核");
				freightOrderDao.modify(freightOrder);
			}else if(orderStatus.equals("订单初审中")){
				freightOrder.setOrderStatus("订单复审中");
				freightOrderDao.modify(freightOrder);
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
	public  boolean backOrderAudit(String[] freightOrderIds){
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("审核中") || orderStatus.equals("锁定中")){
				freightOrder.setOrderStatus("已退回");
				freightOrderDao.modify(freightOrder);
			}else if(orderStatus.equals("订单初审中")){
				freightOrder.setOrderStatus("已退回");
				freightOrder.setExpenseStatus("添加中");//走三级审核的必然是点击费用添加完毕
				freightOrderDao.modify(freightOrder);
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
	public boolean toOrderRecover(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("审核中") || orderStatus.equals("已审核")){
				freightOrder.setOrderStatus("确认追回");
				freightOrderDao.modify(freightOrder);
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
	public boolean doneOrderRecover(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("确认追回")){
				freightOrder.setOrderStatus("已追回");
				freightOrderDao.modify(freightOrder);
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
	public boolean toExpenseAudit(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String expenseStatus = freightOrder.getExpenseStatus();
			if(expenseStatus.equals("添加中")){
				List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrderId);
				for(FreightExpense freightExpense : freightExpenses){
					if("未提交".equals(freightExpense.getStatus())){
						freightExpense.setStatus("审核中");
						freightExpenseDao.modify(freightExpense);
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
	public boolean doneExpenseAudit(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String expenseStatus = freightOrder.getExpenseStatus();
			if(expenseStatus.equals("添加中") || expenseStatus.equals("添加完毕")){
				List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrderId);
				for(FreightExpense freightExpense : freightExpenses){
					if("审核中".equals(freightExpense.getStatus())){
						freightExpense.setStatus("已审核");
						freightExpenseDao.modify(freightExpense);
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
	public boolean backExpenseAudit(String[] freightOrderIds) {
		boolean flag = true;
		
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String expenseStatus = freightOrder.getExpenseStatus();
			if(expenseStatus.equals("添加中") || expenseStatus.equals("添加完毕")){
				List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrderId);
				for(FreightExpense freightExpense : freightExpenses){
					if("审核中".equals(freightExpense.getStatus())){
						freightExpense.setStatus("已退回");
						freightExpenseDao.modify(freightExpense);
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
	public boolean doneCompleteExpense(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			//收支差小于0或者因税金收入的原因才导致收支差不小于0
			if(freightOrder.getBalance() < 0 || freightOrder.getIncomeAmount() < freightOrder.getPaymentAmount()){
				flag = false;
				break;
			}else{
				List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrderId);
				for(FreightExpense freightExpense : freightExpenses){
					if("未提交".equals(freightExpense.getStatus())){
						freightExpense.setStatus("审核中");
						freightExpenseDao.modify(freightExpense);
					}
				}
				
				freightOrder.setExpenseStatus("添加完毕");//设置费用状态
				
				boolean hasCompleteAction = true;
				List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
				if(freightActions != null && !freightActions.isEmpty() && freightActions.size() > 0){
					for(FreightAction freightAction : freightActions){
						if(!"已执行".equals(freightAction.getStatus())){
							hasCompleteAction = false;
							break;
						}
					}
				}else{
					hasCompleteAction = false;//如果没有添加动作，则不能完成该订单
				}
				
				if(hasCompleteAction){
					freightOrder.setOrderStatus("已完成");//设置订单状态
				}
				
				freightOrderDao.modify(freightOrder);
				
				//TODO 暂时不开放，可能导致错误的DEDUCT_REVISED状态下变成DEDUCT_HAVE
				/**
				FreightDeduct freightDeduct = freightDeductDao.getByFreightOrderId(freightOrderId);
				if(freightDeduct != null && FreightDeduct.DEDUCT_REVISED.equals(freightDeduct.getStatus())){
					if(Math.abs((freightDeduct.getOrderBalance() - freightOrder.getBalance())) <= 1){//不使用等于，排除因误差导致的问题
						freightDeduct.setStatus(FreightDeduct.DEDUCT_HAVE);
						freightDeduct.setModifyTime(new Date());
						freightDeductDao.modify(freightDeduct);
					}else{
						freightDeduct.setModifyTime(new Date());
						freightDeduct.setStatus(FreightDeduct.DEDUCT_REVISED);
						freightDeductDao.modify(freightDeduct);
					}
				}
				**/
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}
	
	@Override
	public boolean recallCompleteExpenseWithoutDeduct(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			FreightDeduct freightDeduct = freightDeductDao.getByFreightOrderId(freightOrderId);
			//未进入提成或者未提成，则可进行
			if("添加完毕".equals(freightOrder.getExpenseStatus()) 
					&& (freightDeduct == null 
					|| FreightDeduct.DEDUCT_PREPARED.equals(freightDeduct.getStatus()) 
					|| FreightDeduct.RECONCILE_HAVNT.equals(freightDeduct.getStatus())
					|| FreightDeduct.RECONCILE_HAVE.equals(freightDeduct.getStatus()))){
				freightOrder.setExpenseStatus("添加中");
				freightOrder.setModifyTime(new Date());
				freightOrderDao.modify(freightOrder);
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
	public boolean recallCompleteExpenseWithDeduct(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			FreightDeduct freightDeduct = freightDeductDao.getByFreightOrderId(freightOrderId);
			//已进入提成或者已提成，则可进行
			if("添加完毕".equals(freightOrder.getExpenseStatus()) 
					&& freightDeduct != null 
					&& (FreightDeduct.DEDUCT_HAVE.equals(freightDeduct.getStatus()) 
							|| FreightDeduct.DEDUCT_REVISED.equals(freightDeduct.getStatus()))){
				freightOrder.setExpenseStatus("添加中");
				freightOrder.setModifyTime(new Date());
				freightOrderDao.modify(freightOrder);
				
				//此处就直接修改状态
				//TODO 此处也暂不开放，收支差变化将通过orderBalance 与 deductBalance 体现。
				/**
				freightDeduct.setStatus(FreightDeduct.DEDUCT_REVISED);
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.modify(freightDeduct);
				**/
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
	public boolean finishOrder(String[] freightOrderIds){
		boolean flag = true;
		
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			String expenseStatus = freightOrder.getExpenseStatus();
			if(orderStatus.equals("已审核") && expenseStatus.equals("添加完毕")){
				List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
				for(FreightAction freightAction : freightActions){
					if(!"已执行".equals(freightAction.getStatus())){
						flag = false;
						break;
					}
				}
				if(flag){
					freightOrder.setOrderStatus("已完成");
					freightOrderDao.modify(freightOrder);
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
	public boolean invalidOrder(String[] freightOrderIds){
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			if(!flag){//如果已经有失败的，则直接退出
				break;
			}
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("未提交") || orderStatus.equals("已退回") || orderStatus.equals("已追回")){
				List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrderId);
				for(FreightExpense freightExpense : freightExpenses){
					if(freightExpense.getFreightStatement() != null){//说明已对账，则取消追回
						flag = false;
						break;
					}
				}
				
				if(flag){
					List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
					for(FreightAction freightAction : freightActions){
						FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightAction.getId());
						if(freightDelegate != null){
							if(!"草稿".equals(freightDelegate.getStatus())){
								logger.warn("作废订单失败： {} 有委托尚未撤回， 委托编号： {} ", freightOrder.getOrderNumber(), freightDelegate.getDelegateNumber());
								flag = false;
								break;
							}else{
								freightDelegateDao.delete(freightDelegate.getId());//只需要删除委托
							}
						}
					}
					
					if(flag){
						for(FreightExpense freightExpense : freightExpenses){
							if("已对账".equals(freightExpense.getStatus())){
								logger.warn("作废订单失败： {} 有费用已对账，费用编号： {} ", freightOrder.getOrderNumber(), freightExpense.getExpenseNumber());
								flag = false;
								break;
							}else{
								freightExpenseDao.delete(freightExpense.getId());
							}
						}
					}
					if(flag){
						freightOrder.setExpenseStatus("已作废");
						freightOrder.setOrderStatus("已作废");
						freightOrderDao.modify(freightOrder);
					}
				}
			}else{
				flag = false;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}
	
	@Override
	public String getNextOrderNumber(String userName) {
		String sql = "SELECT CONCAT(LEFT(ORDER_NUMBER, LENGTH(ORDER_NUMBER) - 3), REPEAT('0',3 - LENGTH(MAX(RIGHT(ORDER_NUMBER, 3)) + 1)),MAX(RIGHT(ORDER_NUMBER, 3)) + 1)"
					+ " FROM FRE_ORDER WHERE "
					+ " ORDER_NUMBER LIKE CONCAT('%" + userName.toUpperCase() + "', DATE_FORMAT(SYSDATE(),'%y%m%u'),'%')";
		String orderNumber = jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(orderNumber)){
            sql = "SELECT DATE_FORMAT(SYSDATE(),'%y%m%u')";
            String time = jdbcTemplate.queryForObject(sql, String.class);
            orderNumber = "CY" + userName.toUpperCase() + time + "001";
        }
		return orderNumber;
	}

	@Override
	public boolean doneRemoveOrder(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			if(!flag){//如果已经有false了则都跳出循环
				break;
			}
			
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			if("未提交".equals(freightOrder.getOrderStatus()) ||
					"已退回".equals(freightOrder.getOrderStatus()) ||
					"已追回".equals(freightOrder.getOrderStatus()) ){
				//删除动作，先删除动作，再删除操作范围，否则动作节点删除不掉，删除动作的同时，删除对应的委托
				List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
				for(FreightAction freightAction : freightActions){
					FreightDelegate freightDelegate = freightDelegateDao.getByFreightActionId(freightAction.getId());
					if(freightDelegate != null){
						if(!"草稿".equals(freightDelegate.getStatus())){
							logger.warn("删除订单： {} 有委托尚未撤回， 委托编号： {} ", freightOrder.getOrderNumber(), freightDelegate.getDelegateNumber());
							flag = false;
							break;
						}else{
							freightDelegateDao.delete(freightDelegate.getId());
						}
					}
					freightActionValueDao.deleteByFreightActionId(freightAction.getId());//删除动作信息
					freightActionDao.delete(freightAction.getId());
				}
				
				//删除操作范围
				FreightMaintain fitlerMaintain = new FreightMaintain();
				fitlerMaintain.setFreightOrder(freightOrder);
				List<FreightMaintain> freightMaintains = freightMaintainDao.queryForList(fitlerMaintain);
				for(FreightMaintain freightMaintain : freightMaintains){
					freightMaintainDao.delete(freightMaintain.getId());
				}
				
				//删除费用
				FreightExpense fitlerExpense = new FreightExpense();
				fitlerExpense.setFreightOrder(freightOrder);
				List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(fitlerExpense);
				for(FreightExpense freightExpense : freightExpenses){
					if("已对账".equals(freightExpense.getStatus())){
						logger.warn("删除订单： {} 已有费用已对账， 费用编号： {} ", freightOrder.getOrderNumber(), freightExpense.getExpenseNumber());
						flag = false;
						break;
					}else{
						freightExpenseDao.delete(freightExpense.getId());
					}
				}
				//删除箱需，因为还是状态还是未提交，箱封信息是执行放箱动作之后再有的，箱封信息此时必然没有。
				List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao.getByFreightOrderId(freightOrderId);
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					if("已选箱".equals(freightBoxRequire.getStatus()) || "已放箱".equals(freightBoxRequire.getStatus())){
						logger.warn("删除订单： {} 已有箱需选箱", freightOrder.getOrderNumber());
						flag = false;
						break;
					}else{
						freightBoxRequireDao.delete(freightBoxRequire.getId());
					}
				}
				freightOrderDao.delete(freightOrderId);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return flag;
		}else{
			return flag;
		}
	}

	@Override
	public Map<String, Object> toAddOrderByCopy(String freightOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//订单
		FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
		
		map.put("freightOrder", freightOrder);
		//箱需
		map.put("freightBoxRequires", freightBoxRequireDao.getByFreightOrderId(freightOrderId));
		//操作
		FreightMaintain filterMaintain = new FreightMaintain();
		filterMaintain.setFreightOrder(freightOrder);
		map.put("freightMaintains", freightMaintainDao.queryForList(filterMaintain));
		//动作
		List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
		map.put("freightActions", freightActions);
		//动作信息
		Map<String, List<FreightActionValue>> freightActionValues = new HashMap<String, List<FreightActionValue>>();
		for(FreightAction freightAction : freightActions){
			if("T".equals(freightAction.getPrime())){
				FreightActionValue filterActionValue = new FreightActionValue();
				filterActionValue.setFreightAction(freightAction);
				freightActionValues.put(freightAction.getId(), freightActionValueDao.queryForList(filterActionValue));
			}
		}
		map.put("freightActionValues", freightActionValues);
		//费用
		/*FreightExpense filterExpense = new FreightExpense();
		filterExpense.setFreightOrder(freightOrder);
		map.put("freightExpenses", freightExpenseDao.queryForList(filterExpense));*/
		
		map.put("freightExpenses", freightExpenseDao.getByFreightOrderId(freightOrderId));
		return map;
	}

	@Override
	public String doneAddOrderByCopy(String freightOrderId,
			String[] freightBoxRequireIds, String[] freightExpenseIds,
			String[] freightActionValueIds, User creator) {
		//用于存放各种新数据，key为原数据的ID，value为新数据对象
		Map<String, Object> map = new HashMap<String, Object>();
		
		String orderNumber = null;
		FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
		orderNumber =  freightOrder.getOrderNumber();//保留订单号，为费用更新费用号做准备
		freightOrder.setId(StringUtil.getUUID());
		freightOrder.setOrderNumber(getNextOrderNumber(creator.getUserName()));
		freightOrder.setDelegateNumber(null);//复制新增委托号和委托书都不应当被复制
		freightOrder.setCommission(null);
		freightOrder.setCreatorUserId(creator.getId());
		freightOrder.setOrgEntityId(creator.getOrgEntity().getId());
		freightOrder.setOrderStatus("未提交");
		freightOrder.setExpenseStatus("未添加");
		freightOrder.setCreateTime(new Date());
		freightOrder.setPlaceTime(new Date());
		freightOrder.setModifyTime(new Date());
		freightOrderDao.add(freightOrder);
		map.put(freightOrderId, freightOrder);//订单
		//箱需
		if(freightBoxRequireIds != null && freightBoxRequireIds.length > 0){
			//List<FreightOrderBox> freightOrderBoxs = new ArrayList<FreightOrderBox>();//保存为列表，为动作信息复制用
			for(String freightBoxRequireId : freightBoxRequireIds){
				FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
				freightBoxRequire.setId(StringUtil.getUUID());
				freightBoxRequire.setFreightOrder(freightOrder);
				freightBoxRequire.setBlNo(null);
				freightBoxRequire.setStatus("未提交");
				freightBoxRequire.setCreateTime(new Date());
				freightBoxRequire.setModifyTime(new Date());
				freightBoxRequire.setDescn(null);//设置备注(提单号为空)
				freightBoxRequireDao.add(freightBoxRequire);
				
				//复制箱需时，箱封信息也生成
				for(int i=0, len=freightBoxRequire.getBoxCount(); i<len; i++){
					FreightOrderBox freightOrderBox = new FreightOrderBox();
					freightOrderBox.setId(StringUtil.getUUID());
					freightOrderBox.setFreightBoxRequire(freightBoxRequire);
					freightOrderBox.setStatus(freightBoxRequire.getStatus());
					freightOrderBoxDao.add(freightOrderBox);
					
					//freightOrderBoxs.add(freightOrderBox);
				}
				
				map.put(freightBoxRequireId, freightBoxRequire);
			}
			
			//map.put("freightOrderBoxs", freightOrderBoxs);//保存箱封信息
		}
		
		//操作
		FreightMaintain filterMaintain = new FreightMaintain();
		filterMaintain.setFreightOrder(freightOrderDao.getById(freightOrderId));
		List<FreightMaintain> freightMaintains = freightMaintainDao.queryForList(filterMaintain);
		for(FreightMaintain freightMaintain : freightMaintains){
			String freightMaintainId = freightMaintain.getId();
			freightMaintain.setId(StringUtil.getUUID());
			freightMaintain.setFreightOrder(freightOrder);
			freightMaintain.setCreateTime(new Date());
			freightMaintain.setModifyTime(new Date());
			freightMaintainDao.add(freightMaintain);
			
			map.put(freightMaintainId, freightMaintain);
		}
		
		for(FreightMaintain freightMaintain : freightMaintains){
			if(freightMaintain.getParentMaintain() != null){
				freightMaintain.setParentMaintain((FreightMaintain)map.get(freightMaintain.getParentMaintain().getId()));
				freightMaintainDao.modify(freightMaintain);
			}
		}
		//动作
		List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
		for(FreightAction freightAction : freightActions){
			if(freightAction.getFreightMaintain() != null){
				String freightActionId = freightAction.getId();
				
				String newActionId = StringUtil.getUUID();
				freightAction.setId(newActionId);
				freightAction.setStatus("未执行");
				freightAction.setFreightMaintain((FreightMaintain)map.get(freightAction.getFreightMaintain().getId()));
				//重新从动作类型中获取信息
				FreightActionType freightActionType = freightActionTypeDao.getById(freightAction.getFreightActionType().getId());
				freightAction.setInternal(freightActionType.getInternal());
				freightAction.setPrime(freightActionType.getPrime());
				freightAction.setDelegate(freightActionType.getDelegate());
				freightAction.setCreateTime(new Date());
				freightAction.setModifyTime(new Date());
				freightActionDao.add(freightAction);
				
				map.put(freightActionId, freightAction);
				
				freightActionService.initPrime(newActionId);//复制新增时，初始化动作信息， 暂不在此处初始化，修改箱需可能引起该问题数据
			}
		}
		//动作值暂不复制。
		/*if(freightActionValueIds != null && freightActionValueIds.length > 0){
			for(String freightActionValueId : freightActionValueIds){
				FreightActionValue freightActionValue = freightActionValueDao.getById(freightActionValueId);
				if("T".equals(freightActionValue.getFreightActionField().getForBox())){//如果是含箱字段，则跳过复制。
					continue;
				}

				freightActionValue.setId(StringUtil.getUUID());
				freightActionValue.setFreightAction((FreightAction)map.get(freightActionValue.getFreightAction().getId()));
				freightActionValue.setCreateTime(new Date());
				freightActionValue.setModifyTime(new Date());
				freightActionValueDao.add(freightActionValue);
			}
		}*/
		//费用
		if(freightExpenseIds != null && freightExpenseIds.length > 0){
			//复制有费用，则状态变为添加中
			freightOrder.setExpenseStatus("添加中");
			freightOrderDao.modify(freightOrder);
			
			for(String freightExpenseId : freightExpenseIds){
				FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
				if("箱".equals(freightExpense.getCountUnit())){
					//continue;//按箱计费的费用暂时取消复制 FIXME
				}
				freightExpense.setId(StringUtil.getUUID());
				
				/*if(freightExpense.getActualCount() == freightExpense.getPredictCount()){
					freightExpense.setStatus("未提交");
				}else{
					freightExpense.setStatus("异常费用");
				}*/
				freightExpense.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightExpense.getCurrency(), null));
				//默认费用复制只取其预计金额
				freightExpense.setStatus("未提交");
				freightExpense.setActualCount(freightExpense.getPredictCount());

				freightExpense.setExpenseNumber(freightExpense.getExpenseNumber().replaceAll(orderNumber, freightOrder.getOrderNumber()));
				freightExpense.setFreightOrder(freightOrder);
				if(freightExpense.getFreightAction() != null){
					freightExpense.setFreightAction((FreightAction)map.get(freightExpense.getFreightAction().getId()));
				}
				//设置对账单为空
				FreightStatement freightStatementNull = new FreightStatement();
				freightStatementNull.setId("A");
				freightExpense.setFreightStatement(freightStatementNull);
				
				FreightPrice freightPrice = freightExpense.getFreightPrice();
				if(freightPrice != null){
					freightPrice.setId(StringUtil.getUUID());
					freightPrice.setActual(null);
					freightPrice.setActualCount(0);
					freightPrice.setStatus("T");
					freightPrice.setCreateTime(new Date());
					freightPrice.setModifyTime(new Date());
					freightPriceDao.add(freightPrice);
					freightExpense.setFreightPrice(freightPrice);
				}
				
				freightExpense.setCreateTime(new Date());
				freightExpense.setModifyTime(new Date());
				freightExpenseDao.add(freightExpense);
			}
		}
		
		return freightOrder.getId();
	}

	@Override
	public Map<String, Object> toCopyExpense(String freightOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
		map.put("sourceOrder", freightOrder);
		
		FreightExpense filterExpense = new FreightExpense();
		filterExpense.setFreightOrder(freightOrder);
		map.put("freightExpenses", freightExpenseDao.queryForList(filterExpense));
		
		FreightOrder filterOrder = new FreightOrder();
		filterOrder.setCreatorUserId(freightOrder.getCreatorUserId());
		filterOrder.setExpenseStatus("未添加");
		map.put("freightOrders", freightOrderDao.queryForList(filterOrder));
		return map;
	}

	@Override
	public boolean doneCopyExpense(String sourceOrderId, String[] targetOrderIds, String[] freightExpenseIds,  String sheatheAllBox){
		boolean flag = true;
		FreightOrder sourceOrder = freightOrderDao.getById(sourceOrderId);
		for(String targetOrderId : targetOrderIds){
			if(freightExpenseIds != null && freightExpenseIds.length > 0){
				FreightOrder targetOrder = freightOrderDao.getById(targetOrderId);
				targetOrder.setExpenseStatus("添加中");
				freightOrderDao.modify(targetOrder);
				
				List<FreightOrderBox> freightOrderBoxes = freightOrderBoxDao.getByFreightOrderId(targetOrderId);//订单箱封信息
				List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(targetOrderId);//订单动作信息
				for(String freightExpenseId : freightExpenseIds){
					FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
					String newExpenseId = StringUtil.getUUID();
					if("箱".equals(freightExpense.getCountUnit())){
						if("T".equals(sheatheAllBox)){//如果确定覆盖所有箱子
							for(FreightOrderBox freightOrderBox : freightOrderBoxes){
								freightOrderBoxDao.addExpenseBox(newExpenseId, freightOrderBox.getId());
							}
						}else{
							//如果不全部覆盖，则20、40尺型进行匹配
							List<FreightOrderBox> freightOrderBoxesSource = freightExpense.getFreightOrderBoxs();
							if(freightOrderBoxesSource != null && !freightOrderBoxesSource.isEmpty()){
								String boxType = "";
								for(FreightOrderBox freightOrderBoxSource : freightOrderBoxesSource){
									if(StringUtil.isNullOrEmpty(boxType)){
										boxType = freightOrderBoxSource.getFreightBoxRequire().getBoxType().substring(0, 2);
										for(FreightOrderBox freightOrderBox : freightOrderBoxes){
											if(boxType.equals(freightOrderBox.getFreightBoxRequire().getBoxType().substring(0, 2))){
												freightOrderBoxDao.addExpenseBox(newExpenseId, freightOrderBox.getId());
											}
										}
									}else{
										if(boxType.equals(freightOrderBoxSource.getFreightBoxRequire().getBoxType().substring(0, 2))){
											continue;
										}else{
											boxType = freightOrderBoxSource.getFreightBoxRequire().getBoxType();
											for(FreightOrderBox freightOrderBox : freightOrderBoxes){
												if(boxType.equals(freightOrderBox.getFreightBoxRequire().getBoxType().substring(0, 2))){
													freightOrderBoxDao.addExpenseBox(newExpenseId, freightOrderBox.getId());
												}
											}
										}
									}
								}
							}
						}
					}
					freightExpense.setId(newExpenseId);
					freightExpense.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightExpense.getCurrency(), targetOrder.getPlaceTime()));
					/*if(freightExpense.getActualCount() == freightExpense.getPredictCount()){
						freightExpense.setStatus("未提交");
					}else{
						freightExpense.setStatus("异常费用");
					}*/
					//默认费用复制只取其预计金额
					freightExpense.setStatus("未提交");
					freightExpense.setActualCount(freightExpense.getPredictCount());
					
					freightExpense.setExpenseNumber(freightExpense.getExpenseNumber().replaceAll(sourceOrder.getOrderNumber(), targetOrder.getOrderNumber()));
					freightExpense.setFreightOrder(targetOrder);
					if(freightExpense.getFreightAction() != null){
						for(FreightAction freightAction : freightActions){
							if(freightExpense.getFreightAction().getFreightActionType().getId().equals(freightAction.getFreightActionType().getId())){
								freightExpense.setFreightAction(freightAction);
								break;
							}
						}
					}
					
					//设置对账单为空
					FreightStatement freightStatementNull = new FreightStatement();
					freightStatementNull.setId("A");
					freightExpense.setFreightStatement(freightStatementNull);
					
					FreightPrice freightPrice = freightExpense.getFreightPrice();
					if(freightPrice != null){
						freightPrice.setId(StringUtil.getUUID());
						freightPrice.setActual(null);
						freightPrice.setActualCount(0);
						freightPrice.setStatus("T");
						freightPrice.setCreateTime(new Date());
						freightPrice.setModifyTime(new Date());
						freightPriceDao.add(freightPrice);
						freightExpense.setFreightPrice(freightPrice);
					}
					
					freightExpense.setCreateTime(new Date());
					freightExpense.setModifyTime(new Date());
					freightExpenseDao.add(freightExpense);
				}
			}
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}

	@Override
	public Map<String, String> getKeyData(String freightOrderId) {
		Map<String, String> map = new HashMap<String, String>();//提单号优先取箱需信息里的
		List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao.getByFreightOrderId(freightOrderId);
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
			map.put("TDH", text.toString());
		}
		
		if(map.get("TDH") == null){
			map.put("TDH", freightActionValueDao.getSingleValue(freightOrderId, "TDH"));
		}
		map.put("QYG", freightActionValueDao.getSingleValue(freightOrderId, "QYG"));
		map.put("MDG", freightActionValueDao.getSingleValue(freightOrderId, "MDG"));
		//先取出共享字段数据
		/*List<FreightActionValue> participateValues = freightActionValueDao.getAllParticipateValue(freightOrderId);
		Map<String, FreightActionValue> participateValueMap = new HashMap<String, FreightActionValue>();
		if(participateValues != null && !participateValues.isEmpty()){
			for(FreightActionValue item : participateValues){
				if(participateValueMap.get(item.getFreightActionField().getFieldColumn()) == null){
					participateValueMap.put(item.getFreightActionField().getFieldColumn(), item);
				}
			}
		}
		if(map.get("TDH") == null){
			map.put("TDH", participateValueMap.get("TDH") == null ? "" : participateValueMap.get("TDH").getStringValue());
		}
		map.put("QYG", participateValueMap.get("QYG") == null ? "" : participateValueMap.get("QYG").getStringValue());
		map.put("MDG", participateValueMap.get("MDG") == null ? "" : participateValueMap.get("MDG").getStringValue());*/
		
		
		
		/*List<FreightAction> freightActions = freightActionDao.getByFreightOrderId(freightOrderId);
		for(FreightAction freightAction : freightActions){
			List<FreightActionValue> freightActionValues = freightActionValueDao.getNormalByFreightActionId(freightAction.getId());
			for(FreightActionValue freightActionValue : freightActionValues){
				int count = 0;
				if(StringUtil.isNullOrEmpty(map.get("TDH"))){
					if(map.get("TDH") == null){
						if("TDH".equals(freightActionValue.getFreightActionField().getFieldColumn()) 
								&& !StringUtil.isNullOrEmpty(freightActionValue.getStringValue())){
							map.put("TDH", freightActionValue.getStringValue());
							count++;
						}
					}
				}
				if(count > 0){
					continue;
				}
				
				if(map.get("QYG") == null){
					if("QYG".equals(freightActionValue.getFreightActionField().getFieldColumn()) 
							&& !StringUtil.isNullOrEmpty(freightActionValue.getStringValue())){
						map.put("QYG", freightActionValue.getStringValue());
						count++;
					}
				}
				
				if(count > 0){
					continue;
				}
				if(map.get("MDG") == null){
					if("MDG".equals(freightActionValue.getFreightActionField().getFieldColumn()) 
							&& !StringUtil.isNullOrEmpty(freightActionValue.getStringValue())){
						map.put("MDG", freightActionValue.getStringValue());
					}
				}
			}
		}*/
		
		return map;
	}

	@Override
	public boolean doneRehearOrder(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("订单复审中")){
				freightOrder.setOrderStatus("订单终审中");
				freightOrderDao.modify(freightOrder);
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
	public boolean backRehearOrder(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("订单复审中")){
				freightOrder.setOrderStatus("订单初审中");
				freightOrderDao.modify(freightOrder);
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
	public boolean doneEventideOrder(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("订单终审中")){
				freightOrder.setOrderStatus("已审核");
				freightOrderDao.modify(freightOrder);
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
	public boolean backEventideOrder(String[] freightOrderIds) {
		boolean flag = true;
		for(String freightOrderId : freightOrderIds){
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			String orderStatus = freightOrder.getOrderStatus();
			if(orderStatus.equals("订单终审中")){
				freightOrder.setOrderStatus("订单复审中");
				freightOrderDao.modify(freightOrder);
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
	public boolean doneAddDeficitReason(String freightOrderId, String deficitReason) {
		boolean flag = true;
		FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
		freightOrder.setDeficitReason(deficitReason);
		freightOrder.setOrderStatus("订单初审中");
		freightOrder.setExpenseStatus("添加完毕");
		freightOrderDao.modify(freightOrder);
		
		List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightOrderId(freightOrderId);
		for(FreightExpense freightExpense : freightExpenses){
			if("未提交".equals(freightExpense.getStatus())){
				freightExpense.setStatus("审核中");
				freightExpenseDao.modify(freightExpense);
			}
		}
		
		return flag;
	}

	@Override
	public List<List<String>> toBatchOrderExport(String[] freightOrderIds) {
		PageView<FreightOrder> pageView = new PageView<FreightOrder>(freightOrderIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String freightOrderId : freightOrderIds){
			filterText.append("'" + freightOrderId + "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		
		List<FreightOrder> freightOrders = freightOrderDao.query(pageView, new FreightOrder());
		List<List<String>> values = new ArrayList<List<String>>();
		
		for(FreightOrder freightOrder : freightOrders){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightOrder.getOrderScope());
			singleValue.add(freightOrder.getFirstType());
			singleValue.add(freightOrder.getSecondType());
			singleValue.add(freightOrder.getThirdType());
			singleValue.add(freightOrder.getFourthType());
			singleValue.add(freightOrder.getCargoName());
			singleValue.add(freightOrder.getDelegatePart());
			singleValue.add(freightOrder.getOrderNumber());
			int tCount = 0;
			int fCount = 0;
			int teu = 0;
			for(FreightBoxRequire freightBoxRequire : freightOrder.getFreightBoxRequires()){
				if(freightBoxRequire.getBoxType().startsWith("2")){
					tCount += freightBoxRequire.getBoxCount();
					teu += 1;
				}else if(freightBoxRequire.getBoxType().startsWith("4")){
					fCount += freightBoxRequire.getBoxCount();
					teu += 2;
				}
			}
			
			singleValue.add(String.valueOf(tCount));
			singleValue.add(String.valueOf(fCount));
			singleValue.add(String.valueOf(teu));
			singleValue.add(String.valueOf(freightOrder.getBalance()));
			
			double taxIn = 0;
			double taxOut = 0;
			for(FreightExpense freightExpense : freightOrder.getFreightExpenses()){
				double d = 0;
				if("付".equals(freightExpense.getIncomeOrExpense()) && freightExpense.getFreightPrice() != null
						&& ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){
					if("箱".equals(freightExpense.getCountUnit())){
						if(freightExpense.getFreightOrderBoxs() == null || freightExpense.getFreightOrderBoxs().size() == 0){
							continue;
						}else{
							d = (freightExpense.getFreightPrice().getActualCount()*freightExpense.getExchangeRate()*freightExpense.getFasInvoiceType().getTaxRate())*freightExpense.getFreightOrderBoxs().size()/(1 + freightExpense.getFasInvoiceType().getTaxRate());
						}
					}else{
						d = (freightExpense.getFreightPrice().getActualCount()*freightExpense.getExchangeRate()*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
					}
				}else{
					if("箱".equals(freightExpense.getCountUnit())){
						if(freightExpense.getFreightOrderBoxs() == null || freightExpense.getFreightOrderBoxs().size() == 0){
							continue;
						}else{
							d = (freightExpense.getPredictCount()*freightExpense.getExchangeRate()*freightExpense.getFasInvoiceType().getTaxRate())*freightExpense.getFreightOrderBoxs().size()/(1 + freightExpense.getFasInvoiceType().getTaxRate());
						}
					}else{
						d = (freightExpense.getPredictCount()*freightExpense.getExchangeRate()*freightExpense.getFasInvoiceType().getTaxRate())/(1 + freightExpense.getFasInvoiceType().getTaxRate());
					}
				}
				
				if("收".equals(freightExpense.getIncomeOrExpense())){
					taxIn += d;
				}else if("付".equals(freightExpense.getIncomeOrExpense())){
					taxOut += d;
				}
			}
			
			singleValue.add(String.valueOf(Double.parseDouble(String.format("%.2f", taxIn))));
			singleValue.add(String.valueOf(Double.parseDouble(String.format("%.2f", taxOut))));
			singleValue.add(String.valueOf(Double.parseDouble(String.format("%.2f", taxOut - taxIn))));
			singleValue.add(String.valueOf(freightOrder.getFasTaxBalance()));
			singleValue.add(String.valueOf(freightOrder.getSaleTaxBalance()));
			singleValue.add(userDao.getByDisplayName(freightOrder.getSalesman()).getOrgEntity().getOrgEntityName());
			singleValue.add(freightOrder.getSalesman());
			values.add(singleValue);
		}
		
		return values;
	}

	
}

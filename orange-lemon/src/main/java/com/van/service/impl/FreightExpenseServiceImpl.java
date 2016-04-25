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
import org.springframework.util.Assert;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasExchangeRateDao;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.FreightActionDao;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightExpenseTypeDao;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.FreightPriceDao;
import com.van.halley.db.persistence.FreightStatementDao;
import com.van.halley.db.persistence.OrgEntityDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.FreightAction;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightExpenseType;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.db.persistence.entity.FreightPrice;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.fre.util.FreightCommonUtil;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.FreightExpenseService;
import com.van.service.FreightExpenseTypeService;

@Transactional
@Service("freightExpenseService")
public class FreightExpenseServiceImpl implements FreightExpenseService {
	private static Logger logger = LoggerFactory.getLogger(FreightExpenseServiceImpl.class);
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightStatementDao freightStatementDao;
	@Autowired
	private FasExchangeRateDao fasExchangeRateDao;
	@Autowired
	private FreightActionDao freightActionDao;
	@Autowired
	private FreightActionValueDao freightActionValueDao;
	@Autowired
	private FreightExpenseTypeDao freightExpenseTypeDao;
	@Autowired
	private FreightPartDao freightPartDao;
	@Autowired
	private FreightPriceDao freightPriceDao;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightOrderBoxDao freightOrderBoxDao;
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;
	@Autowired
	private OrgEntityDao orgEntityDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FreightBoxRequireDao freightBoxRequireDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightExpense> getAll() {
		return freightExpenseDao.getAll();
	}

	public List<FreightExpense> queryForList(FreightExpense freightExpense) {
		return freightExpenseDao.queryForList(freightExpense);
	}

	public PageView<FreightExpense> query(PageView<FreightExpense> pageView, FreightExpense freightExpense) {
		List<FreightExpense> list = freightExpenseDao.query(pageView,
				freightExpense);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightExpense freightExpense) {
		freightExpenseDao.add(freightExpense);
	}

	public void delete(String id) {
		freightExpenseDao.delete(id);
	}

	public void modify(FreightExpense freightExpense) {
		freightExpenseDao.modify(freightExpense);
	}

	public FreightExpense getById(String id) {
		return freightExpenseDao.getById(id);
	}

	@Override
	public String getNextExpenseNumber(String freightOrderId, String addType) {
		String expenseNumber = "";
		String sql = "";
		if("FY".equals(addType)){
			sql = "SELECT CONCAT(LEFT(EXPENSE_NUMBER,LENGTH(EXPENSE_NUMBER) - 4), REPEAT('0',4 - LENGTH(MAX(RIGHT(EXPENSE_NUMBER,4)) + 1)), MAX(RIGHT(EXPENSE_NUMBER,4)) + 1) "
					+ " FROM FRE_EXPENSE WHERE FRE_ORDER_ID=?";
			expenseNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
			if(StringUtil.isNullOrEmpty(expenseNumber)){
				sql = "SELECT CONCAT(ORDER_NUMBER, '-FY-0001') FROM FRE_ORDER WHERE ID=?";
				expenseNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
			}
		}else if("NB".equals(addType)){
			sql = "SELECT CONCAT(LEFT(EXPENSE_NUMBER,LENGTH(EXPENSE_NUMBER) - 4), REPEAT('0',4 - LENGTH(MAX(RIGHT(EXPENSE_NUMBER,4)) + 1)), MAX(RIGHT(EXPENSE_NUMBER,4)) + 1) "
					+ " FROM FRE_EXPENSE WHERE EXPENSE_NUMBER LIKE '%NB%' AND FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN (SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?)) AND FRE_ORDER_ID IS NULL AND FRE_ACTION_ID IS NOT NULL";
			expenseNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
			if(StringUtil.isNullOrEmpty(expenseNumber)){
				sql = "SELECT CONCAT(ORDER_NUMBER, '-NB-0001') FROM FRE_ORDER WHERE ID=?";
				expenseNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
			}
		}else if("TS".equals(addType)){
			sql = "SELECT CONCAT(LEFT(EXPENSE_NUMBER,LENGTH(EXPENSE_NUMBER) - 4), REPEAT('0',4 - LENGTH(MAX(RIGHT(EXPENSE_NUMBER,4)) + 1)), MAX(RIGHT(EXPENSE_NUMBER,4)) + 1) "
					+ " FROM FRE_EXPENSE WHERE EXPENSE_NUMBER LIKE '%TS%' AND FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN (SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?)) AND FRE_ORDER_ID IS NULL AND FRE_ACTION_ID IS NOT NULL";
			expenseNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
			if(StringUtil.isNullOrEmpty(expenseNumber)){
				sql = "SELECT CONCAT(ORDER_NUMBER, '-TS-0001') FROM FRE_ORDER WHERE ID=?";
				expenseNumber = jdbcTemplate.queryForObject(sql, String.class, freightOrderId);
			}
		}
		return expenseNumber;
	}
	
	/**
	 * 获取与业务关联的费用（仅业务添加）
	 * @param freightOrderId
	 * @return
	 */
	@Override
	public List<FreightExpense> getByFreightOrderId(String freightOrderId){
		return freightExpenseDao.getByFreightOrderId(freightOrderId);
	}
	/**
	 * 账单关联费用
	 * @param freightStatementId
	 * @return
	 */
	@Override
	public List<FreightExpense> getByFreightStatementId(String freightStatementId){
		return freightExpenseDao.getByFreightStatementId(freightStatementId);
	}

	@Override
	public boolean doneAddInternal(FreightExpense freightExpense, String freightActionId, 
			String freightPartId, String freightExpenseTypeId, String freightPriceId, 
			String fasInvoiceTypeId, String[] freightOrderBoxIds, User creator) {
		Assert.notNull(freightPartId);
		Assert.notNull(freightExpenseTypeId);
		Assert.notNull(fasInvoiceTypeId);
		
		if("付".equals(freightExpense.getIncomeOrExpense())){
			Assert.notNull(freightPriceId);
			Assert.notNull(freightActionId);
		}
		
		if("箱".equals(freightExpense.getCountUnit())){
			Assert.notNull(freightOrderBoxIds);
		}
		
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		FreightExpenseType freightExpenseType = freightExpenseTypeDao.getById(freightExpenseTypeId);
		FreightPart freightPartA = freightPartDao.getByOrgEntityId(creator.getOrgEntity().getId());
		FreightPart freightPartB = freightPartDao.getById(freightPartId);
		//先将业务部门给其他部门的对应费用找出，默认在一个订单中的一个动作中，支付给某部门的一种费用仅有一次；
		FreightExpense filter = new FreightExpense();
		filter.setFreightAction(freightAction);
		filter.setFreightExpenseType(freightExpenseType);
		filter.setFreightPartA(freightPartB);
		filter.setFreightPartB(freightPartA);
		if("收".equals(freightExpense.getIncomeOrExpense())){
			filter.setIncomeOrExpense("付");
		}else{
			filter.setIncomeOrExpense("收");
		}
		FreightExpense targetExpense = freightExpenseDao.queryForOne(filter);
		
		freightExpense.setActualCount(freightExpense.getPredictCount());//预计费用与实际费用暂保存一致
		if(targetExpense == null){
			freightExpense.setActual("T");//设置与实际一致
		}else{
			if(targetExpense.getPredictCount() != freightExpense.getPredictCount()){
				freightExpense.setActual("F");
			}else if(targetExpense.getActualCount() != freightExpense.getActualCount()){
				freightExpense.setActual("F");
			}
		}
		freightExpense.setFreightAction(freightAction);
		freightExpense.setFreightExpenseType(freightExpenseType);
		freightExpense.setFreightPartA(freightPartA);
		freightExpense.setFreightPartB(freightPartB);
		freightExpense.setFasInvoiceType(fasInvoiceTypeDao.getById(fasInvoiceTypeId));
		freightExpense.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightExpense.getCurrency(), 
				freightAction.getFreightMaintain().getFreightOrder().getPlaceTime()));
		
		freightExpense.setCreator(creator);
		freightExpense.setOrgEntity(creator.getOrgEntity());
		
		//设置费用编号
		if(StringUtil.isNullOrEmpty(freightExpense.getExpenseNumber())){
			freightExpense.setExpenseNumber(getNextExpenseNumber(freightAction.getFreightMaintain().getFreightOrder().getId(), "NB"));
		}
		if(!StringUtil.isNullOrEmpty(freightPriceId)){//如果成本ID不为空，则复制一个该成本信息并关联至费用
			FreightPrice freightPrice = freightPriceDao.getById(freightPriceId);
			freightPrice.setId(StringUtil.getUUID());
			freightPrice.setActual(null);//针对有特殊费用的处理的成本，否则下次关联的费用就直接成为特殊费用了。
			freightPrice.setActualCount(0);
			freightPrice.setStatus("T");
			freightPrice.setCreateTime(new Date());
			freightPrice.setModifyTime(new Date());
			freightPriceDao.add(freightPrice);
			freightExpense.setFreightPrice(freightPrice);
			//如果为收入直接设为与成本一致，如果为支出则与成本进行比较，再判断与成本是否一致
			if("收".equals(freightExpense.getIncomeOrExpense()) || "F".equals(freightPrice.getRegular()) || freightExpense.getPredictCount() == freightPrice.getMoneyCount()){
				freightExpense.setPrice("T");
			}else{
				freightExpense.setPrice("F");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(freightExpense.getId())){//修改单条费用
			freightExpense.setStatus("审核中");//添加内部费用需要审核
			freightExpenseDao.modify(freightExpense);
		}else{
			String freightExpenseId = StringUtil.getUUID();
			freightExpense.setId(freightExpenseId);
			freightExpense.setStatus("审核中");//添加内部费用需要审核
			freightExpenseDao.add(freightExpense);
		}
		
		if("箱".equals(freightExpense.getCountUnit())){
			if(freightOrderBoxIds.length > 0){
				freightOrderBoxDao.deleteExpenseBox(freightExpense.getId());
				for(String freightOrderBoxId : freightOrderBoxIds){
					freightOrderBoxDao.addExpenseBox(freightExpense.getId(), freightOrderBoxId);
				}
			}else{
				logger.error("添加费用出错，按箱费用必须选择箱封信息！");
				throw new IllegalAccessError("添加费用出错，按箱费用必须选择箱封信息！");
			}
		}
		
		checkUnanimity(freightExpense.getId());//进行比对
		return true;
	}

	@Override
	public boolean doneAddNormal(FreightExpense freightExpense, String freightOrderId, 
			String freightActionId, String freightPartId, String freightPriceId, String freightExpenseTypeId,
			String fasInvoiceTypeId, String[] freightOrderBoxIds, User creator) {
		Assert.notNull(freightPartId);
		Assert.notNull(freightExpenseTypeId);
		Assert.notNull(fasInvoiceTypeId);
		
		if("付".equals(freightExpense.getIncomeOrExpense())){
			Assert.notNull(freightPriceId);
			Assert.notNull(freightActionId);
		}
		
		if("箱".equals(freightExpense.getCountUnit())){
			Assert.notNull(freightOrderBoxIds);
		}
		
		freightExpense.setActualCount(freightExpense.getPredictCount());//预计费用与实际费用暂保存一致
		freightExpense.setActual("T");//设置与实际一致
		FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
		freightExpense.setFreightOrder(freightOrder);
		freightExpense.setFreightPartA(freightPartDao.getByOrgEntityId(freightOrder.getOrgEntityId()));
		freightExpense.setFreightPartB(freightPartDao.getById(freightPartId));
		freightExpense.setFreightExpenseType(freightExpenseTypeDao.getById(freightExpenseTypeId));
		freightExpense.setFasInvoiceType(fasInvoiceTypeDao.getById(fasInvoiceTypeId));
		freightExpense.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightExpense.getCurrency(), freightOrder.getPlaceTime()));
		freightExpense.setCreator(userDao.getById(freightOrder.getCreatorUserId()));
		freightExpense.setOrgEntity(orgEntityDao.getById(freightOrder.getOrgEntityId()));
		
		if(!StringUtil.isNullOrEmpty(freightActionId)){
			freightExpense.setFreightAction(freightActionDao.getById(freightActionId));
		}
		if(StringUtil.isNullOrEmpty(freightExpense.getExpenseNumber())){
			freightExpense.setExpenseNumber(getNextExpenseNumber(freightOrderId, "FY"));
		}
		
		if(!StringUtil.isNullOrEmpty(freightPriceId)){
			FreightPrice freightPrice = freightPriceDao.getById(freightPriceId);
			freightPrice.setId(StringUtil.getUUID());
			freightPrice.setActual(null);//针对有特殊费用的处理的成本，否则下次关联的费用就直接成为特殊费用了。
			freightPrice.setActualCount(0);
			freightPrice.setStatus("T");
			freightPrice.setCreateTime(new Date());
			freightPrice.setModifyTime(new Date());
			freightPriceDao.add(freightPrice);
			freightExpense.setFreightPrice(freightPrice);
			//如果为收入直接设为与成本一致，如果为支出则与成本进行比较，再判断与成本是否一致
			if("收".equals(freightExpense.getIncomeOrExpense()) || "F".equals(freightPrice.getRegular()) || freightExpense.getPredictCount() == freightPrice.getMoneyCount()){
				freightExpense.setPrice("T");
			}else{
				freightExpense.setPrice("F");
			}
		}
		if(!StringUtil.isNullOrEmpty(freightExpense.getId())){//修改单条费用
			freightExpense.setStatus("未提交");
			freightExpenseDao.modify(freightExpense);
		}else{
			String freightExpenseId = StringUtil.getUUID();
			freightExpense.setId(freightExpenseId);
			freightExpense.setStatus("未提交");
			freightExpenseDao.add(freightExpense);
		}
		
		if("箱".equals(freightExpense.getCountUnit())){
			if(freightOrderBoxIds.length > 0){
				freightOrderBoxDao.deleteExpenseBox(freightExpense.getId());
				for(String freightOrderBoxId : freightOrderBoxIds){
					freightOrderBoxDao.addExpenseBox(freightExpense.getId(), freightOrderBoxId);
				}
			}else{
				logger.error("添加费用出错，按箱费用必须选择箱封信息！");
				throw new IllegalAccessError("添加费用出错，按箱费用必须选择箱封信息！");
			}
		}
		
		if("未添加".equals(freightOrder.getExpenseStatus())){
			freightOrder.setExpenseStatus("添加中");
			freightOrderDao.modify(freightOrder);
		}
		
		checkUnanimity(freightExpense.getId());//进行比对
		return true;
	}
	
	@Override
	public boolean doneBatchNormal(FreightExpense freightExpense,
			String freightOrderId, String freightActionId,
			String freightPartId, String freightPriceId,
			String freightExpenseTypeId, String fasInvoiceTypeId,
			String[] freightOrderBoxIds, User creator) {
		boolean flag = true;
		if(!"箱".equals(freightExpense.getCountUnit()) || freightOrderBoxIds == null || freightOrderBoxIds.length == 0){
			flag = false;
		}else{
			freightExpense.setActualCount(freightExpense.getPredictCount());//预计费用与实际费用暂保存一致
			freightExpense.setActual("T");//设置与实际一致
			FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
			freightExpense.setFreightOrder(freightOrder);
			freightExpense.setFreightPartA(freightPartDao.getByOrgEntityId(freightOrder.getOrgEntityId()));
			freightExpense.setFreightPartB(freightPartDao.getById(freightPartId));
			freightExpense.setFreightExpenseType(freightExpenseTypeDao.getById(freightExpenseTypeId));
			freightExpense.setFasInvoiceType(fasInvoiceTypeDao.getById(fasInvoiceTypeId));
			freightExpense.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightExpense.getCurrency(), freightOrder.getPlaceTime()));
			freightExpense.setCreator(userDao.getById(freightOrder.getCreatorUserId()));
			freightExpense.setOrgEntity(orgEntityDao.getById(freightOrder.getOrgEntityId()));
			
			if(!StringUtil.isNullOrEmpty(freightActionId)){
				freightExpense.setFreightAction(freightActionDao.getById(freightActionId));
			}
			
			if(!StringUtil.isNullOrEmpty(freightPriceId)){
				FreightPrice freightPrice = freightPriceDao.getById(freightPriceId);
				freightPrice.setActual(null);//针对有特殊费用的处理的成本，否则下次关联的费用就直接成为特殊费用了。
				freightPrice.setActualCount(0);
				freightPrice.setStatus("T");
				freightPrice.setCreateTime(new Date());
				freightPrice.setModifyTime(new Date());
				freightExpense.setFreightPrice(freightPrice);
				//收、非固定成本、金额一致则为T
				if("收".equals(freightExpense.getIncomeOrExpense()) || "F".equals(freightPrice.getRegular()) || freightExpense.getPredictCount() == freightPrice.getMoneyCount()){
					freightExpense.setPrice("T");
				}else{
					freightExpense.setPrice("F");
				}
			}
			
			if("未添加".equals(freightOrder.getExpenseStatus())){
				freightOrder.setExpenseStatus("添加中");
				freightOrderDao.modify(freightOrder);
			}
			
			for(String freightOrderBoxId : freightOrderBoxIds){
				String freightExpenseId = StringUtil.getUUID();
				freightExpense.setId(freightExpenseId);
				freightExpense.setExpenseNumber(getNextExpenseNumber(freightOrderId, "FY"));
				freightExpense.setStatus("未提交");
				if(freightExpense.getFreightPrice() != null){
					FreightPrice singlePrice = freightExpense.getFreightPrice();
					singlePrice.setId(StringUtil.getUUID());
					freightPriceDao.add(singlePrice);
					freightExpense.setFreightPrice(singlePrice);
				}
				
				freightOrderBoxDao.addExpenseBox(freightExpenseId, freightOrderBoxId);
				freightExpenseDao.add(freightExpense);
			}
			checkUnanimity(freightExpense.getId());//进行比对
		}
		
		return flag;
	}
	
	@Override
	public boolean doneBatchInternal(FreightExpense freightExpense,
			String freightActionId, String freightPartId,
			String freightExpenseTypeId, String freightPriceId,
			String fasInvoiceTypeId, String[] freightOrderBoxIds, User creator) {
		boolean flag = true;
		if(!"箱".equals(freightExpense.getCountUnit()) || freightOrderBoxIds == null || freightOrderBoxIds.length == 0){
			flag = false;
		}else{
			FreightAction freightAction = freightActionDao.getById(freightActionId);
			FreightExpenseType freightExpenseType = freightExpenseTypeDao.getById(freightExpenseTypeId);
			//FreightPart freightPartA = freightPartDao.getByOrgEntityId(creator.getOrgEntity().getId());
			//FreightPart freightPartB = freightPartDao.getById(freightPartId);
			freightExpense.setActualCount(freightExpense.getPredictCount());
			freightExpense.setActual("T");
			freightExpense.setFreightAction(freightAction);
			freightExpense.setFreightExpenseType(freightExpenseType);
			freightExpense.setFreightPartA(freightPartDao.getByOrgEntityId(creator.getOrgEntity().getId()));
			freightExpense.setFreightPartB(freightPartDao.getById(freightPartId));
			freightExpense.setFasInvoiceType(fasInvoiceTypeDao.getById(fasInvoiceTypeId));
			freightExpense.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightExpense.getCurrency(), freightAction.getFreightMaintain().getFreightOrder().getPlaceTime()));
			
			freightExpense.setCreator(creator);
			freightExpense.setOrgEntity(creator.getOrgEntity());
			
			if(!StringUtil.isNullOrEmpty(freightPriceId)){//如果成本ID不为空，则复制一个该成本信息并关联至费用
				FreightPrice freightPrice = freightPriceDao.getById(freightPriceId);
				freightPrice.setActual(null);//针对有特殊费用的处理的成本，否则下次关联的费用就直接成为特殊费用了。
				freightPrice.setActualCount(0);
				freightPrice.setStatus("T");
				freightPrice.setCreateTime(new Date());
				freightPrice.setModifyTime(new Date());
				freightExpense.setFreightPrice(freightPrice);
				//如果为收入直接设为与成本一致，如果为支出则与成本进行比较，再判断与成本是否一致
				if("收".equals(freightExpense.getIncomeOrExpense()) || "F".equals(freightPrice.getRegular()) || freightExpense.getPredictCount() == freightPrice.getMoneyCount()){
					freightExpense.setPrice("T");
				}else{
					freightExpense.setPrice("F");
				}
			}
			
			if(freightOrderBoxIds.length > 0){
				freightOrderBoxDao.deleteExpenseBox(freightExpense.getId());
				for(String freightOrderBoxId : freightOrderBoxIds){
					String freightExpenseId = StringUtil.getUUID();
					freightExpense.setId(freightExpenseId);
					freightExpense.setExpenseNumber(getNextExpenseNumber(freightAction.getFreightMaintain().getFreightOrder().getId(), "NB"));
					freightExpense.setStatus("审核中");
					if(freightExpense.getFreightPrice() != null){
						FreightPrice singlePrice = freightExpense.getFreightPrice();
						singlePrice.setId(StringUtil.getUUID());
						freightPriceDao.add(singlePrice);
						freightExpense.setFreightPrice(singlePrice);
					}
					
					freightOrderBoxDao.addExpenseBox(freightExpenseId, freightOrderBoxId);
					freightExpenseDao.add(freightExpense);
				}
			}
			checkUnanimity(freightExpense.getId());//进行比对
		}
		return flag;
	}

	@Override
	public boolean compareInternalExpense(boolean isInternal, FreightOrder freightOrder, FreightExpenseType freightExpenseType, FreightPart freightPartA, FreightPart freightPartB) {
		List<FreightExpense> internalExpeneses = null;
		List<FreightExpense> normalExpeneses = null;
		if(isInternal){
			internalExpeneses = getInternalExpense(freightOrder, freightExpenseType, freightPartA, freightPartB);
			normalExpeneses = getNormalExpense(freightOrder, freightExpenseType, freightPartB, freightPartA);
		}else{
			internalExpeneses = getInternalExpense(freightOrder, freightExpenseType, freightPartB, freightPartA);
			normalExpeneses = getNormalExpense(freightOrder, freightExpenseType, freightPartA, freightPartB);
		}
		
		if(internalExpeneses == null || internalExpeneses.isEmpty() || normalExpeneses == null || normalExpeneses.isEmpty()){
			logger.info("没有获取到应该进行比对的费用，比对取消。");
			return false;
		}else{
			//判断收付类型是否一致， 先获取内部部门提交的费用金额，再提取客户服务部门提交的费用金额
			//除了比对总金额之外还要比对税金否则可能发票种类不一致（即便如此发票种类也可能不一致，但是税金一致，总金额一致，则对收支无影响）
			double internalAmount = 0;
			double internalTaxation = 0;
			
			double normalAmount = 0;
			double normalTaxation = 0;
			boolean canCompare = true;
			String incomeOrExpense = null;
			for(FreightExpense internalExpenese : internalExpeneses){
				if(incomeOrExpense == null){
					incomeOrExpense = internalExpenese.getIncomeOrExpense();
				}
				if(!internalExpenese.getIncomeOrExpense().equals(incomeOrExpense)){
					canCompare = false;
					logger.info("收付状态不一致，比对取消。");
					break;
				}
				internalAmount += internalExpenese.getActualAmount();//应当获取总额
				internalTaxation += internalExpenese.getTaxation();
			}
			
			if(canCompare){
				for(FreightExpense normalExpenese : normalExpeneses){
					if(normalExpenese.getIncomeOrExpense().equals(incomeOrExpense)){//如果同收同付，则取消比对
						canCompare = false;
						logger.info("其他部门提交费用与客户服务部们提交费用的收付状态一致，比对取消。");
						break;
					}
					normalAmount += normalExpenese.getActualAmount();//应当获取总额
					normalTaxation += normalExpenese.getTaxation();
				}
			}
			//如果可继续比较
			if(canCompare){
				if(Math.abs(internalAmount - normalAmount) > 1 || Math.abs(internalTaxation - normalTaxation) > 1){
				//if(internalAmount != normalAmount || Math.round(internalTaxation) != Math.round(normalTaxation)){//如果不一致，此处取整，避免一些取税金的时候出现过多位数的小数。
					for(FreightExpense internalExpenese : internalExpeneses){
						internalExpenese.setActual("FI");//标记为与内部费用且与客户服务部门提交费用不一致
						internalExpenese.setStatus("异常费用");
						freightExpenseDao.modify(internalExpenese);
					}
				}else{
					for(FreightExpense internalExpenese : internalExpeneses){
						internalExpenese.setActual("T");//标记为与内部费用且与客户服务部门提交费用一致
						internalExpenese.setStatus("审核中");
						freightExpenseDao.modify(internalExpenese);
					}
				}
			}
			return canCompare;//返回的值应该是是否比对过
		}
	}

	@Override
	public List<FreightExpense> getInternalExpense(FreightOrder freightOrder, FreightExpenseType freightExpenseType, FreightPart freightPartA, FreightPart freightPartB) {
		FreightExpense filter = new FreightExpense();
		filter.setFreightOrder(freightOrder);
		filter.setFreightExpenseType(freightExpenseType);
		filter.setFreightPartA(freightPartA);
		filter.setFreightPartB(freightPartB);
		return freightExpenseDao.getInternalExpense(filter);
	}

	@Override
	public List<FreightExpense> getNormalExpense(FreightOrder freightOrder, FreightExpenseType freightExpenseType, FreightPart freightPartA, FreightPart freightPartB) {
		FreightExpense filter = new FreightExpense();
		filter.setFreightOrder(freightOrder);
		filter.setFreightExpenseType(freightExpenseType);
		filter.setFreightPartA(freightPartA);
		filter.setFreightPartB(freightPartB);
		return freightExpenseDao.getNormalExpense(filter);
	}
	
	@Override
	public boolean validBatchForRevise(String[] freightExpenseIds) {
		boolean flag = true;
		StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM FRE_EXPENSE WHERE ID IN(");
		for(String freightExpenseId : freightExpenseIds){
			sql.append("'" + freightExpenseId + "',");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") GROUP BY INCOME_OR_EXPENSE, FRE_EXPENSE_TYPE_ID, FRE_PART_ID_B, FAS_INVOICE_TYPE_ID LIMIT 1");
		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		if(count != freightExpenseIds.length){
			flag = false;
		}
		
		if(flag){
			sql = new StringBuilder("SELECT COUNT(1) FROM FRE_EXPENSE WHERE ID IN(");
			for(String freightExpenseId : freightExpenseIds){
				sql.append("'" + freightExpenseId + "',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") AND STATUS IN ('已审核', '异常费用')");
			count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			if(count != freightExpenseIds.length){
				flag = false;
			}
		}
		
		if(flag){
			sql = new StringBuilder("SELECT COUNT(1) FROM FRE_EXPENSE WHERE ID IN(");
			for(String freightExpenseId : freightExpenseIds){
				sql.append("'" + freightExpenseId + "',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") AND STATUS ='已审核'");
			count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			if(count != freightExpenseIds.length && count != 0){
				flag = false;
			}
		}
		
		if(flag){
			sql = new StringBuilder("SELECT COUNT(1) FROM FRE_EXPENSE WHERE ID IN(");
			for(String freightExpenseId : freightExpenseIds){
				sql.append("'" + freightExpenseId + "',");
			}
			sql.deleteCharAt(sql.lastIndexOf(","));
			sql.append(") AND STATUS ='异常费用'");
			count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
			if(count != freightExpenseIds.length && count != 0){
				flag = false;
			}
		}
		
		return flag;
	}

	@Override
	public boolean revisePredictCountBatch(String[] freightExpenseIds, double predictCount) {
		boolean flag = true;
		StringBuilder sql = new StringBuilder("UPDATE FRE_EXPENSE SET PREDICT_COUNT=?, STATUS=? WHERE ID IN(");
		for(String freightExpenseId : freightExpenseIds){
			sql.append("'" + freightExpenseId + "',");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") AND STATUS ='异常费用'");
		int count = jdbcTemplate.update(sql.toString(), predictCount, "初审中(一般异常)");
		if(count == freightExpenseIds.length){
			for(String freightExpenseId : freightExpenseIds){//修改其一致信息
				checkUnanimity(freightExpenseId);
			}
		}else{
			flag = false;
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean reviseActualCountBatch(String[] freightExpenseIds, double actualCount) {
		boolean flag = true;
		StringBuilder sql = new StringBuilder("UPDATE FRE_EXPENSE SET ACTUAL_COUNT=?, STATUS=? WHERE ID IN(");
		for(String freightExpenseId : freightExpenseIds){
			sql.append("'" + freightExpenseId + "',");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") AND STATUS ='已审核'");
		int count = jdbcTemplate.update(sql.toString(), actualCount, "异常费用");
		if(count == freightExpenseIds.length){
			for(String freightExpenseId : freightExpenseIds){//修改其一致信息
				checkUnanimity(freightExpenseId);
			}
		}else{
			flag = false;
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public void checkUnanimity(String freightExpenseId) {
		FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
		if(freightExpense.getPredictCount() == freightExpense.getActualCount()){
			freightExpense.setActual("T");
		}else{
			freightExpense.setActual("F");
		}
		
		if(freightExpense.getFreightPrice() == null){
			freightExpense.setPrice("T");
		}else{
			if(freightExpense.getPredictCount() == freightExpense.getFreightPrice().getMoneyCount()){
				freightExpense.setPrice("T");
			}else{
				freightExpense.setPrice("F");
			}
		}
		freightExpenseDao.modify(freightExpense);
		
		//如果该费用已经关联账单，则标记该账单的状态为异常锁定；如果已关联账单的费用的状态为已审核，则费用状态自动为已对账
		FreightStatement freightStatement = freightExpense.getFreightStatement();
		if(freightStatement != null){
			if("F".equals(freightExpense.getActual()) || (!"已审核".equals(freightExpense.getStatus()) && !"已对账".equals(freightExpense.getStatus()))){
				if(!"异常锁定".equals(freightStatement.getStatus())){
					freightStatement.setStatus("异常锁定");
					freightStatementDao.modify(freightStatement);
				}
			}else{//如果本条费用与实际金额一致，则需要判断其他费用是否与实际一致，且状态都为已审核
				boolean flag = true;
				FreightExpense filter = new FreightExpense();
				filter.setFreightStatement(freightStatement);
				List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(freightExpense);
				for(FreightExpense exp : freightExpenses){
					if("F".equals(exp.getActual()) || (!"已审核".equals(exp.getStatus()) && !"已对账".equals(freightExpense.getStatus()))){
						flag = false;
						break;
					}
				}
				if(flag){//所有费用都与实际一致，且状态都已为已审核或已对账，则恢复账单状态为未提交
					if(!"未提交".equals(freightStatement.getStatus())){
						freightStatement.setStatus("未提交");
						freightStatementDao.modify(freightStatement);
					}
					for(FreightExpense exp : freightExpenses){
						if("已审核".equals(exp.getStatus())){//审核通过自动变为已对账
							exp.setStatus("已对账");
							freightExpenseDao.modify(exp);
						}
					}
					
				}else{
					//只要有费用都与实际不一致，或者状态不为已审核，则恢复账单状态为异常锁定
					if(!"异常锁定".equals(freightStatement.getStatus())){
						freightStatement.setStatus("异常锁定");
						freightStatementDao.modify(freightStatement);
					}
				}
			}
		}
		
		//如果收付单位都映射到公司内部部门，则进行比对
		if(freightExpense.getFreightPartA().getOrgEntity() != null && freightExpense.getFreightPartB().getOrgEntity() != null){
			if(freightExpense.getFreightOrder() == null){
				compareInternalExpense(true, freightExpense.getFreightAction().getFreightMaintain().getFreightOrder(), 
						freightExpense.getFreightExpenseType(), freightExpense.getFreightPartA(), freightExpense.getFreightPartB());
			}else{
				compareInternalExpense(false, freightExpense.getFreightOrder(), 
						freightExpense.getFreightExpenseType(), freightExpense.getFreightPartA(), freightExpense.getFreightPartB());
			}
		}
	}

	

	@Override
	public boolean revisePredictCount(FreightExpense freightExpense) {
		//单价金额超过人民币100，美元15，则要求二级审核
		FreightExpense preFreightExpense = freightExpenseDao.getById(freightExpense.getId());
		double prePredictCount = preFreightExpense.getPredictCount();
		double predictCount = freightExpense.getPredictCount();
		if(("人民币".equals(preFreightExpense.getCurrency()) && Math.abs(prePredictCount - predictCount) >= 100) ||
				("美元".equals(preFreightExpense.getCurrency()) && Math.abs(prePredictCount - predictCount) >= 15)){
			freightExpense.setStatus("初审中(特殊异常)");//一般审核可见，审核之后会复审
		}else{
			freightExpense.setStatus("审核中(一般异常)");//仅一般审核
		}
		
		freightExpenseDao.modify(freightExpense);
		//更新一致性
		checkUnanimity(freightExpense.getId());
		return true;
	}

	@Override
	public boolean reviseActualCount(FreightExpense freightExpense) {
		double actualCount = freightExpense.getActualCount();
		double predictCount = freightExpense.getPredictCount();
		if(predictCount != actualCount){
			freightExpense.setStatus("异常费用");//异常费用时，业务可见。
		}
		freightExpenseDao.modify(freightExpense);
		//更新一致性，同一个事务中不能对同一个对象反复修改?。
		checkUnanimity(freightExpense.getId());
		return true;
	}

	@Override
	public boolean toAuditExpense(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			if("未提交".equals(freightExpense.getStatus()) || "已退回".equals(freightExpense.getStatus())){
				freightExpense.setStatus("审核中");
				freightExpense.setModifyTime(new Date());
				freightExpenseDao.modify(freightExpense);
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
	public boolean doneAuditExpense(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			if("审核中".equals(freightExpense.getStatus()) || 
					"审核中(一般异常)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("已审核");
				freightExpenseDao.modify(freightExpense);
			}else if("初审中(特殊异常)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("复审中(特殊异常)");
				freightExpenseDao.modify(freightExpense);
			}else if("初审中(特殊费用)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("复审中(特殊费用)");
				freightExpenseDao.modify(freightExpense);
			}else{
				flag = false;
				break;
			}
			
			if("已审核".equals(freightExpense.getStatus()) && freightExpense.getFreightStatement() != null){
				freightExpense.setStatus("已对账");
				freightExpenseDao.modify(freightExpense);
			}
			//checkUnanimity(freightExpenseId);//审核费用时也校验
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}
	
	@Override
	public boolean backAuditExpense(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			
			if("审核中".equals(freightExpense.getStatus())){
				freightExpense.setStatus("已退回");
				freightExpenseDao.modify(freightExpense);
			}else if("审核中(一般异常)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("异常费用");
				freightExpenseDao.modify(freightExpense);
			}else if("初审中(特殊异常)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("异常费用");
				freightExpenseDao.modify(freightExpense);
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
	public boolean doneRehearExpense(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			
			if("复审中(特殊异常)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("已审核");
				freightExpenseDao.modify(freightExpense);
			}else if("复审中(特殊费用)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("已审核");
				freightExpenseDao.modify(freightExpense);
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
	public boolean backRehearExpense(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			if("复审中(特殊异常)".equals(freightExpense.getStatus())){
				freightExpense.setStatus("初审中(特殊异常)");
				freightExpenseDao.modify(freightExpense);
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
	
	/*@Override
	public boolean reviseSpecialExpense(String freightExpenseId, double actualCount) {
		boolean flag = true;
		FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
		if("已审核".equals(freightExpense.getStatus()) && freightExpense.getFreightPrice() != null 
				&& (freightExpense.getFreightPrice().getActual() == null || "N".equals(freightExpense.getFreightPrice().getActual()))){
			FreightPrice freightPrice = freightExpense.getFreightPrice();
			freightPrice.setActual("T");
			freightPrice.setActualCount(actualCount);
			freightPrice.setModifyTime(new Date());
			freightPriceDao.modify(freightPrice);//修改关联的成本
		}else{
			flag = false;
		}
		return flag;
	}*/

	@Override
	public boolean createExpenseSpecial(String[] freightExpenseIds, User creator) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			if(freightExpense.getFreightOrder() != null 
					&& freightExpense.getFreightPrice() != null
					&& "付".equals(freightExpense.getIncomeOrExpense()) 
					&& "已对账".equals(freightExpense.getStatus()) 
					&& "T".equals(freightExpense.getFreightPrice().getActual())){
				//此处需要排除第二次生成特殊费用，特殊费用的标记和生成应当是费用已对账之后。收取单为费用相关单位。
				//实际少付则生成为收的特殊费用，实际多付则生成为付的特殊费用。
				FreightExpense specialExpense = new FreightExpense();
				String id = StringUtil.getUUID();
				specialExpense.setId(id);
				
				//specialExpense.setStatus("初审中(特殊费用)");
				specialExpense.setStatus("已审核");//生成特殊费用直接已审核，不再进行多次审核。
				specialExpense.setCurrency(freightExpense.getCurrency());
				specialExpense.setExchangeRate(freightExpense.getExchangeRate());
				double balance = freightExpense.getActualCount() - freightExpense.getFreightPrice().getActualCount();
				if(balance >= 0){//说明对账金额小于收取金额
					specialExpense.setIncomeOrExpense("收");
				}else{
					specialExpense.setIncomeOrExpense("付");
				}
				specialExpense.setPredictCount(Math.abs(balance));//取绝对值
				specialExpense.setActualCount(specialExpense.getPredictCount());
				specialExpense.setActual("T");
				specialExpense.setPrice("T");
				specialExpense.setCountUnit(freightExpense.getCountUnit());
				specialExpense.setCreator(creator);
				specialExpense.setOrgEntity(creator.getOrgEntity());
				
				specialExpense.setFreightPartA(freightPartDao.getByOrgEntityId(creator.getOrgEntity().getId()));
				specialExpense.setFreightPartB(freightExpense.getFreightPartB());
				specialExpense.setFreightExpenseType(freightExpense.getFreightExpenseType());
				specialExpense.setFasInvoiceType(freightExpense.getFasInvoiceType());
				specialExpense.setFreightAction(freightExpense.getFreightAction());
				if(!"票".equals(freightExpense.getCountUnit())){//如果不是按票，则同样的保持相应费用；
					List<FreightOrderBox> freightOrderBoxs = freightExpense.getFreightOrderBoxs();
					for(FreightOrderBox freightOrderBox : freightOrderBoxs){
						freightOrderBoxDao.addExpenseBox(id, freightOrderBox.getId());
					}
				}
				
				specialExpense.setExpenseNumber(getNextExpenseNumber(freightExpense.getFreightOrder().getId(), "TS"));
				specialExpense.setDescn(freightExpense.getId() + "," + freightExpense.getExpenseNumber());//将原费用ID和编号保存在特殊费用备注信息中
				freightExpenseDao.add(specialExpense);
				
				//给其成本进行标记，F表示已经生成了特殊费用。
				FreightPrice freightPrice = freightExpense.getFreightPrice();
				freightPrice.setActual("F");
				freightPriceDao.modify(freightPrice);
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
	public boolean doneRecallSpecial(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			
			if("已审核".equals(freightExpense.getStatus()) 
					&& freightExpense.getFreightPrice() != null 
					&& "T".equals(freightExpense.getFreightPrice().getActual())){
				//判断是否已经生成了新的特殊费用，如果已经生成了，则不能取消标记
				//String sql = "SELECT COUNT(1) FROM FRE_EXPENSE WHERE DESCN =?";
				//int count = jdbcTemplate.queryForObject(sql, Integer.class, freightExpense.getId() + "," + freightExpense.getExpenseNumber());
				
				FreightPrice freightPrice = freightExpense.getFreightPrice();
				freightPrice.setActual("N");//N表示取消特殊标记
				freightPrice.setActualCount(0);
				freightPriceDao.modify(freightPrice);
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
	public void doneRemoveExpense(String[] freightExpenseIds) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			if(freightExpense.getFreightStatement() != null){//已对账费用禁止删除
				flag = false;
				break;
			}
			
			String freightOrderId = null;
			if(freightExpense.getFreightOrder() != null){
				freightOrderId = freightExpense.getFreightOrder().getId();
			}else{
				freightOrderId = freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getId();
			}
			
			boolean canCompare = false;
			if(freightExpense.getFreightPartA().getOrgEntity() != null 
					&& freightExpense.getFreightPartB().getOrgEntity() != null){
				canCompare = true;
			}
			
			boolean isInternal = false;
			if(freightExpense.getFreightOrder() == null){
				isInternal = true;
			}
			
			if(canCompare){
				FreightOrder freightOrder = null;
				if(isInternal){
					freightOrder = freightExpense.getFreightAction().getFreightMaintain().getFreightOrder();
				}else{
					freightOrder = freightExpense.getFreightOrder();
				}
				FreightExpenseType freightExpenseType = freightExpense.getFreightExpenseType();
				FreightPart freightPartA = freightExpense.getFreightPartA();
				FreightPart freightPartB = freightExpense.getFreightPartB();
				freightExpenseDao.delete(freightExpenseId);
				compareInternalExpense(isInternal, freightOrder, freightExpenseType, freightPartA, freightPartB);
			}else{
				freightExpenseDao.delete(freightExpenseId);
			}
			freightOrderBoxDao.deleteExpenseBox(freightExpenseId);//删除关联
			
			//如果订单的所有费用都删除了，则恢复订单费用状态至未添加，以供复制费用
			String sql = "SELECT COUNT(1) FROM FRE_EXPENSE WHERE FRE_ORDER_ID=? OR (FRE_ORDER_ID IS NULL && FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?)))";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, freightOrderId, freightOrderId);
			if(count == 0){
				FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
				freightOrder.setExpenseStatus("未添加");
				freightOrderDao.modify(freightOrder);
			}else{
				FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
				freightOrder.setExpenseStatus("添加中");
				freightOrderDao.modify(freightOrder);
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	@Override
	public List<List<String>> toExport(List<FreightExpense> freightExpenses) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightExpense freightExpense : freightExpenses){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber());
			singleValue.add(freightExpense.getExpenseNumber());
			singleValue.add(freightExpense.getFreightExpenseType().getTypeName());
			singleValue.add(freightExpense.getIncomeOrExpense());
			singleValue.add(freightExpense.getFreightPartB().getPartName());
			singleValue.add(freightExpense.getCurrency());
			singleValue.add(freightExpense.getExchangeRate() + "");
			singleValue.add(freightExpense.getFasInvoiceType().getTypeName());
			singleValue.add(freightExpense.getCountUnit());
			singleValue.add(freightExpense.getPredictCount() + "");
			singleValue.add(freightExpense.getPredictAmount() + "");
			singleValue.add(freightExpense.getActualCount() + "");
			singleValue.add(freightExpense.getActualAmount() + "");
			singleValue.add(freightExpense.getTaxation() + "");
			singleValue.add(freightExpense.getStatus());
			
			values.add(singleValue);
		}
		return values;
	}

	@Override
	public Map<String, Object> toAddNormal(String freightOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", freightExpenseDao.getByFreightOrderId(freightOrderId));
		map.put("freightOrder", freightOrderDao.getById(freightOrderId));
		map.put("freightActions", freightActionDao.getByFreightOrderId(freightOrderId));
		map.put("freightExpenseTypes", freightExpenseTypeDao.getAll());
		map.put("fasInvoiceTypes", fasInvoiceTypeDao.getAll());
		map.put("freightOrderBoxs", freightOrderBoxDao.getByFreightOrderId(freightOrderId));
		return map;
	}

	@Override
	public Map<String, Object> toViewExpense(String freightOrderId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", freightExpenseDao.getByFreightOrderId(freightOrderId));
		map.put("freightOrder", freightOrderDao.getById(freightOrderId));
		return map;
	}

	@Override
	public List<List<String>> toExportByFreightStatementId(String freightStatementId) {
		List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightStatementId);
		List<List<String>> values = new ArrayList<List<String>>();
		String orderNo = null;
		double amountRBM = 0;
		double amountUSD = 0;
		
		for(FreightExpense freightExpense : freightExpenses){
			double actualAmount = 0;
			double actualCount = 0;
			if("付".equals(freightExpense.getIncomeOrExpense()) 
					&& freightExpense.getFreightPrice() != null && ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){
				actualCount = freightExpense.getFreightPrice().getActualCount();
				
				if("票".equals(freightExpense.getCountUnit())){
					actualAmount = freightExpense.getFreightPrice().getActualCount();
				}else{
					actualAmount = freightExpense.getFreightPrice().getActualCount() * freightExpense.getFreightOrderBoxs().size();
				}
			}else{
				actualAmount = freightExpense.getActualAmount();
				actualCount = freightExpense.getActualCount();
			}
			
			if(orderNo == null){
				orderNo = freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber().substring(0, 14);
				if("人民币".equals(freightExpense.getCurrency())){
					amountRBM = actualAmount;
				}else if("美元".equals(freightExpense.getCurrency())){
					amountUSD = actualAmount;
				}
			}else{
				if(orderNo.equals((freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber().substring(0, 14)))){
					if("人民币".equals(freightExpense.getCurrency())){
						amountRBM += actualAmount;
					}else if("美元".equals(freightExpense.getCurrency())){
						amountUSD += actualAmount;
					}
				}else{//应当新增一条合计
					List<String> singleValue = new ArrayList<String>();
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("小计：");
					singleValue.add("人民币");
					singleValue.add(String.valueOf(amountRBM));
					singleValue.add("美元");
					singleValue.add(String.valueOf(amountUSD));
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					values.add(singleValue);
					
					singleValue = new ArrayList<String>();
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					values.add(singleValue);
					
					//重新计算小计信息
					orderNo = freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber().substring(0, 14);
					amountRBM = 0;
					amountUSD = 0;
					if("人民币".equals(freightExpense.getCurrency())){
						amountRBM = actualAmount;
					}else if("美元".equals(freightExpense.getCurrency())){
						amountUSD = actualAmount;
					}
				}
			}
			
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber());
			singleValue.add(freightExpense.getExpenseNumber());
			singleValue.add(freightExpense.getFreightExpenseType().getTypeName());
			singleValue.add(freightExpense.getIncomeOrExpense());
			singleValue.add(freightExpense.getFreightPartB().getPartName());
			singleValue.add(freightExpense.getCurrency());
			singleValue.add(freightExpense.getExchangeRate() + "");
			singleValue.add(freightExpense.getFasInvoiceType().getTypeName());
			singleValue.add(freightExpense.getCountUnit());
			singleValue.add(freightExpense.getPredictCount() + "");
			singleValue.add(freightExpense.getPredictAmount() + "");
			singleValue.add(actualCount + "");
			singleValue.add(actualAmount + "");
			singleValue.add(freightExpense.getTaxation() + "");
			singleValue.add(freightExpense.getStatus());
			if("箱".equals(freightExpense.getCountUnit())){//箱型箱量箱属
				//singleValue.add(FreightCommonUtil.getFreightOrderBoxInfo(freightExpense.getFreightOrderBoxs()));
				String[] orderBoxInfo = FreightCommonUtil.getFreightOrderBoxSingle(freightExpense.getFreightOrderBoxs());
				singleValue.add(orderBoxInfo[0]);
				singleValue.add(orderBoxInfo[1]);
				singleValue.add(orderBoxInfo[2]);
			}else{
				singleValue.add("");
				singleValue.add("");
				singleValue.add("");
			}
			if("箱".equals(freightExpense.getCountUnit())){
				List<FreightOrderBox> freightOrderBoxs = freightExpense.getFreightOrderBoxs();
				StringBuilder text = new StringBuilder();
				for(FreightOrderBox freightOrderBox : freightOrderBoxs){
					if(freightOrderBox.getFreightBox() == null){
						text.append("未选箱,");
					}else{
						text.append(freightOrderBox.getFreightBox().getBoxNumber() + ",");
					}
				}
				
				singleValue.add(text.toString());
			}else{
				singleValue.add("");
			}
			if(freightExpense.getFreightOrder() != null){
				List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao.getByFreightOrderId(freightExpense.getFreightOrder().getId());
				StringBuilder text = new StringBuilder();
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
						text.append(freightBoxRequire.getBlNo() + ",");
					}
				}
				
				if(text.lastIndexOf(",") > 0){
					text.deleteCharAt(text.lastIndexOf(","));
					singleValue.add(text.toString());
				}else{
					singleValue.add(freightActionValueDao.getSingleValue(freightExpense.getFreightOrder().getId(), "TDH"));
				}
				
				singleValue.add(freightActionValueDao.getSingleValue(freightExpense.getFreightOrder().getId(), "MDG"));//目的港
			}else{
				singleValue.add("");
				singleValue.add("");
			}
			
			singleValue.add(freightExpense.getDescn());
			values.add(singleValue);
		}
		
		//最后一条需要补充
		List<String> singleValue = new ArrayList<String>();
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("小计：");
		singleValue.add("人民币");
		singleValue.add(String.valueOf(amountRBM));
		singleValue.add("美元");
		singleValue.add(String.valueOf(amountUSD));
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		values.add(singleValue);
		
		//账单数据
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		List<String> statementValue = new ArrayList<String>();
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("总计：");
		statementValue.add("人民币");
		statementValue.add(String.valueOf(freightStatement.getMoneyCountRmb()));
		statementValue.add("美元");
		statementValue.add(String.valueOf(freightStatement.getMoneyCountDollar()));
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		values.add(statementValue);
				
		return values;
	}

	
	@Override
	public List<List<String>> toExportByFreightStatementIdWithSplit(
			String freightStatementId) {
		List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightStatementId);
		List<List<String>> values = new ArrayList<List<String>>();
		
		String orderNo = null;
		double amountRBM = 0;
		double amountUSD = 0;
		
		for(FreightExpense freightExpense : freightExpenses){
			double actualAmount = 0;
			double actualCount = 0;
			if("付".equals(freightExpense.getIncomeOrExpense()) 
					&& freightExpense.getFreightPrice() != null && ("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){
				actualCount = freightExpense.getFreightPrice().getActualCount();
				
				if("票".equals(freightExpense.getCountUnit())){
					actualAmount = freightExpense.getFreightPrice().getActualCount();
				}else{
					actualAmount = freightExpense.getFreightPrice().getActualCount() * freightExpense.getFreightOrderBoxs().size();
				}
			}else{
				actualAmount = freightExpense.getActualAmount();
				actualCount = freightExpense.getActualCount();
			}
			
			if(orderNo == null){
				orderNo = freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber().substring(0, 14);
				if("人民币".equals(freightExpense.getCurrency())){
					amountRBM = actualAmount;
				}else if("美元".equals(freightExpense.getCurrency())){
					amountUSD = actualAmount;
				}
			}else{
				if(orderNo.equals((freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber().substring(0, 14)))){
					if("人民币".equals(freightExpense.getCurrency())){
						amountRBM += actualAmount;
					}else if("美元".equals(freightExpense.getCurrency())){
						amountUSD += actualAmount;
					}
				}else{//应当新增一条小计，暂取消
					/*List<String> singleValue = new ArrayList<String>();
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("小计：");
					singleValue.add("人民币");
					singleValue.add(String.valueOf(amountRBM));
					singleValue.add("美元");
					singleValue.add(String.valueOf(amountUSD));
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					values.add(singleValue);
					
					singleValue = new ArrayList<String>();
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					values.add(singleValue);*/
					
					//重新计算小计信息
					orderNo = freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber().substring(0, 14);
					amountRBM = 0;
					amountUSD = 0;
					if("人民币".equals(freightExpense.getCurrency())){
						amountRBM = actualAmount;
					}else if("美元".equals(freightExpense.getCurrency())){
						amountUSD = actualAmount;
					}
				}
			}
			
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightExpense.getFreightOrder() == null ? "内部费用" : freightExpense.getFreightOrder().getOrderNumber());
			singleValue.add(freightExpense.getExpenseNumber());
			singleValue.add(freightExpense.getFreightExpenseType().getTypeName());
			singleValue.add(freightExpense.getIncomeOrExpense());
			singleValue.add(freightExpense.getFreightPartB().getPartName());
			singleValue.add(freightExpense.getCurrency());
			singleValue.add(freightExpense.getExchangeRate() + "");
			singleValue.add(freightExpense.getFasInvoiceType().getTypeName());
			singleValue.add(freightExpense.getCountUnit());
			
			singleValue.add(freightExpense.getPredictCount() + "");
			singleValue.add(actualCount + "");
			if(freightExpense.getFreightOrder() != null){
				List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao.getByFreightOrderId(freightExpense.getFreightOrder().getId());
				StringBuilder text = new StringBuilder();
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
						text.append(freightBoxRequire.getBlNo() + ",");
					}
				}
				
				if(text.lastIndexOf(",") > 0){
					text.deleteCharAt(text.lastIndexOf(","));
					singleValue.add(text.toString());
				}else{
					singleValue.add(freightActionValueDao.getSingleValue(freightExpense.getFreightOrder().getId(), "TDH"));
				}
				singleValue.add(freightActionValueDao.getSingleValue(freightExpense.getFreightOrder().getId(), "MDG"));//目的港
			}else{
				singleValue.add("");
				singleValue.add("");
			}
			
			if("箱".equals(freightExpense.getCountUnit())){
				List<FreightOrderBox> freightOrderBoxs = freightExpense.getFreightOrderBoxs();
				for(FreightOrderBox freightOrderBox : freightOrderBoxs){
					if(freightOrderBox.getFreightBox() == null){
						if(singleValue.size() == 17){
							singleValue.remove(16);
							singleValue.remove(15);
							singleValue.remove(14);
							singleValue.remove(13);
						}
						singleValue.add("未选箱");
					}else{
						if(singleValue.size() == 17){
							singleValue.remove(16);
							singleValue.remove(15);
							singleValue.remove(14);
							singleValue.remove(13);
						}
						singleValue.add(freightOrderBox.getFreightBox().getBoxNumber());
					}
					//singleValue.add(freightOrderBox.getFreightBoxRequire().getBoxType() + "*1 " + freightOrderBox.getFreightBoxRequire().getBoxBelong());
					singleValue.add(freightOrderBox.getFreightBoxRequire().getBoxType());
					singleValue.add("1");
					singleValue.add(freightOrderBox.getFreightBoxRequire().getBoxBelong());
					singleValue.add(freightExpense.getDescn());
					//此处注意对象的堆栈意义
					List<String> tempList = new ArrayList<String>();
					tempList.addAll(singleValue);
					values.add(tempList);
				}
			}else{
				singleValue.add("");
				singleValue.add("");
				singleValue.add("");
				singleValue.add("");
				singleValue.add("");
				values.add(singleValue);
			}
		}
		
		//最后一条需要补充，暂取消
		/*List<String> singleValue = new ArrayList<String>();
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("小计：");
		singleValue.add("人民币");
		singleValue.add(String.valueOf(amountRBM));
		singleValue.add("美元");
		singleValue.add(String.valueOf(amountUSD));
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		values.add(singleValue);*/
		
		//账单数据
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		List<String> statementValue = new ArrayList<String>();
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("总计：");
		statementValue.add("人民币");
		statementValue.add(String.valueOf(freightStatement.getMoneyCountRmb()));
		statementValue.add("美元");
		statementValue.add(String.valueOf(freightStatement.getMoneyCountDollar()));
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		statementValue.add("");
		values.add(statementValue);
		return values;
	}
	
	@Override
	public List<List<String>> toExportByFilterText(
			FreightExpense freightExpense, String typeName, String partName,
			String orderNumber, String boxNumber, String TDH, String CMHC,
			String orderCreateTimeStart, String orderCreateTimeEnd) {
		PageView<FreightExpense> pageView = new PageView<FreightExpense>(freightExpenseDao.count(freightExpense), 1);
		//费用类型
		if(!StringUtil.isNullOrEmpty(typeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+typeName+"%')");
			}
		}
				
		//订单号
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+orderNumber+"%')");
			}
		}
		//单位名称
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID_B IN(SELECT P.ID FROM FRE_PART P WHERE P.PART_NAME LIKE '%"+partName+"%')");
			}
		}
		//箱号
		if(!StringUtil.isNullOrEmpty(boxNumber)){
			String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", boxNumber);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		//提单号
		if(!StringUtil.isNullOrEmpty(TDH)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "TDH", TDH);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//船名航次
		if(!StringUtil.isNullOrEmpty(CMHC)){
			String filterString = FreightFilterUtil.sqlFilterColumn("FRE_ORDER_ID", "CMHC", CMHC);
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(orderCreateTimeStart) && !StringUtil.isNullOrEmpty(orderCreateTimeEnd)){
			String filterString = " FRE_ORDER_ID IN (SELECT ID FROM FRE_ORDER WHERE CREATE_TIME BETWEEN '" + orderCreateTimeStart + "' AND '" + orderCreateTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		pageView.setOrder("ASC");
		pageView.setOrderBy("LEFT(EXPENSE_NUMBER,14) ASC, CURRENCY ASC, EXPENSE_NUMBER");
		if(StringUtil.isNullOrEmpty(freightExpense.getStatus())){
			freightExpense.setStatus("已审核");
		}
		
		List<FreightExpense>  freightExpenses = freightExpenseDao.query(pageView, freightExpense);
		List<List<String>> values = new ArrayList<List<String>>();
		String orderNo = null;
		double amountRBM = 0;
		double amountUSD = 0;
		for(FreightExpense item : freightExpenses){
			double actualAmount = 0;
			double actualCount = 0;
			if("付".equals(item.getIncomeOrExpense()) 
					&& freightExpense.getFreightPrice() != null && ("T".equals(item.getFreightPrice().getActual()) || "F".equals(item.getFreightPrice().getActual()))){
				actualCount = item.getFreightPrice().getActualCount();
				
				if("票".equals(item.getCountUnit())){
					actualAmount = item.getFreightPrice().getActualCount();
				}else{
					actualAmount = item.getFreightPrice().getActualCount() * item.getFreightOrderBoxs().size();
				}
			}else{
				actualAmount = item.getActualAmount();
				actualCount = item.getActualCount();
			}
			
			if(orderNo == null){
				orderNo = item.getFreightOrder() == null ? "内部费用" : item.getFreightOrder().getOrderNumber().substring(0, 14);
				if("人民币".equals(item.getCurrency())){
					amountRBM = actualAmount;
				}else if("美元".equals(item.getCurrency())){
					amountUSD = actualAmount;
				}
			}else{
				if(orderNo.equals((item.getFreightOrder() == null ? "内部费用" : item.getFreightOrder().getOrderNumber().substring(0, 14)))){
					if("人民币".equals(item.getCurrency())){
						amountRBM += actualAmount;
					}else if("美元".equals(item.getCurrency())){
						amountUSD += actualAmount;
					}
				}else{//应当新增一条合计
					List<String> singleValue = new ArrayList<String>();
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("小计：");
					singleValue.add("人民币");
					singleValue.add(String.valueOf(amountRBM));
					singleValue.add("美元");
					singleValue.add(String.valueOf(amountUSD));
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					values.add(singleValue);
					
					singleValue = new ArrayList<String>();
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					singleValue.add("");
					values.add(singleValue);
				}
			}
			
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(item.getFreightOrder() == null ? "内部费用" : item.getFreightOrder().getOrderNumber());
			singleValue.add(item.getExpenseNumber());
			singleValue.add(item.getFreightExpenseType().getTypeName());
			singleValue.add(item.getIncomeOrExpense());
			singleValue.add(item.getFreightPartB().getPartName());
			singleValue.add(item.getCurrency());
			singleValue.add(item.getExchangeRate() + "");
			singleValue.add(item.getFasInvoiceType().getTypeName());
			singleValue.add(item.getCountUnit());
			singleValue.add(item.getPredictCount() + "");
			singleValue.add(item.getPredictAmount() + "");
			singleValue.add(actualCount + "");
			singleValue.add(actualAmount + "");
			singleValue.add(item.getTaxation() + "");
			singleValue.add(item.getStatus());
			List<FreightOrderBox> freightOrderBoxs = item.getFreightOrderBoxs();//获取费用关联的箱封
			if("箱".equals(item.getCountUnit())){
				StringBuilder text = new StringBuilder();
				for(FreightOrderBox freightOrderBox : freightOrderBoxs){
					if(freightOrderBox.getFreightBox() == null){
						text.append("未选箱,");
					}else{
						text.append(freightOrderBox.getFreightBox().getBoxNumber() + ",");
					}
				}
				
				singleValue.add(text.toString());
			}else{
				singleValue.add("");
			}
			if(item.getFreightOrder() != null){
				List<FreightBoxRequire> freightBoxRequires = freightBoxRequireDao.getByFreightOrderId(item.getFreightOrder().getId());
				StringBuilder text = new StringBuilder();
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					if(!StringUtil.isNullOrEmpty(freightBoxRequire.getBlNo())){
						text.append(freightBoxRequire.getBlNo() + ",");
					}
				}
				
				if(text.lastIndexOf(",") > 0){
					text.deleteCharAt(text.lastIndexOf(","));
					singleValue.add(text.toString());
				}else{
					singleValue.add(freightActionValueDao.getSingleValue(item.getFreightOrder().getId(), "TDH"));
				}
			}else{
				singleValue.add("");
			}
			
			if(item.getFreightOrder() != null){
				singleValue.add(item.getFreightOrder().getCargoName());
			}else{
				//内部费用必然有动作
				singleValue.add(item.getFreightAction().getFreightMaintain().getFreightOrder().getCargoName());
			}
			
			if("箱".equals(item.getCountUnit())){
				StringBuilder text = new StringBuilder();
				Map<String, Integer> map = new HashMap<String, Integer>();
				for(FreightOrderBox freightOrderBox : freightOrderBoxs){
					if(map.get(freightOrderBox.getFreightBoxRequire().getBoxType()) == null){
						map.put(freightOrderBox.getFreightBoxRequire().getBoxType(), new Integer(1));
					}else{
						map.put(freightOrderBox.getFreightBoxRequire().getBoxType(), 
								new Integer(map.get(freightOrderBox.getFreightBoxRequire().getBoxType()) + 1));
					}
				}
				
				for(String key : map.keySet()){
					text.append(key + "*" + map.get(key) + " ");
				}
				singleValue.add(text.toString());
			}else{
				if(item.getFreightOrder() != null){
					StringBuilder text = new StringBuilder();
					for(FreightBoxRequire freightBoxRequire : 
						freightBoxRequireDao.getByFreightOrderId(item.getFreightOrder().getId())){
						text.append(freightBoxRequire.getBoxCount() + "*" + freightBoxRequire.getBoxType() + " " + freightBoxRequire.getBoxBelong() + " ");
					}
					singleValue.add(text.toString());
				}else{
					StringBuilder text = new StringBuilder();
					for(FreightBoxRequire freightBoxRequire : 
						freightBoxRequireDao.getByFreightOrderId(item.getFreightAction().getFreightMaintain().getFreightOrder().getId())){
						text.append(freightBoxRequire.getBoxCount() + "*" + freightBoxRequire.getBoxType() + " " + freightBoxRequire.getBoxBelong() + " ");
					}
					singleValue.add(text.toString());
				}
			}
			
			values.add(singleValue);
		}
		//最后一条需要补充
		List<String> singleValue = new ArrayList<String>();
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("小计：");
		singleValue.add("人民币");
		singleValue.add(String.valueOf(amountRBM));
		singleValue.add("美元");
		singleValue.add(String.valueOf(amountUSD));
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		values.add(singleValue);
		return values;
	}

	@Override
	public Map<String, Object> toAddInternal(String freightActionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightAction freightAction = freightActionDao.getById(freightActionId);
		FreightPart freightPartB = freightPartDao.getByOrgEntityId(freightAction.getFreightMaintain().getFreightOrder().getOrgEntityId());
		map.put("hasAddData", freightExpenseDao.getInternalByFreightActionId(freightActionId));
		map.put("freightPartB", freightPartB);//如果是费用是收，则只能通过此部门收取
		map.put("freightExpenseTypes", freightExpenseTypeDao.getAll());
		map.put("fasInvoiceTypes", fasInvoiceTypeDao.getAll());
		map.put("freightOrderBoxs", freightOrderBoxDao.getByFreightOrderId(freightAction.getFreightMaintain().getFreightOrder().getId()));
		return map;
	}

	@Override
	public Map<String, Object> toBatchSpecial(String[] freightExpenseIds) {
		boolean flag = true;
		StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM FRE_EXPENSE ");
		sql.append(" WHERE INCOME_OR_EXPENSE='付' AND STATUS='已审核' AND FRE_PRICE_ID IS NOT NULL ");
		sql.append(" AND EXISTS (SELECT 1 FROM (SELECT ID FROM FRE_PRICE WHERE ACTUAL IS NULL OR ACTUAL='N') AS T WHERE T.ID=FRE_PRICE_ID) ");
		sql.append(" AND ID IN( ");
		for(String freightExpenseId : freightExpenseIds){
			sql.append("'" + freightExpenseId + "',");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") GROUP BY INCOME_OR_EXPENSE, FRE_EXPENSE_TYPE_ID, FRE_PART_ID_B, FAS_INVOICE_TYPE_ID, PREDICT_COUNT LIMIT 1");
		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		if(count != freightExpenseIds.length){
			flag = false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", flag ? "success" : "error");
		map.put("freightExpense", freightExpenseDao.getById(freightExpenseIds[0]));
		
		return map;
	}

	@Override
	public boolean doneBatchSpecial(String[] freightExpenseIds, double actualCount) {
		boolean flag = true;
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			FreightPrice freightPrice = freightExpense.getFreightPrice();
			if(freightExpense.getFreightOrder() == null || !"已审核".equals(freightExpense.getStatus()) || freightPrice == null || "T".equals(freightPrice.getActual()) || "F".equals(freightPrice.getActual())){
				flag = false;
				break;
			}else{
				freightPrice.setActual("T");
				freightPrice.setActualCount(actualCount);
				freightPrice.setModifyTime(new Date());
				freightPriceDao.modify(freightPrice);//修改关联的成本
			}
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public List<List<String>> toExportDetails(String[] freightExpenseIds) {
		PageView<FreightExpense> pageView = new PageView<FreightExpense>(freightExpenseIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String freightExpenseId : freightExpenseIds){
			filterText.append("'" +freightExpenseId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		
		List<List<String>> values = new ArrayList<List<String>>();
		List<FreightExpense> freightExpenses = freightExpenseDao.query(pageView, new FreightExpense());
		for(FreightExpense item : freightExpenses){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(item.getFreightOrder() == null ? "内部费用" : item.getFreightOrder().getOrderNumber());
			singleValue.add(item.getExpenseNumber());
			singleValue.add(item.getFreightExpenseType().getTypeName());
			singleValue.add(item.getIncomeOrExpense());
			singleValue.add(item.getFreightPartB().getPartName());
			singleValue.add(item.getCurrency());
			singleValue.add(item.getExchangeRate() + "");
			singleValue.add(item.getFasInvoiceType().getTypeName());
			singleValue.add(item.getCountUnit());
			singleValue.add(item.getPredictCount() + "");
			singleValue.add(item.getPredictAmount() + "");
			singleValue.add(item.getActualCount() + "");
			singleValue.add(item.getActualAmount() + "");
			singleValue.add(item.getStatus());
			
			values.add(singleValue);
		}
		
		return values;
	}
	
}

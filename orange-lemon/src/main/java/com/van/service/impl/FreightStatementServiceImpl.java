package com.van.service.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
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
import com.van.halley.db.persistence.FasInvoiceDao;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.FreightPartDao;
import com.van.halley.db.persistence.FreightStatementDao;
import com.van.halley.db.persistence.FreightStatementOffsetDao;
import com.van.halley.db.persistence.entity.FasInvoice;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.FreightStatementOffset;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.service.FreightStatementService;

@Transactional
@Service("freightStatementService")
public class FreightStatementServiceImpl implements FreightStatementService {
	private static Logger logger = LoggerFactory.getLogger(FreightStatementServiceImpl.class);
	@Autowired
	private FreightStatementDao freightStatementDao;
	@Autowired
	private FasExchangeRateDao fasExchangeRateDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private FreightPartDao freightPartDao;
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;
	@Autowired
	private FasInvoiceDao fasInvoiceDao;
	@Autowired
	private FreightStatementOffsetDao freightStatementOffsetDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightStatement> getAll() {
		return freightStatementDao.getAll();
	}

	public List<FreightStatement> queryForList(FreightStatement freightStatement) {
		return freightStatementDao.queryForList(freightStatement);
	}

	public PageView<FreightStatement> query(PageView<FreightStatement> pageView, FreightStatement freightStatement) {
		List<FreightStatement> list = freightStatementDao.query(pageView,
				freightStatement);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightStatement freightStatement) {
		freightStatementDao.add(freightStatement);
	}

	public void delete(String id) {
		freightStatementDao.delete(id);
	}

	public void modify(FreightStatement freightStatement) {
		freightStatementDao.modify(freightStatement);
	}

	public FreightStatement getById(String id) {
		return freightStatementDao.getById(id);
	}

	/*
	 * 收付，按票还是按箱，币种，汇率等也需要计算（币种，汇率暂未算）
	 * 注意特殊费用的计算。在收款中不存在特殊费用的情况，在付款中存在。
	 * (non-Javadoc)
	 * @see com.van.service.FreightStatementService#calculate(java.lang.String)
	 */
	@Override
	public void calculateStatement(String freightStatementId) {
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightStatementId);
		
		double moneyCountRmb = 0;
		double moneyCountDollar = 0;
		for(FreightExpense freightExpense : freightExpenses){
			if(freightExpense.getCurrency().equals("人民币")){
				if(freightExpense.getCountUnit().equals("票")){
					if(freightExpense.getFreightPrice() != null && 
							("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
						moneyCountRmb += freightExpense.getFreightPrice().getActualCount();
					}else{
						moneyCountRmb += freightExpense.getActualAmount();
					}
				}else if(freightExpense.getCountUnit().equals("箱")){
					if(freightExpense.getFreightPrice() != null && 
							("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
						moneyCountRmb += freightExpense.getFreightPrice().getActualCount() * freightExpense.getFreightOrderBoxs().size();
					}else{
						moneyCountRmb += freightExpense.getActualAmount();
					}
				}
			}else if(freightExpense.getCurrency().equals("美元")){
				if(freightExpense.getCountUnit().equals("票")){
					if(freightExpense.getFreightPrice() != null && 
							("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
						moneyCountDollar += freightExpense.getFreightPrice().getActualCount();
					}else{
						moneyCountDollar += freightExpense.getActualAmount();
					}
				}else if(freightExpense.getCountUnit().equals("箱")){
					if(freightExpense.getFreightPrice() != null && 
							("T".equals(freightExpense.getFreightPrice().getActual()) || "F".equals(freightExpense.getFreightPrice().getActual()))){//如果是特殊费用，则按照对应成本中的价格进行计算
						moneyCountDollar += freightExpense.getFreightPrice().getActualCount() * freightExpense.getFreightOrderBoxs().size();
					}else{
						moneyCountDollar += freightExpense.getActualAmount();
					}
				}
			}
		}
		
		freightStatement.setMoneyCountRmb(moneyCountRmb);
		freightStatement.setRemainCountRmb(moneyCountRmb);
		freightStatement.setMoneyCountDollar(moneyCountDollar);
		freightStatement.setRemainCountDollar(moneyCountDollar);
		freightStatement.setModifyTime(new Date());
		freightStatementDao.modify(freightStatement);
	}

	@Override
	public boolean toIssueInvoice(String freightStatementId) {
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		if(freightStatement.getRemainCountRmb() == 0 && freightStatement.getRemainCountDollar() == 0){
			if("未提交".equals(freightStatement.getStatus()) || "财务退回".equals(freightStatement.getStatus())){
				freightStatement.setStatus("待开票");
				freightStatementDao.modify(freightStatement);
				List<FreightInvoice> freightInvoices = freightInvoiceDao.getByFreightStatementId(freightStatementId);
				for(FreightInvoice freightInvoice : freightInvoices){
					freightInvoice.setStatus("待开票");
					freightInvoiceDao.modify(freightInvoice);
				}
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public String getNextStatementNumberForExpense() {
		String sql = "SELECT CONCAT('FKD', DATE_FORMAT(SYSDATE(),'%Y%m%d'), REPEAT('0',7 - LENGTH(MAX(RIGHT(STATEMENT_NUMBER, 7)) + 1)),MAX(RIGHT(STATEMENT_NUMBER, 7)) + 1)"
					+ " FROM FRE_STATEMENT WHERE INCOME_OR_EXPENSE = '付'";
		String statementNumber  = (String)jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(statementNumber)){
			statementNumber = "FKD" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0000001";
		}
		return statementNumber;
	}

	@Override
	public String getNextStatementNumberForIncome() {
		String sql = "SELECT CONCAT('SKD', DATE_FORMAT(SYSDATE(),'%Y%m%d'), REPEAT('0',7 - LENGTH(MAX(RIGHT(STATEMENT_NUMBER, 7)) + 1)),MAX(RIGHT(STATEMENT_NUMBER, 7)) + 1)"
				+ " FROM FRE_STATEMENT WHERE INCOME_OR_EXPENSE = '收'";
		String statementNumber  = (String)jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(statementNumber)){
			statementNumber = "SKD" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0000001";
		}
		return statementNumber;
	}
	

	@Override
	public Map<String, Object> toAddOffset(String freightStatementId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		map.put("freightStatement", freightStatement);
		map.put("hasOffsetStatement", freightStatementDao.getHasOffsetStatement(freightStatementId));
		
		FreightStatement filter = new FreightStatement();
		filter.setStatus("未提交");
		filter.setFreightPart(freightStatement.getFreightPart());
		filter.setFasInvoiceType(freightStatement.getFasInvoiceType());
		if("收".equals(freightStatement.getIncomeOrExpense())){
			filter.setIncomeOrExpense("付");
		}else{
			filter.setIncomeOrExpense("收");
		}
		map.put("toOffsetStatement", freightStatementDao.queryForList(filter));
		return map;
	}
	
	@Override
	public boolean doneAddOffset(String sourceStatementId, String targetStatementId, String offsetType) {
		boolean flag = true;
		FreightStatement source = freightStatementDao.getById(sourceStatementId);
		FreightStatement target = freightStatementDao.getById(targetStatementId);
		//被冲抵账单只能是未提交
		if(("未提交".equals(source.getStatus()) || "冲抵过".equals(source.getStatus())) && "未提交".equals(target.getStatus())
				&& (source.getRemainCountRmb() > 0 || source.getRemainCountDollar() > 0)
				&& (target.getRemainCountRmb() > 0 || target.getRemainCountDollar() > 0)){
			FreightStatementOffset freightStatementOffset = new FreightStatementOffset();
			freightStatementOffset.setOffsetType(offsetType);
			freightStatementOffset.setFreightStatementIdA(sourceStatementId);
			freightStatementOffset.setFreightStatementIdB(targetStatementId);
			
			//同币种
			if("careCurrency".equals(offsetType)){
				//人民币
				if(source.getRemainCountRmb() > target.getRemainCountRmb()){
					source.setEliminateCountRmb(source.getEliminateCountRmb() + target.getRemainCountRmb());
					source.setRemainCountRmb(source.getMoneyCountRmb() - source.getEliminateCountRmb());
					freightStatementOffset.setEliminateCountRmbA(target.getRemainCountRmb());
					
					freightStatementOffset.setEliminateCountRmbB(target.getRemainCountRmb());
					target.setRemainCountRmb(0);
					target.setEliminateCountRmb(target.getMoneyCountRmb());
				}else{
					target.setEliminateCountRmb(target.getEliminateCountRmb() + source.getRemainCountRmb());
					target.setRemainCountRmb(target.getMoneyCountRmb() - target.getEliminateCountRmb());
					freightStatementOffset.setEliminateCountRmbB(source.getRemainCountRmb());
					
					freightStatementOffset.setEliminateCountRmbA(source.getRemainCountRmb());
					source.setRemainCountRmb(0);
					source.setEliminateCountRmb(source.getMoneyCountRmb());
				}
				//美元
				if(source.getRemainCountDollar() > target.getRemainCountDollar()){
					source.setEliminateCountDollar(source.getEliminateCountDollar() + target.getRemainCountDollar());
					source.setRemainCountDollar(source.getMoneyCountDollar() - source.getEliminateCountDollar());
					freightStatementOffset.setEliminateCountDollarA(target.getRemainCountDollar());
					
					freightStatementOffset.setEliminateCountDollarB(target.getRemainCountDollar());
					target.setRemainCountDollar(0);
					target.setEliminateCountDollar(target.getMoneyCountDollar());
				}else{
					target.setEliminateCountDollar(target.getEliminateCountDollar() + source.getRemainCountDollar());
					target.setRemainCountDollar(target.getMoneyCountDollar() - target.getEliminateCountDollar());
					freightStatementOffset.setEliminateCountDollarB(source.getRemainCountDollar());
					
					freightStatementOffset.setEliminateCountDollarA(source.getRemainCountDollar());
					source.setRemainCountDollar(0);
					source.setEliminateCountDollar(source.getMoneyCountDollar());
				}
				if(source.getEliminateCountRmb() == source.getMoneyCountRmb()
						&& source.getEliminateCountDollar() == source.getMoneyCountDollar()){
					source.setStatus("已冲抵销账");
				}else{
					source.setStatus("冲抵过");
				}
				
				if(target.getEliminateCountRmb() == target.getMoneyCountRmb()
						&& target.getEliminateCountDollar() == target.getMoneyCountDollar()){
					target.setStatus("已冲抵销账");
				}else{
					target.setStatus("冲抵过");
				}
				
				freightStatementDao.modify(source);
				freightStatementDao.modify(target);
				
				freightStatementOffsetDao.add(freightStatementOffset);
				//折合后的金额
			}else if("ignoreCurrency".equals(offsetType)){
				double sourceCount = source.getRemainCountRmb()*source.getExchangeRateRmb() 
						+ source.getRemainCountDollar() * source.getExchangeRateDollar();
				double targetCount = target.getRemainCountRmb()*target.getExchangeRateRmb() 
						+ target.getRemainCountDollar() * target.getExchangeRateDollar();
				//差额
				double blanceRtv = sourceCount - targetCount;
				//差额绝对值
				double blanceAbs = Math.abs(sourceCount - targetCount);
				if(blanceRtv > 0){
					//如果美元折后已经冲抵了所有费用人民币金额则不用动
					if(blanceAbs > source.getRemainCountRmb()){
						freightStatementOffset.setEliminateCountDollarA(targetCount / source.getExchangeRateDollar());
						freightStatementOffset.setEliminateCountRmbA(0);
						
						source.setRemainCountDollar(source.getRemainCountDollar() - targetCount / source.getExchangeRateDollar());
						source.setEliminateCountDollar(source.getMoneyCountDollar() - source.getRemainCountDollar());
					}else{//如果美元折后不够冲抵了所有费用，则最后保有人民币金额
						freightStatementOffset.setEliminateCountDollarA(source.getRemainCountDollar());
						source.setEliminateCountDollar(source.getMoneyCountDollar());
						source.setRemainCountDollar(0);
						
						freightStatementOffset.setEliminateCountRmbA(source.getRemainCountRmb() - blanceAbs);
						source.setRemainCountRmb(blanceAbs);
						source.setEliminateCountRmb(source.getMoneyCountRmb() - source.getRemainCountRmb());
					}
					source.setStatus("冲抵过");
					
					freightStatementOffset.setEliminateCountRmbB(target.getRemainCountRmb());
					freightStatementOffset.setEliminateCountDollarB(target.getRemainCountDollar());
					target.setStatus("已冲抵销账");
					target.setRemainCountRmb(0);
					target.setEliminateCountRmb(target.getMoneyCountRmb());
					target.setRemainCountDollar(0);
					target.setEliminateCountDollar(target.getMoneyCountDollar());
				}else{
					//如果美元折后已经冲抵了所有费用人民币金额则不用动
					if(blanceAbs > target.getRemainCountRmb()){
						freightStatementOffset.setEliminateCountDollarB(sourceCount / target.getExchangeRateDollar());
						freightStatementOffset.setEliminateCountRmbB(0);
						
						target.setRemainCountDollar(target.getRemainCountDollar() - sourceCount / target.getExchangeRateDollar());
						target.setEliminateCountDollar(target.getMoneyCountDollar() - target.getRemainCountDollar());
					}else{//如果美元折后不够冲抵了所有费用，则最后保有人民币金额
						freightStatementOffset.setEliminateCountDollarB(target.getRemainCountDollar());
						target.setEliminateCountDollar(target.getMoneyCountDollar());
						target.setRemainCountDollar(0);
						
						freightStatementOffset.setEliminateCountRmbB(target.getRemainCountRmb() - blanceAbs);
						target.setRemainCountRmb(blanceAbs);
						target.setEliminateCountRmb(target.getMoneyCountRmb() - target.getRemainCountRmb());
					}
					target.setStatus("冲抵过");
					
					freightStatementOffset.setEliminateCountRmbA(source.getRemainCountRmb());
					freightStatementOffset.setEliminateCountDollarA(source.getRemainCountDollar());
					source.setStatus("已冲抵销账");
					source.setRemainCountRmb(0);
					source.setEliminateCountRmb(source.getMoneyCountRmb());
					source.setRemainCountDollar(0);
					source.setEliminateCountDollar(source.getMoneyCountDollar());
				}
				
				freightStatementDao.modify(source);
				freightStatementDao.modify(target);
				
				
				freightStatementOffsetDao.add(freightStatementOffset);
			}else{
				flag = false;
				logger.error("冲抵取消，请确认冲抵类型。");
			}
		}else{
			flag = false;
			logger.error("冲抵取消，请确认状态和金额。");
		}
		return flag;
	}
	
	@Override
	public boolean doneRemoveOffset(String sourceStatementId, String targetStatementId) {
		boolean flag = true;
		FreightStatementOffset filter = new FreightStatementOffset();
		filter.setFreightStatementIdA(sourceStatementId);
		filter.setFreightStatementIdB(targetStatementId);
		FreightStatementOffset freightStatementOffset = freightStatementOffsetDao.queryForOne(filter);
		if(freightStatementOffset != null){
			FreightStatement freightStatementA = freightStatementDao.getById(sourceStatementId);
			FreightStatement freightStatementB = freightStatementDao.getById(targetStatementId);
			
			FreightInvoice filterInvoice = new FreightInvoice();//取消冲抵之前，保证账单都无开票
			filterInvoice.setFreightStatement(freightStatementA);
			if(freightInvoiceDao.count(filterInvoice) > 0){
				logger.error("取消冲抵失败，有开票未取消，账单ID：{}", freightStatementA.getId());
				flag = false;
			}else{
				filterInvoice.setFreightStatement(freightStatementB);
				if(freightInvoiceDao.count(filterInvoice) > 0){
					logger.error("取消冲抵失败，有开票未取消，账单ID：{}", freightStatementB.getId());
					flag = false;
				}
			}
			
			if(flag){
				freightStatementA.setRemainCountRmb(freightStatementA.getRemainCountRmb() + freightStatementOffset.getEliminateCountRmbA());
				freightStatementA.setEliminateCountRmb(freightStatementA.getMoneyCountRmb() - freightStatementA.getRemainCountRmb());
				freightStatementA.setRemainCountDollar(freightStatementA.getRemainCountDollar() + freightStatementOffset.getEliminateCountDollarA());
				freightStatementA.setEliminateCountDollar(freightStatementA.getMoneyCountDollar() - freightStatementA.getRemainCountDollar());
				
				freightStatementB.setRemainCountRmb(freightStatementB.getRemainCountRmb() + freightStatementOffset.getEliminateCountRmbB());
				freightStatementB.setEliminateCountRmb(freightStatementB.getMoneyCountRmb() - freightStatementB.getRemainCountRmb());
				freightStatementB.setRemainCountDollar(freightStatementB.getRemainCountDollar() + freightStatementOffset.getEliminateCountDollarB());
				freightStatementB.setEliminateCountDollar(freightStatementB.getMoneyCountDollar() - freightStatementB.getRemainCountDollar());
				//如果所有的金额都被取消冲抵了，则状态恢复至未提交
				if(freightStatementA.getRemainCountRmb() == freightStatementA.getMoneyCountRmb()
						&& freightStatementA.getRemainCountDollar() == freightStatementA.getMoneyCountDollar()){
					freightStatementA.setStatus("未提交");
				}
				if(freightStatementB.getRemainCountRmb() == freightStatementB.getMoneyCountRmb()
						&& freightStatementB.getRemainCountDollar() == freightStatementB.getMoneyCountDollar()){
					freightStatementB.setStatus("未提交");
				}
				freightStatementDao.modify(freightStatementA);
				freightStatementDao.modify(freightStatementB);
				freightStatementOffsetDao.delete(freightStatementOffset.getId());
			}
		}else{
			flag = false;
			logger.error("取消账单冲抵失败，账单ID: {}, {}", sourceStatementId, targetStatementId);
		}
		return flag;
	}

	@Override
	public void doneAddStatement(FreightStatement freightStatement, String freightPartId, String fasInvoiceTypeId, User creator) {
		freightStatement.setFreightPart(freightPartDao.getById(freightPartId));
		freightStatement.setFasInvoiceType(fasInvoiceTypeDao.getById(fasInvoiceTypeId));
		
		if(StringUtil.isNullOrEmpty(freightStatement.getStatementNumber())){
			if("收".equals(freightStatement.getIncomeOrExpense())){
				freightStatement.setStatementNumber(getNextStatementNumberForIncome());
			}else{
				freightStatement.setStatementNumber(getNextStatementNumberForExpense());
			}
		}
		freightStatement.setCurrencyRmb("人民币");
		freightStatement.setExchangeRateRmb(fasExchangeRateDao.getExchangeRate("人民币", null));
		
		freightStatement.setCurrencyDollar("美元");
		freightStatement.setExchangeRateDollar(fasExchangeRateDao.getExchangeRate("美元", null));
		
		freightStatement.setStatus("未提交");
		freightStatement.setCreator(creator);
		freightStatement.setOrgEntity(creator.getOrgEntity());
		freightStatementDao.add(freightStatement);
	}

	@Override
	public boolean doneInvalidStatement(String[] freightStatementIds) {
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			if(!"未提交".equals(freightStatement.getStatus())){//再次对账单状态进行排查
				continue;
			}
			FreightExpense filterExpense = new FreightExpense();
			filterExpense.setFreightStatement(freightStatement);
			List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(filterExpense);
			for(FreightExpense freightExpense : freightExpenses){
				//只对已对账状态的费用改变状态，因为同时还可能存在异常费用？包含异常费用的账单应当是锁定的，也无法作废才对。
				//FIXME
				if("已对账".equals(freightExpense.getStatus())){
					freightExpense.setStatus("已审核");
					FreightStatement freightStatementNew = new FreightStatement();
					freightStatementNew.setId("A");
					freightExpense.setFreightStatement(freightStatementNew);
					freightExpenseDao.modify(freightExpense);
				}
			}
			//如果是付款中
			if("付".equals(freightStatement.getIncomeOrExpense())){
				FreightInvoice filterInvoice = new FreightInvoice();
				filterInvoice.setFreightStatement(freightStatement);
				List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filterInvoice);
				for(FreightInvoice freightInvoice : freightInvoices){
					//对于与付款账单关联的税务发票直接删除。
					FasInvoice fasInvoice = freightInvoice.getFasInvoice();
					if(fasInvoice != null){
						fasInvoiceDao.delete(fasInvoice.getId());
					}
					//对于开票信息也一起删除
					freightInvoiceDao.delete(freightInvoice.getId());
				}
			}
			
			freightStatement.setStatus("已作废");
			freightStatement.setMoneyCountRmb(0);
			freightStatement.setMoneyCountDollar(0);
			freightStatement.setRemainCountRmb(0);
			freightStatement.setRemainCountDollar(0);
			freightStatement.setEliminateCountRmb(0);
			freightStatement.setEliminateCountDollar(0);
			freightStatementDao.modify(freightStatement);
		}
		return true;
	}

	@Override
	public boolean doneRemoveStatement(String[] freightStatementIds) {
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			if(!"未提交".equals(freightStatement.getStatus())){//再次对账单状态进行排查
				continue;
			}
			FreightExpense filterExpense = new FreightExpense();
			filterExpense.setFreightStatement(freightStatement);
			List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(filterExpense);
			for(FreightExpense freightExpense : freightExpenses){
				//只对已对账状态的费用改变状态，因为同时还可能存在异常费用？包含异常费用的账单应当是锁定的，也无法作废才对。
				//FIXME
				if("已对账".equals(freightExpense.getStatus())){
					freightExpense.setStatus("已审核");
					FreightStatement freightStatementNew = new FreightStatement();
					freightStatementNew.setId("A");
					freightExpense.setFreightStatement(freightStatementNew);
					freightExpenseDao.modify(freightExpense);
				}
			}
			//如果是付款中
			if("付".equals(freightStatement.getIncomeOrExpense())){
				FreightInvoice filterInvoice = new FreightInvoice();
				filterInvoice.setFreightStatement(freightStatement);
				List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filterInvoice);
				for(FreightInvoice freightInvoice : freightInvoices){
					//对于与付款账单关联的税务发票直接删除。
					FasInvoice fasInvoice = freightInvoice.getFasInvoice();
					if(fasInvoice != null){
						fasInvoiceDao.delete(fasInvoice.getId());
					}
					//对于开票信息也一起删除
					freightInvoiceDao.delete(freightInvoice.getId());
				}
			}
			freightStatementDao.delete(freightStatementId);
		}
		return true;
	}

	@Override
	public void toPayStatement(String freightStatementId, String descn, Date releaseTime) {
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		FreightInvoice filter = new FreightInvoice();
		filter.setFreightStatement(freightStatement);
		List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filter);
		for(FreightInvoice freightInvoice : freightInvoices){
			freightInvoice.setStatus("付款初审中");
			freightInvoice.setReleaseTime(releaseTime);
			freightInvoice.setDescn(freightInvoice.getDescn() + ";" + descn);
			freightInvoiceDao.modify(freightInvoice);
		}
		
		freightStatement.setStatus("付款审核中");
		freightStatementDao.modify(freightStatement);
	}

	@Override
	public List<FreightStatement> getHasOffsetStatement(
			String freightStatementId) {
		return freightStatementDao.getHasOffsetStatement(freightStatementId);
	}

	@Override
	public boolean doneStatementAudit(String[] freightStatementIds) {
		boolean flag = true;
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			String status = freightStatement.getStatus();
			if(status.equals("审核中") || status.equals("锁定中")){
				freightStatement.setStatus("已审核");
				freightStatementDao.modify(freightStatement);
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
	public boolean backStatementAudit(String[] freightStatementIds) {
		boolean flag = true;
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			String status = freightStatement.getStatus();
			if(status.equals("审核中") || status.equals("锁定中")){
				freightStatement.setStatus("未提交");
				freightStatementDao.modify(freightStatement);
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
	public boolean toStatementAudit(String[] freightStatementIds) {
		boolean flag = true;
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			String status = freightStatement.getStatus();
			if(status.equals("未提交") || status.equals("已退回")){
				freightStatement.setStatus("审核中");
				freightStatementDao.modify(freightStatement);
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
	public Map<String, Object> toAddExpense(String freightStatementId,
			String FYMC, String DDH, String PM, String WTDW, String XH, String XS, String TDH, String CMHC, String NB) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		map.put("freightStatement", freightStatement);
		
		FreightExpense filter = new FreightExpense();
		filter.setIncomeOrExpense(freightStatement.getIncomeOrExpense());
		filter.setFreightPartB(freightStatement.getFreightPart());
		filter.setFasInvoiceType(freightStatement.getFasInvoiceType());
		filter.setStatus("已审核");
		int totalCount = freightExpenseDao.count(filter);
		if(totalCount == 0){
			map.put("toAddData", Collections.EMPTY_LIST);
			map.put("hasAddData", freightExpenseDao.getByFreightStatementId(freightStatementId));
			return map;
		}
		PageView<FreightExpense> pageView = new PageView<FreightExpense>(1);
		if(totalCount > 50){//可添加费用每次最多加载100条
			pageView.setPageSize(50);
		}else{
			pageView.setPageSize(totalCount);
		}
		
		//是否为内部费用
		if(!StringUtil.isNullOrEmpty(NB)){
			String filterText = null;
			if("T".equals(NB)){
				filterText = " (FRE_ORDER_ID IS NULL OR FRE_ORDER_ID='A') ";
			}else{
				filterText = " (FRE_ORDER_ID IS NOT NULL) ";
			}
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		//费用类型
		if(!StringUtil.isNullOrEmpty(FYMC)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+FYMC+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+FYMC+"%')");
			}
		}
		
		//订单号
		if(!StringUtil.isNullOrEmpty(DDH)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" EXPENSE_NUMBER LIKE '%" + DDH + "%' ");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND EXPENSE_NUMBER LIKE '%" + DDH + "%' ");
			}
		}
				
		//品名
		if(!StringUtil.isNullOrEmpty(PM)){
			String filterText = "FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.CARGO_NAME LIKE '%" + PM + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		//委托单位
		if(!StringUtil.isNullOrEmpty(WTDW)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.DELEGATE_PART LIKE '%"+WTDW+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.DELEGATE_PART LIKE '%"+WTDW+"%')");
			}
		}
		//箱号
		if(!StringUtil.isNullOrEmpty(XH)){
			//String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", XH);//修改为具体的费用集装箱关联
			String filterString = "EXISTS (SELECT 1 FROM (SELECT FRE_EXPENSE_ID FROM FRE_EXPENSE_BOX AS EB LEFT JOIN FRE_ORDER_BOX AS OB ON EB.FRE_ORDER_BOX_ID=OB.ID "
								+ "LEFT JOIN FRE_BOX AS B ON OB.FRE_BOX_ID=B.ID WHERE B.BOX_NUMBER='" + XH + "') AS T WHERE T.FRE_EXPENSE_ID=ID) ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		//箱属
		if(!StringUtil.isNullOrEmpty(XS)){
			String filterString = FreightFilterUtil.sqlFilterBelong("FRE_ORDER_ID", XS);
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
		
		pageView.setOrder("ASC");
		pageView.setOrderBy("LEFT(EXPENSE_NUMBER,14) ASC, CURRENCY ASC, EXPENSE_NUMBER");
		
		map.put("toAddData", freightExpenseDao.query(pageView, filter));
		map.put("hasAddData", freightExpenseDao.getByFreightStatementId(freightStatementId));
		return map;
	}
	
	
	@Override
	public Map<String, Object> toAddExpenseFilter(String freightStatementId,
			String FYMC, String DDH, String XH, String TDH, String CMHC) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		map.put("freightStatement", freightStatement);
		
		FreightExpense filter = new FreightExpense();
		filter.setIncomeOrExpense(freightStatement.getIncomeOrExpense());
		filter.setFreightPartB(freightStatement.getFreightPart());
		filter.setFasInvoiceType(freightStatement.getFasInvoiceType());
		filter.setStatus("已审核");
		filter.setCreator(freightStatement.getCreator());//只取属于自己的费用
		int totalCount = freightExpenseDao.count(filter);
		if(totalCount == 0){
			map.put("toAddData", Collections.EMPTY_LIST);
			map.put("hasAddData", freightExpenseDao.getByFreightStatementId(freightStatementId));
			return map;
		}
		PageView<FreightExpense> pageView = new PageView<FreightExpense>(1);
		pageView.setPageSize(totalCount);
		
		//费用类型
		if(!StringUtil.isNullOrEmpty(FYMC)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+FYMC+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT T.ID FROM FRE_EXPENSE_TYPE T WHERE T.TYPE_NAME LIKE '%"+FYMC+"%')");
			}
		}
				
		//订单号
		if(!StringUtil.isNullOrEmpty(DDH)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+DDH+"%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_ORDER_ID IN(SELECT O.ID FROM FRE_ORDER O WHERE O.ORDER_NUMBER LIKE '%"+DDH+"%')");
			}
		}
		//箱号
		if(!StringUtil.isNullOrEmpty(XH)){
			String filterString = FreightFilterUtil.sqlFilterNumber("FRE_ORDER_ID", XH);
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
		
		pageView.setOrder("ASC");
		pageView.setOrderBy("LEFT(EXPENSE_NUMBER,14) ASC, CURRENCY ASC, EXPENSE_NUMBER");
		
		map.put("toAddData", freightExpenseDao.query(pageView, filter));
		map.put("hasAddData", freightExpenseDao.getByFreightStatementId(freightStatementId));
		
		return map;
	}

	@Override
	public void doneAddExpense(String freightStatementId, String[] freightExpenseIds) {
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			freightExpense.setStatus("已对账");
			freightExpense.setFreightStatement(freightStatement);
			freightExpenseDao.modify(freightExpense);
		}
		if(!"未提交".equals(freightStatement.getStatus())){
			freightStatement.setStatus("未提交");
		}
		calculateStatement(freightStatementId);
	}

	@Override
	public void doneDeleteExpense(String[] freightExpenseIds) {
		String freightStatementId = freightExpenseDao.getById(freightExpenseIds[0]).getFreightStatement().getId();
		for(String freightExpenseId : freightExpenseIds){
			FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
			FreightStatement freightStatement = new FreightStatement();
			freightStatement.setId("A");
			freightExpense.setFreightStatement(freightStatement);
			freightExpense.setStatus("已审核");
			freightExpenseDao.modify(freightExpense);
		}
		
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		if(!"未提交".equals(freightStatement.getStatus())){
			freightStatement.setStatus("未提交");
		}
		calculateStatement(freightStatementId);
	}

	@Override
	public boolean doneRecallStatement(String[] freightStatementIds) {
		boolean flag = true;
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			String status = freightStatement.getStatus();
			FreightInvoice filter = new FreightInvoice();
			filter.setFreightStatement(freightStatement);
			int count = freightInvoiceDao.count(filter);
			if(status.equals("已审核") && count == 0){//状态为已审核，且无开票
				freightStatement.setStatus("未提交");
				freightStatementDao.modify(freightStatement);
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
	public boolean backInvoiceStatement(String[] freightStatementIds) {
		boolean flag = true;
		for(String freightStatementId : freightStatementIds){
			FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
			if(!"收".equals(freightStatement.getIncomeOrExpense()) || 
					(!"待开票".equals(freightStatement.getStatus()) && !"已开票".equals(freightStatement.getStatus()))){//此处退回只针对收款账单
				flag = false;
				break;
			}
			
			String sql = "SELECT COUNT(1) FROM FRE_INVOICE WHERE FRE_STATEMENT_ID=? AND STATUS != '未提交' AND STATUS != '待开票' AND STATUS != '已开票' AND STATUS != '待销账'";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, freightStatement.getId());
			if(count > 0){//如果其关联的开票有已经销账了的，不能退回
				flag = false;
				break;
			}
			
			FreightInvoice filterInvoice = new FreightInvoice();
			filterInvoice.setFreightStatement(freightStatement);
			List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filterInvoice);
			if(freightInvoices != null && !freightInvoices.isEmpty()){
				for(FreightInvoice freightInvoice : freightInvoices){
					FasInvoice fasInvoice = freightInvoice.getFasInvoice();
					if(fasInvoice != null){//如果已经开票，则税务发票作废
						fasInvoice.setStatus("已作废");
						fasInvoice.setDescn(fasInvoice.getDescn() + "; 作废账单: " + freightStatement.getStatementNumber() + "时，同时作废税务发票"); 
						fasInvoiceDao.modify(fasInvoice);
					}
					
					if("人民币".equals(freightInvoice.getCurrency())){//将开票金额退回到账单
						freightStatement.setRemainCountRmb(freightStatement.getRemainCountRmb() + freightInvoice.getMoneyCount());
						freightStatement.setEliminateCountRmb(freightStatement.getMoneyCountRmb() - freightStatement.getRemainCountRmb());
					}else if("美元".equals(freightInvoice.getCurrency())){
						freightStatement.setRemainCountDollar(freightStatement.getRemainCountDollar() + freightInvoice.getMoneyCount());
						freightStatement.setEliminateCountDollar(freightStatement.getMoneyCountDollar() - freightStatement.getRemainCountDollar());
					}
					freightInvoiceDao.delete(freightInvoice.getId());//删除开票任务
				}
			}
			
			if(freightStatement.getMoneyCountRmb() > freightStatement.getRemainCountRmb()//如果未销账金额不等于金额，则判断是有冲抵
					|| freightStatement.getMoneyCountDollar() > freightStatement.getRemainCountDollar()){
				freightStatement.setStatus("冲抵过");
			}else{
				freightStatement.setStatus("未提交");
			}
			freightStatementDao.modify(freightStatement);
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}

	@Override
	public boolean doneReviseExpense(String freightExpenseId, double actualCount) {
		FreightExpense freightExpense = freightExpenseDao.getById(freightExpenseId);
		if("已审核".equals(freightExpense.getStatus())){
			freightExpense.setActualCount(actualCount);
			double predictCount = freightExpense.getPredictCount();
			if(predictCount != actualCount){
				freightExpense.setStatus("异常费用");//异常费用时，业务可见。
			}
			freightExpenseDao.modify(freightExpense);
			return true;
		}else{
			return false;
		}
	}
}

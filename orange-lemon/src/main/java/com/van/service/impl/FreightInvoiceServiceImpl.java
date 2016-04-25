package com.van.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.van.halley.db.persistence.FasAccountDao;
import com.van.halley.db.persistence.FasExchangeRateDao;
import com.van.halley.db.persistence.FasInvoiceDao;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.FasPayDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.FreightInvoiceOffsetDao;
import com.van.halley.db.persistence.FreightNetDayDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightStatementDao;
import com.van.halley.db.persistence.entity.FasAccount;
import com.van.halley.db.persistence.entity.FasInvoice;
import com.van.halley.db.persistence.entity.FasPay;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;
import com.van.halley.db.persistence.entity.FreightNetDay;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.FasInvoiceService;
import com.van.service.FreightInvoiceService;

@Transactional
@Service("freightInvoiceService")
public class FreightInvoiceServiceImpl implements FreightInvoiceService {
	private static Logger logger = LoggerFactory.getLogger(FreightInvoiceServiceImpl.class);
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private FasInvoiceDao fasInvoiceDao;
	@Autowired
	private FasExchangeRateDao fasExchangeRateDao;
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;
	@Autowired
	private FreightStatementDao freightStatementDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FasInvoiceService fasInvoiceService;
	@Autowired
	private FreightInvoiceOffsetDao freightInvoiceOffsetDao;
	@Autowired
	private FreightNetDayDao freightNetDayDao;
	@Autowired
	private FasAccountDao fasAccountDao;
	@Autowired
	private FasPayDao fasPayDao;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightInvoice> getAll() {
		return freightInvoiceDao.getAll();
	}

	public List<FreightInvoice> queryForList(FreightInvoice freightInvoice) {
		return freightInvoiceDao.queryForList(freightInvoice);
	}

	public PageView<FreightInvoice> query(PageView<FreightInvoice> pageView, FreightInvoice freightInvoice) {
		List<FreightInvoice> list = freightInvoiceDao.query(pageView,
				freightInvoice);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightInvoice freightInvoice) {
		freightInvoiceDao.add(freightInvoice);
	}

	public void delete(String id) {
		freightInvoiceDao.delete(id);
	}

	public void modify(FreightInvoice freightInvoice) {
		freightInvoiceDao.modify(freightInvoice);
	}

	public FreightInvoice getById(String id) {
		return freightInvoiceDao.getById(id);
	}

	@Override
	public boolean doneAddInvoice(FreightInvoice freightInvoice, String freightStatementId, String fasInvoiceTypeId) {
		boolean flag = true;
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		if(("人民币".equals(freightInvoice.getCurrency()) && freightStatement.getRemainCountRmb() >= freightInvoice.getMoneyCount())
				|| ("美元".equals(freightInvoice.getCurrency()) && freightStatement.getRemainCountDollar() >= freightInvoice.getMoneyCount())){
			freightInvoice.setFreightStatement(freightStatement);
			freightInvoice.setFreightPart(freightStatement.getFreightPart());
			freightInvoice.setFasInvoiceType(fasInvoiceTypeDao.getById(fasInvoiceTypeId));
			freightInvoice.setIncomeOrExpense(freightStatement.getIncomeOrExpense());
			if(freightInvoice.getCurrency().equals("人民币")){
				freightStatement.setEliminateCountRmb(freightStatement.getEliminateCountRmb() + freightInvoice.getMoneyCount());
				freightStatement.setRemainCountRmb(freightStatement.getMoneyCountRmb() - freightStatement.getEliminateCountRmb());
			}else if(freightInvoice.getCurrency().equals("美元")){
				freightStatement.setEliminateCountDollar(freightStatement.getEliminateCountDollar() + freightInvoice.getMoneyCount());
				freightStatement.setRemainCountDollar(freightStatement.getMoneyCountDollar() - freightStatement.getEliminateCountDollar());
			}
			freightInvoice.setIncomeOrExpense(freightStatement.getIncomeOrExpense());
			freightInvoice.setRemainCount(freightInvoice.getMoneyCount());//设置未销账金额
			freightInvoice.setEliminateCount(0);
			//freightInvoice.setExchangeRate(FasExchangeRateService.cacheFasExchangeRate.get(freightInvoice.getCurrency()));
			if(freightInvoice.getExchangeRate() == 0){
				freightInvoice.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightInvoice.getCurrency(), null));
			}
			freightInvoice.setStatus("未提交");//先设置状态为未提交，等到全部开票完成之后所有开票任务再变为待开票进入税务开票环节。
			freightInvoiceDao.add(freightInvoice);
			freightStatementDao.modify(freightStatement);
			//如果已经全部分配完毕，则修改开票任务状态为待开票
			if(freightStatement.getRemainCountRmb() == 0 && freightStatement.getRemainCountDollar() == 0){
				FreightInvoice filter = new FreightInvoice();
				filter.setFreightStatement(freightStatement);
				List<FreightInvoice> frieghtInvoices = freightInvoiceDao.queryForList(filter);
				for(FreightInvoice invoice : frieghtInvoices){
					invoice.setStatus("待开票");
					freightInvoiceDao.modify(invoice);
				}
			}
		}else{
			flag = false;
		}
		
		return flag;
	}

	@Override
	public boolean doneDeleteInvoice(String[] freightInvoiceIds) {
		boolean flag = true;
		FreightStatement freightStatement = null;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if(freightStatement == null){
				freightStatement = freightInvoiceDao.getById(freightInvoiceId).getFreightStatement();
			}
			if(freightInvoice.getCurrency().equals("人民币")){
				freightStatement.setRemainCountRmb(freightStatement.getRemainCountRmb() + freightInvoice.getMoneyCount());
				freightStatement.setEliminateCountRmb(freightStatement.getMoneyCountRmb() - freightStatement.getRemainCountRmb());
			}else if(freightInvoice.getCurrency().equals("美元")){
				freightStatement.setRemainCountDollar(freightStatement.getRemainCountDollar() + freightInvoice.getMoneyCount());
				freightStatement.setEliminateCountDollar(freightStatement.getMoneyCountDollar() - freightStatement.getRemainCountDollar());
			}
			
			freightInvoiceDao.delete(freightInvoiceId);
		}
		freightStatementDao.modify(freightStatement);
		//全部开票
		if(freightStatement.getRemainCountRmb() == 0 && freightStatement.getRemainCountDollar() == 0){
			FreightInvoice filter = new FreightInvoice();
			filter.setFreightStatement(freightStatement);
			List<FreightInvoice> frieghtInvoices = freightInvoiceDao.queryForList(filter);
			for(FreightInvoice invoice : frieghtInvoices){
				invoice.setStatus("待开票");
				freightInvoiceDao.modify(invoice);
			}
		}else{
			FreightInvoice filter = new FreightInvoice();
			filter.setFreightStatement(freightStatement);
			List<FreightInvoice> frieghtInvoices = freightInvoiceDao.queryForList(filter);
			for(FreightInvoice invoice : frieghtInvoices){
				invoice.setStatus("未提交");
				freightInvoiceDao.modify(invoice);
			}
		}
		
		return flag;
	}

	@Override
	public int batchReleaseInvoice(String[] freightInvoiceIds) {
		int count = 0;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("待开票".equals(freightInvoice.getStatus()) && freightInvoice.getFasInvoice() == null){
				FasInvoice filter = new FasInvoice();
				filter.setFasInvoiceType(freightInvoice.getFasInvoiceType());
				filter.setStatus("可用");
				FasInvoice fasInvoice = fasInvoiceDao.getFasInvoiceProximate(filter);
				if(fasInvoice != null){
					fasInvoice.setIncomeOrExpense(freightInvoice.getIncomeOrExpense());
					fasInvoice.setStatus("已用");
					fasInvoice.setReleaseTime(new Date());
					fasInvoiceDao.modify(fasInvoice);
					//添加账期
					FreightNetDay filterNetDay = new FreightNetDay();
					filterNetDay.setFreightPart(freightInvoice.getFreightPart());
					filterNetDay.setCurrency(freightInvoice.getCurrency());
					filterNetDay.setIncomeOrExpense(freightInvoice.getIncomeOrExpense());
					FreightNetDay freightNetDay = freightNetDayDao.queryForOne(filterNetDay);
					Date effectTime = null;
					if(freightNetDay == null){
						logger.error("查询不到该公司的 账期：{}，默认设置账期为1个月", freightInvoice.getFreightPart().getPartName());
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DAY_OF_YEAR, 30);
						effectTime = cal.getTime();
					}else{
						if("T".equals(freightNetDay.getRegular())){//固定账期
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.MONTH, freightNetDay.getDelayMonth());
							cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), freightNetDay.getRegularDay());
							effectTime = cal.getTime();
						}else{
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DAY_OF_YEAR, freightNetDay.getPeriod());
							effectTime = cal.getTime();
						}
					}
					freightInvoice.setEffectTime(effectTime);
					freightInvoice.setReleaseTime(new Date());
					freightInvoice.setFasInvoice(fasInvoice);
					freightInvoice.setStatus("已开票");
					freightInvoiceDao.modify(freightInvoice);
					count++;
				}
				
				FreightStatement freightStatement = freightInvoice.getFreightStatement();
				if("待开票".equals(freightStatement.getStatus()) || "开票中".equals(freightStatement.getStatus())){
					//查看该账单涉及的所有开票是否都已开具，如果是，状态未已开票，如果否，状态未开票中
					FreightInvoice filterInvoice = new FreightInvoice();
					filterInvoice.setFreightStatement(freightStatement);
					filterInvoice.setStatus("待开票");
					if(freightInvoiceDao.count(filterInvoice) == 0){
						freightStatement.setStatus("已开票");
						freightStatementDao.modify(freightStatement);
					}else{
						if("待开票".equals(freightStatement.getStatus())){
							freightStatement.setStatus("开票中");
							freightStatementDao.modify(freightStatement);
						}
					}
				}
			}else{
				//没有了税务发票之后直接返回
				return count;
			}
		}
		return count;
	}

	@Override
	public boolean singleReleaseInvoice(String freightInvoiceId, String fasInvoiceId) {
		FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
		if("待开票".equals(freightInvoice.getStatus()) && freightInvoice.getFasInvoice() == null){
			FasInvoice fasInvoice = fasInvoiceDao.getById(fasInvoiceId);
			if("可用".equals(fasInvoice.getStatus())){
				fasInvoice.setStatus("已用");
				fasInvoice.setReleaseTime(new Date());
				fasInvoiceDao.modify(fasInvoice);
				//添加账期
				FreightNetDay filterNetDay = new FreightNetDay();
				filterNetDay.setFreightPart(freightInvoice.getFreightPart());
				filterNetDay.setCurrency(freightInvoice.getCurrency());
				filterNetDay.setIncomeOrExpense(freightInvoice.getIncomeOrExpense());
				FreightNetDay freightNetDay = freightNetDayDao.queryForOne(filterNetDay);
				Date effectTime = null;
				if(freightNetDay == null){
					logger.error("查询不到该公司的 账期：{}，默认设置账期为1个月", freightInvoice.getFreightPart().getPartName());
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_YEAR, 30);
					effectTime = cal.getTime();
				}else{
					if("T".equals(freightNetDay.getRegular())){//固定账期
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.MONTH, freightNetDay.getDelayMonth());
						cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), freightNetDay.getRegularDay());
						effectTime = cal.getTime();
					}else{
						Calendar cal = Calendar.getInstance();
						cal.add(Calendar.DAY_OF_YEAR, freightNetDay.getPeriod());
						effectTime = cal.getTime();
					}
				}
				freightInvoice.setEffectTime(effectTime);
				freightInvoice.setReleaseTime(new Date());
				freightInvoice.setFasInvoice(fasInvoice);
				freightInvoice.setStatus("已开票");
				freightInvoiceDao.modify(freightInvoice);
				
				FreightStatement freightStatement = freightInvoice.getFreightStatement();
				if("待开票".equals(freightStatement.getStatus()) || "开票中".equals(freightStatement.getStatus())){
					//查看该账单涉及的所有开票是否都已开具，如果是，状态未已开票，如果否，状态未开票中
					FreightInvoice filterInvoice = new FreightInvoice();
					filterInvoice.setFreightStatement(freightStatement);
					filterInvoice.setStatus("待开票");
					if(freightInvoiceDao.count(filterInvoice) == 0){
						freightStatement.setStatus("已开票");
						freightStatementDao.modify(freightStatement);
					}else{
						if("待开票".equals(freightStatement.getStatus())){
							freightStatement.setStatus("开票中");
							freightStatementDao.modify(freightStatement);
						}
					}
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
	public Map<String, Object> toOffsetInvoice(String freightInvoiceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
		map.put("freightInvoice", freightInvoice);
		if(freightInvoice.getFreightStatement() == null){//~对非正常产生的开票的限制
			return map;
		}
		map.put("hasOffsetInvoice", freightInvoiceDao.getHasOffsetInvoice(freightInvoiceId));
		FreightInvoice filter = new FreightInvoice();
		filter.setStatus("待销账");
		filter.setFreightPart(freightInvoice.getFreightPart());
		if("收".equals(freightInvoice.getIncomeOrExpense())){
			filter.setIncomeOrExpense("付");
		}else{
			filter.setIncomeOrExpense("收");
		}
		
		PageView<FreightInvoice> pageView = new PageView<FreightInvoice>(1, freightInvoiceDao.count(filter));
		pageView.setFilterText(" FRE_STATEMENT_ID IS NOT NULL ");//~对非正常产生的开票的限制
		map.put("toOffsetInvoice", freightInvoiceDao.query(pageView, filter));
		
		return map;
	}

	@Override
	public boolean offsetInvoice(String sourceInvoiceId, String targetInvoiceId, String offsetType) {
		FreightInvoice source = freightInvoiceDao.getById(sourceInvoiceId);
		FreightInvoice target = freightInvoiceDao.getById(targetInvoiceId);
		
		FreightInvoiceOffset freightInvoiceOffset = new FreightInvoiceOffset();
		freightInvoiceOffset.setOffsetType(offsetType);
		freightInvoiceOffset.setFreightInvoiceIdA(sourceInvoiceId);
		freightInvoiceOffset.setFreightInvoiceIdB(targetInvoiceId);
		
		double remainCountSource = source.getRemainCount();
		double remainCountTarget = target.getRemainCount();
		
		boolean flag = true;
		if(remainCountSource <= 0 || remainCountTarget <= 0){
			logger.error("未销账金额不正确， remainCountSource {}， remainCountTarget {}", remainCountSource, remainCountTarget);
			flag = false;
		}else{
			if(!source.getIncomeOrExpense().equals(target.getIncomeOrExpense())){
				if("careCurrency".equals(offsetType)){
					if(source.getCurrency().equals(target.getCurrency())){
						if(remainCountSource > remainCountTarget){
							if("人民币".equals(source.getCurrency())){
								freightInvoiceOffset.setEliminateCountRmbA(remainCountTarget);
								freightInvoiceOffset.setEliminateCountRmbB(remainCountTarget);
							}else if("美元".equals(source.getCurrency())){
								freightInvoiceOffset.setEliminateCountDollarA(remainCountTarget);
								freightInvoiceOffset.setEliminateCountDollarB(remainCountTarget);
							}
							
							source.setRemainCount(remainCountSource - remainCountTarget);
							source.setEliminateCount(source.getMoneyCount() - source.getRemainCount());
							if(source.getRemainCount() == 0){
								source.setStatus("已冲抵销账");
							}else{
								source.setStatus("冲抵过");
							}
							
							target.setRemainCount(0);
							target.setEliminateCount(target.getMoneyCount());
							target.setStatus("已冲抵销账");
						}else{
							if("人民币".equals(source.getCurrency())){
								freightInvoiceOffset.setEliminateCountRmbA(remainCountSource);
								freightInvoiceOffset.setEliminateCountRmbB(remainCountSource);
							}else if("美元".equals(source.getCurrency())){
								freightInvoiceOffset.setEliminateCountDollarA(remainCountSource);
								freightInvoiceOffset.setEliminateCountDollarB(remainCountSource);
							}
							
							target.setRemainCount(remainCountTarget - remainCountSource);
							target.setEliminateCount(target.getMoneyCount() - target.getRemainCount());
							if(target.getRemainCount() == 0){
								target.setStatus("已冲抵销账");
							}else{
								target.setStatus("冲抵过");
							}
							
							source.setRemainCount(0);
							source.setEliminateCount(source.getMoneyCount());
							source.setStatus("已冲抵销账");
						}
						
						freightInvoiceDao.modify(source);
						freightInvoiceDao.modify(target);
						
						freightInvoiceOffsetDao.add(freightInvoiceOffset);
					}else{
						logger.error("冲抵方式与币种不一致。");
						flag = false;
					}
				}else if("ignoreCurrency".equals(offsetType)){
					double sourceCount = remainCountSource * source.getExchangeRate();
					double targetCount = remainCountTarget * target.getExchangeRate();
					if(sourceCount > targetCount){
						if("人民币".equals(source.getCurrency())){
							freightInvoiceOffset.setEliminateCountRmbA(targetCount);
						}else if("美元".equals(source.getCurrency())){
							freightInvoiceOffset.setEliminateCountDollarA(targetCount/source.getExchangeRate());
						}
						
						if("人民币".equals(target.getCurrency())){
							freightInvoiceOffset.setEliminateCountRmbB(targetCount);
						}else if("美元".equals(target.getCurrency())){
							freightInvoiceOffset.setEliminateCountDollarB(targetCount/target.getExchangeRate());
						}
						
						source.setRemainCount((sourceCount - targetCount)/source.getExchangeRate());
						source.setEliminateCount(source.getMoneyCount() - source.getRemainCount());
						if(source.getRemainCount() == 0){
							source.setStatus("已冲抵销账");
						}else{
							source.setStatus("冲抵过");
						}
						
						target.setRemainCount(0);
						target.setEliminateCount(target.getMoneyCount());
						target.setStatus("已冲抵销账");
					}else{
						if("人民币".equals(source.getCurrency())){
							freightInvoiceOffset.setEliminateCountRmbA(sourceCount);
						}else if("美元".equals(source.getCurrency())){
							freightInvoiceOffset.setEliminateCountDollarA(sourceCount/source.getExchangeRate());
						}
						
						if("人民币".equals(target.getCurrency())){
							freightInvoiceOffset.setEliminateCountRmbB(sourceCount);
						}else if("美元".equals(target.getCurrency())){
							freightInvoiceOffset.setEliminateCountDollarB(sourceCount/target.getExchangeRate());
						}
						
						target.setRemainCount((targetCount - sourceCount)/target.getExchangeRate());
						target.setEliminateCount(target.getMoneyCount() - target.getRemainCount());
						if(target.getRemainCount() == 0){
							target.setStatus("已冲抵销账");
						}else{
							target.setStatus("冲抵过");
						}
						
						source.setRemainCount(0);
						source.setEliminateCount(source.getMoneyCount());
						source.setStatus("已冲抵销账");
					}
					
					freightInvoiceDao.modify(source);
					freightInvoiceDao.modify(target);
					
					freightInvoiceOffsetDao.add(freightInvoiceOffset);
				}else{
					logger.error("错误的冲抵方式。");
					flag = false;
				}
			}else{
				logger.error("同收同付发票无法冲抵。");
				flag = false;
			}
		}
		return flag;
	}

	@Override
	public void deleteInvoiceOffset(String sourceInvoiceId, String targetInvoiceId) {
		FreightInvoiceOffset filter = new FreightInvoiceOffset();
		filter.setFreightInvoiceIdA(sourceInvoiceId);
		filter.setFreightInvoiceIdB(targetInvoiceId);
		FreightInvoiceOffset freightInvoiceOffset = freightInvoiceOffsetDao.queryForOne(filter);
		if(freightInvoiceOffset != null){
			FreightInvoice freightInvoiceA = freightInvoiceDao.getById(sourceInvoiceId);
			FreightInvoice freightInvoiceB = freightInvoiceDao.getById(targetInvoiceId);
			if("人民币".equals(freightInvoiceA.getCurrency())){
				freightInvoiceA.setRemainCount(freightInvoiceA.getRemainCount() + freightInvoiceOffset.getEliminateCountRmbA());
				freightInvoiceA.setEliminateCount(freightInvoiceA.getMoneyCount() - freightInvoiceA.getRemainCount());
			}else if("美元".equals(freightInvoiceA.getCurrency())){
				freightInvoiceA.setRemainCount(freightInvoiceA.getRemainCount() + freightInvoiceOffset.getEliminateCountDollarA());
				freightInvoiceA.setEliminateCount(freightInvoiceA.getMoneyCount() - freightInvoiceA.getRemainCount());
			}
			
			if("人民币".equals(freightInvoiceB.getCurrency())){
				freightInvoiceB.setRemainCount(freightInvoiceB.getRemainCount() + freightInvoiceOffset.getEliminateCountRmbB());
				freightInvoiceB.setEliminateCount(freightInvoiceB.getMoneyCount() - freightInvoiceB.getRemainCount());
			}else if("美元".equals(freightInvoiceB.getCurrency())){
				freightInvoiceB.setRemainCount(freightInvoiceB.getRemainCount() + freightInvoiceOffset.getEliminateCountDollarB());
				freightInvoiceB.setEliminateCount(freightInvoiceB.getMoneyCount() - freightInvoiceB.getRemainCount());
			}
			//修改状态
			//FIXME TODO
			if(freightInvoiceA.getRemainCount() == freightInvoiceA.getMoneyCount()){
				freightInvoiceA.setStatus("待销账");
			}
			
			if(freightInvoiceB.getRemainCount() == freightInvoiceB.getMoneyCount()){
				freightInvoiceB.setStatus("待销账");
			}
			
			freightInvoiceDao.modify(freightInvoiceA);
			freightInvoiceDao.modify(freightInvoiceB);
			
			freightInvoiceOffsetDao.delete(freightInvoiceOffset.getId());
		}else{
			logger.error("取消冲抵失败，对应开票ID：{}， {}", sourceInvoiceId, targetInvoiceId);
		}
		
	}
	
	@Override
	public boolean toInvoiceReconcile(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("已开票".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("待销账");
				freightInvoiceDao.modify(freightInvoice);
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
	public boolean toInvoiceRelease(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("待销账".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("已开票");
				freightInvoiceDao.modify(freightInvoice);
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
	public boolean invalidInvoice(String[] freightInvoiceIds) {
		boolean isRollback = false;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightStatement freightStatement = freightInvoiceDao.getById(freightInvoiceId).getFreightStatement();
			//作废其他开票
			FreightInvoice filter = new FreightInvoice();
			filter.setFreightStatement(freightStatement);
			List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filter);
			for(FreightInvoice freightInvoice : freightInvoices){
				//已销账金额大于0，或还未到待销账状态，则作废失败。
				if(freightInvoice.getEliminateCount() > 0 || !"待销账".equals(freightInvoice.getStatus())){
					isRollback = true;
					break;
				}else{
					//作废税务发票
					FasInvoice fasInvoice = freightInvoice.getFasInvoice();
					fasInvoice.setStatus("已作废");
					fasInvoiceDao.modify(fasInvoice);
					
					freightInvoice.setStatus("已作废");
					freightInvoice.setMoneyCount(0);
					freightInvoice.setRemainCount(0);
					freightInvoice.setEliminateCount(0);
					freightInvoiceDao.modify(freightInvoice);
				}
			}
			
			//作废费用
			FreightExpense filterExpense = new FreightExpense();
			filterExpense.setFreightStatement(freightStatement);
			List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(filterExpense);
			for(FreightExpense freightExpense : freightExpenses){
				freightExpense.setStatus("未提交");
				FreightStatement freightStatementNew = new FreightStatement();
				freightStatementNew.setId("A");
				freightExpense.setFreightStatement(freightStatementNew);
				freightExpenseDao.modify(freightExpense);
			}
			//作废账单
			freightStatement.setStatus("已作废");
			freightStatement.setMoneyCountDollar(0);
			freightStatement.setEliminateCountDollar(0);
			freightStatement.setRemainCountDollar(0);
			
			freightStatement.setMoneyCountRmb(0);
			freightStatement.setEliminateCountRmb(0);
			freightStatement.setRemainCountRmb(0);
			freightStatementDao.modify(freightStatement);
		}
		
		if(isRollback){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean doneAuditInvoicePay(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("付款初审中".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("付款复审中");
				freightInvoiceDao.modify(freightInvoice);
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
	public boolean doneRehearInvoicePay(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("付款复审中".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("付款终审中");
				freightInvoiceDao.modify(freightInvoice);
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
	public boolean doneEventideInvoicePay(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("付款终审中".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("待销账");
				freightInvoiceDao.modify(freightInvoice);
				//终审完成时，检查账单关联的所有开票是否都完成终审了，如果是则账单状态为已开票
				FreightStatement freightStatement = freightInvoice.getFreightStatement();
				FreightInvoice filter = new FreightInvoice();
				filter.setFreightStatement(freightStatement);
				List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filter);
				boolean isAllHasDone = true;
				for(FreightInvoice item : freightInvoices){
					if(!"待销账".equals(item.getStatus())){
						isAllHasDone = false;
					}
				}
				if(isAllHasDone){
					freightStatement.setStatus("已开票");
					freightStatementDao.modify(freightStatement);
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
	public boolean backAuditInvoicePay(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("付款初审中".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("未提交");
				freightInvoiceDao.modify(freightInvoice);
				//退回时，检查账单关联的所有开票是否都是退回了，如果是则账单状态恢复到已审核或审核过
				FreightStatement freightStatement = freightInvoice.getFreightStatement();
				FreightInvoice filter = new FreightInvoice();
				filter.setFreightStatement(freightStatement);
				List<FreightInvoice> freightInvoices = freightInvoiceDao.queryForList(filter);
				boolean isAllHasBack = true;
				double moneyCountRmb = 0;
				double moneyCountDollar = 0;
				for(FreightInvoice item : freightInvoices){
					if(!"未提交".equals(item.getStatus())){
						isAllHasBack = false;
					}
					if("人民币".equals(item.getCurrency())){
						moneyCountRmb += item.getMoneyCount();
					}else if("美元".equals(item.getCurrency())){
						moneyCountDollar += item.getMoneyCount();
					}
				}
				//如果总金额相等，则说明无冲抵，否则必然有冲抵
				if(isAllHasBack){
					if(freightStatement.getMoneyCountRmb() == moneyCountRmb && freightStatement.getMoneyCountDollar() == moneyCountDollar){
						freightStatement.setStatus("已审核");
						freightStatementDao.modify(freightStatement);
					}else{
						freightStatement.setStatus("冲抵过");
						freightStatementDao.modify(freightStatement);
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
	public boolean backRehearInvoicePay(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("付款复审中".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("付款初审中");
				freightInvoiceDao.modify(freightInvoice);
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
	public boolean backEventideInvoicePay(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("付款终审中".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("付款复审中");
				freightInvoiceDao.modify(freightInvoice);
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
	public boolean doneAddNumber(FreightInvoice freightInvoice, String freightStatementId, String fasInvoiceNumber){
		boolean flag = true;
		FreightStatement freightStatement = freightStatementDao.getById(freightStatementId);
		if(("人民币".equals(freightInvoice.getCurrency()) && freightStatement.getRemainCountRmb() >= freightInvoice.getMoneyCount())
				|| ("美元".equals(freightInvoice.getCurrency()) && freightStatement.getRemainCountDollar() >= freightInvoice.getMoneyCount())){
			
			if(freightInvoice.getCurrency().equals("人民币")){
				freightStatement.setEliminateCountRmb(freightStatement.getEliminateCountRmb() + freightInvoice.getMoneyCount());
				freightStatement.setRemainCountRmb(freightStatement.getMoneyCountRmb() - freightStatement.getEliminateCountRmb());
			}else if(freightInvoice.getCurrency().equals("美元")){
				freightStatement.setEliminateCountDollar(freightStatement.getEliminateCountDollar() + freightInvoice.getMoneyCount());
				freightStatement.setRemainCountDollar(freightStatement.getMoneyCountDollar() - freightStatement.getEliminateCountDollar());
			}
			freightStatementDao.modify(freightStatement);
			
			freightInvoice.setFreightStatement(freightStatement);
			freightInvoice.setFreightPart(freightStatement.getFreightPart());
			freightInvoice.setFasInvoiceType(freightStatement.getFasInvoiceType());
			freightInvoice.setIncomeOrExpense(freightStatement.getIncomeOrExpense());
			freightInvoice.setRemainCount(freightInvoice.getMoneyCount());//设置未销账金额
			freightInvoice.setEliminateCount(0);
			if(freightInvoice.getExchangeRate() == 0){
				freightInvoice.setExchangeRate(fasExchangeRateDao.getExchangeRate(freightInvoice.getCurrency(), null));
			}
			
			freightInvoice.setStatus("已开票");
			
			//如果发票号为空，则取虚拟发票号
			FasInvoice fasInvoice = new FasInvoice();
			if(StringUtil.isNullOrEmpty(fasInvoiceNumber)){
				fasInvoice.setInvoiceNumber(fasInvoiceService.getNextVirtualInvoiceNumber());
			}else{
				fasInvoice.setInvoiceNumber(fasInvoiceNumber);
			}
			String fasInvoiceId = StringUtil.getUUID();
			fasInvoice.setId(fasInvoiceId);
			fasInvoice.setFasInvoiceType(freightInvoice.getFasInvoiceType());
			fasInvoice.setIncomeOrExpense(freightInvoice.getIncomeOrExpense());
			fasInvoice.setStatus("已用");
			fasInvoice.setReleaseTime(new Date());
			fasInvoiceDao.add(fasInvoice);
			freightInvoice.setFasInvoice(fasInvoice);//设置税务发票
			//添加账期
			FreightNetDay filterNetDay = new FreightNetDay();
			filterNetDay.setFreightPart(freightInvoice.getFreightPart());
			filterNetDay.setCurrency(freightInvoice.getCurrency());
			filterNetDay.setIncomeOrExpense(freightInvoice.getIncomeOrExpense());
			FreightNetDay freightNetDay = freightNetDayDao.queryForOne(filterNetDay);
			Date effectTime = null;
			if(freightNetDay == null){
				logger.error("查询不到该公司的 账期：{}，默认设置账期为1个月", freightInvoice.getFreightPart().getPartName());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_YEAR, 30);
				effectTime = cal.getTime();
			}else{
				if("T".equals(freightNetDay.getRegular())){//固定账期
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MONTH, freightNetDay.getDelayMonth());
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), freightNetDay.getRegularDay());
					effectTime = cal.getTime();
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_YEAR, freightNetDay.getPeriod());
					effectTime = cal.getTime();
				}
			}
			
			freightInvoice.setEffectTime(effectTime);
			freightInvoice.setReleaseTime(new Date());
			freightInvoiceDao.add(freightInvoice);
		}else{
			flag = false;
		}
		
		return flag;
	}

	@Override
	public void doneDeleteNumber(String[] freightInvoiceIds) {
		FreightStatement freightStatement = null;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if(freightStatement == null){
				freightStatement = freightInvoice.getFreightStatement();
			}
			
			if(freightInvoice.getCurrency().equals("人民币")){
				freightStatement.setRemainCountRmb(freightStatement.getRemainCountRmb() + freightInvoice.getMoneyCount());
				freightStatement.setEliminateCountRmb(freightStatement.getMoneyCountRmb() - freightStatement.getRemainCountRmb());
			}else if(freightInvoice.getCurrency().equals("美元")){
				freightStatement.setRemainCountDollar(freightStatement.getRemainCountDollar() + freightInvoice.getMoneyCount());
				freightStatement.setEliminateCountDollar(freightStatement.getMoneyCountDollar() - freightStatement.getRemainCountDollar());
			}
			
			fasInvoiceDao.delete(freightInvoice.getFasInvoice().getId());//删除关联的税务发票。因为是付款，直接删除
			freightInvoiceDao.delete(freightInvoiceId);
		}
		
		freightStatementDao.modify(freightStatement);
	}

	@Override
	public List<FreightInvoice> getHasOffsetInvoice(String freightInvoiceId) {
		return freightInvoiceDao.getHasOffsetInvoice(freightInvoiceId);
	}

	@Override
	public void reviseFasInvoice(FreightInvoice freightInvoice, String fasInvoiceId) {
		FasInvoice preFasInvoice = freightInvoiceDao.getById(freightInvoice.getId()).getFasInvoice();
		preFasInvoice.setDescn(freightInvoice.getDescn() + ", 原开票ID：" + freightInvoice.getId());
		preFasInvoice.setStatus("已作废");
		fasInvoiceDao.modify(preFasInvoice);
		//更新为新的税务发票
		FasInvoice fasInvoice = fasInvoiceDao.getById(fasInvoiceId);
		freightInvoice.setFasInvoice(fasInvoice);
		freightInvoiceDao.modify(freightInvoice);
		
		fasInvoice.setStatus("已用");
		fasInvoiceDao.modify(fasInvoice);
	}

	@Override
	public FasPay toInvoicePay(String[] freightInvoiceIds, User proposer) {
		try{
			FasAccount fasAccountRmb = null;
			FasAccount fasAccountDollar = null;
			FreightPart freightPart = null;
			double moneyCountRmb = 0;
			double moneyCountDollar = 0;
			
			StringBuilder relatedOrderNumber = new StringBuilder();
			String fasPayId = StringUtil.getUUID();
			for(String freightInvoiceId : freightInvoiceIds){
				FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
				
				List<FreightOrder> freightOrders = freightOrderDao
						.getByFreightStatementId(freightInvoice.getFreightStatement().getId());
				if(freightOrders != null && !freightOrders.isEmpty()){
					for(FreightOrder freightOrder : freightOrders){
						relatedOrderNumber.append(freightOrder.getOrderNumber() + ",");
					}
				}
				
				//即便销过账页可继续销账
				if("待销账".equals(freightInvoice.getStatus()) || "销账中".equals(freightInvoice.getStatus())){
					if(freightPart == null){
						freightPart = freightInvoice.getFreightPart();
					}
					if(fasAccountRmb == null || fasAccountDollar == null){
						FasAccount filter = new FasAccount();
						filter.setFreightPart(freightPart);
						List<FasAccount> fasAccounts = fasAccountDao.queryForList(filter);
						if(fasAccounts == null || fasAccounts.isEmpty() || fasAccounts.size() != 2){
							logger.error("获取单位的银行账户出错， 单位ID {}", freightPart.getId());
							return null;
						}else{
							for(FasAccount fasAccount : fasAccounts){
								if("人民币".equals(fasAccount.getCurrency())){
									fasAccountRmb = fasAccount;
								}else if("美元".equals(fasAccount.getCurrency())){
									fasAccountDollar = fasAccount;
								}
							}
							
							if(fasAccountRmb == null || fasAccountDollar == null){
								logger.error("获取单位的银行账户出错， 单位ID {}", freightPart.getId());
								return null;
							}
						}
					}
					
					if("人民币".equals(freightInvoice.getCurrency())){
						moneyCountRmb += freightInvoice.getRemainCount();
					}else if("美元".equals(freightInvoice.getCurrency())){
						moneyCountDollar += freightInvoice.getRemainCount();
					}
					
					freightInvoice.setEliminateCount(freightInvoice.getMoneyCount());
					freightInvoice.setRemainCount(0);
					freightInvoice.setStatus("已销账");
					freightInvoiceDao.modify(freightInvoice);
					
					/*FasPayInvoice fasPayInvoice = new FasPayInvoice();
					fasPayInvoice.setFasPayId(fasPayId);
					fasPayInvoice.setFreightInvoiceId(freightInvoiceId);
					fasPayInvoiceDao.add(fasPayInvoice);*/
				}else{
					continue;
				}
				
			}
			FasPay fasPay = new FasPay();
			fasPay.setId(fasPayId);
			fasPay.setFasAccountRmb(fasAccountRmb);
			fasPay.setFasAccountDollar(fasAccountDollar);
			fasPay.setPayCountRmb(moneyCountRmb);
			fasPay.setPayCountDollar(moneyCountDollar);
			fasPay.setStatus("已生成");
			fasPay.setBeneficiary(freightPart);
			fasPay.setProposer(proposer);
			fasPay.setOrgEntity(proposer.getOrgEntity());
			fasPay.setApplyTime(new Date());
			fasPay.setInvolveOrderNumber(relatedOrderNumber.deleteCharAt(relatedOrderNumber.lastIndexOf(",")).toString());
			
			fasPayDao.add(fasPay);
			
			return fasPay;
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}

	@Override
	public List<List<String>> toExportByFilterText(
			FreightInvoice freightInvoice, String orderNumber, String partName,
			String invoiceNumber, String netDayTimeStart, String netDayTimeEnd,
			String releaseTimeStart, String releaseTimeEnd) {
		
		PageView<FreightInvoice> pageView = new PageView<FreightInvoice>(freightInvoiceDao.count(freightInvoice), 1);
		if(!StringUtil.isNullOrEmpty(orderNumber)){
			String filterText = " FRE_STATEMENT_ID IN (SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE WHERE FRE_ORDER_ID IN(SELECT ID FROM FRE_ORDER WHERE ORDER_NUMBER LIKE '%" + orderNumber + "%'))";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			String filterText = " FAS_INVOICE_ID IN (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%" + invoiceNumber + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(netDayTimeStart) && !StringUtil.isNullOrEmpty(netDayTimeEnd)){
			String filterText = " (EFFECT_TIME BETWEEN '" + netDayTimeStart + "' AND '" + netDayTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(releaseTimeStart) && !StringUtil.isNullOrEmpty(releaseTimeEnd)){
			String filterText = " (RELEASE_TIME BETWEEN '" + releaseTimeStart + "' AND '" + releaseTimeEnd + "')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		List<FreightInvoice> freightInvoices = freightInvoiceDao.query(pageView, freightInvoice);
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightInvoice item : freightInvoices){
			List<String> singleValue = new ArrayList<String>();
			List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(item.getFreightStatement().getId());
			StringBuilder textSalesman = new StringBuilder();
			StringBuilder textOrdernumber = new StringBuilder();
			for(FreightExpense freightExpense : freightExpenses){
				if(freightExpense.getFreightOrder() != null){
					if(textSalesman.indexOf(freightExpense.getFreightOrder().getSalesman()) < 0){
						textSalesman.append(freightExpense.getFreightOrder().getSalesman() + ",");
					}
					
					if(textOrdernumber.indexOf(freightExpense.getFreightOrder().getOrderNumber()) < 0){
						textOrdernumber.append(freightExpense.getFreightOrder().getOrderNumber() + ",");
					}
				}else{
					if(textSalesman.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman()) < 0){
						textSalesman.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman() + ",");
					}
					
					if(textOrdernumber.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber()) < 0){
						textOrdernumber.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber() + ",");
					}
				}
			}
			
			if(textSalesman.lastIndexOf(",") > 0){
				textSalesman.deleteCharAt(textSalesman.lastIndexOf(","));
				textOrdernumber.deleteCharAt(textOrdernumber.lastIndexOf(","));
			}
			
			singleValue.add(textSalesman.toString());
			singleValue.add(textOrdernumber.toString());
			singleValue.add(item.getFreightPart().getPartName());
			singleValue.add(StringUtil.getDateString(item.getReleaseTime()));
			singleValue.add(item.getFasInvoice().getInvoiceNumber());
			singleValue.add(item.getFasInvoiceType().getTypeName());
			singleValue.add(item.getDescn());
			if("人民币".equals(item.getCurrency())){
				singleValue.add(String.valueOf(item.getMoneyCount()));
				singleValue.add("");
			}else{
				singleValue.add("");
				singleValue.add(String.valueOf(item.getMoneyCount()));
			}
			singleValue.add(String.valueOf(item.getExchangeRate()));
			singleValue.add(String.valueOf(item.getMoneyCount() * item.getExchangeRate()));
			singleValue.add(item.getFreightStatement().getStatementNumber());
			singleValue.add(item.getStatus());
			singleValue.add(StringUtil.getDateString(item.getEffectTime()));
			values.add(singleValue);
		}
		
		return values;
	}

	@Override
	public List<List<String>> toForecastExport(String[] freightInvoiceIds) {
		PageView<FreightInvoice> pageView = new PageView<FreightInvoice>(freightInvoiceIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String freightInvoiceId : freightInvoiceIds){
			filterText.append("'" +freightInvoiceId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		
		List<FreightInvoice> freightInvoices = freightInvoiceDao.query(pageView, new FreightInvoice());
		List<List<String>> values = new ArrayList<List<String>>();
		Map<String, double[]> subtotalMap = new HashMap<String, double[]>();//以单位为基础的小计
		double totalRmb = 0;
		double totalDollar = 0;
		for(FreightInvoice item : freightInvoices){
			if("人民币".equals(item.getCurrency())){
				totalRmb += item.getMoneyCount();
			}else if("美元".equals(item.getCurrency())){
				totalDollar += item.getMoneyCount();
			}
			
			if(subtotalMap.get(item.getFreightPart().getPartName()) == null){
				if("人民币".equals(item.getCurrency())){
					subtotalMap.put(item.getFreightPart().getPartName(), new double[]{item.getMoneyCount(), 0});
				}else if("美元".equals(item.getCurrency())){
					subtotalMap.put(item.getFreightPart().getPartName(), new double[]{0, item.getMoneyCount()});
				}
			}else{
				if("人民币".equals(item.getCurrency())){
					subtotalMap.put(item.getFreightPart().getPartName(), new double[]{subtotalMap.get(item.getFreightPart().getPartName())[0] + item.getMoneyCount(), subtotalMap.get(item.getFreightPart().getPartName())[1]});
				}else if("美元".equals(item.getCurrency())){
					subtotalMap.put(item.getFreightPart().getPartName(), new double[]{subtotalMap.get(item.getFreightPart().getPartName())[0], subtotalMap.get(item.getFreightPart().getPartName())[1] + item.getMoneyCount()});
				}
			}
			
			List<String> singleValue = new ArrayList<String>();
			List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(item.getFreightStatement().getId());
			StringBuilder textSalesman = new StringBuilder();
			StringBuilder textOrdernumber = new StringBuilder();
			for(FreightExpense freightExpense : freightExpenses){
				if(freightExpense.getFreightOrder() != null){
					if(textSalesman.indexOf(freightExpense.getFreightOrder().getSalesman()) < 0){
						textSalesman.append(freightExpense.getFreightOrder().getSalesman() + ",");
					}
					
					if(textOrdernumber.indexOf(freightExpense.getFreightOrder().getOrderNumber()) < 0){
						textOrdernumber.append(freightExpense.getFreightOrder().getOrderNumber() + ",");
					}
				}else{
					if(textSalesman.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman()) < 0){
						textSalesman.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman() + ",");
					}
					
					if(textOrdernumber.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber()) < 0){
						textOrdernumber.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber() + ",");
					}
				}
			}
			
			if(textSalesman.lastIndexOf(",") > 0){
				textSalesman.deleteCharAt(textSalesman.lastIndexOf(","));
				textOrdernumber.deleteCharAt(textOrdernumber.lastIndexOf(","));
			}
			
			singleValue.add(textSalesman.toString());
			singleValue.add(textOrdernumber.toString());
			singleValue.add(item.getFreightPart().getPartName());
			singleValue.add(StringUtil.getDateString(item.getReleaseTime()));
			singleValue.add(item.getFasInvoice().getInvoiceNumber());
			singleValue.add(item.getDescn());
			if("人民币".equals(item.getCurrency())){
				singleValue.add(String.valueOf(item.getMoneyCount()));
				singleValue.add("");
			}else{
				singleValue.add("");
				singleValue.add(String.valueOf(item.getMoneyCount()));
			}
			singleValue.add(StringUtil.getDateString(item.getEffectTime()));
			singleValue.add(item.getEffectTime().after(new Date()) ? "未超期" : ((int)((new Date().getTime() - item.getEffectTime().getTime())/(1000*60*60*24))) + "");
			values.add(singleValue);
		}
		
		List<List<String>> resultValues = new ArrayList<List<String>>();
		for(String partName : subtotalMap.keySet()){
			for(List<String> value : values){
				if(partName.equals(value.get(2))){
					resultValues.add(value);
				}
			}
			
			List<String> singleValue = new ArrayList<String>();
			singleValue.add("");
			singleValue.add("");
			singleValue.add("小计");
			singleValue.add("");
			singleValue.add("");
			singleValue.add("");
			singleValue.add(String.valueOf(subtotalMap.get(partName)[0]));
			singleValue.add(String.valueOf(subtotalMap.get(partName)[1]));
			singleValue.add("");
			singleValue.add("");
			
			resultValues.add(singleValue);
		}
		
		List<String> singleValue = new ArrayList<String>();
		singleValue.add("");
		singleValue.add("");
		singleValue.add("总计");
		singleValue.add("");
		singleValue.add("");
		singleValue.add("");
		singleValue.add(String.valueOf(totalRmb));
		singleValue.add(String.valueOf(totalDollar));
		singleValue.add("");
		singleValue.add("");
		resultValues.add(singleValue);
		
		return resultValues;
	}

	@Override
	public boolean backAuditInvoice(String[] freightInvoiceIds) {
		boolean flag = true;
		for(String freightInvoiceId : freightInvoiceIds){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
			if("待销账".equals(freightInvoice.getStatus())){
				freightInvoice.setStatus("付款初审中");
				freightInvoiceDao.modify(freightInvoice);
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
	public Map<String, Object> toBatchoffset(String[] freightInvoiceIds) {
		boolean flag = true;
		StringBuilder sql = new StringBuilder("SELECT COUNT(1) FROM FRE_INVOICE ");
		sql.append(" WHERE STATUS='待销账' AND FRE_STATEMENT_ID IS NOT NULL AND ID IN( ");//通过账单ID是否为空判断是否有批量冲抵新生成的，避免重复冲抵
		for(String freightInvoiceId : freightInvoiceIds){
			sql.append("'" + freightInvoiceId + "',");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(") GROUP BY FRE_PART_ID LIMIT 1");
		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);
		if(count != freightInvoiceIds.length){
			flag = false;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", String.valueOf(flag));
		if(flag){
			FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceIds[0]);
			FreightInvoice filter = new FreightInvoice();
			filter.setFreightPart(freightInvoice.getFreightPart());
			filter.setIncomeOrExpense("收".equals(freightInvoice.getIncomeOrExpense()) ? "付" : "收");
			filter.setStatus("待销账");
			PageView<FreightInvoice> pageView = new PageView<FreightInvoice>(freightInvoiceDao.count(filter), 1);
			pageView.setFilterText(" FRE_STATEMENT_ID IS NOT NULL ");//通过账单ID是否为空判断是否有批量冲抵新生成的，避免重复冲抵
			map.put("freightInvoices", freightInvoiceDao.query(pageView, filter));
		}
		return map;
	}

	@Override
	public Map<String, Object> doneBatchoffset(String[] freightInvoiceIdsIn, String[] freightInvoiceIdsOut, String offsetType) {
		boolean flag = true;
		List<FreightInvoice> resultInvoice = new ArrayList<FreightInvoice>();//保存最后生成的发票信息
		
		if(freightInvoiceIdsIn.length > 0 && freightInvoiceIdsOut.length > 0){
			//收
			double moneyCountRMBIn = 0;
			double moneyCountDollarIn = 0;
			//付
			double moneyCountRMBOut = 0;
			double moneyCountDollarOut = 0;
			
			//折合
			double moneyCountIn = 0;
			double moneyCountOut = 0;
			
			FreightPart freightPart = null;
			//计算金额
			for(String freightInvoiceId : freightInvoiceIdsIn){
				FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
				if(freightPart == null){
					freightPart = freightInvoice.getFreightPart();
				}
				if("待销账".equals(freightInvoice.getStatus()) 
						&& freightInvoice.getMoneyCount() == freightInvoice.getRemainCount()
						&& freightPart.getId().equals(freightInvoice.getFreightPart().getId())){
					if("人民币".equals(freightInvoice.getCurrency())){
						moneyCountRMBIn += freightInvoice.getMoneyCount();
					}else if("美元".equals(freightInvoice.getCurrency())){
						moneyCountDollarIn += freightInvoice.getMoneyCount();
					}
					
					moneyCountIn += freightInvoice.getMoneyCount() * freightInvoice.getExchangeRate();
					freightInvoice.setRemainCount(0);
					freightInvoice.setEliminateCount(freightInvoice.getMoneyCount());
					freightInvoice.setStatus("已批量冲抵销账");
					freightInvoiceDao.modify(freightInvoice);
				}else{
					flag = false;
					logger.info("发票不符合批量冲抵的条件, 发票号 ： {}", freightInvoice.getFasInvoice().getInvoiceNumber());
					break;
				}
			}
			
			for(String freightInvoiceId : freightInvoiceIdsOut){
				FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
				if("待销账".equals(freightInvoice.getStatus()) 
						&& freightInvoice.getMoneyCount() == freightInvoice.getRemainCount()
						&& freightPart.getId().equals(freightInvoice.getFreightPart().getId())){
					if("人民币".equals(freightInvoice.getCurrency())){
						moneyCountRMBOut += freightInvoice.getMoneyCount();
					}else if("美元".equals(freightInvoice.getCurrency())){
						moneyCountDollarOut += freightInvoice.getMoneyCount();
					}
					
					moneyCountOut += freightInvoice.getMoneyCount() * freightInvoice.getExchangeRate();
					freightInvoice.setRemainCount(0);
					freightInvoice.setEliminateCount(freightInvoice.getMoneyCount());
					freightInvoice.setStatus("已批量冲抵销账");
					freightInvoiceDao.modify(freightInvoice);
				}else{
					flag = false;
					logger.info("发票不符合批量冲抵的条件, 发票号 ： {}", freightInvoice.getFasInvoice().getInvoiceNumber());
					break;
				}
			}
			
			if(flag){
				String batchInvoiceIdRBM = null;
				String batchInvoiceIdDollar = null;
				//同币种
				if("careCurrencyBatch".equals(offsetType)){
					double rmb = moneyCountRMBIn - moneyCountRMBOut;
					double dollar = moneyCountDollarIn - moneyCountDollarOut;
					if(rmb != 0){
						batchInvoiceIdRBM = StringUtil.getUUID();
						
						FasInvoice fasInvoice = new FasInvoice();
						fasInvoice.setId(StringUtil.getUUID());
						fasInvoice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName("无票(0)"));
						fasInvoice.setIncomeOrExpense(rmb > 0 ? "收" : "付");
						fasInvoice.setInvoiceNumber(fasInvoiceService.getNextOffsetInvoiceNumber());
						fasInvoice.setStatus("已用");
						fasInvoiceDao.add(fasInvoice);
						
						FreightInvoice freightInvoice = new FreightInvoice();
						freightInvoice.setId(batchInvoiceIdRBM);
						freightInvoice.setCurrency("人民币");
						freightInvoice.setIncomeOrExpense(rmb > 0 ? "收" : "付");
						freightInvoice.setExchangeRate(fasExchangeRateDao.getExchangeRate("人民币", null));
						freightInvoice.setMoneyCount(Math.abs(rmb));
						freightInvoice.setRemainCount(Math.abs(rmb));
						freightInvoice.setEliminateCount(0);
						freightInvoice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName("无票(0)"));
						freightInvoice.setFreightPart(freightPart);
						freightInvoice.setFasInvoice(fasInvoice);//设置税务发票
						freightInvoice.setStatus("待销账");
						freightInvoiceDao.add(freightInvoice);
						
						resultInvoice.add(freightInvoice);
					}
					
					if(dollar != 0){
						batchInvoiceIdDollar = StringUtil.getUUID();
						
						FasInvoice fasInvoice = new FasInvoice();
						fasInvoice.setId(StringUtil.getUUID());
						fasInvoice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName("无票(0)"));
						fasInvoice.setIncomeOrExpense(rmb > 0 ? "收" : "付");
						fasInvoice.setInvoiceNumber(fasInvoiceService.getNextOffsetInvoiceNumber());
						fasInvoice.setStatus("已用");
						fasInvoiceDao.add(fasInvoice);
						
						FreightInvoice freightInvoice = new FreightInvoice();
						freightInvoice.setId(batchInvoiceIdDollar);
						freightInvoice.setCurrency("美元");
						freightInvoice.setIncomeOrExpense(dollar > 0 ? "收" : "付");
						freightInvoice.setExchangeRate(fasExchangeRateDao.getExchangeRate("美元", null));
						freightInvoice.setMoneyCount(Math.abs(dollar));
						freightInvoice.setRemainCount(Math.abs(dollar));
						freightInvoice.setEliminateCount(0);
						freightInvoice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName("无票(0)"));
						freightInvoice.setFreightPart(freightPart);
						freightInvoice.setFasInvoice(fasInvoice);//设置税务发票
						freightInvoice.setStatus("待销账");
						freightInvoiceDao.add(freightInvoice);
						
						resultInvoice.add(freightInvoice);
					}
					
					if(rmb == 0 && dollar == 0){
						flag = false;//如果全部冲抵，则取消批量冲抵，否则无法进行取消冲抵
						logger.info("全部已冲抵，不能生成相关发票，取消冲抵！");
					}
				}else if("ignoreCurrencyBatch".equals(offsetType)){
					double moneyCount = moneyCountIn - moneyCountOut;
					if(moneyCount != 0){
						batchInvoiceIdRBM = StringUtil.getUUID();
						
						FasInvoice fasInvoice = new FasInvoice();
						fasInvoice.setId(StringUtil.getUUID());
						fasInvoice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName("无票(0)"));
						fasInvoice.setIncomeOrExpense(moneyCount > 0 ? "收" : "付");
						fasInvoice.setInvoiceNumber(fasInvoiceService.getNextOffsetInvoiceNumber());
						fasInvoice.setStatus("已用");
						fasInvoiceDao.add(fasInvoice);
						
						FreightInvoice freightInvoice = new FreightInvoice();
						freightInvoice.setId(batchInvoiceIdRBM);
						freightInvoice.setCurrency("人民币");
						freightInvoice.setIncomeOrExpense(moneyCount > 0 ? "收" : "付");
						freightInvoice.setExchangeRate(fasExchangeRateDao.getExchangeRate("人民币", null));
						freightInvoice.setMoneyCount(Math.abs(moneyCount));
						freightInvoice.setRemainCount(Math.abs(moneyCount));
						freightInvoice.setEliminateCount(0);
						freightInvoice.setFasInvoiceType(fasInvoiceTypeDao.getByTypeName("无票(0)"));
						freightInvoice.setFreightPart(freightPart);
						freightInvoice.setFasInvoice(fasInvoice);//设置税务发票
						freightInvoice.setStatus("待销账");
						freightInvoiceDao.add(freightInvoice);
						
						resultInvoice.add(freightInvoice);
					}else{
						flag = false;//如果全部冲抵，则取消批量冲抵，否则无法进行取消冲抵
						logger.info("全部已冲抵，不能生成相关发票，取消冲抵！");
					}
				}
				
				String freightInvoiceIdB = batchInvoiceIdRBM + (batchInvoiceIdDollar == null ? "" : ";" + batchInvoiceIdDollar);
				
				//添加冲抵信息
				FreightInvoiceOffset offset = new FreightInvoiceOffset();
				offset.setOffsetType(offsetType);
				offset.setFreightInvoiceIdB(freightInvoiceIdB);//新生成的开票IDs
				for(String freightInvoiceId : freightInvoiceIdsIn){
					FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
					offset.setFreightInvoiceIdA(freightInvoiceId);
					if("人民币".equals(freightInvoice.getCurrency())){
						offset.setEliminateCountRmbA(freightInvoice.getRemainCount());
					}else if("美元".equals(freightInvoice.getCurrency())){
						offset.setEliminateCountDollarA(freightInvoice.getRemainCount());
					}
					freightInvoiceOffsetDao.add(offset);
				}
				
				for(String freightInvoiceId : freightInvoiceIdsOut){
					FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
					offset.setFreightInvoiceIdA(freightInvoiceId);
					if("人民币".equals(freightInvoice.getCurrency())){
						offset.setEliminateCountRmbA(freightInvoice.getRemainCount());
					}else if("美元".equals(freightInvoice.getCurrency())){
						offset.setEliminateCountDollarA(freightInvoice.getRemainCount());
					}
					freightInvoiceOffsetDao.add(offset);
				}
			}
		}else{
			flag = false;
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", String.valueOf(flag));
		map.put("resultInvoice", resultInvoice);
		return map;
	}

	@Override
	public boolean deleteBatchOffset(String freightInvoiceId) {
		boolean flag = true;
		FreightInvoice freightInvoice = freightInvoiceDao.getById(freightInvoiceId);
		if("待销账".equals(freightInvoice.getStatus()) && freightInvoice.getFasInvoice() != null && freightInvoice.getFasInvoice().getInvoiceNumber().endsWith("-CD")){
			String sql = "SELECT * FROM FRE_INVOICE_OFFSET WHERE OFFSET_TYPE IN('careCurrencyBatch', 'ignoreCurrencyBatch') AND  FRE_INVOICE_ID_B LIKE '%" + freightInvoiceId + "%'";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			String[] batchInvoiceIds = ((String)list.get(0).get("FRE_INVOICE_ID_B")).split(";");
			for(String batchInvoiceId : batchInvoiceIds){
				FreightInvoice batchInvoice = freightInvoiceDao.getById(batchInvoiceId);
				if("待销账".equals(batchInvoice.getStatus()) && batchInvoice.getFasInvoice() != null && batchInvoice.getFasInvoice().getInvoiceNumber().endsWith("-CD")){
					fasInvoiceDao.delete(batchInvoice.getFasInvoice().getId());
					freightInvoiceDao.delete(batchInvoiceId);
				}else{
					flag = false;
					break;
				}
			}
			//恢复所有已销账的开票
			if(flag){
				for(Map<String, Object> map : list){
					FreightInvoice singleInvoice = freightInvoiceDao.getById((String)map.get("FRE_INVOICE_ID_A"));
					singleInvoice.setRemainCount(singleInvoice.getMoneyCount());
					singleInvoice.setEliminateCount(0);
					singleInvoice.setStatus("待销账");
					freightInvoiceDao.modify(singleInvoice);
					
					freightInvoiceOffsetDao.delete((String)map.get("ID"));//删除冲抵信息
				}
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
	public List<List<String>> toBatchOffsetExport(String freightInvoiceId) {
		List<List<String>> values = new ArrayList<List<String>>();
		
		FreightInvoice resultInvoice = freightInvoiceDao.getById(freightInvoiceId);
		String[] resultInvoiceIds = null;
		if(resultInvoice.getFasInvoice() != null && resultInvoice.getFasInvoice().getInvoiceNumber().endsWith("-CD")){
			String sqlOffset = "SELECT FRE_INVOICE_ID_A, FRE_INVOICE_ID_B  FROM FRE_INVOICE_OFFSET WHERE FRE_INVOICE_ID_B LIKE '%" + freightInvoiceId + "%'";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlOffset);
			
			resultInvoiceIds = list.get(0).get("FRE_INVOICE_ID_B").toString().split(";");
			
			StringBuilder sqlInvoice = new StringBuilder();
			sqlInvoice.append(" ID IN(");
			for(Map<String, Object> map : list){
				sqlInvoice.append("'" + map.get("FRE_INVOICE_ID_A").toString() + "',");
			}
			
			sqlInvoice.deleteCharAt(sqlInvoice.lastIndexOf(","));
			sqlInvoice.append(")");
			PageView<FreightInvoice> pageView = new PageView<FreightInvoice>(100, 1);
			pageView.setFilterText(sqlInvoice.toString());
			pageView.setOrder("ASC");
			pageView.setOrderBy("INCOME_OR_EXPENSE, RELEASE_TIME");
			List<FreightInvoice> freightInvoices = freightInvoiceDao.query(pageView, new FreightInvoice());
			
			for(FreightInvoice item : freightInvoices){
				if("收".equals(item.getIncomeOrExpense())){
					List<String> singleValue = new ArrayList<String>();
					List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(item.getFreightStatement().getId());
					StringBuilder textSalesman = new StringBuilder();
					StringBuilder textOrdernumber = new StringBuilder();
					for(FreightExpense freightExpense : freightExpenses){
						if(freightExpense.getFreightOrder() != null){
							if(textSalesman.indexOf(freightExpense.getFreightOrder().getSalesman()) < 0){
								textSalesman.append(freightExpense.getFreightOrder().getSalesman() + ",");
							}
							
							if(textOrdernumber.indexOf(freightExpense.getFreightOrder().getOrderNumber()) < 0){
								textOrdernumber.append(freightExpense.getFreightOrder().getOrderNumber() + ",");
							}
						}else{
							if(textSalesman.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman()) < 0){
								textSalesman.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman() + ",");
							}
							
							if(textOrdernumber.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber()) < 0){
								textOrdernumber.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber() + ",");
							}
						}
					}
					
					if(textSalesman.lastIndexOf(",") > 0){
						textSalesman.deleteCharAt(textSalesman.lastIndexOf(","));
						textOrdernumber.deleteCharAt(textOrdernumber.lastIndexOf(","));
					}
					
					singleValue.add(textSalesman.toString());
					singleValue.add(textOrdernumber.toString());
					singleValue.add(item.getFreightPart().getPartName());
					singleValue.add(StringUtil.parseDateTime(item.getReleaseTime()));
					singleValue.add(item.getFasInvoice().getInvoiceNumber());
					singleValue.add(item.getFasInvoiceType().getTypeName());
					singleValue.add(item.getDescn());
					if("人民币".equals(item.getCurrency())){
						singleValue.add(String.valueOf(item.getMoneyCount()));
						singleValue.add("");
					}else{
						singleValue.add("");
						singleValue.add(String.valueOf(item.getMoneyCount()));
					}
					
					singleValue.add(String.valueOf(item.getExchangeRate()));
					singleValue.add(item.getStatus());
					values.add(singleValue);
				}
			}
			
			List<String> segmente = new ArrayList<String>();
			segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");
			values.add(segmente);
			
			List<String> title = new ArrayList<String>();
			title.add("业务员");title.add("订单号");title.add("单位名称");title.add("开票日期");title.add("发票号");
			title.add("发票类型");title.add("内容摘要");title.add("应付(人民币)");title.add("应付(美元)"); title.add("汇率");
			values.add(title);
			
			for(FreightInvoice item : freightInvoices){
				if("付".equals(item.getIncomeOrExpense())){
					List<String> singleValue = new ArrayList<String>();
					List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(item.getFreightStatement().getId());
					StringBuilder textSalesman = new StringBuilder();
					StringBuilder textOrdernumber = new StringBuilder();
					for(FreightExpense freightExpense : freightExpenses){
						if(freightExpense.getFreightOrder() != null){
							if(textSalesman.indexOf(freightExpense.getFreightOrder().getSalesman()) < 0){
								textSalesman.append(freightExpense.getFreightOrder().getSalesman() + ",");
							}
							
							if(textOrdernumber.indexOf(freightExpense.getFreightOrder().getOrderNumber()) < 0){
								textOrdernumber.append(freightExpense.getFreightOrder().getOrderNumber() + ",");
							}
						}else{
							if(textSalesman.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman()) < 0){
								textSalesman.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getSalesman() + ",");
							}
							
							if(textOrdernumber.indexOf(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber()) < 0){
								textOrdernumber.append(freightExpense.getFreightAction().getFreightMaintain().getFreightOrder().getOrderNumber() + ",");
							}
						}
					}
					
					if(textSalesman.lastIndexOf(",") > 0){
						textSalesman.deleteCharAt(textSalesman.lastIndexOf(","));
						textOrdernumber.deleteCharAt(textOrdernumber.lastIndexOf(","));
					}
					
					singleValue.add(textSalesman.toString());
					singleValue.add(textOrdernumber.toString());
					singleValue.add(item.getFreightPart().getPartName());
					singleValue.add(StringUtil.parseDateTime(item.getReleaseTime()));
					singleValue.add(item.getFasInvoice().getInvoiceNumber());
					singleValue.add(item.getFasInvoiceType().getTypeName());
					singleValue.add(item.getDescn());
					if("人民币".equals(item.getCurrency())){
						singleValue.add(String.valueOf(item.getMoneyCount()));
						singleValue.add("");
					}else{
						singleValue.add("");
						singleValue.add(String.valueOf(item.getMoneyCount()));
					}
					
					singleValue.add(String.valueOf(item.getExchangeRate()));
					singleValue.add(item.getStatus());
					values.add(singleValue);
				}
			}
			
			segmente = new ArrayList<String>();
			segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");segmente.add("");
			values.add(segmente);
			
			for(String resultInvoiceId : resultInvoiceIds){
				FreightInvoice item = freightInvoiceDao.getById(resultInvoiceId);
				
				List<String> singleValue = new ArrayList<String>();
				singleValue.add("");
				singleValue.add("");
				singleValue.add("收".equals(item.getIncomeOrExpense()) ? "冲抵后应收： " : "冲抵后应付： ");
				singleValue.add("");
				singleValue.add(item.getFasInvoice().getInvoiceNumber());
				singleValue.add(item.getFasInvoiceType().getTypeName());
				singleValue.add(item.getDescn());
				if("人民币".equals(item.getCurrency())){
					singleValue.add(String.valueOf(item.getMoneyCount()));
					singleValue.add("");
				}else{
					singleValue.add("");
					singleValue.add(String.valueOf(item.getMoneyCount()));
				}
				
				singleValue.add(String.valueOf(item.getExchangeRate()));
				singleValue.add(item.getStatus());
				values.add(singleValue);
			}
		}
		return values;
	}
}

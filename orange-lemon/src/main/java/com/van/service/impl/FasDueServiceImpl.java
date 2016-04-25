package com.van.service.impl;

import java.text.SimpleDateFormat;
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
import com.van.halley.db.persistence.FasDueDao;
import com.van.halley.db.persistence.FasReconcileDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.entity.FasDue;
import com.van.halley.db.persistence.entity.FasReconcile;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.util.DoubleUtil;
import com.van.halley.util.StringUtil;
import com.van.service.FasDueService;

@Transactional
@Service("fasDueService")
public class FasDueServiceImpl implements FasDueService {
	private static Logger logger = LoggerFactory.getLogger(FasDueServiceImpl.class);
	@Autowired
	private FasDueDao fasDueDao;
	@Autowired
	private FasReconcileDao fasReconcileDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FasDue> getAll() {
		return fasDueDao.getAll();
	}

	public List<FasDue> queryForList(FasDue fasDue) {
		return fasDueDao.queryForList(fasDue);
	}

	public FasDue queryForOne(FasDue fasDue) {
		return fasDueDao.queryForOne(fasDue);
	}

	public PageView query(PageView pageView, FasDue fasDue) {
		List<FasDue> list = fasDueDao.query(pageView, fasDue);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasDue fasDue) {
		fasDueDao.add(fasDue);
	}

	public void delete(String id) {
		fasDueDao.delete(id);
	}

	public void modify(FasDue fasDue) {
		fasDueDao.modify(fasDue);
	}

	public FasDue getById(String id) {
		return fasDueDao.getById(id);
	}

	@Override
	public Map<String, Object> toAddReconcile(String fasDueId, String partName, String invoiceNumber) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FasReconcile filterReconcile = new FasReconcile();
		filterReconcile.setTargetId(fasDueId);
		map.put("fasReconciles", fasReconcileDao.queryForList(filterReconcile));
		
		FasDue fasDue = fasDueDao.getById(fasDueId);
		map.put("fasDue", fasDue);
		
		//取消单位的过滤
		FreightInvoice filter = new FreightInvoice();
		filter.setIncomeOrExpense("收");
		PageView pageView = new PageView(1);
		pageView.setFilterText(" STATUS IN ('冲抵过','待销账', '销账中')");//新增冲抵之后的开票
		
		if(!StringUtil.isNullOrEmpty(partName)){
			pageView.setPageSize(freightInvoiceDao.count(filter));
			String filterText = " EXISTS (SELECT 1 FROM (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%"+ partName +"%') AS T WHERE T.ID = FRE_PART_ID)";
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
		}
		
		if(!StringUtil.isNullOrEmpty(invoiceNumber)){
			pageView.setPageSize(freightInvoiceDao.count(filter));
			String filterText = " EXISTS (SELECT 1 FROM (SELECT ID FROM FAS_INVOICE WHERE INVOICE_NUMBER LIKE '%"+ invoiceNumber +"%') AS T WHERE T.ID = FAS_INVOICE_ID)";
			pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
		}
		pageView.setOrderBy("RELEASE_TIME");
		pageView.setOrder("ASC");
		map.put("toReconcileInvoices", freightInvoiceDao.query(pageView, filter));
		//销账过的相关发票
		map.put("hasReconcileInvoices", freightInvoiceDao.getByFasPayId(fasDueId));
		return map;
	}

	@Override
	public boolean doneAddReconcile(String sourceId, String targetId, double moneyCount) {
		boolean flag = true;
		FreightInvoice freightInvoice = freightInvoiceDao.getById(sourceId);
		if(moneyCount <= 0 
				|| freightInvoice.getRemainCount() <= 0 
				|| freightInvoice.getEliminateCount() < 0
				|| freightInvoice.getRemainCount() < moneyCount){
			logger.error("销账金额大于未销账的余额，未销账ID: {} , 金额： {}", sourceId, moneyCount);
			flag = false;
		}else{
			FasDue fasDue = fasDueDao.getById(targetId);
			double actualCount = DoubleUtil.mul(fasDue.getActualCount(), fasDue.getExchangeRate());
			double eliminateCount = DoubleUtil.mul(fasDue.getEliminateCount(), fasDue.getExchangeRate());
			double predictCount = DoubleUtil.mul(moneyCount, freightInvoice.getExchangeRate());
			double result = DoubleUtil.sub(actualCount, eliminateCount, predictCount);//销账后预计金额
			//if(result < 0){
			if(result < -1){
				logger.error("销账金额大于收款余额");
				flag = false;
			}else{
				freightInvoice.setRemainCount(freightInvoice.getRemainCount() - moneyCount);
				freightInvoice.setEliminateCount(freightInvoice.getMoneyCount() - freightInvoice.getRemainCount());
				if(freightInvoice.getRemainCount() == 0){
					freightInvoice.setStatus("已销账");
				}else{
					freightInvoice.setStatus("销账中");
				}
				freightInvoice.setEliminateTime(new Date());
				freightInvoiceDao.modify(freightInvoice);
				
				FasReconcile fasReconcile = new FasReconcile();
				fasReconcile.setIncomeOrExpense("收");
				fasReconcile.setSourceId(sourceId);
				fasReconcile.setTargetId(targetId);
				fasReconcile.setMoneyCount(moneyCount);
				fasReconcile.setCurrency(freightInvoice.getCurrency());
				fasReconcile.setExchangeRate(freightInvoice.getExchangeRate());
				fasReconcileDao.add(fasReconcile);
				
				fasDue = fasDueDao.getById(targetId);
				if(fasDue.getRemainCount() == 0 || Math.abs(fasDue.getRemainCount()) < 1){//此处避免因汇率产生的小数而无法完全消除的的问题
					fasDue.setStatus("已销账");
				}else if(fasDue.getEliminateCount() == 0){
					fasDue.setStatus("已确认");
				}else{
					fasDue.setStatus("销账中");
				}
				fasDueDao.modify(fasDue);
			}
		}
		
		return flag;
	}

	@Override
	public void doneDeleteReconcile(String[] fasReconcileIds) {
		for(String fasReconcileId : fasReconcileIds){
			FasReconcile fasReconcile = fasReconcileDao.getById(fasReconcileId);
			if("收".equals(fasReconcile.getIncomeOrExpense())){
				String currency = fasReconcile.getCurrency();
				FreightInvoice freightInvoice = freightInvoiceDao.getById(fasReconcile.getSourceId());
				if(currency.equals(freightInvoice.getCurrency())){
					freightInvoice.setRemainCount(freightInvoice.getRemainCount() + fasReconcile.getMoneyCount());
					freightInvoice.setEliminateCount(freightInvoice.getMoneyCount() - freightInvoice.getRemainCount());
					if(freightInvoice.getEliminateCount() == 0){
						freightInvoice.setStatus("待销账");
					}else{
						freightInvoice.setStatus("销账中");
					}
					freightInvoiceDao.modify(freightInvoice);
					
					fasReconcileDao.delete(fasReconcileId);
					
					FasDue fasDue = fasDueDao.getById(fasReconcile.getTargetId());
					if(fasDue.getRemainCount() == 0){
						fasDue.setStatus("已销账");
					}else if(fasDue.getEliminateCount() == 0){
						fasDue.setStatus("已确认");
					}else{
						fasDue.setStatus("销账中");
					}
					fasDueDao.modify(fasDue);
				}
				
			}
		}
		
	}

	@Override
	public boolean doneConfirmDue(String[] fasDueIds) {
		boolean flag = true;
		for(String fasDueId : fasDueIds){
			FasDue fasDue = fasDueDao.getById(fasDueId);
			String status = fasDue.getStatus();
			if("未提交".equals(status)){
				fasDue.setStatus("已确认");
				fasDue.setActualCount(fasDue.getDueCount());
				fasDueDao.modify(fasDue);
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
	public boolean doneRecallDue(String[] fasDueIds) {
		boolean flag = true;
		for(String fasDueId : fasDueIds){
			FasDue fasDue = fasDueDao.getById(fasDueId);
			String status = fasDue.getStatus();
			if("已确认".equals(status)){
				fasDue.setStatus("未提交");
				fasDue.setActualCount(fasDue.getDueCount());
				fasDueDao.modify(fasDue);
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
	public boolean doneForceReconcile(String[] fasDueIds) {
		boolean flag = true;
		for(String fasDueId : fasDueIds){
			FasDue fasDue = fasDueDao.getById(fasDueId);
			String status = fasDue.getStatus();
			if("销账中".equals(status) && fasDue.getRemainCount() > 0){
				fasDue.setStatus("已销账");
				fasDue.setActualCount(fasDue.getDueCount());
				fasDueDao.modify(fasDue);
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
	public boolean doneRecallReconcile(String[] fasDueIds) {
		boolean flag = true;
		for(String fasDueId : fasDueIds){
			FasDue fasDue = fasDueDao.getById(fasDueId);
			String status = fasDue.getStatus();
			if("已销账".equals(status)){
				fasDue.setStatus("销账中");
				fasDue.setActualCount(fasDue.getDueCount());
				fasDueDao.modify(fasDue);
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
	public String getNextDueNumber() {
		String sql = "SELECT CONCAT('SK', DATE_FORMAT(SYSDATE(),'%Y%m%d'), REPEAT('0',5 - LENGTH(MAX(RIGHT(DUE_NUMBER, 5)) + 1)),MAX(RIGHT(DUE_NUMBER, 5)) + 1)"
				+ " FROM FAS_DUE";
		String number  = (String)jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(number)){
			number = "SK" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00001";
		}
		return number;
	}

	@Override
	public List<List<String>> toExport(String[] fasDueIds) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(String fasDueId : fasDueIds){
			List<String> singleValue = new ArrayList<String>();
			FasDue fasDue = fasDueDao.getById(fasDueId);
			singleValue.add(fasDue.getDueNumber());
			singleValue.add(fasDue.getPayPart().getPartName());
			singleValue.add(fasDue.getCurrency());
			singleValue.add(String.valueOf(fasDue.getDueCount()));
			singleValue.add(StringUtil.parseDateTime(fasDue.getDueTime()));
			singleValue.add(fasDue.getPayAccount() == null ? "" : fasDue.getPayAccount().getAccountNumber());
			singleValue.add(fasDue.getDueAccount() == null ? "" : fasDue.getDueAccount().getAccountNumber());
			singleValue.add(fasDue.getDescn());
			values.add(singleValue);
		}
		return values;
	}

	

	

	
}

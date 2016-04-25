package com.van.service.impl;

import java.text.SimpleDateFormat;
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

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasPayDao;
import com.van.halley.db.persistence.FasReconcileDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.ReportTemplateDao;
import com.van.halley.db.persistence.entity.FasPay;
import com.van.halley.db.persistence.entity.FasReconcile;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.util.StringUtil;
import com.van.service.FasPayService;

@Transactional
@Service("fasPayService")
public class FasPayServiceImpl implements FasPayService {
	private static Logger logger = LoggerFactory.getLogger(FasPayServiceImpl.class);
	@Autowired
	private FasPayDao fasPayDao;
	@Autowired
	private FasReconcileDao fasReconcileDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private ReportTemplateDao reportTemplateDao;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FasPay> getAll() {
		return fasPayDao.getAll();
	}

	public List<FasPay> queryForList(FasPay fasPay) {
		return fasPayDao.queryForList(fasPay);
	}

	public FasPay queryForOne(FasPay fasPay) {
		return fasPayDao.queryForOne(fasPay);
	}

	public PageView query(PageView pageView, FasPay fasPay) {
		List<FasPay> list = fasPayDao.query(pageView, fasPay);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasPay fasPay) {
		fasPayDao.add(fasPay);
	}

	public void delete(String id) {
		fasPayDao.delete(id);
	}

	public void modify(FasPay fasPay) {
		fasPayDao.modify(fasPay);
	}

	public FasPay getById(String id) {
		return fasPayDao.getById(id);
	}

	@Override
	public void confirmPay(String[] fasPayIds) {
		for(String fasPayId : fasPayIds){
			FasPay fasPay = fasPayDao.getById(fasPayId);
			if("已生成".equals(fasPay.getStatus())){
				fasPay.setStatus("已付款");
				fasPay.setPayTime(new Date());
				fasPayDao.modify(fasPay);
			}
		}
	}
	
	@Override
	public Map<String, Object> toAddReconcile(String fasPayId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FasReconcile filterReconcile = new FasReconcile();
		filterReconcile.setTargetId(fasPayId);
		map.put("fasReconciles", fasReconcileDao.queryForList(filterReconcile));
		
		FasPay fasPay = fasPayDao.getById(fasPayId);
		map.put("fasPay", fasPay);
		
		FreightInvoice filter = new FreightInvoice();
		filter.setIncomeOrExpense("付");
		filter.setFreightPart(fasPay.getBeneficiary());
		PageView pageView = new PageView(freightInvoiceDao.count(filter), 1);
		pageView.setOrderBy("RELEASE_TIME");
		pageView.setOrder("ASC");
		pageView.setFilterText(" STATUS IN ('冲抵过','待销账', '销账中')");//新增冲抵之后的开票
		map.put("toReconcileInvoices", freightInvoiceDao.query(pageView, filter));
		//销账过的相关发票
		map.put("hasReconcileInvoices", freightInvoiceDao.getByFasPayId(fasPayId));
		return map;
	}

	@Override
	public boolean doneAddReconcile(String sourceId, String targetId, double moneyCount) {
		FreightInvoice freightInvoice = freightInvoiceDao.getById(sourceId);
		if(moneyCount <= 0 
				|| freightInvoice.getRemainCount() <= 0 
				|| freightInvoice.getEliminateCount() < 0
				|| freightInvoice.getRemainCount() < moneyCount){
			logger.error("销账金额大于未销账的余额，未销账ID: {} , 金额： {}", sourceId, moneyCount);
			return false;
		}
		
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
		fasReconcile.setIncomeOrExpense("付");
		fasReconcile.setSourceId(sourceId);
		fasReconcile.setTargetId(targetId);
		fasReconcile.setMoneyCount(moneyCount);
		fasReconcile.setCurrency(freightInvoice.getCurrency());
		fasReconcile.setExchangeRate(freightInvoice.getExchangeRate());
		fasReconcileDao.add(fasReconcile);
		
		FasPay fasPay = fasPayDao.getById(targetId);
		
		FasReconcile filter = new FasReconcile();
		filter.setTargetId(targetId);
		if(fasReconcileDao.count(filter) == 0){
			fasPay.setStatus("未提交");
		}else{
			fasPay.setStatus("已生成");
		}
		fasPayDao.modify(fasPay);
		
		return true;
	}

	@Override
	public void doneDeleteReconcile(String[] fasReconcileIds) {
		for(String fasReconcileId : fasReconcileIds){
			FasReconcile fasReconcile = fasReconcileDao.getById(fasReconcileId);
			if("付".equals(fasReconcile.getIncomeOrExpense())){
				String sourceId = fasReconcile.getSourceId();
				double moneyCount = fasReconcile.getMoneyCount();
				String currency = fasReconcile.getCurrency();
				FreightInvoice freightInvoice = freightInvoiceDao.getById(sourceId);
				if(currency.equals(freightInvoice.getCurrency())){
					freightInvoice.setRemainCount(freightInvoice.getRemainCount() + moneyCount);
					freightInvoice.setEliminateCount(freightInvoice.getMoneyCount() - freightInvoice.getRemainCount());
					if(freightInvoice.getEliminateCount() == 0){
						freightInvoice.setStatus("待销账");
					}else{
						freightInvoice.setStatus("销账中");
					}
					freightInvoiceDao.modify(freightInvoice);
					fasReconcileDao.delete(fasReconcileId);
					
					FasPay fasPay = fasPayDao.getById(fasReconcile.getTargetId());
					FasReconcile filter = new FasReconcile();
					filter.setTargetId(fasReconcile.getTargetId());
					if(fasReconcileDao.count(filter) == 0){
						fasPay.setStatus("未提交");
					}else{
						fasPay.setStatus("已生成");
					}
					
					fasPayDao.modify(fasPay);
				}
			}
		}
		
	}

	@Override
	public String getNextPayNumber() {
		String sql = "SELECT CONCAT('FK', DATE_FORMAT(SYSDATE(),'%Y%m%d'), REPEAT('0',5 - LENGTH(MAX(RIGHT(PAY_NUMBER, 5)) + 1)),MAX(RIGHT(PAY_NUMBER, 5)) + 1)"
				+ " FROM FAS_PAY";
		String number  = (String)jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(number)){
			number = "FK" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00001";
		}
		return number;
	}

	@Override
	public void initFasPay(String fasPayId) {
		FasPay fasPay = fasPayDao.getById(fasPayId);
		
		FasReconcile filter = new FasReconcile();
		filter.setTargetId(fasPayId);
		List<FasReconcile> fasReconciles = fasReconcileDao.queryForList(filter);
		StringBuilder involveOrderNumber = new StringBuilder();
		for(FasReconcile item : fasReconciles){
			FreightInvoice invoice = freightInvoiceDao.getById(item.getSourceId());
			List<FreightOrder> freightOrders = freightOrderDao.getByFreightStatementId(invoice.getFreightStatement().getId());
			if(freightOrders != null && !freightOrders.isEmpty()){
				for(FreightOrder freightOrder : freightOrders){
					if(involveOrderNumber.indexOf(freightOrder.getOrderNumber()) < 0){
						involveOrderNumber.append(freightOrder.getOrderNumber() + ",");
					}
				}
			}
		}
		if(involveOrderNumber.length() > 0){
			involveOrderNumber.deleteCharAt(involveOrderNumber.lastIndexOf(","));
			fasPay.setInvolveOrderNumber(involveOrderNumber.toString());
		}else{
			fasPay.setInvolveOrderNumber("无");
		}
		
		if(fasReconciles == null || fasReconciles.isEmpty()){
			fasPay.setStatus("未提交");
		}else{
			fasPay.setStatus("已生成");
		}
	}
}

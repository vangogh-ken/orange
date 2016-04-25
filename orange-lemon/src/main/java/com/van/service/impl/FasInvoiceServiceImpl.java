package com.van.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasInvoiceDao;
import com.van.halley.db.persistence.entity.FasInvoice;
import com.van.halley.util.StringUtil;
import com.van.service.FasInvoiceService;

@Transactional
@Service("fasInvoiceService")
public class FasInvoiceServiceImpl implements FasInvoiceService {
	private static Logger logger = LoggerFactory.getLogger(FasInvoiceServiceImpl.class);
	@Autowired
	private FasInvoiceDao fasInvoiceDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FasInvoice> getAll() {
		return fasInvoiceDao.getAll();
	}

	public List<FasInvoice> queryForList(FasInvoice fasInvoice) {
		return fasInvoiceDao.queryForList(fasInvoice);
	}

	public FasInvoice queryForOne(FasInvoice fasInvoice) {
		return fasInvoiceDao.queryForOne(fasInvoice);
	}

	public PageView query(PageView pageView, FasInvoice fasInvoice) {
		List<FasInvoice> list = fasInvoiceDao.query(pageView, fasInvoice);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasInvoice fasInvoice) {
		try{
			Integer.parseInt(fasInvoice.getInvoiceNumber());
			FasInvoice filter = new FasInvoice();
			filter.setInvoiceNumber(fasInvoice.getInvoiceNumber());
			filter.setFasInvoiceType(fasInvoice.getFasInvoiceType());
			if(fasInvoiceDao.count(filter) == 0){
				fasInvoiceDao.add(fasInvoice);
			}
		}catch(NumberFormatException e){
			logger.info("添加发票出错: 发票号:{}, 错误信息{}", fasInvoice.getInvoiceNumber(), e);
		}
		
	}

	public void delete(String id) {
		fasInvoiceDao.delete(id);
	}

	public void modify(FasInvoice fasInvoice) {
		fasInvoiceDao.modify(fasInvoice);
	}

	public FasInvoice getById(String id) {
		return fasInvoiceDao.getById(id);
	}

	@Override
	public int addBatch(FasInvoice fasInvoice, String startInvoiceNumber, String endInvoiceNumber) {
		try{
			if(startInvoiceNumber.length() != endInvoiceNumber.length() && Integer.parseInt(startInvoiceNumber) >= Integer.parseInt(endInvoiceNumber)){
				return 0;
			}else{
				fasInvoice.setStatus("可用");
				fasInvoice.setRecordTime(new Date());
				int length = startInvoiceNumber.length();
				int start = Integer.parseInt(startInvoiceNumber);
				int end = Integer.parseInt(endInvoiceNumber);
				int count = 0;
				for(int i=start; i<end+1; i++){
					StringBuilder newInvoiceNumber = new StringBuilder(String.valueOf(i));
					if(length > newInvoiceNumber.length()){
						for(int j=0, len=length-newInvoiceNumber.length(); j<len; j++){
							newInvoiceNumber.insert(0, 0);
						}
					}
					
					FasInvoice filter = new FasInvoice();
					filter.setInvoiceNumber(newInvoiceNumber.toString());
					filter.setFasInvoiceType(fasInvoice.getFasInvoiceType());
					if(fasInvoiceDao.count(filter) > 0){
						break;
					}else{
						fasInvoice.setInvoiceNumber(newInvoiceNumber.toString());
						fasInvoiceDao.add(fasInvoice);
						count++;
					}
				}
				
				return count;
			}
		}catch(NumberFormatException e){
			logger.info("批量导入发票出错: 起始发票号:{}, 终止发票号:{}, 错误信息{}", startInvoiceNumber, endInvoiceNumber, e);
			return 0;
		}
	}

	@Override
	public String getNextVirtualInvoiceNumber() {
		String sql = "SELECT CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m'), REPEAT('0',3 - LENGTH(MAX(RIGHT(INVOICE_NUMBER, 3)) + 1)),MAX(RIGHT(INVOICE_NUMBER, 3)) + 1)"
				+ " FROM FAS_INVOICE WHERE INCOME_OR_EXPENSE = '付' AND LEFT(INVOICE_NUMBER, 6) = DATE_FORMAT(SYSDATE(),'%Y%m')";
		String invoiceNumber  = jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(invoiceNumber)){
			invoiceNumber = new SimpleDateFormat("yyyyMM").format(new Date()) + "001";
		}
		return invoiceNumber;
	}

	@Override
	public String getNextOffsetInvoiceNumber() {
		String sql = "SELECT CONCAT(DATE_FORMAT(SYSDATE(),'%Y%m'), REPEAT('0',3 - LENGTH(MAX(MID(INVOICE_NUMBER, 7, 3)) + 1)), MAX(MID(INVOICE_NUMBER, 7, 3)) + 1, '-CD')"
				+ " FROM FAS_INVOICE WHERE LEFT(INVOICE_NUMBER, 6) = DATE_FORMAT(SYSDATE(),'%Y%m') AND RIGHT(INVOICE_NUMBER, 2)='CD'";
		String invoiceNumber  = jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(invoiceNumber)){
			invoiceNumber = new SimpleDateFormat("yyyyMM").format(new Date()) + "001-CD";
		}
		return invoiceNumber;
	}
	
}

package com.van.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasAccountDao;
import com.van.halley.db.persistence.FasDueDao;
import com.van.halley.db.persistence.FasPayDao;
import com.van.halley.db.persistence.FasReconcileDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.FreightInvoiceOffsetDao;
import com.van.halley.db.persistence.entity.FasReconcile;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;
import com.van.halley.util.StringUtil;
import com.van.service.FasReconcileService;

@Transactional
@Service("fasReconcileService")
public class FasReconcileServiceImpl implements FasReconcileService {
	private static Logger logger = LoggerFactory.getLogger(FasReconcileServiceImpl.class);
	@Autowired
	private FasReconcileDao fasReconcileDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightInvoiceOffsetDao freightInvoiceOffsetDao;
	@Autowired
	private FasPayDao fasPayDao;
	@Autowired
	private FasDueDao fasDueDao;
	@Autowired
	private FasAccountDao fasAccountDao;

	public List<FasReconcile> getAll() {
		return fasReconcileDao.getAll();
	}

	public List<FasReconcile> queryForList(FasReconcile fasReconcile) {
		return fasReconcileDao.queryForList(fasReconcile);
	}

	public FasReconcile queryForOne(FasReconcile fasReconcile) {
		return fasReconcileDao.queryForOne(fasReconcile);
	}

	public PageView query(PageView pageView, FasReconcile fasReconcile) {
		List<FasReconcile> list = fasReconcileDao.query(pageView, fasReconcile);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FasReconcile fasReconcile) {
		fasReconcileDao.add(fasReconcile);
	}

	public void delete(String id) {
		fasReconcileDao.delete(id);
	}

	public void modify(FasReconcile fasReconcile) {
		fasReconcileDao.modify(fasReconcile);
	}

	public FasReconcile getById(String id) {
		return fasReconcileDao.getById(id);
	}

	@Override
	public List<List<String>> toExportByFasPayID(String fasPayId) {
		FasReconcile filter = new FasReconcile();
		filter.setTargetId(fasPayId);
		List<FasReconcile> fasReconciles = fasReconcileDao.queryForList(filter);
		List<List<String>> values = new ArrayList<List<String>>();
		for(FasReconcile item : fasReconciles){
			List<String> singleValue = new ArrayList<String>();
			FreightInvoice freightInvoice = freightInvoiceDao.getById(item.getSourceId());
			List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightInvoice.getFreightStatement().getId());
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
			singleValue.add(freightInvoice.getFreightPart().getPartName());
			singleValue.add(StringUtil.parseDateTime(freightInvoice.getReleaseTime()));
			singleValue.add(freightInvoice.getFasInvoice().getInvoiceNumber());
			singleValue.add(freightInvoice.getFasInvoiceType().getTypeName());
			singleValue.add(freightInvoice.getDescn());
			if("人民币".equals(freightInvoice.getCurrency())){
				singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
				singleValue.add("");
			}else{
				singleValue.add("");
				singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
			}
			
			singleValue.add(String.valueOf(item.getMoneyCount()));
			singleValue.add(StringUtil.parseDateTime(item.getCreateTime()));
			values.add(singleValue);
		}
		
		//分割两行
		List<String> segmentation = new ArrayList<String>();
		segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");
		segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");
		segmentation.add("");
		values.add(segmentation);
		values.add(segmentation);
		
		List<String> title = new ArrayList<String>();
		title.add("业务员");title.add("订单号");title.add("单位名称");title.add("开票日期");title.add("发票号");
		title.add("发票类型");title.add("内容摘要");title.add("应收(人民币)");title.add("应收(美元)");title.add("销账金额");
		title.add("销账时间");
		values.add(title);
		
		//此部分是查找冲抵的数据
		for(FasReconcile item : fasReconciles){
			List<FreightInvoice> freightInvoices = freightInvoiceDao.getHasOffsetInvoice(item.getSourceId());
			if(freightInvoices != null && !freightInvoices.isEmpty()){
				for(FreightInvoice freightInvoice : freightInvoices){
					List<String> singleValue = new ArrayList<String>();
					List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightInvoice.getFreightStatement().getId());
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
					singleValue.add(freightInvoice.getFreightPart().getPartName());
					singleValue.add(StringUtil.parseDateTime(freightInvoice.getReleaseTime()));
					singleValue.add(freightInvoice.getFasInvoice().getInvoiceNumber());
					singleValue.add(freightInvoice.getFasInvoiceType().getTypeName());
					singleValue.add(freightInvoice.getDescn());
					if("人民币".equals(freightInvoice.getCurrency())){
						singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
						singleValue.add("");
					}else{
						singleValue.add("");
						singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
					}
					
					FreightInvoiceOffset filterOffset = new FreightInvoiceOffset();
					filterOffset.setFreightInvoiceIdA(item.getSourceId());
					filterOffset.setFreightInvoiceIdB(freightInvoice.getId());
					FreightInvoiceOffset freightInvoiceOffset = freightInvoiceOffsetDao.queryForOne(filterOffset);
					singleValue.add(String.valueOf(
							freightInvoiceOffset.getEliminateCountRmbB() > 0 ? 
									freightInvoiceOffset.getEliminateCountRmbB(): freightInvoiceOffset.getEliminateCountDollarB()));
					
					singleValue.add(StringUtil.parseDateTime(item.getCreateTime()));
					values.add(singleValue);
				}
			}
		}
		return values;
	}

	@Override
	public List<List<String>> toExportByFasDueID(String fasDueId) {
		FasReconcile filter = new FasReconcile();
		filter.setTargetId(fasDueId);
		List<FasReconcile> fasReconciles = fasReconcileDao.queryForList(filter);
		List<List<String>> values = new ArrayList<List<String>>();
		for(FasReconcile item : fasReconciles){
			List<String> singleValue = new ArrayList<String>();
			FreightInvoice freightInvoice = freightInvoiceDao.getById(item.getSourceId());
			List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightInvoice.getFreightStatement().getId());
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
			singleValue.add(freightInvoice.getFreightPart().getPartName());
			singleValue.add(StringUtil.parseDateTime(freightInvoice.getReleaseTime()));
			singleValue.add(freightInvoice.getFasInvoice().getInvoiceNumber());
			singleValue.add(freightInvoice.getFasInvoiceType().getTypeName());
			singleValue.add(freightInvoice.getDescn());
			if("人民币".equals(freightInvoice.getCurrency())){
				singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
				singleValue.add("");
			}else{
				singleValue.add("");
				singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
			}
			
			singleValue.add(String.valueOf(item.getMoneyCount()));
			singleValue.add(StringUtil.parseDateTime(item.getCreateTime()));
			values.add(singleValue);
		}
		
		//分割两行
		List<String> segmentation = new ArrayList<String>();
		segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");
		segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");segmentation.add("");
		segmentation.add("");
		values.add(segmentation);
		values.add(segmentation);
		
		List<String> title = new ArrayList<String>();
		title.add("业务员");title.add("订单号");title.add("单位名称");title.add("开票日期");title.add("发票号");
		title.add("发票类型");title.add("内容摘要");title.add("应付(人民币)");title.add("应付(美元)");title.add("销账金额");
		title.add("销账时间");
		values.add(title);
		
		//此部分是查找冲抵的数据
		for(FasReconcile item : fasReconciles){
			List<FreightInvoice> freightInvoices = freightInvoiceDao.getHasOffsetInvoice(item.getSourceId());
			if(freightInvoices != null && !freightInvoices.isEmpty()){
				for(FreightInvoice freightInvoice : freightInvoices){
					List<String> singleValue = new ArrayList<String>();
					List<FreightExpense> freightExpenses = freightExpenseDao.getByFreightStatementId(freightInvoice.getFreightStatement().getId());
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
					singleValue.add(freightInvoice.getFreightPart().getPartName());
					singleValue.add(StringUtil.parseDateTime(freightInvoice.getReleaseTime()));
					singleValue.add(freightInvoice.getFasInvoice().getInvoiceNumber());
					singleValue.add(freightInvoice.getFasInvoiceType().getTypeName());
					singleValue.add(freightInvoice.getDescn());
					if("人民币".equals(freightInvoice.getCurrency())){
						singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
						singleValue.add("");
					}else{
						singleValue.add("");
						singleValue.add(String.valueOf(freightInvoice.getMoneyCount()));
					}
					
					FreightInvoiceOffset filterOffset = new FreightInvoiceOffset();
					filterOffset.setFreightInvoiceIdA(item.getSourceId());
					filterOffset.setFreightInvoiceIdB(freightInvoice.getId());
					FreightInvoiceOffset freightInvoiceOffset = freightInvoiceOffsetDao.queryForOne(filterOffset);
					singleValue.add(String.valueOf(
							freightInvoiceOffset.getEliminateCountRmbB() > 0 ? 
									freightInvoiceOffset.getEliminateCountRmbB(): freightInvoiceOffset.getEliminateCountDollarB()));
					
					singleValue.add(StringUtil.parseDateTime(item.getCreateTime()));
					values.add(singleValue);
				}
			}
		}
		return values;
	}

}

package com.van.halley.rep.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.core.aspect.ReflectInvokeUtil;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.rep.data.dto.FeeIncomeDTO;
import com.van.halley.rep.data.dto.FeePaymentDTO;
import com.van.halley.util.StringUtil;

public class FreightDataSource {
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	
	/**
	 * 收入支出统计表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getFreightOrderFilterByDate(Map<String, Object> params){
		//String KSSJ = StringUtil.customFormat((Date)params.get("KSSJ"), "yyyy-MM-dd");
		//String JSSJ = StringUtil.customFormat((Date)params.get("JSSJ"), "yyyy-MM-dd");
		String KSSJ = (String)params.get("KSSJ");
		String JSSJ = (String)params.get("JSSJ");
		
		FreightOrder filter = new FreightOrder();
		int totalCount = freightOrderDao.count(filter);
		
		PageView<FreightOrder> pageView = new PageView<FreightOrder>(totalCount, 1);
		pageView.setFilterText(" PLACE_TIME > '" + KSSJ + "' AND PLACE_TIME < '" + JSSJ + "'");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" ORDER_NUMBER, (CONVERT( DELEGATE_PART USING gbk ) COLLATE gbk_chinese_ci) ");
		List<FreightOrder> freightOrders = freightOrderDao.query(pageView, filter);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ORDERS", freightOrders);
		
		List<Map<String, Object>> list = ReflectInvokeUtil.groupObject(FreightOrder.class, freightOrders, "delegatePart");
		result.put("ORDERS_G", list);//分组之后的数据
		
		return result;
	}
	
	/**
	 * 收入统计表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getIncomeFreightExpenseFilterByDate(Map<String, Object> params){
		//String KSSJ = StringUtil.customFormat((Date)params.get("KSSJ"), "yyyy-MM-dd");
		//String JSSJ = StringUtil.customFormat((Date)params.get("JSSJ"), "yyyy-MM-dd");
		String KSSJ = (String)params.get("KSSJ");
		String JSSJ = (String)params.get("JSSJ");
		
		FreightExpense filter = new FreightExpense();
		filter.setIncomeOrExpense("收");
		filter.setCurrency("人民币");
		int totalCount = freightExpenseDao.count(filter);
		PageView<FreightExpense> pageView = new PageView<FreightExpense>(totalCount, 1);
		
		pageView.setFilterText(" FRE_ORDER_ID IN( SELECT ID FROM FRE_ORDER WHERE PLACE_TIME > '" + KSSJ + "' AND PLACE_TIME < '" + JSSJ + "')");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" EXPENSE_NUMBER, FRE_PART_ID_B, FAS_INVOICE_TYPE_ID ");
		
		List<FreightExpense> freightExpenseRmbs = freightExpenseDao.query(pageView, filter);
		 
		filter.setCurrency("美元");
		totalCount = freightExpenseDao.count(filter);
		pageView = new PageView<FreightExpense>(totalCount, 1);
		pageView.setFilterText(" FRE_ORDER_ID IN( SELECT ID FROM FRE_ORDER WHERE PLACE_TIME > '" + KSSJ + "' AND PLACE_TIME < '" + JSSJ + "')");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" EXPENSE_NUMBER, FRE_PART_ID_B, FAS_INVOICE_TYPE_ID ");
		
		List<FreightExpense> freightExpenseDollars = freightExpenseDao.query(pageView, filter);
		
		List<FreightExpense> list = new ArrayList<FreightExpense>();
		list.addAll(freightExpenseRmbs);
		list.addAll(freightExpenseDollars);
		
		List<FeeIncomeDTO> result = new ArrayList<FeeIncomeDTO>();
		for(FreightExpense item : list){
			boolean flag = false;
			for(FeeIncomeDTO key : result){
				if(item.getFreightPartB().getPartName().equals(key.getPartName())
						&& item.getFasInvoiceType().getTaxRate() == key.getTaxRate()){
					key.setOrderNumber((key.getOrderNumber() == null ? "" : (key.getOrderNumber() + ","))+ item.getExpenseNumber().substring(0, 14));
					if(item.getCurrency().equals("人民币")){
						key.setIncomeAmountRmb(key.getIncomeAmountRmb() + item.getPredictAmount());
						key.setIncomeTaxRmb(key.getIncomeTaxRmb() + item.getTaxation()/item.getExchangeRate());
					}else if(item.getCurrency().equals("美元")){
						key.setIncomeAmountUsd(key.getIncomeAmountUsd() + item.getPredictAmount());
						key.setIncomeTaxUsd(key.getIncomeTaxUsd() + item.getTaxation()/item.getExchangeRate());
					}
					
					flag = true;
					break;
				}
			}
			//没有匹配上
			if(!flag){
				FeeIncomeDTO feeInfo = new FeeIncomeDTO();
				feeInfo.setOrderNumber(item.getExpenseNumber().substring(0, 14));
				feeInfo.setPartName(item.getFreightPartB().getPartName());
				feeInfo.setTaxRate(item.getFasInvoiceType().getTaxRate());
				if(item.getCurrency().equals("人民币")){
					feeInfo.setIncomeAmountRmb(feeInfo.getIncomeAmountRmb() + item.getPredictAmount());
					feeInfo.setIncomeTaxRmb(feeInfo.getIncomeTaxRmb() + item.getTaxation()/item.getExchangeRate());
				}else if(item.getCurrency().equals("美元")){
					feeInfo.setIncomeAmountUsd(feeInfo.getIncomeAmountUsd() + item.getPredictAmount());
					feeInfo.setIncomeTaxUsd(feeInfo.getIncomeTaxUsd() + item.getTaxation()/item.getExchangeRate());
				}
				result.add(feeInfo);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FEE", result);
		return map;
	}
	
	/**
	 * 支出统计表
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPaymentFreightExpenseFilterByDate(Map<String, Object> params){
		//String KSSJ = StringUtil.customFormat((Date)params.get("KSSJ"), "yyyy-MM-dd");
		//String JSSJ = StringUtil.customFormat((Date)params.get("JSSJ"), "yyyy-MM-dd");
		String KSSJ = (String)params.get("KSSJ");
		String JSSJ = (String)params.get("JSSJ");
		
		FreightExpense filter = new FreightExpense();
		filter.setIncomeOrExpense("付");
		filter.setCurrency("人民币");
		int totalCount = freightExpenseDao.count(filter);
		PageView<FreightExpense> pageView = new PageView<FreightExpense>(totalCount, 1);
		
		pageView.setFilterText(" FRE_ORDER_ID IN( SELECT ID FROM FRE_ORDER WHERE PLACE_TIME > '" + KSSJ + "' AND PLACE_TIME < '" + JSSJ + "')");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" EXPENSE_NUMBER, FRE_PART_ID_B, FAS_INVOICE_TYPE_ID ");
		
		List<FreightExpense> freightExpenseRmbs = freightExpenseDao.query(pageView, filter);
		 
		filter.setCurrency("美元");
		totalCount = freightExpenseDao.count(filter);
		pageView = new PageView<FreightExpense>(totalCount, 1);
		pageView.setFilterText(" FRE_ORDER_ID IN( SELECT ID FROM FRE_ORDER WHERE PLACE_TIME > '" + KSSJ + "' AND PLACE_TIME < '" + JSSJ + "')");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" EXPENSE_NUMBER, FRE_PART_ID_B, FAS_INVOICE_TYPE_ID ");
		
		List<FreightExpense> freightExpenseDollars = freightExpenseDao.query(pageView, filter);
		
		List<FreightExpense> list = new ArrayList<FreightExpense>();
		list.addAll(freightExpenseRmbs);
		list.addAll(freightExpenseDollars);
		
		List<FeePaymentDTO> result = new ArrayList<FeePaymentDTO>();
		for(FreightExpense item : list){
			boolean flag = false;
			for(FeePaymentDTO key : result){
				if(item.getFreightPartB().getPartName().equals(key.getPartName())
						&& item.getFasInvoiceType().getTaxRate() == key.getTaxRate()){
					key.setOrderNumber((key.getOrderNumber() == null ? "" : (key.getOrderNumber() + ","))+ item.getExpenseNumber().substring(0, 14));
					if(item.getCurrency().equals("人民币")){
						key.setPaymentAmountRmb(key.getPaymentAmountRmb() + item.getPredictAmount());
						key.setPaymentTaxRmb(key.getPaymentTaxRmb() + item.getTaxation()/item.getExchangeRate());
					}else if(item.getCurrency().equals("美元")){
						key.setPaymentAmountUsd(key.getPaymentAmountUsd() + item.getPredictAmount());
						key.setPaymentTaxUsd(key.getPaymentTaxUsd() + item.getTaxation()/item.getExchangeRate());
					}
					
					flag = true;
					break;
				}
			}
			//没有匹配上
			if(!flag){
				FeePaymentDTO feeInfo = new FeePaymentDTO();
				feeInfo.setOrderNumber(item.getExpenseNumber().substring(0, 14));
				feeInfo.setPartName(item.getFreightPartB().getPartName());
				feeInfo.setTaxRate(item.getFasInvoiceType().getTaxRate());
				if(item.getCurrency().equals("人民币")){
					feeInfo.setPaymentAmountRmb(feeInfo.getPaymentAmountRmb() + item.getPredictAmount());
					feeInfo.setPaymentTaxRmb(feeInfo.getPaymentTaxRmb() + item.getTaxation()/item.getExchangeRate());
				}else if(item.getCurrency().equals("美元")){
					feeInfo.setPaymentAmountUsd(feeInfo.getPaymentAmountUsd() + item.getPredictAmount());
					feeInfo.setPaymentTaxUsd(feeInfo.getPaymentTaxUsd() + item.getTaxation()/item.getExchangeRate());
				}
				result.add(feeInfo);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FEE", result);
		return map;
	}
	
	/**
	 * 销售
	 * @param params
	 * @return
	 */
	public Map<String, Object> getFreightOrderBalanceFilterBySalesmanAndDate(Map<String, Object> params){
		int TJNF = (int)params.get("TJNF");
		int TJYF = (int)params.get("TJYF");
		String currentDisplayName = (String)params.get("CURRENT_DISPLAY_NAME");
		
		FreightOrder filter = new FreightOrder();
		int totalCount = freightOrderDao.count(filter);
		PageView<FreightOrder> pageView = new PageView<FreightOrder>(totalCount, 1);
		pageView.setFilterText(" YEAR(PLACE_TIME) =" + TJNF + " AND MONTH(PLACE_TIME) =" + TJYF + " AND SALESMAN='" + currentDisplayName + "'");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" ORDER_NUMBER, (CONVERT( DELEGATE_PART USING gbk ) COLLATE gbk_chinese_ci) ");
		List<FreightOrder> freightOrders = freightOrderDao.query(pageView, filter);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ORDER", freightOrders);
		return result;
	}
	
	/**
	 * 下单
	 * @param params
	 * @return
	 */
	public Map<String, Object> getFreightOrderBalanceFilterByPlacemanAndDate(Map<String, Object> params){
		int TJNF = (int)params.get("TJNF");
		int TJYF = (int)params.get("TJYF");
		String currentUserId = (String)params.get("CURRENT_USER_ID");
		
		FreightOrder filter = new FreightOrder();
		int totalCount = freightOrderDao.count(filter);
		PageView<FreightOrder> pageView = new PageView<FreightOrder>(totalCount, 1);
		pageView.setFilterText(" YEAR(PLACE_TIME) =" + TJNF + " AND MONTH(PLACE_TIME) =" + TJYF + " AND CREATOR_USER_ID='" + currentUserId + "'");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" ORDER_NUMBER, (CONVERT( DELEGATE_PART USING gbk ) COLLATE gbk_chinese_ci) ");
		List<FreightOrder> freightOrders = freightOrderDao.query(pageView, filter);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ORDER", freightOrders);
		return result;
	}
	
	/**
	 * 部门
	 * @param params
	 * @return
	 */
	public Map<String, Object> getFreightOrderBalanceFilterByDepartmentAndDate(Map<String, Object> params){
		int TJNF = (int)params.get("TJNF");
		int TJYF = (int)params.get("TJYF");
		String currentOrgEntityId = (String)params.get("CURRENT_ORG_ENTITY_ID");
		
		FreightOrder filter = new FreightOrder();
		int totalCount = freightOrderDao.count(filter);
		PageView<FreightOrder> pageView = new PageView<FreightOrder>(totalCount, 1);
		pageView.setFilterText(" YEAR(PLACE_TIME) =" + TJNF + " AND MONTH(PLACE_TIME) =" + TJYF + " AND ORG_ENTITY_ID='" + currentOrgEntityId + "'");
		pageView.setOrder("ASC");
		pageView.setOrderBy(" ORDER_NUMBER, (CONVERT( DELEGATE_PART USING gbk ) COLLATE gbk_chinese_ci) ");
		List<FreightOrder> freightOrders = freightOrderDao.query(pageView, filter);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ORDER", freightOrders);
		return result;
	}
}

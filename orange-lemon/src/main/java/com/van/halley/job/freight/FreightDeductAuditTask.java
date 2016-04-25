package com.van.halley.job.freight;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.FreightDeductDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.FreightInvoiceOffsetDao;
import com.van.halley.db.persistence.FreightStatementOffsetDao;
import com.van.halley.db.persistence.entity.FreightDeduct;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.FreightStatementOffset;

@Component
public class FreightDeductAuditTask {
	private static Logger LOG = LoggerFactory.getLogger(FreightDeductAuditTask.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FreightDeductDao freightDeductDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private FreightInvoiceOffsetDao freightInvoiceOffsetDao;
	@Autowired
	private FreightStatementOffsetDao freightStatementOffsetDao;

	/**
	 * 该任务用于已提成，有修改状态下的提成进行再审核
	 */
	@Scheduled(cron = "0 0 8,12,16 * * ?") // 每天8点、12点、16点执行小时执行
	public void execute() {
		//String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != '" + FreightDeduct.DEDUCT_HAVE
				//+ "' AND D.STATUS != '" + FreightDeduct.DEDUCT_REVISED + "'  AND D.DEDUCT_TYPE='订单提成'";
		
		String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS = '" + FreightDeduct.DEDUCT_HAVE + "' AND D.DEDUCT_TYPE='订单提成'";
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if (freightDeductIds != null && !freightDeductIds.isEmpty()) {
			for (String freightDeductId : freightDeductIds) {
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				FreightOrder freightOrder = freightDeduct.getFreightOrder();
				boolean flag = true; // 是否应该设置已销账时间
				Date reconcileTime = null;
				// 相关账单
				Map<String, FreightStatement> freightStatementMap = new HashMap<String, FreightStatement>();
				FreightExpense filterEX = new FreightExpense();
				filterEX.setFreightOrder(freightOrder);
				filterEX.setIncomeOrExpense("收");
				List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(filterEX);
				for (FreightExpense freightExpense : freightExpenses) {
					if ("已对账".equals(freightExpense.getStatus())) {
						if (!freightStatementMap.containsKey(freightExpense.getFreightStatement().getId())) {
							freightStatementMap.put(freightExpense.getFreightStatement().getId(),
									freightExpense.getFreightStatement());
						}
					} else {
						flag = false;
						break;
					}
				}

				if (flag) {
					for (Entry<String, FreightStatement> entry : freightStatementMap.entrySet()) {
						if ("已开票".equals(((FreightStatement) entry.getValue()).getStatus())) {
							List<FreightInvoice> freightInvoices = freightInvoiceDao
									.getByFreightStatementId(entry.getKey());
							for (FreightInvoice freightInvoice : freightInvoices) {
								if ("已销账".equals(freightInvoice.getStatus())) {
									if (reconcileTime == null) {
										reconcileTime = freightInvoice.getEliminateTime();
									} else {
										if (reconcileTime.getTime() < freightInvoice.getEliminateTime().getTime()) {
											reconcileTime = freightInvoice.getEliminateTime();
										}
									}
								} else if ("已冲抵销账".equals(freightInvoice.getStatus())) {
									FreightInvoiceOffset filter = new FreightInvoiceOffset();
									filter.setFreightInvoiceIdA(freightInvoice.getId());
									List<FreightInvoiceOffset> offsets = freightInvoiceOffsetDao.queryForList(filter);
									for (FreightInvoiceOffset offset : offsets) {
										if (reconcileTime == null) {
											reconcileTime = offset.getCreateTime();
										} else {
											if (reconcileTime.getTime() < offset.getCreateTime().getTime()) {
												reconcileTime = offset.getCreateTime();
											}
										}
									}

									filter = new FreightInvoiceOffset();
									filter.setFreightInvoiceIdB(freightInvoice.getId());
									offsets = freightInvoiceOffsetDao.queryForList(filter);
									for (FreightInvoiceOffset offset : offsets) {
										if (reconcileTime == null) {
											reconcileTime = offset.getCreateTime();
										} else {
											if (reconcileTime.getTime() < offset.getCreateTime().getTime()) {
												reconcileTime = offset.getCreateTime();
											}
										}
									}
								} else if ("已批量冲抵销账".equals(freightInvoice.getStatus())) {
									FreightInvoiceOffset filter = new FreightInvoiceOffset();
									filter.setFreightInvoiceIdA(freightInvoice.getId());
									List<FreightInvoiceOffset> offsets = freightInvoiceOffsetDao.queryForList(filter);
									for (FreightInvoiceOffset offset : offsets) {
										if (reconcileTime == null) {
											reconcileTime = offset.getCreateTime();
										} else {
											if (reconcileTime.getTime() < offset.getCreateTime().getTime()) {
												reconcileTime = offset.getCreateTime();
											}
										}
									}
								} else {
									flag = false;
									break;
								}
							}
						} else if ("已冲抵销账".equals(entry.getValue().getStatus())) {
							// 冲抵与被冲抵都需要进行计算
							FreightStatementOffset filter = new FreightStatementOffset();
							filter.setFreightStatementIdA(entry.getKey());
							List<FreightStatementOffset> offsets = freightStatementOffsetDao.queryForList(filter);
							for (FreightStatementOffset offset : offsets) {
								if (reconcileTime == null) {
									reconcileTime = offset.getCreateTime();
								} else if (reconcileTime.getTime() < offset.getCreateTime().getTime()) {
									reconcileTime = offset.getCreateTime();// 以冲抵时间
								}
							}

							filter = new FreightStatementOffset();
							filter.setFreightStatementIdB(entry.getKey());
							offsets = freightStatementOffsetDao.queryForList(filter);
							for (FreightStatementOffset offset : offsets) {
								if (reconcileTime == null) {
									reconcileTime = offset.getCreateTime();
								} else if (reconcileTime.getTime() < offset.getCreateTime().getTime()) {
									reconcileTime = offset.getCreateTime();// 以冲抵时间
								}
							}
						} else {
							flag = false;
							break;
						}
					}
				}

				if (flag && reconcileTime != null) {
					
					/*freightDeduct.setReconcileTime(reconcileTime);
					freightDeduct.setSettleTimeSalesman(reconcileTime);
					freightDeduct.setSettleDoneSalesman(FreightDeduct.RECONCILE_HAVE);
					freightDeduct.setSettleTimeService(reconcileTime);
					freightDeduct.setSettleDoneService(FreightDeduct.RECONCILE_HAVE);
					freightDeduct.setStatus(FreightDeduct.RECONCILE_HAVE);
					freightDeductDao.modify(freightDeduct);*/
				}else{
					LOG.info("提成状态为 已提成，无修改，但实际未销账， 订单号: {}", freightDeduct.getFreightOrder().getOrderNumber());
				}
			}
		}
	}
}
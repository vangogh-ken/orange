package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightDeductDao;
import com.van.halley.db.persistence.FreightExpenseDao;
import com.van.halley.db.persistence.FreightInvoiceDao;
import com.van.halley.db.persistence.FreightInvoiceOffsetDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightStatementOffsetDao;
import com.van.halley.db.persistence.OrgEntityDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightDeduct;
import com.van.halley.db.persistence.entity.FreightExpense;
import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.halley.db.persistence.entity.FreightInvoiceOffset;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightStatement;
import com.van.halley.db.persistence.entity.FreightStatementOffset;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.fre.util.FreightVersionUtil;
import com.van.halley.util.StringUtil;
import com.van.service.FreightDeductService;

@Transactional
@Service("freightDeductService")
public class FreightDeductServiceImpl implements FreightDeductService {
	private static Logger LOG = LoggerFactory.getLogger(FreightDeductServiceImpl.class);
	@Autowired
	private FreightDeductDao freightDeductDao;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightExpenseDao freightExpenseDao;
	@Autowired
	private FreightInvoiceDao freightInvoiceDao;
	@Autowired
	private FreightStatementOffsetDao freightStatementOffsetDao;
	@Autowired
	private FreightInvoiceOffsetDao freightInvoiceOffsetDao;
	@Autowired
	private OrgEntityDao orgEntityDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightDeduct> getAll() {
		return freightDeductDao.getAll();
	}

	public List<FreightDeduct> queryForList(FreightDeduct freightDeduct) {
		return freightDeductDao.queryForList(freightDeduct);
	}

	public FreightDeduct queryForOne(FreightDeduct freightDeduct) {
		return freightDeductDao.queryForOne(freightDeduct);
	}

	public PageView<FreightDeduct> query(PageView<FreightDeduct> pageView, FreightDeduct freightDeduct) {
		List<FreightDeduct> list = freightDeductDao.query(pageView,
				freightDeduct);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightDeduct freightDeduct) {
		freightDeductDao.add(freightDeduct);
	}

	public void delete(String id) {
		freightDeductDao.delete(id);
	}

	public void modify(FreightDeduct freightDeduct) {
		freightDeductDao.modify(freightDeduct);
	}

	public FreightDeduct getById(String id) {
		return freightDeductDao.getById(id);
	}
	
	@PostConstruct
	public void init(){
		String sql = "UPDATE FRE_DEDUCT SET STATUS='" + FreightDeduct.RECONCILE_HAVNT + "' WHERE STATUS='F'";
		int count = jdbcTemplate.update(sql);
		LOG.debug("更新'未结算' 修改为 'RECONCILE_HAVNT' 状态成功: {} 条数据", count);
		sql = "UPDATE FRE_DEDUCT SET STATUS='" + FreightDeduct.RECONCILE_HAVE + "' WHERE STATUS='T'";
		count = jdbcTemplate.update(sql);
		LOG.debug("更新 '已结算' 修改为 'RECONCILE_HAVE' 状态成功: {} 条数据", count);
		sql = "UPDATE FRE_DEDUCT SET STATUS='" + FreightDeduct.DEDUCT_HAVE + "' WHERE STATUS='D'";
		count = jdbcTemplate.update(sql);
		LOG.debug("更新'已提成' 修改为 'DEDUCT_HAVE' 状态成功: {} 条数据", count);
		sql = "UPDATE FRE_DEDUCT SET STATUS='" + FreightDeduct.DEDUCT_REVISED + "' WHERE STATUS='DF'";
		count = jdbcTemplate.update(sql);
		LOG.debug("更新 '已提成且修改' 修改为 'DEDUCT_REVISED' 状态成功: {} 条数据", count);
		
		sql = "UPDATE FRE_ORDER SET ORDER_SCOPE='销售自揽' WHERE ORDER_SCOPE='自开发业务' AND PLACE_TIME > '2016-02-01'";
		count = jdbcTemplate.update(sql);
		LOG.debug("更新 '自开发业务' 修改为 '销售自揽' 状态成功: {} 条数据", count);
		sql = "UPDATE FRE_ORDER SET ORDER_SCOPE='公司指定' WHERE ORDER_SCOPE='公司业务' AND PLACE_TIME > '2016-02-01'";
		count = jdbcTemplate.update(sql);
		LOG.debug("更新 '公司业务' 修改为 '公司指定' 状态成功: {} 条数据", count);
		sql = "UPDATE FRE_ORDER SET ORDER_SCOPE='公司指定' WHERE ORDER_SCOPE='战略客户业务' AND PLACE_TIME > '2016-02-01'";
		count = jdbcTemplate.update(sql);
		LOG.debug("更新 '战略客户业务' 修改为 '公司指定' 状态成功: {} 条数据", count);
	}

	@Override
	public void synOrderDeduct() {
		//查询提成表中没有的。订单新增就都直接计入，但是未添加完毕的不显示
		//String sql = "SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_STATUS != '已作废' AND O.EXPENSE_STATUS='添加完毕' AND  NOT EXISTS (SELECT 1 FROM FRE_DEDUCT AS D WHERE D.DEDUCT_TYPE='订单提成' AND D.FRE_ORDER_ID=O.ID) LIMIT 100";
		//String sql = "SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_STATUS != '已作废' AND  NOT EXISTS (SELECT 1 FROM FRE_DEDUCT AS D WHERE D.DEDUCT_TYPE='订单提成' AND D.FRE_ORDER_ID=O.ID) LIMIT 100";
		String sql = "SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_STATUS != '已作废' AND  NOT EXISTS (SELECT 1 FROM FRE_DEDUCT AS D WHERE D.DEDUCT_TYPE='订单提成' AND D.FRE_ORDER_ID=O.ID)";
		List<String> freightOrderIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightOrderIds != null && !freightOrderIds.isEmpty()){
			for(String freightOrderId : freightOrderIds){
				FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
				
				FreightDeduct freightDeduct = new FreightDeduct();
				freightDeduct.setDeductType("订单提成");
				freightDeduct.setFreightOrder(freightOrder);
				freightDeduct.setOrgEntity(userDao.getByDisplayName(freightOrder.getSalesman()).getOrgEntity());
				freightDeduct.setOrderBalance(freightOrder.getBalance());
				
				freightDeduct.setSettleDoneSalesman(FreightDeduct.DEDUCT_PREPARED);
				freightDeduct.setSettleDoneService(FreightDeduct.DEDUCT_PREPARED);
				freightDeduct.setSettleDoneManipulator(FreightDeduct.DEDUCT_PREPARED);
				//freightDeduct.setStatus("F");
				freightDeduct.setStatus(FreightDeduct.DEDUCT_PREPARED);
				
				freightDeduct.setCreateTime(new Date());
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.add(freightDeduct);
			}
		}
		//如果未提成，非添加完毕，或已作废或订单已被删除的，则提成也删除。
		//sql = "SELECT D.ID FROM FRE_DEDUCT AS D LEFT JOIN FRE_ORDER AS O ON D.FRE_ORDER_ID=O.ID WHERE D.STATUS != 'D' AND D.STATUS != 'DF' AND (O.ORDER_STATUS = '已作废' OR O.EXPENSE_STATUS != '添加完毕' OR O.ID IS NULL) ";
		sql = "SELECT D.ID FROM FRE_DEDUCT AS D LEFT JOIN FRE_ORDER AS O ON D.FRE_ORDER_ID=O.ID WHERE D.STATUS != '" 
		+ FreightDeduct.DEDUCT_HAVE 
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_REVISED
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_NONE
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_SATE
		+ "' AND D.SETTLE_DONE_MANIPULATOR != '" 
		+ FreightDeduct.DEDUCT_HAVE
		+ "' AND (O.ORDER_STATUS = '已作废' OR O.ID IS NULL) ";//非添加完毕的也不动。
		//+ "' AND (O.ORDER_STATUS = '已作废' OR O.EXPENSE_STATUS != '添加完毕' OR O.ID IS NULL) ";
		
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				freightDeductDao.delete(freightDeductId);
			}
		}
		
		//如果已提成，非添加完毕，或已作废的状态都标记
		//sql = "SELECT D.ID FROM FRE_DEDUCT AS D LEFT JOIN FRE_ORDER AS O ON D.FRE_ORDER_ID=O.ID WHERE D.STATUS = 'D' AND (O.ORDER_STATUS = '已作废' OR O.EXPENSE_STATUS != '添加完毕' OR O.ID IS NULL) ";
		sql = "SELECT D.ID FROM FRE_DEDUCT AS D LEFT JOIN FRE_ORDER AS O ON D.FRE_ORDER_ID=O.ID WHERE D.STATUS = '" 
		+ FreightDeduct.DEDUCT_HAVE
		+ "' AND (O.ORDER_STATUS = '已作废' OR O.ID IS NULL) ";//非添加完毕的也不动。
		//+ "' AND (O.ORDER_STATUS = '已作废' OR O.EXPENSE_STATUS != '添加完毕' OR O.ID IS NULL) ";
		freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				freightDeduct.setStatus(FreightDeduct.DEDUCT_DELETED);
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.modify(freightDeduct);
			}
		}
		
		//~ 对已提成的数据收支差进行校验
		sql = "SELECT D.ID FROM FRE_DEDUCT AS D LEFT JOIN FRE_ORDER AS O ON D.FRE_ORDER_ID=O.ID WHERE D.STATUS = '" 
				+ FreightDeduct.DEDUCT_HAVE + "'";
		freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				if(Math.abs(freightDeduct.getOrderBalance() - freightDeduct.getFreightOrder().getBalance()) > 1){
					freightDeduct.setOrderBalance(freightDeduct.getFreightOrder().getBalance());
					freightDeduct.setModifyTime(new Date());
					freightDeductDao.modify(freightDeduct);
				}
			}
		}
		
		LOG.info("订单提成同步完成！");
	}

	@Override
	public void calculateOrderDeductV1() {
		//只要是非已提成的都重新计算提成
		//String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != 'D' AND D.STATUS != 'DF'  AND D.DEDUCT_TYPE='订单提成'";
		String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != '" 
		+ FreightDeduct.DEDUCT_HAVE 
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_REVISED
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_NONE
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_SATE
		+ "' AND D.DEDUCT_TYPE='订单提成'";
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				
				if(!FreightVersionUtil.assertV1(freightDeduct.getFreightOrder().getPlaceTime())){
					continue;
				}
				FreightOrder freightOrder = freightDeduct.getFreightOrder();
				OrgEntity orgEntity = freightDeduct.getOrgEntity();
				
				freightDeduct.setOrderBalance(freightOrder.getBalance());//重新设置收支差
				freightDeduct.setDeductBalance(freightOrder.getBalance());
				//首先对单位进行判断，铁水联运团队的人员原则上是谁的业务由谁来填写订单，因此，此处就取订单的orgEntityId
				OrgEntity orgEntityOfService = orgEntityDao.getById(freightOrder.getOrgEntityId());
				if("铁水市场一部".equals(orgEntityOfService.getOrgEntityName())
						|| "铁水市场二部".equals(orgEntityOfService.getOrgEntityName())
						|| "操作部(创源铁水联运团队)".equals(orgEntityOfService.getOrgEntityName())
						|| "业务一部(创源铁水联运团队)".equals(orgEntityOfService.getOrgEntityName())
						|| "业务二部(创源铁水联运团队)".equals(orgEntityOfService.getOrgEntityName())
						|| "客户服务部(创源铁水联运团队)".equals(orgEntityOfService.getOrgEntityName())){
					if("公司业务".equals(freightOrder.getOrderScope())){
						freightDeduct.setDeductCountSalesman(0);//铁水的业务提成都归结到客服提成
						freightDeduct.setDeductCountService(freightDeduct.getDeductBalance() * 0.05);
					}else if("自开发业务".equals(freightOrder.getOrderScope())){
						freightDeduct.setDeductCountSalesman(0);//铁水的业务提成都归结到客服提成
						freightDeduct.setDeductCountService(freightDeduct.getDeductBalance() * 0.12);
					}
				}else{
					if("公司业务".equals(freightOrder.getOrderScope())){
						if("市场一部".equals(orgEntity.getOrgEntityName()) ||
								"市场二部".equals(orgEntity.getOrgEntityName()) ||
								"市场三部".equals(orgEntity.getOrgEntityName()) ||
								"市场四部".equals(orgEntity.getOrgEntityName())){
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.03);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.2);
						}else if("项目维护及开发部".equals(orgEntity.getOrgEntityName())){
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.03);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.2);
						}else{//其他部门
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.03);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.2);
						}
					}else if("自开发业务".equals(freightOrder.getOrderScope())){
						if("市场一部".equals(orgEntity.getOrgEntityName()) ||
								"市场二部".equals(orgEntity.getOrgEntityName()) ||
								"市场三部".equals(orgEntity.getOrgEntityName()) ||
								"市场四部".equals(orgEntity.getOrgEntityName())){
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.2);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.1);
						}else if("项目维护及开发部".equals(orgEntity.getOrgEntityName())){
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.15);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.1);
						}else if("销售服务中心(客户服务部一组)".equals(orgEntity.getOrgEntityName()) ||
								"销售服务中心(客户服务部二组)".equals(orgEntity.getOrgEntityName()) ||
								"客户服务一部".equals(orgEntity.getOrgEntityName()) ||
								"客户服务二部".equals(orgEntity.getOrgEntityName())){
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.1);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.1);
						}else if("市场(系统)".equals(orgEntity.getOrgEntityName())){
							freightDeduct.setDeductCountSalesman(freightDeduct.getDeductBalance() * 0.1);
							freightDeduct.setDeductCountService(freightDeduct.getDeductBalance() * 0.1);
						}
					}else if("战略客户业务".equals(freightOrder.getOrderScope())){
						if("项目维护及开发部".equals(orgEntity.getOrgEntityName())){
							List<FreightBoxRequire> freightBoxRequires = freightOrder.getFreightBoxRequires();
							int teuCount = 0;
							for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
								if(freightBoxRequire.getBoxType().startsWith("4")){
									teuCount += freightBoxRequire.getBoxCount() * 2;
								}else if(freightBoxRequire.getBoxType().startsWith("2")){
									teuCount += freightBoxRequire.getBoxCount() * 1;
								}
							}
							
							freightDeduct.setDeductCountSalesman(teuCount * 5);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.2);
						}else{//其他部门
							List<FreightBoxRequire> freightBoxRequires = freightOrder.getFreightBoxRequires();
							int teuCount = 0;
							for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
								if(freightBoxRequire.getBoxType().startsWith("4")){
									teuCount += freightBoxRequire.getBoxCount() * 2;
								}else if(freightBoxRequire.getBoxType().startsWith("2")){
									teuCount += freightBoxRequire.getBoxCount() * 1;
								}
							}
							
							freightDeduct.setDeductCountSalesman(teuCount * 5);
							freightDeduct.setDeductCountService(freightDeduct.getDeductCountSalesman() * 0.2);
						}
					}
				}
				//计算操作票数
				if("外贸".equals(freightOrder.getFirstType()) && "出口".equals(freightOrder.getSecondType())
						&& ("整箱".equals(freightOrder.getThirdType()) || "散杂货".equals(freightOrder.getThirdType())
								|| "铁路整车".equals(freightOrder.getThirdType()) || "滚装".equals(freightOrder.getThirdType()))){
					int count = 0;
					List<FreightBoxRequire> freightBoxRequires = freightOrder.getFreightBoxRequires();
					if(freightBoxRequires != null && !freightBoxRequires.isEmpty()){
						for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
							count += freightBoxRequire.getBoxCount();
						}
					}else{
						count = 0;
					}
					
					if(count < 3){
						freightDeduct.setDeductCountManipulator(1 * 15);
					}else{
						freightDeduct.setDeductCountManipulator(1.5 * 15);
					}
				}else{
					freightDeduct.setDeductCountManipulator(0.4 * 15);
				}
				
				freightDeduct.setSettleDoneManipulator(FreightDeduct.DEDUCT_PREPARED);
				//将下单时间或创建时间作为操作提成时间
				freightDeduct.setSettleTimeManipulator(freightOrder.getPlaceTime() == null ?freightOrder.getCreateTime() : freightOrder.getPlaceTime());
				
				freightDeductDao.modify(freightDeduct);
			}
		}
		
		LOG.info("V1 订单提成计算完成！");
	}
	
	@Override
	public void calculateOrderDeductV2(){
		//只要是非已提成的都重新计算提成
		//String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != 'D' AND D.STATUS != 'DF'  AND D.DEDUCT_TYPE='订单提成'";
		String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != '" 
				+ FreightDeduct.DEDUCT_HAVE 
				+ "' AND D.STATUS != '" 
				+ FreightDeduct.DEDUCT_REVISED 
				+ "' AND D.DEDUCT_TYPE='订单提成'";
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				
				if(FreightVersionUtil.assertV1(freightDeduct.getFreightOrder().getPlaceTime())){
					continue;
				}
				FreightOrder freightOrder = freightDeduct.getFreightOrder();
				freightDeduct.setOrderBalance(freightOrder.getBalance());//重新设置收支差
				freightDeduct.setDeductBalance(freightOrder.getBalance());//此处已结去除税金收益
				double predictSaleman = 0;
				double predictService = 0;
				if("销售自揽".equals(freightOrder.getOrderScope())){//下单员录入
					predictSaleman = freightDeduct.getDeductBalance() * 0.18;
					predictService = 0;
				}else if("公司指定".equals(freightOrder.getOrderScope())){//客服员录入
					predictSaleman = freightDeduct.getDeductBalance() * 0.02;
					predictService = freightDeduct.getDeductBalance() * 0.01;
				}else if("客服自揽".equals(freightOrder.getOrderScope())){//客服员录入
					predictSaleman = freightDeduct.getDeductBalance() * 0.05;
					predictService = freightDeduct.getDeductBalance() * 0.01;
				}else if("客服自揽(项目)".equals(freightOrder.getOrderScope())){//客服员录入
					predictSaleman = freightDeduct.getDeductBalance() * 0.09;
					predictService = freightDeduct.getDeductBalance() * 0.01;
				}
				//未提成的，则根据销账时间或当前时间的资金占用成本来计算其实际应提成金额
				Date palceTime = freightOrder.getPlaceTime();//下单时间
				Date reconcileTime = freightDeduct.getReconcileTime() == null ? new Date() : freightDeduct.getReconcileTime();
				int delayDays = (int)((reconcileTime.getTime() - palceTime.getTime()) / (1000 * 60 * 60 * 24));
				
				//根据不同的类型进行处理
				if("内贸".equals(freightOrder.getFirstType()) && 
						"发运".equals(freightOrder.getSecondType()) && 
						"整箱".equals(freightOrder.getThirdType())){
					if(delayDays <= 100){
						freightDeduct.setDeductCountSalesman(predictSaleman);
						freightDeduct.setDeductCountService(predictService);
					}else{
						//按营收收入金额
						freightDeduct.setDeductCountSalesman(predictSaleman - freightOrder.getRevenueAmount() * (delayDays - 90) * 0.0005);
						freightDeduct.setDeductCountService(predictService);
						
						if(freightDeduct.getDeductCountSalesman() > 0){
							freightDeduct.setStatus(FreightDeduct.DEDUCT_NONE);//有提成则不提成
						}else{
							freightDeduct.setStatus(FreightDeduct.DEDUCT_SATE);//负的提成则需补偿
						}
					}
				}else{
					if(delayDays <= 75){
						freightDeduct.setDeductCountSalesman(predictSaleman);
						freightDeduct.setDeductCountService(predictService);
					}else{
						//按营收收入金额
						freightDeduct.setDeductCountSalesman(predictSaleman - freightOrder.getRevenueAmount() * (delayDays - 90) * 0.0005);
						freightDeduct.setDeductCountService(predictService);
					}
				}
				
				//计算操作票数
				//规则: 1. 外贸-出口-整箱、散杂货、铁路整车、滚装	2. 内贸-发运、到达-整箱
				if("外贸".equals(freightOrder.getFirstType()) && "出口".equals(freightOrder.getSecondType())
						&& ("整箱".equals(freightOrder.getThirdType()) || 
								"散杂货".equals(freightOrder.getThirdType())|| 
								"铁路整车".equals(freightOrder.getThirdType())|| 
								"滚装".equals(freightOrder.getThirdType()))){
					int count = 0;
					List<FreightBoxRequire> freightBoxRequires = freightOrder.getFreightBoxRequires();
					if(freightBoxRequires != null && !freightBoxRequires.isEmpty()){
						for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
							count += freightBoxRequire.getBoxCount();
						}
					}
					
					if(count < 3){
						freightDeduct.setDeductCountManipulator(1 * 15);
					}else{
						freightDeduct.setDeductCountManipulator(1.5 * 15);
					}
				}else if("内贸".equals(freightOrder.getFirstType()) && 
						("发运".equals(freightOrder.getSecondType()) || "到达".equals(freightOrder.getSecondType())
						&& "整箱".equals(freightOrder.getThirdType()))){
					int count = 0;
					List<FreightBoxRequire> freightBoxRequires = freightOrder.getFreightBoxRequires();
					if(freightBoxRequires != null && !freightBoxRequires.isEmpty()){
						for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
							count += freightBoxRequire.getBoxCount();
						}
					}
					
					if(count < 3){
						freightDeduct.setDeductCountManipulator(1 * 15);
					}else if(count >= 3 && count < 7){
						freightDeduct.setDeductCountManipulator(1.5 * 15);
					}else if(count > 6){
						freightDeduct.setDeductCountManipulator(2 * 15);
					}
				}else{
					freightDeduct.setDeductCountManipulator(0.4 * 15);
				}
				
				freightDeduct.setSettleDoneManipulator(FreightDeduct.DEDUCT_PREPARED);
				//将下单时间或创建时间作为操作提成时间
				freightDeduct.setSettleTimeManipulator(freightOrder.getPlaceTime() == null ?freightOrder.getCreateTime() : freightOrder.getPlaceTime());
				
				freightDeductDao.modify(freightDeduct);
			}
		}
		
		LOG.info("V2 订单提成计算完成！");
	}
/*
 * 查询全部数据的SQL
 * SELECT COUNT(1) AS C FROM FRE_ORDER AS O 
 * WHERE EXPENSE_STATUS !='未添加' 
 * AND NOT EXISTS (SELECT E.ID FROM FRE_EXPENSE AS E WHERE E.INCOME_OR_EXPENSE='收' AND STATUS != '已对账' AND E.FRE_ORDER_ID=O.ID)
 * AND NOT EXISTS (SELECT S.ID FROM FRE_STATEMENT AS S WHERE STATUS != '已开票' AND EXISTS (SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE AS E WHERE E.INCOME_OR_EXPENSE='收' AND E.FRE_ORDER_ID=O.ID AND E.FRE_STATEMENT_ID=S.ID))
 * AND NOT EXISTS (SELECT I.ID FROM FRE_INVOICE AS I WHERE STATUS != '已销账' AND EXISTS (SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE AS E WHERE E.INCOME_OR_EXPENSE='收' AND E.FRE_ORDER_ID=O.ID AND E.FRE_STATEMENT_ID=I.FRE_STATEMENT_ID))
 * 
 * 只要实际未发放提成的，则重新计算应提成时间
 * **/
	@Override
	public void settleDeductTime() {
		//String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE SETTLE_DONE_SALESMAN !='T' OR SETTLE_DONE_SERVISE != 'T' OR SETTLE_DONE_MANIPULATOR !='T' OR STATUS != 'D' OR STATUS != 'DF'";
		String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != '" 
		+ FreightDeduct.DEDUCT_HAVE 
		+ "' AND D.STATUS != '" 
		+ FreightDeduct.DEDUCT_REVISED 
		+ "'  AND D.DEDUCT_TYPE='订单提成'";
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				FreightOrder freightOrder = freightDeduct.getFreightOrder();
				boolean flag = true; //是否应该设置已销账时间
				Date reconcileTime = null;
				//相关账单
				Map<String, FreightStatement> freightStatementMap = new HashMap<String, FreightStatement>();
				FreightExpense filterEX = new FreightExpense();
				filterEX.setFreightOrder(freightOrder);
				filterEX.setIncomeOrExpense("收");
				List<FreightExpense> freightExpenses = freightExpenseDao.queryForList(filterEX);
				for(FreightExpense freightExpense : freightExpenses){
					if("F".equals(freightExpense.getFreightExpenseType().getRevenue())){//排除非营收费用
						continue;
					}
					if("已对账".equals(freightExpense.getStatus())){
						if(!freightStatementMap.containsKey(freightExpense.getFreightStatement().getId())){
							freightStatementMap.put(freightExpense.getFreightStatement().getId(), freightExpense.getFreightStatement());
						}
					}else{
						flag = false;
						break;
					}
				}
				
				if(flag){
					for(Entry<String, FreightStatement> entry : freightStatementMap.entrySet()){
						if("已开票".equals(((FreightStatement)entry.getValue()).getStatus())){
							List<FreightInvoice> freightInvoices = freightInvoiceDao.getByFreightStatementId(entry.getKey());
							for(FreightInvoice freightInvoice : freightInvoices){
								if("已销账".equals(freightInvoice.getStatus())){
									if(reconcileTime == null){
										reconcileTime = freightInvoice.getEliminateTime();
									}else{
										if(reconcileTime.getTime() < freightInvoice.getEliminateTime().getTime()){
											reconcileTime = freightInvoice.getEliminateTime();
										}
									}
								}else if("已冲抵销账".equals(freightInvoice.getStatus())){
									FreightInvoiceOffset filter = new FreightInvoiceOffset();
									filter.setFreightInvoiceIdA(freightInvoice.getId());
									List<FreightInvoiceOffset> offsets = freightInvoiceOffsetDao.queryForList(filter);
									for(FreightInvoiceOffset offset : offsets){
										if(reconcileTime == null){
											reconcileTime = offset.getCreateTime();
										}else{
											if(reconcileTime.getTime() < offset.getCreateTime().getTime()){
												reconcileTime = offset.getCreateTime();
											}
										}
									}
									
									filter = new FreightInvoiceOffset();
									filter.setFreightInvoiceIdB(freightInvoice.getId());
									offsets = freightInvoiceOffsetDao.queryForList(filter);
									for(FreightInvoiceOffset offset : offsets){
										if(reconcileTime == null){
											reconcileTime = offset.getCreateTime();
										}else{
											if(reconcileTime.getTime() < offset.getCreateTime().getTime()){
												reconcileTime = offset.getCreateTime();
											}
										}
									}
								}else if("已批量冲抵销账".equals(freightInvoice.getStatus())){
									FreightInvoiceOffset filter = new FreightInvoiceOffset();
									filter.setFreightInvoiceIdA(freightInvoice.getId());
									List<FreightInvoiceOffset> offsets = freightInvoiceOffsetDao.queryForList(filter);
									for(FreightInvoiceOffset offset : offsets){
										if(reconcileTime == null){
											reconcileTime = offset.getCreateTime();
										}else{
											if(reconcileTime.getTime() < offset.getCreateTime().getTime()){
												reconcileTime = offset.getCreateTime();
											}
										}
									}
								}else{
									flag = false;
									break;
								}
							}
						}else if("已冲抵销账".equals(entry.getValue().getStatus())){
							//冲抵与被冲抵都需要进行计算
							FreightStatementOffset filter = new FreightStatementOffset();
							filter.setFreightStatementIdA(entry.getKey());
							List<FreightStatementOffset> offsets = freightStatementOffsetDao.queryForList(filter);
							for(FreightStatementOffset offset : offsets){
								if(reconcileTime == null){
									reconcileTime = offset.getCreateTime();
								}else if(reconcileTime.getTime() < offset.getCreateTime().getTime()){
									reconcileTime = offset.getCreateTime();//以冲抵时间
								}
							}
							
							filter = new FreightStatementOffset();
							filter.setFreightStatementIdB(entry.getKey());
							offsets = freightStatementOffsetDao.queryForList(filter);
							for(FreightStatementOffset offset : offsets){
								if(reconcileTime == null){
									reconcileTime = offset.getCreateTime();
								}else if(reconcileTime.getTime() < offset.getCreateTime().getTime()){
									reconcileTime = offset.getCreateTime();//以冲抵时间
								}
							}
						}else{
							flag = false;
							break;
						}
					}
				}
				
				if(flag && reconcileTime != null){
					freightDeduct.setReconcileTime(reconcileTime);
					freightDeduct.setSettleTimeSalesman(reconcileTime);
					freightDeduct.setSettleDoneSalesman(FreightDeduct.RECONCILE_HAVE);
					freightDeduct.setSettleTimeService(reconcileTime);
					freightDeduct.setSettleDoneService(FreightDeduct.RECONCILE_HAVE);
					freightDeduct.setStatus(FreightDeduct.RECONCILE_HAVE);
					freightDeductDao.modify(freightDeduct);
				}
				
			}
			
			/***
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				FreightOrder freightOrder = freightDeduct.getFreightOrder();
				//所有费用已对账
				sql = "SELECT COUNT(1) AS C FROM FRE_ORDER AS O " + 
						" WHERE ID=? AND EXPENSE_STATUS !='未添加' " + 
						" AND EXISTS(SELECT 1 FROM (SELECT COUNT(1) AS C FROM FRE_EXPENSE AS E WHERE E.FRE_ORDER_ID=?) AS T WHERE T.C>0) " +
						" AND NOT EXISTS (SELECT E.ID FROM FRE_EXPENSE AS E WHERE E.INCOME_OR_EXPENSE='收' AND STATUS != '已对账' AND E.FRE_ORDER_ID=O.ID)";
				int resultCount = jdbcTemplate.queryForObject(sql, Integer.class, freightOrder.getId(), freightOrder.getId());
				if(resultCount == 0){
					continue;
				}
				//所有账单已开票
				sql = " SELECT COUNT(1) AS C FROM FRE_ORDER AS O " + 
						" WHERE ID=? AND EXPENSE_STATUS !='未添加' " + 
						" AND NOT EXISTS (SELECT S.ID FROM FRE_STATEMENT AS S WHERE STATUS != '已开票' AND EXISTS (SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE AS E WHERE E.INCOME_OR_EXPENSE='收' AND E.FRE_ORDER_ID=O.ID AND E.FRE_STATEMENT_ID=S.ID))";
				
				resultCount = jdbcTemplate.queryForObject(sql, Integer.class, freightOrder.getId());
				if(resultCount == 0){
					continue;
				}
				//所有开票已销账
				sql = " SELECT COUNT(1) AS C FROM FRE_ORDER AS O " +
						" WHERE ID=? AND EXPENSE_STATUS !='未添加' " +
						" AND NOT EXISTS (SELECT I.ID FROM FRE_INVOICE AS I WHERE STATUS != '已销账' AND EXISTS (SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE AS E WHERE E.INCOME_OR_EXPENSE='收' AND E.FRE_ORDER_ID=O.ID AND E.FRE_STATEMENT_ID=I.FRE_STATEMENT_ID))";
				
				resultCount = jdbcTemplate.queryForObject(sql, Integer.class, freightOrder.getId());
				if(resultCount == 0){
					continue;
				}
				//最晚销账时间
				sql = "SELECT MAX(CREATE_TIME) AS D FROM FAS_RECONCILE AS R " +
						" WHERE EXISTS (SELECT ID FROM FRE_INVOICE AS I WHERE EXISTS (SELECT FRE_STATEMENT_ID FROM FRE_EXPENSE AS E WHERE E.FRE_ORDER_ID=? AND E.FRE_STATEMENT_ID=I.FRE_STATEMENT_ID) AND R.SOURCE_ID=I.ID)";
				Date reconcileTime = jdbcTemplate.queryForObject(sql, Date.class, freightOrder.getId());
				if(reconcileTime != null){
					freightDeduct.setReconcileTime(reconcileTime);
					freightDeduct.setSettleTimeSalesman(reconcileTime);
					freightDeduct.setSettleDoneSalesman("T");
					freightDeduct.setSettleTimeService(reconcileTime);
					freightDeduct.setSettleDoneService("T");
					freightDeduct.setStatus("T");
					freightDeductDao.modify(freightDeduct);
				}
			}
			**/
			
		}
		LOG.info("订单提成结算完成！");
	}

	@Override
	public void doneOrderDeduct() {
		this.synOrderDeduct();
		this.calculateOrderDeductV1();
		this.calculateOrderDeductV2();
	}

	@Override
	public boolean doneSalesmanDeduct(String[] freightDeductIds) {
		boolean flag = true;
		for(String freightDeductId : freightDeductIds){
			FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
			if(FreightDeduct.RECONCILE_HAVE.equals(freightDeduct.getStatus())){
				freightDeduct.setDeductTime(new Date());
				
				freightDeduct.setSettleDoneSalesman(FreightDeduct.DEDUCT_HAVE);
				freightDeduct.setSettleTimeSalesman(new Date());
				freightDeduct.setSettleDoneService(FreightDeduct.DEDUCT_HAVE);
				freightDeduct.setSettleTimeService(new Date());
				freightDeduct.setStatus(FreightDeduct.DEDUCT_HAVE);
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.modify(freightDeduct);
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
	public boolean recallSalesmanDeduct(String[] freightDeductIds) {
		boolean flag = true;
		for(String freightDeductId : freightDeductIds){
			FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
			if(FreightDeduct.DEDUCT_HAVE.equals(freightDeduct.getStatus())){
				freightDeduct.setSettleDoneSalesman(FreightDeduct.RECONCILE_HAVE);
				freightDeduct.setSettleTimeSalesman(new Date());
				freightDeduct.setSettleDoneService(FreightDeduct.RECONCILE_HAVE);
				freightDeduct.setSettleTimeService(new Date());
				freightDeduct.setStatus(FreightDeduct.RECONCILE_HAVE);
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.modify(freightDeduct);
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
	public boolean doneManipulatorDeduct(String[] freightDeductIds) {
		boolean flag = true;
		for(String freightDeductId : freightDeductIds){
			FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
			if(FreightDeduct.DEDUCT_PREPARED.equals(freightDeduct.getSettleDoneManipulator())){
				freightDeduct.setSettleDoneManipulator(FreightDeduct.DEDUCT_HAVE);
				freightDeduct.setSettleTimeManipulator(new Date());
				freightDeductDao.modify(freightDeduct);
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
	public boolean recallManipulatorDeduct(String[] freightDeductIds) {
		boolean flag = true;
		for(String freightDeductId : freightDeductIds){
			FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
			if(FreightDeduct.DEDUCT_HAVE.equals(freightDeduct.getSettleDoneManipulator())){
				freightDeduct.setSettleDoneManipulator(FreightDeduct.DEDUCT_PREPARED);
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.modify(freightDeduct);
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
	@Deprecated
	public void synBoxDeduct() {
		String sql = "SELECT DISTINCT(O.ID) FROM FRE_ACTION  AS A "
					+ " LEFT JOIN FRE_MAINTAIN AS M ON A.FRE_MAINTAIN_ID=M.ID "
					+ " LEFT JOIN FRE_ORDER AS O ON M.FRE_ORDER_ID=O.ID "
					+ " LEFT JOIN FRE_ACTION_TYPE AS AAT ON A.FRE_ACTION_TYPE_ID=AAT.ID "
					+ " WHERE A.STATUS='已执行' "
					+ " AND O.ORDER_STATUS!='已作废' "
					+ " AND AAT.TYPE_NAME   "
					+ " IN('请班列计划-城厢/万州', '请班列计划-普兴/万州', '请班列计划-眉山/万州', '请班列计划-万州/眉山', '请班列计划-城厢/果园', '请班列计划-果园/城厢') "
					+ " AND NOT EXISTS (SELECT 1 FROM FRE_DEDUCT AS D WHERE D.DEDUCT_TYPE='标箱提成' AND D.FRE_ORDER_ID=O.ID)";
		
		List<String> freightOrderIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightOrderIds != null && !freightOrderIds.isEmpty()){
			for(String freightOrderId : freightOrderIds){
				FreightOrder freightOrder = freightOrderDao.getById(freightOrderId);
				
				FreightDeduct freightDeduct = new FreightDeduct();
				freightDeduct.setDeductType("标箱提成");
				freightDeduct.setFreightOrder(freightOrder);
				freightDeduct.setOrgEntity(userDao.getByDisplayName(freightOrder.getSalesman()).getOrgEntity());
				freightDeduct.setOrderBalance(freightOrder.getBalance());
				
				freightDeduct.setSettleDoneSalesman(FreightDeduct.RECONCILE_HAVNT);
				freightDeduct.setSettleDoneService(FreightDeduct.RECONCILE_HAVNT);
				freightDeduct.setSettleDoneManipulator(FreightDeduct.RECONCILE_HAVNT);
				freightDeduct.setStatus(FreightDeduct.RECONCILE_HAVNT);
				
				freightDeduct.setCreateTime(new Date());
				freightDeduct.setModifyTime(new Date());
				freightDeductDao.add(freightDeduct);
			}
		}
		
		//sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.FRE_ORDER_ID NOT IN(SELECT ID FROM FRE_ORDER AS O WHERE O.ORDER_STATUS !='已作废')";
		//sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS != 'D' AND D.STATUS != 'DF' AND NOT EXISTS (SELECT 1 FROM FRE_ORDER AS O WHERE O.ORDER_STATUS !='已作废' AND O.ID=D.FRE_ORDER_ID)";
		//未提成且订单状态为作废或者订单被删除的，则删除提成
		sql = "SELECT D.ID FROM FRE_DEDUCT AS D LEFT JOIN FRE_ORDER AS O ON D.FRE_ORDER_ID=O.ID WHERE D.STATUS != 'D' AND (O.ORDER_STATUS = '已作废' OR O.ID IS NULL)";
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				freightDeductDao.delete(freightDeductId);
			}
		}
		
		LOG.info("标箱提成同步完成！");
	}

	@Override
	@Deprecated
	public void calculateBoxDeduct() {
		String sql = "SELECT ID FROM FRE_DEDUCT AS D WHERE D.STATUS='F' AND D.DEDUCT_TYPE='标箱提成'";
		List<String> freightDeductIds = jdbcTemplate.queryForList(sql, String.class);
		if(freightDeductIds != null && !freightDeductIds.isEmpty()){
			for(String freightDeductId : freightDeductIds){
				FreightDeduct freightDeduct = freightDeductDao.getById(freightDeductId);
				FreightOrder freightOrder = freightDeduct.getFreightOrder();
				List<FreightBoxRequire> freightBoxRequires = freightOrder.getFreightBoxRequires();
				int teuCount = 0;
				for(FreightBoxRequire freightBoxRequire : freightBoxRequires){
					teuCount += (freightBoxRequire.getBoxType().startsWith("2") ? freightBoxRequire.getBoxCount() : 2 * freightBoxRequire.getBoxCount());
				}
				
				if("自开发业务".equals(freightOrder.getOrderScope())){
					freightDeduct.setDeductCountSalesman(teuCount * 20);
				}else{
					freightDeduct.setDeductCountSalesman(teuCount * 10);
				}
				
				freightDeductDao.modify(freightDeduct);
			}
		}
		
		LOG.info("标箱提成计算完成！");
	}
	
	@Override
	@Deprecated
	public void doneBoxDeduct() {
		synBoxDeduct();
		calculateBoxDeduct();
	}

	@Override
	public List<List<String>> doneBatchExport(String[] freightDeductIds) {
		PageView<FreightDeduct> pageView = new PageView<FreightDeduct>(freightDeductIds.length, 1);
		StringBuilder filterText = new StringBuilder(" ID IN(");
		for(String freightDeductId : freightDeductIds){
			filterText.append("'" +freightDeductId+ "',");
		}
		filterText.deleteCharAt(filterText.lastIndexOf(","));
		filterText.append(")");
		pageView.setFilterText(filterText.toString());
		List<FreightDeduct> freightDeducts = freightDeductDao.query(pageView, new FreightDeduct());
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightDeduct freightDeduct : freightDeducts){
			List<String> singleValue = new ArrayList<String>();
			singleValue.add(freightDeduct.getDeductType());
			singleValue.add(freightDeduct.getFreightOrder().getOrderNumber());
			singleValue.add(freightDeduct.getFreightOrder().getDelegatePart());
			singleValue.add(freightDeduct.getFreightOrder().getOrderScope());
			singleValue.add(freightDeduct.getOrgEntity().getOrgEntityName());
			singleValue.add(freightDeduct.getFreightOrder().getSalesman());
			singleValue.add(freightDeduct.getFreightOrder().getReceptionist().getDisplayName());
			singleValue.add(freightDeduct.getFreightOrder().getManipulator());
			
			if(freightDeduct.getFreightOrder().getFreightBoxRequires() != null 
					&& !freightDeduct.getFreightOrder().getFreightBoxRequires().isEmpty()){
				StringBuilder text = new StringBuilder();
				for(FreightBoxRequire freightBoxRequire : freightDeduct.getFreightOrder().getFreightBoxRequires()){
					text.append(freightBoxRequire.getBoxType() + "*" + freightBoxRequire.getBoxCount() + " " + freightBoxRequire.getBoxBelong() + ";");
				}
				singleValue.add(text.toString());
			}else{
				singleValue.add("");
			}
			
			singleValue.add(String.valueOf(freightDeduct.getDeductBalance()));
			
			singleValue.add(String.valueOf(freightDeduct.getDeductCountSalesman()));
			singleValue.add(freightDeduct.getSettleDoneSalesman());
			singleValue.add(StringUtil.parseDateTime(freightDeduct.getSettleTimeSalesman()));
			
			singleValue.add(String.valueOf(freightDeduct.getDeductCountService()));
			singleValue.add(freightDeduct.getSettleDoneService());
			singleValue.add(StringUtil.parseDateTime(freightDeduct.getSettleTimeService()));
			
			singleValue.add(String.valueOf(freightDeduct.getDeductCountManipulator()));
			singleValue.add(freightDeduct.getSettleDoneManipulator());
			singleValue.add(StringUtil.parseDateTime(freightDeduct.getSettleTimeManipulator()));
			
			singleValue.add(StringUtil.parseDateTime(freightDeduct.getReconcileTime()));
			Date time = freightDeduct.getReconcileTime() == null ? new Date() : freightDeduct.getReconcileTime();
			int delayDays = (int)((time.getTime() - freightDeduct.getFreightOrder().getPlaceTime().getTime()) / (1000 * 60 * 60 * 24));
			singleValue.add(String.valueOf(delayDays));
			singleValue.add(freightDeduct.getStatus());
			singleValue.add(StringUtil.parseDateTime(freightDeduct.getCreateTime()));
			singleValue.add(StringUtil.parseDateTime(freightDeduct.getModifyTime()));
			
			values.add(singleValue);
		}
		
		return values;
	}
}

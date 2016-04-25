package com.van.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightActionValueDao;
import com.van.halley.db.persistence.FreightBoxDao;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.FreightOrderDao;
import com.van.halley.db.persistence.FreightSealDao;
import com.van.halley.db.persistence.entity.FreightBox;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.FreightSeal;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.service.FreightBoxRequireService;
import com.van.service.FreightOrderService;

@Transactional
@Service("freightBoxRequireService")
public class FreightBoxRequireServiceImpl implements FreightBoxRequireService {
	private Logger logger = LoggerFactory.getLogger(FreightBoxRequireServiceImpl.class);
	@Autowired
	private FreightBoxRequireDao freightBoxRequireDao;
	@Autowired
	private FreightOrderBoxDao freightOrderBoxDao;
	@Autowired
	private FreightBoxDao freightBoxDao;
	@Autowired
	private FreightSealDao freightSealDao;
	@Autowired
	private FreightOrderService freightOrderService;
	@Autowired
	private FreightOrderDao freightOrderDao;
	@Autowired
	private FreightActionValueDao freightActionValueDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<FreightBoxRequire> getAll() {
		return freightBoxRequireDao.getAll();
	}

	public List<FreightBoxRequire> queryForList(
			FreightBoxRequire freightBoxRequire) {
		return freightBoxRequireDao.queryForList(freightBoxRequire);
	}

	public FreightBoxRequire queryForOne(FreightBoxRequire freightBoxRequire) {
		return freightBoxRequireDao.queryForOne(freightBoxRequire);
	}

	public PageView<FreightBoxRequire> query(PageView<FreightBoxRequire> pageView, FreightBoxRequire freightBoxRequire) {
		List<FreightBoxRequire> list = freightBoxRequireDao.query(pageView,
				freightBoxRequire);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightBoxRequire freightBoxRequire) {
		freightBoxRequireDao.add(freightBoxRequire);
	}

	public void delete(String id) {
		freightBoxRequireDao.delete(id);
	}

	public void modify(FreightBoxRequire freightBoxRequire) {
		freightBoxRequireDao.modify(freightBoxRequire);
	}

	public FreightBoxRequire getById(String id) {
		return freightBoxRequireDao.getById(id);
	}
	
	@Override
	public boolean doneAddRequire(FreightBoxRequire freightBoxRequire, String freightOrderId, String freightBoxRequireId) {
		boolean flag = true;
		
		freightBoxRequire.setFreightOrder(freightOrderDao.getById(freightOrderId));
		freightBoxRequire.setStatus("未提交");
		if(!StringUtil.isNullOrEmpty(freightBoxRequireId)){
			freightBoxRequire.setId(freightBoxRequireId);
			freightBoxRequireDao.modify(freightBoxRequire);
			
			List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightBoxRequireId(freightBoxRequireId);
			if(freightOrderBoxs.size() <= freightBoxRequire.getBoxCount()){//只需添加新增的
				for(int i=0, count = freightBoxRequire.getBoxCount() - freightOrderBoxs.size(); i<count; i++){
					FreightOrderBox freightOrderBox = new FreightOrderBox();
					freightOrderBox.setFreightBoxRequire(freightBoxRequire);
					freightOrderBox.setStatus(freightBoxRequire.getStatus());
					freightOrderBoxDao.add(freightOrderBox);
				}
			}else{
				//只要所有动作状态为未执行
				for(FreightOrderBox freightOrderBox : freightOrderBoxs){
					if("已选箱".equals(freightOrderBox.getStatus()) || "已放箱".equals(freightOrderBox.getStatus())){
						flag = false;
						logger.error("修改箱需失败，已有选箱或放箱， 箱需ID: {}", freightBoxRequireId);
						break;
					}
				}
				if(flag){//判断是否有已对账的费用，如果已经有禁止减少
					String sql = "SELECT COUNT(1) FROM FRE_EXPENSE WHERE FRE_ORDER_ID=? AND STATUS='已对账'";
					int count = jdbcTemplate.queryForObject(sql, Integer.class, freightOrderId);
					if(count > 0){
						logger.error("修改箱需失败，已有费用对账， 箱需ID: {}", freightBoxRequireId);
						flag = false;
					}else{
						sql = "SELECT COUNT(1) FROM FRE_EXPENSE WHERE FRE_ORDER_ID IS NULL AND STATUS='已对账' AND FRE_ACTION_ID IN(SELECT ID FROM FRE_ACTION WHERE FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?))";
						if(count > 0){
							logger.error("修改箱需失败，已有费用对账， 箱需ID: {}", freightBoxRequireId);
							flag = false;
						}
					}
				}
				if(flag){//判断是否有有非未执行的动作，如果已经有禁止减少
					String sqlCount = "SELECT COUNT(1) FROM FRE_ACTION WHERE STATUS!='未执行' AND FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?)";
					if(jdbcTemplate.queryForObject(sqlCount, Integer.class, freightOrderId) > 0){
						flag = false;
						logger.error("修改箱需失败，该箱需数所相关动作为非未执行状态， 箱需ID: {}", freightBoxRequireId);
					}else{
						for(int i=0, len= freightOrderBoxs.size() - freightBoxRequire.getBoxCount(); i<len; i++){
							FreightOrderBox freightOrderBox = freightOrderBoxs.get(i);
							freightActionValueDao.deleteByFreightOrderBoxId(freightOrderBox.getId());
							freightOrderBoxDao.delete(freightOrderBox.getId());
						}
					}
				}
			}
			
		}else{
			freightBoxRequireId = StringUtil.getUUID();
			freightBoxRequire.setId(freightBoxRequireId);
			freightBoxRequireDao.add(freightBoxRequire);
			
			for(int i=0, count = freightBoxRequire.getBoxCount(); i<count; i++){
				FreightOrderBox freightOrderBox = new FreightOrderBox();
				freightOrderBox.setFreightBoxRequire(freightBoxRequire);
				freightOrderBox.setStatus(freightBoxRequire.getStatus());
				freightOrderBoxDao.add(freightOrderBox);
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return flag;
	}

	@Override
	public boolean doneReleaseRequire(String[] freightBoxRequireIds, HttpServletRequest request) {
		boolean flag = true;
		for(String freightBoxRequireId : freightBoxRequireIds){
			FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
			if("未提交".equals(freightBoxRequire.getStatus())){
				String sql = "SELECT COUNT(1) FROM FRE_BOX_REQUIRE WHERE BL_NO=? AND FRE_ORDER_ID != ?";
				int count = jdbcTemplate.queryForObject(sql, Integer.class, request.getParameter(freightBoxRequireId), freightBoxRequire.getFreightOrder().getId());
				if(count == 0){
					freightBoxRequire.setStatus("待选箱");
					freightBoxRequire.setBlNo(request.getParameter(freightBoxRequireId));
					freightBoxRequire.setModifyTime(new Date());
					freightBoxRequireDao.modify(freightBoxRequire);
					
					List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightBoxRequireId(freightBoxRequireId);
					if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
						for(FreightOrderBox freightOrderBox : freightOrderBoxs){
							freightOrderBox.setStatus(freightBoxRequire.getStatus());
							freightOrderBoxDao.modify(freightOrderBox);
						}
					}
				}else{
					flag = false;
					break;
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
	public boolean doneRemoveRequire(String[] freightBoxRequireIds) {
		boolean flag = true;
		for(String freightBoxRequireId : freightBoxRequireIds){
			FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
			if("未提交".equals(freightBoxRequire.getStatus())){
				List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightBoxRequireId(freightBoxRequireId);
				if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
					String sqlCount = "SELECT COUNT(1) FROM FRE_ACTION WHERE STATUS!='未执行' AND FRE_MAINTAIN_ID IN(SELECT ID FROM FRE_MAINTAIN WHERE FRE_ORDER_ID=?)";
					if(jdbcTemplate.queryForObject(sqlCount, Integer.class, freightBoxRequire.getFreightOrder().getId()) > 0){
						flag = false;
						logger.error("修改箱需失败，该箱需数所相关动作为非未执行状态， 箱需ID: {}", freightBoxRequireId);
					}else{
						for(int i=0, len= freightOrderBoxs.size(); i<len; i++){
							freightActionValueDao.deleteByFreightOrderBoxId(freightOrderBoxs.get(i).getId());//删除箱封信息相关值
						}
					}
					/*//动作中不能填报含箱数据
					for(FreightOrderBox freightOrderBox : freightOrderBoxs){
						FreightActionValue filter = new FreightActionValue();
						filter.setFreightOrderBox(freightOrderBox);
						if(freightActionValueDao.count(filter) > 0){//如果有填报数据
							flag = false;
							logger.error("删除箱需失败，该箱需数据已经有相关动作使用， 箱需ID: {}", freightBoxRequireId);
							break;
						}
					}*/
					//不能有已选箱或已放箱
					if(flag){
						for(FreightOrderBox freightOrderBox : freightOrderBoxs){
							if("已选箱".equals(freightOrderBox.getStatus()) || "已放箱".equals(freightOrderBox.getStatus())){
								flag = false;
								logger.error("删除箱需失败，已有选箱或放箱， 箱需ID: {}", freightBoxRequireId);
								break;
							}
						}
					}
					if(flag){
						for(FreightOrderBox freightOrderBox : freightOrderBoxs){
							freightOrderBoxDao.delete(freightOrderBox.getId());
						}
					}
				}
				freightBoxRequireDao.delete(freightBoxRequireId);
			}else{
				flag = false;
			}
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean toBackRequire(String[] freightBoxRequireIds) {
		boolean flag = true;
		for(String freightBoxRequireId : freightBoxRequireIds){
			FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
			if("待选箱".equals(freightBoxRequire.getStatus())){
				List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightBoxRequireId(freightBoxRequireId);
				if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
					for(FreightOrderBox freightOrderBox : freightOrderBoxs){
						freightOrderBox.setStatus("未提交");
						freightOrderBoxDao.modify(freightOrderBox);
					}
				}
				
				FreightOrder freightOrder = freightBoxRequire.getFreightOrder();
				if("已审核".equals(freightOrder.getOrderStatus())){
					freightOrder.setOrderStatus("未提交");
					freightOrderService.modify(freightOrder);
				}
				freightBoxRequire.setStatus("未提交");
				freightBoxRequireDao.modify(freightBoxRequire);
			}else{
				flag = false;
			}
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public Map<String, Object> toReviseSource(String freightBoxRequireId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freightBoxRequire", freightBoxRequireDao.getById(freightBoxRequireId));
		return map;
	}

	@Override
	public boolean doneReviseSource(String freightBoxRequireId, String boxSource) {
		FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
		if(freightBoxRequire != null){
			freightBoxRequire.setBoxSource(boxSource);
			freightBoxRequireDao.modify(freightBoxRequire);
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public Map<String, Object> toChooseBoxseal(String freightBoxRequireId) {
		Map<String, Object> map = new HashMap<String, Object>();
		FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
		
		map.put("freightBoxRequire", freightBoxRequire);
		FreightOrderBox filter = new FreightOrderBox();
		filter.setFreightBoxRequire(freightBoxRequire);
		map.put("freightOrderBoxs", freightOrderBoxDao.queryForList(filter));
		return map;
	}

	@Override
	public Map<String, Object> doneChooseBoxseal(String freightBoxRequireId, String[] boxNumbers, String[] sealNumbers, String[] locations) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		boolean flag = true;
		String message = null;
		FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
		FreightOrderBox filter = new FreightOrderBox();
		filter.setFreightBoxRequire(freightBoxRequire);
		List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.queryForList(filter);
		if(boxNumbers == null || boxNumbers.length != freightOrderBoxs.size()){
			flag = false; 
			message = "集装箱数量与箱量不一致";
		}else{
			for(String boxNumber : boxNumbers){
				if(!FreightFilterUtil.validateBoxNumber(boxNumber)){
					flag = false;
					message = boxNumber + " 校验失败";
					logger.info("集装箱号检验错误，箱号 ： {}", boxNumber);
					break;
				}
			}
		}
		
		if(flag){//只要有封号为空则不配封
			boolean hasSeal = true;
			if(sealNumbers == null || sealNumbers.length == 0 || sealNumbers.length != freightOrderBoxs.size()){
				hasSeal = false;
			}
			if(hasSeal){
				for(String sealNumber : sealNumbers){//只要出现为空则判断为无封
					if(StringUtil.isNullOrEmpty(sealNumber)){
						hasSeal = false;
					}
				}
			}
			
			if(flag){
				for(int i=0, len=boxNumbers.length; i<len; i++){
					String boxNumber = boxNumbers[i];
					FreightBox freightBox = null;
					//先从可用中获取
					FreightBox filterBox = new FreightBox();
					filterBox.setBoxNumber(boxNumber);
					filterBox.setStatus("可用");
					freightBox = freightBoxDao.queryForOne(filterBox);
					if(freightBox != null){
						freightBox.setBoxBelong(freightBoxRequire.getBoxBelong());
						freightBox.setBoxSource(freightBoxRequire.getBoxSource());
						freightBox.setBoxType(freightBoxRequire.getBoxType());
						freightBox.setBoxCondition(freightBoxRequire.getBoxCondition());
						freightBox.setStatus("已选箱");
						freightBoxDao.modify(freightBox);
						logger.info("使用已存在的可用集装箱，箱号 ： {}", boxNumber);
					}else{
						freightBox = new FreightBox();
						freightBox.setId(StringUtil.getUUID());
						freightBox.setBoxNumber(boxNumber);
						freightBox.setBoxBelong(freightBoxRequire.getBoxBelong());
						freightBox.setBoxSource(freightBoxRequire.getBoxSource());
						freightBox.setBoxType(freightBoxRequire.getBoxType());
						freightBox.setBoxCondition(freightBoxRequire.getBoxCondition());
						freightBox.setStatus("已选箱");
						freightBoxDao.add(freightBox);
						logger.info("暂无可用集装箱，新增，箱号 ： {}", boxNumber);
					}
					
					FreightOrderBox freightOrderBox = freightOrderBoxs.get(i);
					//如果已经存在，则需要删除
					if(freightOrderBox.getFreightBox() != null){
						freightBoxDao.delete(freightOrderBox.getFreightBox().getId());
					}
					freightOrderBox.setFreightBox(freightBox);
					if(hasSeal){
						FreightSeal freightSeal = new FreightSeal();
						freightSeal.setId(StringUtil.getUUID());
						freightSeal.setSealNumber(sealNumbers[i]);
						freightSeal.setSealType("海船封");
						freightSeal.setSealBelong(freightBoxRequire.getBoxBelong());
						freightSeal.setStatus("已使用");
						freightSealDao.add(freightSeal);
						//如果已经存在，则需要删除
						if(freightOrderBox.getFreightSeal() != null){
							freightSealDao.delete(freightOrderBox.getFreightSeal().getId());
						}
						freightOrderBox.setFreightSeal(freightSeal);
					}
					
					freightOrderBox.setStatus("已选箱");
					if(!"外理箱".equals(freightBoxRequire.getBoxSource())){//只有箱管放箱才填写提箱地址
						freightOrderBox.setLocation(locations[i]);//备注(提箱地址)
					}
					freightOrderBoxDao.modify(freightOrderBox);
				}
				
				freightBoxRequire.setStatus("已选箱");
				freightBoxRequireDao.modify(freightBoxRequire);
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		result.put("result", String.valueOf(flag));
		result.put("message", message);
		return result;
	}

	@Override
	public boolean doneReleaseBox(String[] freightBoxRequireIds) {
		boolean flag = true;
		for(String freightBoxRequireId : freightBoxRequireIds){
			FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
			if("已选箱".equals(freightBoxRequire.getStatus())){
				List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightBoxRequireId(freightBoxRequireId);
				if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
					for(FreightOrderBox freightOrderBox : freightOrderBoxs){
						freightOrderBox.setStatus("已放箱");
						freightOrderBoxDao.modify(freightOrderBox);
					}
				}
				freightBoxRequire.setModifyTime(new Date());
				freightBoxRequire.setStatus("已放箱");
				freightBoxRequireDao.modify(freightBoxRequire);
			}else{
				flag = false;
			}
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean doneRecallBox(String[] freightBoxRequireIds) {
		boolean flag = true;
		for(String freightBoxRequireId : freightBoxRequireIds){
			FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
			if("已选箱".equals(freightBoxRequire.getStatus()) || "已放箱".equals(freightBoxRequire.getStatus()) ){
				List<FreightOrderBox> freightOrderBoxs = freightOrderBoxDao.getByFreightBoxRequireId(freightBoxRequireId);
				if(freightOrderBoxs != null && !freightOrderBoxs.isEmpty()){
					for(FreightOrderBox freightOrderBox : freightOrderBoxs){
						FreightBox freightBox = freightOrderBox.getFreightBox();
						freightBox.setStatus("可用");//保留集装箱
						freightBoxDao.modify(freightBox);
						//freightBoxDao.delete(freightBox.getId());
						
						FreightBox freightBoxTemp = new FreightBox();
						freightBoxTemp.setId("A");
						freightOrderBox.setFreightBox(freightBoxTemp);//设置一个无意义的集装箱
						
						FreightSeal freightSeal = freightOrderBox.getFreightSeal();
						if(freightSeal != null){
							freightSealDao.delete(freightSeal.getId());//删除封号
							FreightSeal freightSealTemp = new FreightSeal();
							freightSealTemp.setId("A");
							freightOrderBox.setFreightSeal(freightSealTemp);//设置一个无意义的封号
						}
						
						freightOrderBox.setStatus("未提交");
						freightOrderBoxDao.modify(freightOrderBox);
					}
				}
				freightBoxRequire.setStatus("未提交");
				freightBoxRequireDao.modify(freightBoxRequire);
			}else{
				flag = false;
			}
		}
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public Map<String, Object> doneImportBoxseal(String freightBoxRequireId,
			List<List<String>> values) {
		List<String> boxNumbers = new ArrayList<String>();
		List<String> sealNumbers = new ArrayList<String>();
		List<String> blAddresses = new ArrayList<String>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		FreightBoxRequire freightBoxRequire = freightBoxRequireDao.getById(freightBoxRequireId);
		if(values != null && !values.isEmpty() && values.size() == freightBoxRequire.getBoxCount() 
				&& "待选箱".equals(freightBoxRequire.getStatus())){
			for(List<String> singleValue : values){
				boxNumbers.add(singleValue.get(0));
				sealNumbers.add(singleValue.get(1));
				blAddresses.add(singleValue.get(2));
			}
			
			return doneChooseBoxseal(freightBoxRequireId, 
					boxNumbers.toArray(new String[freightBoxRequire.getBoxCount()]), 
					sealNumbers.toArray(new String[freightBoxRequire.getBoxCount()]), 
					blAddresses.toArray(new String[freightBoxRequire.getBoxCount()]));
		}else{
			result.put("result", "false");
			result.put("message", "导出数据与箱量不一致");
			return result;
		}
	}
}

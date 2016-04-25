package com.van.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.MotorcadeDispatchDao;
import com.van.halley.db.persistence.MotorcadeFeeDao;
import com.van.halley.db.persistence.entity.MotorcadeDispatch;
import com.van.halley.db.persistence.entity.MotorcadeFee;
import com.van.halley.util.StringUtil;
import com.van.service.MotorcadeDispatchService;

@Transactional
@Service("motorcadeDispatchService")
public class MotorcadeDispatchServiceImpl implements MotorcadeDispatchService {
	@Autowired
	private MotorcadeDispatchDao motorcadeDispatchDao;
	@Autowired
	private MotorcadeFeeDao motorcadeFeeDao;
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<MotorcadeDispatch> getAll() {
		return motorcadeDispatchDao.getAll();
	}

	public List<MotorcadeDispatch> queryForList(
			MotorcadeDispatch motorcadeDispatch) {
		return motorcadeDispatchDao.queryForList(motorcadeDispatch);
	}

	public MotorcadeDispatch queryForOne(MotorcadeDispatch motorcadeDispatch) {
		return motorcadeDispatchDao.queryForOne(motorcadeDispatch);
	}

	public PageView query(PageView pageView, MotorcadeDispatch motorcadeDispatch) {
		List<MotorcadeDispatch> list = motorcadeDispatchDao.query(pageView,
				motorcadeDispatch);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MotorcadeDispatch motorcadeDispatch) {
		motorcadeDispatchDao.add(motorcadeDispatch);
	}

	public void delete(String id) {
		motorcadeDispatchDao.delete(id);
	}

	public void modify(MotorcadeDispatch motorcadeDispatch) {
		motorcadeDispatchDao.modify(motorcadeDispatch);
	}

	public MotorcadeDispatch getById(String id) {
		return motorcadeDispatchDao.getById(id);
	}

	@Override
	public String getNextDispatchNumber(String userName) {
		String sql = "SELECT CONCAT(LEFT(DISPATCH_NUMBER, LENGTH(DISPATCH_NUMBER) - 3), REPEAT('0',3 - LENGTH(MAX(RIGHT(DISPATCH_NUMBER, 3)) + 1)),MAX(RIGHT(DISPATCH_NUMBER, 3)) + 1)"
				+ " FROM MOTOR_DISPATCH WHERE "
				+ " DISPATCH_NUMBER LIKE CONCAT('%" + userName.toUpperCase() + "', DATE_FORMAT(SYSDATE(),'%Y%m%d'),'%')";
		String dispatchNumber = jdbcTemplate.queryForObject(sql, String.class);
		if(StringUtil.isNullOrEmpty(dispatchNumber)){
			dispatchNumber = "CY-PD-" + userName.toUpperCase() + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "001";
		}
		return dispatchNumber;
	}

	@Override
	public Map<String, Object> toAddDispatchByCopy(String motorcadeDispatchId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("motorcadeFees", motorcadeFeeDao.getByMotorcadeDispatchId(motorcadeDispatchId));
		map.put("motorcadeDispatch", motorcadeDispatchDao.getById(motorcadeDispatchId));
		return map;	
	}

	@Override
	public String doneAddDispatchByCopy(String motorcadeDispatchId, String[] motorcadeFeeIds, String userName) {
		String motorcadeDispatchIdNew = StringUtil.getUUID();
		MotorcadeDispatch motorcadeDispatch = motorcadeDispatchDao.getById(motorcadeDispatchId);
		motorcadeDispatch.setId(motorcadeDispatchIdNew);
		motorcadeDispatch.setDispatchNumber(getNextDispatchNumber(userName));
		motorcadeDispatch.setBoxNumber(null);
		motorcadeDispatch.setCreateTime(new Date());
		motorcadeDispatch.setModifyTime(new Date());
		motorcadeDispatchDao.add(motorcadeDispatch);
		
		if(motorcadeFeeIds != null && motorcadeFeeIds.length > 0){
			for(String motorcadeFeeId : motorcadeFeeIds){
				MotorcadeFee motorcadeFee = motorcadeFeeDao.getById(motorcadeFeeId);
				motorcadeFee.setId(StringUtil.getUUID());
				motorcadeFee.setMotorcadeDispatch(motorcadeDispatch);
				motorcadeFee.setCreateTime(new Date());
				motorcadeFee.setModifyTime(new Date());
				motorcadeFeeDao.add(motorcadeFee);
			}
		}
		
		return motorcadeDispatchIdNew;
	}

	@Override
	public Map<String, Object> toAddFee(String motorcadeDispatchId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", motorcadeFeeDao.getByMotorcadeDispatchId(motorcadeDispatchId));
		map.put("motorcadeDispatch", motorcadeDispatchDao.getById(motorcadeDispatchId));
		map.put("fasInvoiceTypes", fasInvoiceTypeDao.getAll());
		return map;
	}

	@Override
	public boolean doneAddFee(String motorcadeDispatchId, MotorcadeFee motorcadeFee) {
		motorcadeFee.setMotorcadeDispatch(motorcadeDispatchDao.getById(motorcadeDispatchId));
		if(StringUtil.isNullOrEmpty(motorcadeFee.getId())){
			motorcadeFeeDao.add(motorcadeFee);
		}else{
			motorcadeFeeDao.modify(motorcadeFee);
		}
		return true;
	}

	@Override
	public boolean toRemoveDispatch(String[] motorcadeDispatchIds) {
		boolean flag = true;
		for(String motorcadeDispatchId : motorcadeDispatchIds){
			MotorcadeDispatch motorcadeDispatch = motorcadeDispatchDao.getById(motorcadeDispatchId);
			MotorcadeFee filter = new MotorcadeFee();
			filter.setMotorcadeDispatch(motorcadeDispatch);
			if(motorcadeFeeDao.count(filter) > 0){
				flag = false;
				break;
			}
		}
		
		return flag;
	}

	@Override
	public boolean doneConfirmDispatch(String[] motorcadeDispatchIds) {
		boolean flag = true;
		for(String motorcadeDispatchId : motorcadeDispatchIds){
			MotorcadeDispatch motorcadeDispatch = motorcadeDispatchDao.getById(motorcadeDispatchId);
			if("未提交".equals(motorcadeDispatch.getStatus())){
				motorcadeDispatch.setStatus("已确认");
				motorcadeDispatchDao.modify(motorcadeDispatch);
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
	public boolean doneRecallDispatch(String[] motorcadeDispatchIds) {
		boolean flag = true;
		for(String motorcadeDispatchId : motorcadeDispatchIds){
			MotorcadeDispatch motorcadeDispatch = motorcadeDispatchDao.getById(motorcadeDispatchId);
			if("已确认".equals(motorcadeDispatch.getStatus())){
				motorcadeDispatch.setStatus("未提交");
				motorcadeDispatchDao.modify(motorcadeDispatch);
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
}

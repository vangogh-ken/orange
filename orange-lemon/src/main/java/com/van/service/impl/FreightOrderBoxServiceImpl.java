package com.van.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightBoxDao;
import com.van.halley.db.persistence.FreightBoxRequireDao;
import com.van.halley.db.persistence.FreightOrderBoxDao;
import com.van.halley.db.persistence.FreightSealDao;
import com.van.halley.db.persistence.entity.FreightBox;
import com.van.halley.db.persistence.entity.FreightOrderBox;
import com.van.halley.db.persistence.entity.FreightSeal;
import com.van.service.FreightOrderBoxService;

@Transactional
@Service("freightOrderBoxService")
public class FreightOrderBoxServiceImpl implements FreightOrderBoxService {
	@Autowired
	private FreightOrderBoxDao freightOrderBoxDao;
	@Autowired
	private FreightBoxDao freightBoxDao;
	@Autowired
	private FreightBoxRequireDao freightBoxRequireDao;
	@Autowired
	private FreightSealDao freightSealDao;

	public List<FreightOrderBox> getAll() {
		return freightOrderBoxDao.getAll();
	}

	public List<FreightOrderBox> queryForList(FreightOrderBox freightOrderBox) {
		return freightOrderBoxDao.queryForList(freightOrderBox);
	}

	public PageView query(PageView pageView, FreightOrderBox freightOrderBox) {
		List<FreightOrderBox> list = freightOrderBoxDao.query(pageView,
				freightOrderBox);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightOrderBox freightOrderBox) {
		freightOrderBoxDao.add(freightOrderBox);
	}

	public void delete(String id) {
		freightOrderBoxDao.delete(id);
	}

	public void modify(FreightOrderBox freightOrderBox) {
		freightOrderBoxDao.modify(freightOrderBox);
	}

	public FreightOrderBox getById(String id) {
		return freightOrderBoxDao.getById(id);
	}

	@Override
	public List<FreightOrderBox> getByFreightOrderId(String freightOrderId) {
		return freightOrderBoxDao.getByFreightOrderId(freightOrderId);
	}

	@Override
	public void modifySeal(FreightOrderBox freightOrderBox, String freightSealId) {
		//给原封号进行备注信息更新。
		FreightSeal preFreightSeal = freightOrderBoxDao.getById(freightOrderBox.getId()).getFreightSeal();
		if(preFreightSeal != null){
			preFreightSeal.setDescn(freightOrderBox.getDescn() + ", 原箱封ID：" + freightOrderBox.getId());
			preFreightSeal.setStatus("已作废");
			freightSealDao.modify(preFreightSeal);
		}
		
		//更新为新封号
		FreightSeal freightSeal = freightSealDao.getById(freightSealId);
		freightOrderBox.setFreightSeal(freightSeal);
		freightOrderBoxDao.modify(freightOrderBox);
		freightSeal.setStatus("已使用");
		freightSealDao.modify(freightSeal);
	}

	@Override
	public Map<String, Object> toReviseBox(String freightOrderBoxId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		FreightOrderBox freightOrderBox = freightOrderBoxDao.getById(freightOrderBoxId);
		FreightBox filter = new FreightBox();
		filter.setBoxSource(freightOrderBox.getFreightBox().getBoxSource());
		filter.setBoxBelong(freightOrderBox.getFreightBox().getBoxBelong());
		filter.setBoxType(freightOrderBox.getFreightBox().getBoxType());
		//filter.setBoxCondition(freightOrderBox.getFreightBox().getBoxCondition());取消对箱况的过滤
		filter.setStatus("可用");
		List<FreightBox> freightBoxs = freightBoxDao.queryForList(filter);
		
		map.put("freightBoxs", freightBoxs);
		map.put("freightOrderBox", freightOrderBox);
		return map;
	}

	@Override
	public boolean doneReviseBox(FreightOrderBox freightOrderBox, String freightBoxId) {
		freightOrderBox = freightOrderBoxDao.getById(freightOrderBox.getId());
		//原来的箱子设置为可用
		FreightBox preFreightBox = freightOrderBox.getFreightBox();
		preFreightBox.setStatus("可用");
		freightBoxDao.modify(preFreightBox);
		
		FreightBox freightBox = freightBoxDao.getById(freightBoxId);
		freightOrderBox.setFreightBox(freightBox);
		freightOrderBoxDao.modify(freightOrderBox);
		
		freightBox.setStatus(freightOrderBox.getStatus());
		freightBoxDao.modify(freightBox);
		return true;
	}

	@Override
	public List<List<String>> toExport(List<FreightOrderBox> freightOrderBoxs) {
		List<List<String>> values = new ArrayList<List<String>>();
		for(FreightOrderBox freightOrderBox : freightOrderBoxs){
			List<String> singleValue = new ArrayList<String>(0);
			singleValue.add(freightOrderBox.getFreightBox() == null ? "" : freightOrderBox.getFreightBox().getBoxNumber());
			singleValue.add(freightOrderBox.getFreightSeal() == null ? "" : freightOrderBox.getFreightSeal().getSealNumber());
			singleValue.add(freightOrderBox.getLocation() == null ? "" : freightOrderBox.getLocation());
			values.add(singleValue);
		}
		return values;
	}

	/*@Override
	public Map<String, String> chooseFreightBox(String[] freightBoxIds, String freightBoxRequireId) {
		//FIXME TODO
		Map<String, String> map = new HashMap<String, String>();
		FreightBoxRequire require = freightBoxRequireDao.getById(freightBoxRequireId);
		if(require.getStatus() != null && 
				(require.getStatus().equals("已选箱") || 
						require.getStatus().equals("已放箱")|| 
						require.getStatus().equals("已执行"))){
			map.put("message", "该要求已选定集装箱");
			map.put("url", "redirect:fre-box-require-list.do");
		}else{
			if(require.getBoxCount() != freightBoxIds.length){
				map.put("message", "所选择的集装箱数量与要求的不一致,请重新操作");
				map.put("url", "redirect:fre-order-box-require-list.do");
			}else{
				for(String freightBoxId : freightBoxIds){
					FreightSeal filter = new FreightSeal();
					filter.setStatus("未使用");
					filter.setSealType("其他封");
					FreightSeal freightSeal = freightSealDao.getFreightSealProximate(filter);
					
					FreightBox freightBox = freightBoxDao.getById(freightBoxId);
					FreightOrderBox freightOrderBox = new FreightOrderBox();
					freightOrderBox.setFreightBox(freightBox);
					freightOrderBox.setFreightBoxRequire(require);
					freightOrderBox.setFreightSeal(freightSeal);
					freightOrderBoxDao.add(freightOrderBox);
					
					freightSeal.setStatus("已使用");
					freightSealDao.modify(freightSeal);
					
					freightBox.setStatus("已选箱");
					freightBoxDao.modify(freightBox);
				}
				require.setStatus("已选箱");
				freightBoxRequireDao.modify(require);
				map.put("message", "保存成功");
				map.put("url", "redirect:fre-box-require-list.do");
			}
		}
		
		return map;
	}*/
}

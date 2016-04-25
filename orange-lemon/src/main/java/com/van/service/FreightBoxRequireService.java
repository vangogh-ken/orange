package com.van.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightBoxRequire;

public interface FreightBoxRequireService {
	public List<FreightBoxRequire> getAll();

	public List<FreightBoxRequire> queryForList(
			FreightBoxRequire freightBoxRequire);

	public FreightBoxRequire queryForOne(FreightBoxRequire freightBoxRequire);

	public PageView<FreightBoxRequire> query(PageView<FreightBoxRequire> pageView, FreightBoxRequire freightBoxRequire);

	public void add(FreightBoxRequire freightBoxRequire);

	public void delete(String id);

	public void modify(FreightBoxRequire freightBoxRequire);

	public FreightBoxRequire getById(String id);

	/**
	 * 根据箱需ID放箱，将与箱需关联的箱子状态改变，并配上封号
	 * 注意：需要修改四个对象的数据， 箱需，箱封，封号，集装箱
	 * @param freightBoxRequireId
	 */
	//public boolean releaseFreightBox(String freightBoxRequireId);
	/**
	 * 放箱不配封
	 * @param freightBoxRequireId
	 * @return
	 */
	//public boolean releaseFreightBoxOnly(String freightBoxRequireId);
	
	/**
	 * 根据箱需ID选箱，选箱时不用配封号，在放箱时再配封号
	 * @param freightBoxIds
	 * @param freightBoxRequireId
	 * @return
	 */
	//public boolean chooseFreightBox(String[] freightBoxIds, String freightBoxRequireId);
	/**
	 * 添加箱需，同时新增箱封
	 * @param freightBoxRequire
	 * @param freightOrderId
	 */
	public boolean doneAddRequire(FreightBoxRequire freightBoxRequire, String freightOrderId, String freightBoxRequireId);
	/**
	 * 放箱，操作发送放箱给场站
	 * @param freightBoxRequireId
	 * @param request 保存提单信息到备注中
	 * @return
	 */
	public boolean doneReleaseRequire(String[] freightBoxRequireIds, HttpServletRequest request);
	/**
	 * 删除箱需，同时也删除箱封
	 * @param freightBoxRequireIds
	 * @return
	 */
	public boolean doneRemoveRequire(String[] freightBoxRequireIds);

	/**
	 * 退回修改，状态变为未提交
	 * @param freightBoxRequireIds
	 * @return
	 */
	public boolean toBackRequire(String[] freightBoxRequireIds);

	/**
	 * 修改集装箱来源
	 * @param freightBoxRequireId
	 * @return
	 */
	public Map<String, Object> toReviseSource(String freightBoxRequireId);

	/**
	 * 完成修改集装箱来源
	 * @param freightBoxRequireId
	 * @param boxSource
	 * @return
	 */
	public boolean doneReviseSource(String freightBoxRequireId, String boxSource);

	/**
	 * 选箱配封，返回该箱需对应的箱封信息
	 * @param freightBoxRequireId
	 * @return
	 */
	public Map<String, Object> toChooseBoxseal(String freightBoxRequireId);
	/**
	 * 完成箱需
	 * @param boxNumbers 箱号
	 * @param sealNumbers 封号
	 * @param descns 备注(提箱地址)
	 * @return
	 */
	public Map<String, Object> doneChooseBoxseal(String freightBoxRequireId, String[] boxNumbers, String[] sealNumbers, String[] descns);

	/**
	 * 确定放箱
	 * @param freightBoxRequireId
	 * @return
	 */
	public boolean doneReleaseBox(String[] freightBoxRequireIds);

	/**
	 * 取消放箱，已选的集装箱删除，封号删除，箱需状态为未提交，箱封状态为未提交
	 * @param freightBoxRequireIds
	 * @return
	 */
	public boolean doneRecallBox(String[] freightBoxRequireIds);
	/**
	 * 批量导入箱号封号
	 * @param freightBoxRequireId
	 * @param values
	 * @return
	 */
	public Map<String, Object> doneImportBoxseal(String freightBoxRequireId, List<List<String>> values);

	
}

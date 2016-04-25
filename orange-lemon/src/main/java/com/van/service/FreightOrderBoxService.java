package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.FreightOrderBox;

public interface FreightOrderBoxService {
	public List<FreightOrderBox> getAll();

	public List<FreightOrderBox> queryForList(FreightOrderBox freightOrderBox);

	public PageView query(PageView pageView, FreightOrderBox freightOrderBox);

	public void add(FreightOrderBox freightOrderBox);

	public void delete(String id);

	public void modify(FreightOrderBox freightOrderBox);

	public FreightOrderBox getById(String id);

	public List<FreightOrderBox> getByFreightOrderId(String freightOrderId);
	/**
	 * 修改箱封的封号，比较原封号和新封号
	 * @param freightOrderBox
	 * @param freightSealId
	 */
	public void modifySeal(FreightOrderBox freightOrderBox, String freightSealId);
	/**
	 * 修改箱号
	 * @param freightOrderBoxId
	 */
	public Map<String, Object> toReviseBox(String freightOrderBoxId);
	/**
	 * 完成修改箱号
	 * @param freightOrderBox
	 * @param freightBoxId
	 */
	public boolean doneReviseBox(FreightOrderBox freightOrderBox, String freightBoxId);

	/**
	 * 导出箱封数据
	 * @param freightOrderBoxs
	 * @return
	 */
	public List<List<String>> toExport(List<FreightOrderBox> freightOrderBoxs);
	
}

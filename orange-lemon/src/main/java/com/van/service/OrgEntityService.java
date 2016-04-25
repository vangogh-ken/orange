package com.van.service;

import java.util.List;
import java.util.Map;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.OrgEntity;
import com.van.halley.db.persistence.entity.Position;

public interface OrgEntityService {
	public List<OrgEntity> getAll();

	public List<OrgEntity> queryForList(OrgEntity orgEntity);

	public PageView query(PageView pageView, OrgEntity orgEntity);

	public void add(OrgEntity orgEntity);

	public void delete(String id);

	public void modify(OrgEntity orgEntity);

	public OrgEntity getById(String id);
	/**
	 * 添加负责人
	 * @param orgEntityId
	 * @return
	 */
	public Map<String, Object> toAddGaffer(String orgEntityId);
	/**
	 * 完成添加
	 * @param orgEntityId
	 * @param userId
	 */
	public void doneAddGaffer(String orgEntityId, String userId);
	/**
	 * 删除负责人
	 * @param orgEntityId
	 */
	public void doneRemoveGaffer(String orgEntityId);
	/**
	 * 添加职位
	 * @param orgEntityId
	 * @return
	 */
	public Map<String, Object> toAddPosition(String orgEntityId);
	/**
	 * 完成添加
	 * @param orgEntityId
	 * @param userId
	 */
	public void doneAddPosition(String orgEntityId, Position position);
	/**
	 * 删除职位
	 * @param orgEntityId
	 */
	public void doneRemovePosition(String[] positionIds);
	/**
	 * 修改职位
	 * @param positionId
	 * @return
	 */
	public Map<String, Object> toRevisePosition(String positionId);
}

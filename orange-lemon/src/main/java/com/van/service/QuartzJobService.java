package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.QuartzJob;

public interface QuartzJobService {
	public List<QuartzJob> getAll();

	public List<QuartzJob> queryForList(QuartzJob quartzJob);

	public QuartzJob queryForOne(QuartzJob quartzJob);

	public PageView<QuartzJob> query(PageView<QuartzJob> pageView, QuartzJob quartzJob);

	public void add(QuartzJob quartzJob);
	/**
	 * @param id
	 */
	public void delete(String id);

	public void modify(QuartzJob quartzJob);

	public QuartzJob getById(String id);
	/**
	 * 触发某个任务
	 * @param quartzJob
	 */
	public void trigger(QuartzJob quartzJob);
	/**
	 * 触发所有任务
	 */
	public void triggerCurrent();
	/**
	 * 激活任务
	 * @param byId
	 */
	public void fire(QuartzJob byId);
	/**
	 * 启动
	 * @param quartzJob
	 */
	public void start(QuartzJob quartzJob);
	/**
	 * 停止
	 * @param quartzJob
	 */
	public void stop(QuartzJob quartzJob);
	/**
	 * 暂停
	 * @param quartzJob
	 */
	public void pause(QuartzJob quartzJob);
	/**
	 * 恢复
	 * @param quartzJob
	 */
	public void resume(QuartzJob quartzJob);
	/**
	 * 删除
	 * @param quartzJob
	
	public void delete(QuartzJob quartzJob);
	 */
	/**
	 * 立即执行
	 * @param quartzJob
	 */
	public void runNow(QuartzJob quartzJob);
	/**
	 * 重设表达式
	 * @param quartzJob
	 */
	public void setCron(QuartzJob quartzJob);
	/**
	 * 获取运行中的
	 * @return
	 */
	public List<QuartzJob> getRunning();
	/**
	 * 获取暂停中的
	 * @return
	 */
	public List<QuartzJob> getpaused();

	
}

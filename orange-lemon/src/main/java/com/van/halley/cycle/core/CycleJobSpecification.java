package com.van.halley.cycle.core;

import java.util.Date;

public interface CycleJobSpecification {
	/**
	 * 被调度的次数
	 * @return
	 */
	public int getScheduleCount();

	public void setScheduleCount(int scheduleCount);

	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateTime();

	public void setCreateTime(Date createTime);
	
	/**
	 * 第一次调度时间
	 * @return
	 */
	public Date getFirstScheduleTime();

	public void setFirstScheduleTime(Date firstScheduleTime);
	
	/**
	 * 最近一次调度时间
	 * @return
	 */
	public Date getLastScheduleTime();

	public void setLastScheduleTime(Date lastScheduleTime);

	/**
	 * 更新时间
	 * @return
	 */
	public Date getUpdateTime();

	public void setUpdateTime(Date updateTime);
	
	
}

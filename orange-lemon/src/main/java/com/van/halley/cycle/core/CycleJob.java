package com.van.halley.cycle.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.van.halley.cycle.strategy.CycleStrategy;

public abstract class CycleJob implements CycleJobSpecification, Callable<CycleJobStatus> {
	private int scheduleCount = 0;
	private Date createTime = new Date();
	private Date firstScheduleTime;
	private Date lastScheduleTime;
	private Date updateTime;
	/**
	 * 调度策略
	 */
	private List<CycleStrategy> cycleStrategies = new ArrayList<CycleStrategy>();
	
	@Override
	public CycleJobStatus call() throws Exception {
		if(this.getFirstScheduleTime() == null){
			this.setFirstScheduleTime(new Date());
		}
		
		if(this.getLastScheduleTime() == null){
			this.setLastScheduleTime(new Date());
			return execute();
		}
		
		if(cycleStrategies != null && !cycleStrategies.isEmpty()){
			for(int i = cycleStrategies.size(); i-- > 0;){//FIXME
				CycleStrategy cycleStrategy = cycleStrategies.get(i);
				CycleJobStatus cycleJobStatus = cycleStrategy.strategy(this);
				if(cycleJobStatus == CycleJobStatus.CONINUE){
					continue;
				}else if(cycleJobStatus == CycleJobStatus.ONCE){
					execute();
					return CycleJobStatus.SUCCESS;
				}else if(cycleJobStatus == CycleJobStatus.SKIP){
					return CycleJobStatus.WAITING;
				}else if(cycleJobStatus == CycleJobStatus.GIVEUP){
					return CycleJobStatus.FAILED;
				}
			}
		}
		this.setLastScheduleTime(new Date());
		return execute();
	}

	/**
	 * @return
	 */
	public abstract CycleJobStatus execute();
	
	public CycleJob withStrategy(CycleStrategy cycleStrategy){
		this.cycleStrategies.add(cycleStrategy);
		return this;
	} 

	public int getScheduleCount() {
		return scheduleCount;
	}

	public void setScheduleCount(int scheduleCount) {
		this.scheduleCount = scheduleCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getLastScheduleTime() {
		return lastScheduleTime;
	}

	public void setLastScheduleTime(Date lastScheduleTime) {
		this.lastScheduleTime = lastScheduleTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "scheduleCount: " + this.scheduleCount 
				+ " createTime: " + CycleUtils.format(this.createTime)
				+ " lastScheduleTime: " + (this.lastScheduleTime == null ? "" : CycleUtils.format(this.lastScheduleTime))
				+ " updateTime: " + (this.updateTime == null ? "" : CycleUtils.format(this.updateTime));
	}

	public Date getFirstScheduleTime() {
		return firstScheduleTime;
	}

	public void setFirstScheduleTime(Date firstScheduleTime) {
		this.firstScheduleTime = firstScheduleTime;
	}

	/*public List<CycleStrategy> getCycleStrategies() {
		return cycleStrategies;
	}

	public void setCycleStrategies(List<CycleStrategy> cycleStrategies) {
		this.cycleStrategies = cycleStrategies;
	}*/

	
	
	/*public List<CycleStrategy> getCycleStrategys() {
		return cycleStrategys;
	}

	public void setCycleStrategys(List<CycleStrategy> cycleStrategys) {
		this.cycleStrategys = cycleStrategys;
	}*/
	
	
}



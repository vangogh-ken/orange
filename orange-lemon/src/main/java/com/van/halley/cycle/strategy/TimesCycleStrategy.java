package com.van.halley.cycle.strategy;

import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;

/**
 * 根据调度次数决定是否还进行调度
 * @author Administrator
 *
 */
public class TimesCycleStrategy implements CycleStrategy {
	private int times = 10;
	@Override
	public CycleJobStatus strategy(CycleJob cycleJob) {
		if(cycleJob.getScheduleCount() > times){
			return CycleJobStatus.ONCE;
		}else{
			return CycleJobStatus.CONINUE;
		}
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}

}

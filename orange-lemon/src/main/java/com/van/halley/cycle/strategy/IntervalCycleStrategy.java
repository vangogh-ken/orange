package com.van.halley.cycle.strategy;

import java.util.Date;

import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;

/**
 * 上次调度时间与当前时间的间隔
 * @author Administrator
 *
 */
public class IntervalCycleStrategy implements CycleStrategy {
	private int intervalSecond = 1;
	
	public IntervalCycleStrategy (){
		
	}
	public IntervalCycleStrategy(int intervalSecond){
		this.intervalSecond = intervalSecond;
	}
	@Override
	public CycleJobStatus strategy(CycleJob cycleJob) {
		if(new Date().getTime() - cycleJob.getLastScheduleTime().getTime() > intervalSecond * 1000){
			return CycleJobStatus.CONINUE;
		}else{
			return CycleJobStatus.SKIP;
		}
	}
	public int getIntervalSecond() {
		return intervalSecond;
	}
	public void setIntervalSecond(int intervalSecond) {
		this.intervalSecond = intervalSecond;
	}

	
	
}

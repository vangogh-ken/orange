package com.van.halley.cycle.strategy;

import java.util.Date;

import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;

public class TimeOutCycleStrategy implements CycleStrategy {
	private int timeOutSecend = 30;
	
	@Override
	public CycleJobStatus strategy(CycleJob cycleJob) {
		if(cycleJob.getFirstScheduleTime().getTime() - new Date().getTime() > timeOutSecend * 1000){
			return CycleJobStatus.ONCE;
		}else{
			return CycleJobStatus.CONINUE;
		}
	}

	public int getTimeOutSecend() {
		return timeOutSecend;
	}

	public void setTimeOutSecend(int timeOutSecend) {
		this.timeOutSecend = timeOutSecend;
	}

	
}

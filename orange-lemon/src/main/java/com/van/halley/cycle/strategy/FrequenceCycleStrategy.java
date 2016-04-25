package com.van.halley.cycle.strategy;

import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;

/**
 * 按照调度频率进行处理，即每调度多少次才执行
 * @author Administrator
 *
 */
public class FrequenceCycleStrategy implements CycleStrategy {
	private int frequence = 1;
	
	@Override
	public CycleJobStatus strategy(CycleJob cycleJob) {
		if(cycleJob.getScheduleCount() % frequence == 0){
			return CycleJobStatus.CONINUE;
		}else{
			return CycleJobStatus.SKIP;
		}
	}
	
	public int getFrequence() {
		return frequence;
	}
	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

}

package com.van.halley.cycle.strategy;

import com.van.halley.cycle.core.CycleJob;
import com.van.halley.cycle.core.CycleJobStatus;

public interface CycleStrategy {
	public CycleJobStatus strategy(CycleJob cycleJob);
}

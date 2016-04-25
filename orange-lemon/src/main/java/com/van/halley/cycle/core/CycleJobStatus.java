package com.van.halley.cycle.core;

public enum CycleJobStatus{
	SUCCESS, FAILED, WAITING, //执行结果状态
	ONCE, GIVEUP, SKIP, CONINUE //控制策略状态
}
package com.van.halley.cycle.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class CycleStore {
	private BlockingQueue<CycleJob> queue = new LinkedBlockingQueue<CycleJob>();

	@PostConstruct
	public void start() {
	}

	@PreDestroy
	public void stop() {
	}

	public CycleJob take() throws InterruptedException {
		return queue.take();
	}

	public void put(CycleJob cycleJob) {
		try {
			queue.put(cycleJob);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public BlockingQueue<CycleJob> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<CycleJob> queue) {
		this.queue = queue;
	}
	
	
}

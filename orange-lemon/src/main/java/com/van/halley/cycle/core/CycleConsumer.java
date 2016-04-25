package com.van.halley.cycle.core;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CycleConsumer implements Runnable {
	private static Logger LOG = LoggerFactory.getLogger(CycleConsumer.class);
	private CycleStore cycleStore;
	
	private boolean active;
	private Thread thread;
	private ExecutorService executorService;
	/**
	 * 达到的job数之后就等待1s
	 */
	private int thresholdJobCount = 10;
	/**
	 * 线程数
	 */
	private int consumerThreadCount = 10;
	
	
	public CycleConsumer(){
		
	}
	
	public CycleConsumer(int consumerThreadCount, int thresholdJobCount){
		this.consumerThreadCount = consumerThreadCount;;
		this.thresholdJobCount = thresholdJobCount;
	}

	public void start() {
		active = true;
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
		executorService = Executors.newFixedThreadPool(consumerThreadCount);
	}

	public void stop() {
		active = false;
		thread = null;
		executorService.shutdown();
	}

	@Override
	public void run() {
		while (active) {
			for (int i = 0; i < thresholdJobCount; i++) {
				doConsume();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				LOG.info(ex.getMessage(), ex);
			}
		}
	}

	public void doConsume() {
		try {
			CycleJob cycleJob = cycleStore.take();
			executorService.execute(new Result(cycleStore, cycleJob));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class Result implements Runnable{
		private CycleStore cycleStore;
		private CycleJob cycleJob;
		
		public Result(CycleStore cycleStore, CycleJob cycleJob){
			this.cycleStore = cycleStore;
			this.cycleJob = cycleJob;
		}
		
		@Override
		public void run() {
			try {
				if (cycleJob.call() == CycleJobStatus.WAITING) {
					cycleJob.setScheduleCount(cycleJob.getScheduleCount() + 1);
					cycleJob.setUpdateTime(new Date());
					cycleStore.put(cycleJob);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	//getter setter
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public CycleStore getCycleStore() {
		return cycleStore;
	}

	public void setCycleStore(CycleStore cycleStore) {
		this.cycleStore = cycleStore;
	}

	public int getThresholdJobCount() {
		return thresholdJobCount;
	}

	public void setThresholdJobCount(int thresholdJobCount) {
		this.thresholdJobCount = thresholdJobCount;
	}

	public int getConsumerThreadCount() {
		return consumerThreadCount;
	}

	public void setConsumerThreadCount(int consumerThreadCount) {
		this.consumerThreadCount = consumerThreadCount;
	}
	
	

}


